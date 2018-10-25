// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SynonymFilter.java

package org.apache.lucene.analysis.synonym;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.store.ByteArrayDataInput;
import org.apache.lucene.util.*;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.Outputs;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			SynonymMap

public final class SynonymFilter extends TokenFilter
{
	private static class PendingOutputs
	{

		CharsRef outputs[];
		int endOffsets[];
		int posLengths[];
		int upto;
		int count;
		int posIncr;
		int lastEndOffset;
		int lastPosLength;
		static final boolean $assertionsDisabled = !org/apache/lucene/analysis/synonym/SynonymFilter.desiredAssertionStatus();

		public void reset()
		{
			upto = count = 0;
			posIncr = 1;
		}

		public CharsRef pullNext()
		{
			if (!$assertionsDisabled && upto >= count)
				throw new AssertionError();
			lastEndOffset = endOffsets[upto];
			lastPosLength = posLengths[upto];
			CharsRef result = outputs[upto++];
			posIncr = 0;
			if (upto == count)
				reset();
			return result;
		}

		public int getLastEndOffset()
		{
			return lastEndOffset;
		}

		public int getLastPosLength()
		{
			return lastPosLength;
		}

		public void add(char output[], int offset, int len, int endOffset, int posLength)
		{
			if (count == outputs.length)
			{
				CharsRef next[] = new CharsRef[ArrayUtil.oversize(1 + count, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
				System.arraycopy(outputs, 0, next, 0, count);
				outputs = next;
			}
			if (count == endOffsets.length)
			{
				int next[] = new int[ArrayUtil.oversize(1 + count, 4)];
				System.arraycopy(endOffsets, 0, next, 0, count);
				endOffsets = next;
			}
			if (count == posLengths.length)
			{
				int next[] = new int[ArrayUtil.oversize(1 + count, 4)];
				System.arraycopy(posLengths, 0, next, 0, count);
				posLengths = next;
			}
			if (outputs[count] == null)
				outputs[count] = new CharsRef();
			outputs[count].copyChars(output, offset, len);
			endOffsets[count] = endOffset;
			posLengths[count] = posLength;
			count++;
		}


		public PendingOutputs()
		{
			posIncr = 1;
			outputs = new CharsRef[1];
			endOffsets = new int[1];
			posLengths = new int[1];
		}
	}

	private static class PendingInput
	{

		final CharsRef term;
		org.apache.lucene.util.AttributeSource.State state;
		boolean keepOrig;
		boolean matched;
		boolean consumed;
		int startOffset;
		int endOffset;

		public void reset()
		{
			state = null;
			consumed = true;
			keepOrig = false;
			matched = false;
		}

		private PendingInput()
		{
			term = new CharsRef();
			consumed = true;
		}

	}


	public static final String TYPE_SYNONYM = "SYNONYM";
	private final SynonymMap synonyms;
	private final boolean ignoreCase;
	private final int rollBufferSize;
	private int captureCount;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final PositionIncrementAttribute posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
	private final PositionLengthAttribute posLenAtt = (PositionLengthAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionLengthAttribute);
	private final TypeAttribute typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private int inputSkipCount;
	private final PendingInput futureInputs[];
	private final ByteArrayDataInput bytesReader = new ByteArrayDataInput();
	private final PendingOutputs futureOutputs[];
	private int nextWrite;
	private int nextRead;
	private boolean finished;
	private final org.apache.lucene.util.fst.FST.Arc scratchArc = new org.apache.lucene.util.fst.FST.Arc();
	private final FST fst;
	private final org.apache.lucene.util.fst.FST.BytesReader fstReader;
	private final BytesRef scratchBytes = new BytesRef();
	private final CharsRef scratchChars = new CharsRef();
	private int lastStartOffset;
	private int lastEndOffset;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/synonym/SynonymFilter.desiredAssertionStatus();

	public SynonymFilter(TokenStream input, SynonymMap synonyms, boolean ignoreCase)
	{
		super(input);
		this.synonyms = synonyms;
		this.ignoreCase = ignoreCase;
		fst = synonyms.fst;
		fstReader = fst.getBytesReader(0);
		if (fst == null)
			throw new IllegalArgumentException("fst must be non-null");
		rollBufferSize = 1 + synonyms.maxHorizontalContext;
		futureInputs = new PendingInput[rollBufferSize];
		futureOutputs = new PendingOutputs[rollBufferSize];
		for (int pos = 0; pos < rollBufferSize; pos++)
		{
			futureInputs[pos] = new PendingInput();
			futureOutputs[pos] = new PendingOutputs();
		}

	}

	private void capture()
	{
		captureCount++;
		PendingInput input = futureInputs[nextWrite];
		input.state = captureState();
		input.consumed = false;
		input.term.copyChars(termAtt.buffer(), 0, termAtt.length());
		nextWrite = rollIncr(nextWrite);
		if (!$assertionsDisabled && nextWrite == nextRead)
			throw new AssertionError();
		else
			return;
	}

	private void parse()
		throws IOException
	{
		if (!$assertionsDisabled && inputSkipCount != 0)
			throw new AssertionError();
		int curNextRead = nextRead;
		BytesRef matchOutput = null;
		int matchInputLength = 0;
		int matchEndOffset = -1;
		BytesRef pendingOutput = (BytesRef)fst.outputs.getNoOutput();
		fst.getFirstArc(scratchArc);
		if (!$assertionsDisabled && scratchArc.output != fst.outputs.getNoOutput())
			throw new AssertionError();
		int tokenCount = 0;
label0:
		do
		{
			int inputEndOffset = 0;
			char buffer[];
			int bufferLen;
			if (curNextRead == nextWrite)
			{
				if (finished)
					break;
				if (!$assertionsDisabled && !futureInputs[nextWrite].consumed)
					throw new AssertionError();
				if (this.input.incrementToken())
				{
					buffer = termAtt.buffer();
					bufferLen = termAtt.length();
					PendingInput input = futureInputs[nextWrite];
					lastStartOffset = input.startOffset = offsetAtt.startOffset();
					lastEndOffset = input.endOffset = offsetAtt.endOffset();
					inputEndOffset = input.endOffset;
					if (nextRead != nextWrite)
						capture();
					else
						input.consumed = false;
				} else
				{
					finished = true;
					break;
				}
			} else
			{
				buffer = futureInputs[curNextRead].term.chars;
				bufferLen = futureInputs[curNextRead].term.length;
				inputEndOffset = futureInputs[curNextRead].endOffset;
			}
			tokenCount++;
			int codePoint;
			for (int bufUpto = 0; bufUpto < bufferLen; bufUpto += Character.charCount(codePoint))
			{
				codePoint = Character.codePointAt(buffer, bufUpto, bufferLen);
				if (fst.findTargetArc(ignoreCase ? Character.toLowerCase(codePoint) : codePoint, scratchArc, scratchArc, fstReader) == null)
					break label0;
				pendingOutput = (BytesRef)fst.outputs.add(pendingOutput, scratchArc.output);
			}

			if (scratchArc.isFinal())
			{
				matchOutput = (BytesRef)fst.outputs.add(pendingOutput, scratchArc.nextFinalOutput);
				matchInputLength = tokenCount;
				matchEndOffset = inputEndOffset;
			}
			if (fst.findTargetArc(0, scratchArc, scratchArc, fstReader) == null)
				break;
			pendingOutput = (BytesRef)fst.outputs.add(pendingOutput, scratchArc.output);
			if (nextRead == nextWrite)
				capture();
			curNextRead = rollIncr(curNextRead);
		} while (true);
		if (nextRead == nextWrite && !finished)
			nextWrite = rollIncr(nextWrite);
		if (matchOutput != null)
		{
			inputSkipCount = matchInputLength;
			addOutput(matchOutput, matchInputLength, matchEndOffset);
		} else
		if (nextRead != nextWrite)
			inputSkipCount = 1;
		else
		if (!$assertionsDisabled && !finished)
			throw new AssertionError();
	}

	private void addOutput(BytesRef bytes, int matchInputLength, int matchEndOffset)
	{
		bytesReader.reset(bytes.bytes, bytes.offset, bytes.length);
		int code = bytesReader.readVInt();
		boolean keepOrig = (code & 1) == 0;
		int count = code >>> 1;
		for (int outputIDX = 0; outputIDX < count; outputIDX++)
		{
			synonyms.words.get(bytesReader.readVInt(), scratchBytes);
			UnicodeUtil.UTF8toUTF16(scratchBytes, scratchChars);
			int lastStart = scratchChars.offset;
			int chEnd = lastStart + scratchChars.length;
			int outputUpto = nextRead;
			for (int chIDX = lastStart; chIDX <= chEnd; chIDX++)
			{
				if (chIDX != chEnd && scratchChars.chars[chIDX] != 0)
					continue;
				int outputLen = chIDX - lastStart;
				if (!$assertionsDisabled && outputLen <= 0)
					throw new AssertionError((new StringBuilder()).append("output contains empty string: ").append(scratchChars).toString());
				int endOffset;
				int posLen;
				if (chIDX == chEnd && lastStart == scratchChars.offset)
				{
					endOffset = matchEndOffset;
					posLen = keepOrig ? matchInputLength : 1;
				} else
				{
					endOffset = -1;
					posLen = 1;
				}
				futureOutputs[outputUpto].add(scratchChars.chars, lastStart, outputLen, endOffset, posLen);
				lastStart = 1 + chIDX;
				outputUpto = rollIncr(outputUpto);
				if (!$assertionsDisabled && futureOutputs[outputUpto].posIncr != 1)
					throw new AssertionError((new StringBuilder()).append("outputUpto=").append(outputUpto).append(" vs nextWrite=").append(nextWrite).toString());
			}

		}

		int upto = nextRead;
		for (int idx = 0; idx < matchInputLength; idx++)
		{
			futureInputs[upto].keepOrig |= keepOrig;
			futureInputs[upto].matched = true;
			upto = rollIncr(upto);
		}

	}

	private int rollIncr(int count)
	{
		if (++count == rollBufferSize)
			return 0;
		else
			return count;
	}

	int getCaptureCount()
	{
		return captureCount;
	}

	public boolean incrementToken()
		throws IOException
	{
		do
		{
			for (; inputSkipCount != 0; inputSkipCount--)
			{
				PendingInput input = futureInputs[nextRead];
				PendingOutputs outputs = futureOutputs[nextRead];
				if (!input.consumed && (input.keepOrig || !input.matched))
				{
					if (input.state != null)
						restoreState(input.state);
					else
					if (!$assertionsDisabled && inputSkipCount != 1)
						throw new AssertionError((new StringBuilder()).append("inputSkipCount=").append(inputSkipCount).append(" nextRead=").append(nextRead).toString());
					input.reset();
					if (outputs.count > 0)
					{
						outputs.posIncr = 0;
					} else
					{
						nextRead = rollIncr(nextRead);
						inputSkipCount--;
					}
					return true;
				}
				if (outputs.upto < outputs.count)
				{
					input.reset();
					int posIncr = outputs.posIncr;
					CharsRef output = outputs.pullNext();
					clearAttributes();
					termAtt.copyBuffer(output.chars, output.offset, output.length);
					typeAtt.setType("SYNONYM");
					int endOffset = outputs.getLastEndOffset();
					if (endOffset == -1)
						endOffset = input.endOffset;
					offsetAtt.setOffset(input.startOffset, endOffset);
					posIncrAtt.setPositionIncrement(posIncr);
					posLenAtt.setPositionLength(outputs.getLastPosLength());
					if (outputs.count == 0)
					{
						nextRead = rollIncr(nextRead);
						inputSkipCount--;
					}
					return true;
				}
				input.reset();
				nextRead = rollIncr(nextRead);
			}

			if (finished && nextRead == nextWrite)
			{
				PendingOutputs outputs = futureOutputs[nextRead];
				if (outputs.upto < outputs.count)
				{
					int posIncr = outputs.posIncr;
					CharsRef output = outputs.pullNext();
					futureInputs[nextRead].reset();
					if (outputs.count == 0)
						nextWrite = nextRead = rollIncr(nextRead);
					clearAttributes();
					offsetAtt.setOffset(lastStartOffset, lastEndOffset);
					termAtt.copyBuffer(output.chars, output.offset, output.length);
					typeAtt.setType("SYNONYM");
					posIncrAtt.setPositionIncrement(posIncr);
					return true;
				} else
				{
					return false;
				}
			}
			parse();
		} while (true);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		captureCount = 0;
		finished = false;
		inputSkipCount = 0;
		nextRead = nextWrite = 0;
		PendingInput arr$[] = futureInputs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			PendingInput input = arr$[i$];
			input.reset();
		}

		arr$ = futureOutputs;
		len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			PendingOutputs output = arr$[i$];
			output.reset();
		}

	}

}
