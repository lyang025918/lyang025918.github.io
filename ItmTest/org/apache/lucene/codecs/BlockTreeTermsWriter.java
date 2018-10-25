// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockTreeTermsWriter.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.fst.*;

// Referenced classes of package org.apache.lucene.codecs:
//			FieldsConsumer, PostingsWriterBase, CodecUtil, TermsConsumer, 
//			TermStats, PostingsConsumer

public class BlockTreeTermsWriter extends FieldsConsumer
{
	class TermsWriter extends TermsConsumer
	{
		private class FindBlocks extends org.apache.lucene.util.fst.Builder.FreezeTail
		{

			final TermsWriter this$1;

			public void freeze(org.apache.lucene.util.fst.Builder.UnCompiledNode frontier[], int prefixLenPlus1, IntsRef lastInput)
				throws IOException
			{
				for (int idx = lastInput.length; idx >= prefixLenPlus1; idx--)
				{
					org.apache.lucene.util.fst.Builder.UnCompiledNode node = frontier[idx];
					long totCount = 0L;
					if (node.isFinal)
						totCount++;
					for (int arcIdx = 0; arcIdx < node.numArcs; arcIdx++)
					{
						org.apache.lucene.util.fst.Builder.UnCompiledNode target = (org.apache.lucene.util.fst.Builder.UnCompiledNode)node.arcs[arcIdx].target;
						totCount += target.inputCount;
						target.clear();
						node.arcs[arcIdx].target = null;
					}

					node.numArcs = 0;
					if (totCount >= (long)minItemsInBlock || idx == 0)
					{
						writeBlocks(lastInput, idx, (int)totCount);
						node.inputCount = 1L;
					} else
					{
						node.inputCount = totCount;
					}
					frontier[idx] = new org.apache.lucene.util.fst.Builder.UnCompiledNode(blockBuilder, idx);
				}

			}

			private FindBlocks()
			{
				this$1 = TermsWriter.this;
				super();
			}

		}


		private final FieldInfo fieldInfo;
		private long numTerms;
		long sumTotalTermFreq;
		long sumDocFreq;
		int docCount;
		long indexStartFP;
		private final NoOutputs noOutputs = NoOutputs.getSingleton();
		private final Builder blockBuilder;
		private final List pending = new ArrayList();
		private int lastBlockIndex;
		private int subBytes[];
		private int subTermCounts[];
		private int subTermCountSums[];
		private int subSubCounts[];
		private final IntsRef scratchIntsRef = new IntsRef();
		private final RAMOutputStream bytesWriter = new RAMOutputStream();
		private final RAMOutputStream bytesWriter2 = new RAMOutputStream();
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsWriter.desiredAssertionStatus();
		final BlockTreeTermsWriter this$0;

		void writeBlocks(IntsRef prevTerm, int prefixLength, int count)
			throws IOException
		{
			if (prefixLength == 0 || count <= maxItemsInBlock)
			{
				PendingBlock nonFloorBlock = writeBlock(prevTerm, prefixLength, prefixLength, count, count, 0, false, -1, true);
				nonFloorBlock.compileIndex(null, scratchBytes);
				pending.add(nonFloorBlock);
			} else
			{
				int savLabel = prevTerm.ints[prevTerm.offset + prefixLength];
				List slice = pending.subList(pending.size() - count, pending.size());
				int lastSuffixLeadLabel = -1;
				int termCount = 0;
				int subCount = 0;
				int numSubs = 0;
				for (Iterator i$ = slice.iterator(); i$.hasNext();)
				{
					PendingEntry ent = (PendingEntry)i$.next();
					int suffixLeadLabel;
					if (ent.isTerm)
					{
						PendingTerm term = (PendingTerm)ent;
						if (term.term.length == prefixLength)
						{
							if (!$assertionsDisabled && lastSuffixLeadLabel != -1)
								throw new AssertionError();
							if (!$assertionsDisabled && numSubs != 0)
								throw new AssertionError();
							suffixLeadLabel = -1;
						} else
						{
							suffixLeadLabel = term.term.bytes[term.term.offset + prefixLength] & 0xff;
						}
					} else
					{
						PendingBlock block = (PendingBlock)ent;
						if (!$assertionsDisabled && block.prefix.length <= prefixLength)
							throw new AssertionError();
						suffixLeadLabel = block.prefix.bytes[block.prefix.offset + prefixLength] & 0xff;
					}
					if (suffixLeadLabel != lastSuffixLeadLabel && termCount + subCount != 0)
					{
						if (subBytes.length == numSubs)
						{
							subBytes = ArrayUtil.grow(subBytes);
							subTermCounts = ArrayUtil.grow(subTermCounts);
							subSubCounts = ArrayUtil.grow(subSubCounts);
						}
						subBytes[numSubs] = lastSuffixLeadLabel;
						lastSuffixLeadLabel = suffixLeadLabel;
						subTermCounts[numSubs] = termCount;
						subSubCounts[numSubs] = subCount;
						termCount = subCount = 0;
						numSubs++;
					}
					if (ent.isTerm)
						termCount++;
					else
						subCount++;
				}

				if (subBytes.length == numSubs)
				{
					subBytes = ArrayUtil.grow(subBytes);
					subTermCounts = ArrayUtil.grow(subTermCounts);
					subSubCounts = ArrayUtil.grow(subSubCounts);
				}
				subBytes[numSubs] = lastSuffixLeadLabel;
				subTermCounts[numSubs] = termCount;
				subSubCounts[numSubs] = subCount;
				numSubs++;
				if (subTermCountSums.length < numSubs)
					subTermCountSums = ArrayUtil.grow(subTermCountSums, numSubs);
				int sum = 0;
				for (int idx = numSubs - 1; idx >= 0; idx--)
				{
					sum += subTermCounts[idx];
					subTermCountSums[idx] = sum;
				}

				int pendingCount = 0;
				int startLabel = subBytes[0];
				int curStart = count;
				subCount = 0;
				List floorBlocks = new ArrayList();
				PendingBlock firstBlock = null;
				for (int sub = 0; sub < numSubs; sub++)
				{
					pendingCount += subTermCounts[sub] + subSubCounts[sub];
					subCount++;
					if (pendingCount < minItemsInBlock)
						continue;
					int curPrefixLength;
					if (startLabel == -1)
					{
						curPrefixLength = prefixLength;
					} else
					{
						curPrefixLength = 1 + prefixLength;
						prevTerm.ints[prevTerm.offset + prefixLength] = startLabel;
					}
					PendingBlock floorBlock = writeBlock(prevTerm, prefixLength, curPrefixLength, curStart, pendingCount, subTermCountSums[1 + sub], true, startLabel, curStart == pendingCount);
					if (firstBlock == null)
						firstBlock = floorBlock;
					else
						floorBlocks.add(floorBlock);
					curStart -= pendingCount;
					pendingCount = 0;
					if (!$assertionsDisabled && minItemsInBlock != 1 && subCount <= 1)
						throw new AssertionError((new StringBuilder()).append("minItemsInBlock=").append(minItemsInBlock).append(" subCount=").append(subCount).append(" sub=").append(sub).append(" of ").append(numSubs).append(" subTermCount=").append(subTermCountSums[sub]).append(" subSubCount=").append(subSubCounts[sub]).append(" depth=").append(prefixLength).toString());
					subCount = 0;
					startLabel = subBytes[sub + 1];
					if (curStart == 0)
						break;
					if (curStart > maxItemsInBlock)
						continue;
					if (!$assertionsDisabled && startLabel == -1)
						throw new AssertionError();
					if (!$assertionsDisabled && firstBlock == null)
						throw new AssertionError();
					prevTerm.ints[prevTerm.offset + prefixLength] = startLabel;
					floorBlocks.add(writeBlock(prevTerm, prefixLength, prefixLength + 1, curStart, curStart, 0, true, startLabel, true));
					break;
				}

				prevTerm.ints[prevTerm.offset + prefixLength] = savLabel;
				if (!$assertionsDisabled && firstBlock == null)
					throw new AssertionError();
				firstBlock.compileIndex(floorBlocks, scratchBytes);
				pending.add(firstBlock);
			}
			lastBlockIndex = pending.size() - 1;
		}

		private String toString(BytesRef b)
		{
			return (new StringBuilder()).append(b.utf8ToString()).append(" ").append(b).toString();
			Throwable t;
			t;
			return b.toString();
		}

		private PendingBlock writeBlock(IntsRef prevTerm, int prefixLength, int indexPrefixLength, int startBackwards, int length, int futureTermCount, boolean isFloor, 
				int floorLeadByte, boolean isLastInFloor)
			throws IOException
		{
			if (!$assertionsDisabled && length <= 0)
				throw new AssertionError();
			int start = pending.size() - startBackwards;
			if (!$assertionsDisabled && start < 0)
				throw new AssertionError((new StringBuilder()).append("pending.size()=").append(pending.size()).append(" startBackwards=").append(startBackwards).append(" length=").append(length).toString());
			List slice = pending.subList(start, start + length);
			long startFP = out.getFilePointer();
			BytesRef prefix = new BytesRef(indexPrefixLength);
			for (int m = 0; m < indexPrefixLength; m++)
				prefix.bytes[m] = (byte)prevTerm.ints[m];

			prefix.length = indexPrefixLength;
			out.writeVInt(length << 1 | (isLastInFloor ? 1 : 0));
			boolean isLeafBlock;
			if (lastBlockIndex < start)
				isLeafBlock = true;
			else
			if (!isFloor)
			{
				isLeafBlock = false;
			} else
			{
				boolean v = true;
				Iterator i$ = slice.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					PendingEntry ent = (PendingEntry)i$.next();
					if (ent.isTerm)
						continue;
					v = false;
					break;
				} while (true);
				isLeafBlock = v;
			}
			List subIndices;
			int termCount;
			if (isLeafBlock)
			{
				subIndices = null;
				Iterator i$ = slice.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					PendingEntry ent = (PendingEntry)i$.next();
					if (!$assertionsDisabled && !ent.isTerm)
						throw new AssertionError();
					PendingTerm term = (PendingTerm)ent;
					int suffix = term.term.length - prefixLength;
					bytesWriter.writeVInt(suffix);
					bytesWriter.writeBytes(term.term.bytes, prefixLength, suffix);
					bytesWriter2.writeVInt(term.stats.docFreq);
					if (fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
					{
						if (!$assertionsDisabled && term.stats.totalTermFreq < (long)term.stats.docFreq)
							throw new AssertionError((new StringBuilder()).append(term.stats.totalTermFreq).append(" vs ").append(term.stats.docFreq).toString());
						bytesWriter2.writeVLong(term.stats.totalTermFreq - (long)term.stats.docFreq);
					}
				} while (true);
				termCount = length;
			} else
			{
				subIndices = new ArrayList();
				termCount = 0;
				for (Iterator i$ = slice.iterator(); i$.hasNext();)
				{
					PendingEntry ent = (PendingEntry)i$.next();
					if (ent.isTerm)
					{
						PendingTerm term = (PendingTerm)ent;
						int suffix = term.term.length - prefixLength;
						bytesWriter.writeVInt(suffix << 1);
						bytesWriter.writeBytes(term.term.bytes, prefixLength, suffix);
						bytesWriter2.writeVInt(term.stats.docFreq);
						if (fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
						{
							if (!$assertionsDisabled && term.stats.totalTermFreq < (long)term.stats.docFreq)
								throw new AssertionError();
							bytesWriter2.writeVLong(term.stats.totalTermFreq - (long)term.stats.docFreq);
						}
						termCount++;
					} else
					{
						PendingBlock block = (PendingBlock)ent;
						int suffix = block.prefix.length - prefixLength;
						if (!$assertionsDisabled && suffix <= 0)
							throw new AssertionError();
						bytesWriter.writeVInt(suffix << 1 | 1);
						bytesWriter.writeBytes(block.prefix.bytes, prefixLength, suffix);
						if (!$assertionsDisabled && block.fp >= startFP)
							throw new AssertionError();
						bytesWriter.writeVLong(startFP - block.fp);
						subIndices.add(block.index);
					}
				}

				if (!$assertionsDisabled && subIndices.size() == 0)
					throw new AssertionError();
			}
			out.writeVInt((int)(bytesWriter.getFilePointer() << 1) | (isLeafBlock ? 1 : 0));
			bytesWriter.writeTo(out);
			bytesWriter.reset();
			out.writeVInt((int)bytesWriter2.getFilePointer());
			bytesWriter2.writeTo(out);
			bytesWriter2.reset();
			postingsWriter.flushTermsBlock(futureTermCount + termCount, termCount);
			slice.clear();
			if (lastBlockIndex >= start)
				if (lastBlockIndex < start + length)
					lastBlockIndex = start;
				else
					lastBlockIndex -= length;
			return new PendingBlock(prefix, startFP, termCount != 0, isFloor, floorLeadByte, subIndices);
		}

		public Comparator getComparator()
		{
			return BytesRef.getUTF8SortedAsUnicodeComparator();
		}

		public PostingsConsumer startTerm(BytesRef text)
			throws IOException
		{
			postingsWriter.startTerm();
			return postingsWriter;
		}

		public void finishTerm(BytesRef text, TermStats stats)
			throws IOException
		{
			if (!$assertionsDisabled && stats.docFreq <= 0)
			{
				throw new AssertionError();
			} else
			{
				blockBuilder.add(Util.toIntsRef(text, scratchIntsRef), noOutputs.getNoOutput());
				pending.add(new PendingTerm(BytesRef.deepCopyOf(text), stats));
				postingsWriter.finishTerm(stats);
				numTerms++;
				return;
			}
		}

		public void finish(long sumTotalTermFreq, long sumDocFreq, int docCount)
			throws IOException
		{
			if (numTerms > 0L)
			{
				blockBuilder.finish();
				if (!$assertionsDisabled && (pending.size() != 1 || ((PendingEntry)pending.get(0)).isTerm))
					throw new AssertionError((new StringBuilder()).append("pending.size()=").append(pending.size()).append(" pending=").append(pending).toString());
				PendingBlock root = (PendingBlock)pending.get(0);
				if (!$assertionsDisabled && root.prefix.length != 0)
					throw new AssertionError();
				if (!$assertionsDisabled && root.index.getEmptyOutput() == null)
					throw new AssertionError();
				this.sumTotalTermFreq = sumTotalTermFreq;
				this.sumDocFreq = sumDocFreq;
				this.docCount = docCount;
				indexStartFP = indexOut.getFilePointer();
				root.index.save(indexOut);
			} else
			{
				if (!$assertionsDisabled && sumTotalTermFreq != 0L && (fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY || sumTotalTermFreq != -1L))
					throw new AssertionError();
				if (!$assertionsDisabled && sumDocFreq != 0L)
					throw new AssertionError();
				if (!$assertionsDisabled && docCount != 0)
					throw new AssertionError();
			}
		}






		TermsWriter(FieldInfo fieldInfo)
		{
			this$0 = BlockTreeTermsWriter.this;
			super();
			lastBlockIndex = -1;
			subBytes = new int[10];
			subTermCounts = new int[10];
			subTermCountSums = new int[10];
			subSubCounts = new int[10];
			this.fieldInfo = fieldInfo;
			blockBuilder = new Builder(org.apache.lucene.util.fst.FST.INPUT_TYPE.BYTE1, 0, 0, true, true, 0x7fffffff, noOutputs, new FindBlocks(), false);
			postingsWriter.setField(fieldInfo);
		}
	}

	private static final class PendingBlock extends PendingEntry
	{

		public final BytesRef prefix;
		public final long fp;
		public FST index;
		public List subIndices;
		public final boolean hasTerms;
		public final boolean isFloor;
		public final int floorLeadByte;
		private final IntsRef scratchIntsRef = new IntsRef();
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsWriter.desiredAssertionStatus();

		public String toString()
		{
			return (new StringBuilder()).append("BLOCK: ").append(prefix.utf8ToString()).toString();
		}

		public void compileIndex(List floorBlocks, RAMOutputStream scratchBytes)
			throws IOException
		{
			if (!$assertionsDisabled && (!isFloor || floorBlocks == null || floorBlocks.size() == 0) && (isFloor || floorBlocks != null))
				throw new AssertionError((new StringBuilder()).append("isFloor=").append(isFloor).append(" floorBlocks=").append(floorBlocks).toString());
			if (!$assertionsDisabled && scratchBytes.getFilePointer() != 0L)
				throw new AssertionError();
			scratchBytes.writeVLong(BlockTreeTermsWriter.encodeOutput(fp, hasTerms, isFloor));
			if (isFloor)
			{
				scratchBytes.writeVInt(floorBlocks.size());
				PendingBlock sub;
				for (Iterator i$ = floorBlocks.iterator(); i$.hasNext(); scratchBytes.writeVLong(sub.fp - fp << 1 | (long)(sub.hasTerms ? 1 : 0)))
				{
					sub = (PendingBlock)i$.next();
					if (!$assertionsDisabled && sub.floorLeadByte == -1)
						throw new AssertionError();
					scratchBytes.writeByte((byte)sub.floorLeadByte);
					if (!$assertionsDisabled && sub.fp <= fp)
						throw new AssertionError();
				}

			}
			ByteSequenceOutputs outputs = ByteSequenceOutputs.getSingleton();
			Builder indexBuilder = new Builder(org.apache.lucene.util.fst.FST.INPUT_TYPE.BYTE1, 0, 0, true, false, 0x7fffffff, outputs, null, false);
			byte bytes[] = new byte[(int)scratchBytes.getFilePointer()];
			if (!$assertionsDisabled && bytes.length <= 0)
				throw new AssertionError();
			scratchBytes.writeTo(bytes, 0);
			indexBuilder.add(Util.toIntsRef(prefix, scratchIntsRef), new BytesRef(bytes, 0, bytes.length));
			scratchBytes.reset();
			if (subIndices != null)
			{
				FST subIndex;
				for (Iterator i$ = subIndices.iterator(); i$.hasNext(); append(indexBuilder, subIndex))
					subIndex = (FST)i$.next();

			}
			if (floorBlocks != null)
			{
				PendingBlock sub;
				for (Iterator i$ = floorBlocks.iterator(); i$.hasNext(); sub.subIndices = null)
				{
					sub = (PendingBlock)i$.next();
					if (sub.subIndices == null)
						continue;
					FST subIndex;
					for (Iterator i$ = sub.subIndices.iterator(); i$.hasNext(); append(indexBuilder, subIndex))
						subIndex = (FST)i$.next();

				}

			}
			index = indexBuilder.finish();
			subIndices = null;
		}

		private void append(Builder builder, FST subIndex)
			throws IOException
		{
			BytesRefFSTEnum subIndexEnum = new BytesRefFSTEnum(subIndex);
			org.apache.lucene.util.fst.BytesRefFSTEnum.InputOutput indexEnt;
			while ((indexEnt = subIndexEnum.next()) != null) 
				builder.add(Util.toIntsRef(indexEnt.input, scratchIntsRef), indexEnt.output);
		}


		public PendingBlock(BytesRef prefix, long fp, boolean hasTerms, boolean isFloor, int floorLeadByte, List subIndices)
		{
			super(false);
			this.prefix = prefix;
			this.fp = fp;
			this.hasTerms = hasTerms;
			this.isFloor = isFloor;
			this.floorLeadByte = floorLeadByte;
			this.subIndices = subIndices;
		}
	}

	private static final class PendingTerm extends PendingEntry
	{

		public final BytesRef term;
		public final TermStats stats;

		public String toString()
		{
			return term.utf8ToString();
		}

		public PendingTerm(BytesRef term, TermStats stats)
		{
			super(true);
			this.term = term;
			this.stats = stats;
		}
	}

	private static class PendingEntry
	{

		public final boolean isTerm;

		protected PendingEntry(boolean isTerm)
		{
			this.isTerm = isTerm;
		}
	}


	public static final int DEFAULT_MIN_BLOCK_SIZE = 25;
	public static final int DEFAULT_MAX_BLOCK_SIZE = 48;
	private static final boolean SAVE_DOT_FILES = false;
	static final int OUTPUT_FLAGS_NUM_BITS = 2;
	static final int OUTPUT_FLAGS_MASK = 3;
	static final int OUTPUT_FLAG_IS_FLOOR = 1;
	static final int OUTPUT_FLAG_HAS_TERMS = 2;
	static final String TERMS_EXTENSION = "tim";
	static final String TERMS_CODEC_NAME = "BLOCK_TREE_TERMS_DICT";
	public static final int TERMS_VERSION_START = 0;
	public static final int TERMS_VERSION_CURRENT = 0;
	static final String TERMS_INDEX_EXTENSION = "tip";
	static final String TERMS_INDEX_CODEC_NAME = "BLOCK_TREE_TERMS_INDEX";
	public static final int TERMS_INDEX_VERSION_START = 0;
	public static final int TERMS_INDEX_VERSION_CURRENT = 0;
	private final IndexOutput out;
	private final IndexOutput indexOut;
	final int minItemsInBlock;
	final int maxItemsInBlock;
	final PostingsWriterBase postingsWriter;
	final FieldInfos fieldInfos;
	FieldInfo currentField;
	private final List fields;
	final RAMOutputStream scratchBytes;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsWriter.desiredAssertionStatus();

	public BlockTreeTermsWriter(SegmentWriteState state, PostingsWriterBase postingsWriter, int minItemsInBlock, int maxItemsInBlock)
		throws IOException
	{
		boolean success;
		IndexOutput indexOut;
		fields = new ArrayList();
		scratchBytes = new RAMOutputStream();
		if (minItemsInBlock <= 1)
			throw new IllegalArgumentException((new StringBuilder()).append("minItemsInBlock must be >= 2; got ").append(minItemsInBlock).toString());
		if (maxItemsInBlock <= 0)
			throw new IllegalArgumentException((new StringBuilder()).append("maxItemsInBlock must be >= 1; got ").append(maxItemsInBlock).toString());
		if (minItemsInBlock > maxItemsInBlock)
			throw new IllegalArgumentException((new StringBuilder()).append("maxItemsInBlock must be >= minItemsInBlock; got maxItemsInBlock=").append(maxItemsInBlock).append(" minItemsInBlock=").append(minItemsInBlock).toString());
		if (2 * (minItemsInBlock - 1) > maxItemsInBlock)
			throw new IllegalArgumentException((new StringBuilder()).append("maxItemsInBlock must be at least 2*(minItemsInBlock-1); got maxItemsInBlock=").append(maxItemsInBlock).append(" minItemsInBlock=").append(minItemsInBlock).toString());
		String termsFileName = IndexFileNames.segmentFileName(state.segmentInfo.name, state.segmentSuffix, "tim");
		out = state.directory.createOutput(termsFileName, state.context);
		success = false;
		indexOut = null;
		fieldInfos = state.fieldInfos;
		this.minItemsInBlock = minItemsInBlock;
		this.maxItemsInBlock = maxItemsInBlock;
		writeHeader(out);
		String termsIndexFileName = IndexFileNames.segmentFileName(state.segmentInfo.name, state.segmentSuffix, "tip");
		indexOut = state.directory.createOutput(termsIndexFileName, state.context);
		writeIndexHeader(indexOut);
		currentField = null;
		this.postingsWriter = postingsWriter;
		postingsWriter.start(out);
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				out, indexOut
			});
		break MISSING_BLOCK_LABEL_365;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				out, indexOut
			});
		throw exception;
		this.indexOut = indexOut;
		return;
	}

	protected void writeHeader(IndexOutput out)
		throws IOException
	{
		CodecUtil.writeHeader(out, "BLOCK_TREE_TERMS_DICT", 0);
		out.writeLong(0L);
	}

	protected void writeIndexHeader(IndexOutput out)
		throws IOException
	{
		CodecUtil.writeHeader(out, "BLOCK_TREE_TERMS_INDEX", 0);
		out.writeLong(0L);
	}

	protected void writeTrailer(IndexOutput out, long dirStart)
		throws IOException
	{
		out.seek(CodecUtil.headerLength("BLOCK_TREE_TERMS_DICT"));
		out.writeLong(dirStart);
	}

	protected void writeIndexTrailer(IndexOutput indexOut, long dirStart)
		throws IOException
	{
		indexOut.seek(CodecUtil.headerLength("BLOCK_TREE_TERMS_INDEX"));
		indexOut.writeLong(dirStart);
	}

	public TermsConsumer addField(FieldInfo field)
		throws IOException
	{
		if (!$assertionsDisabled && currentField != null && currentField.name.compareTo(field.name) >= 0)
		{
			throw new AssertionError();
		} else
		{
			currentField = field;
			TermsWriter terms = new TermsWriter(field);
			fields.add(terms);
			return terms;
		}
	}

	static long encodeOutput(long fp, boolean hasTerms, boolean isFloor)
	{
		if (!$assertionsDisabled && fp >= 0x4000000000000000L)
			throw new AssertionError();
		else
			return fp << 2 | (long)(hasTerms ? 2 : 0) | (long)(isFloor ? 1 : 0);
	}

	public void close()
		throws IOException
	{
		IOException ioe = null;
		int nonZeroCount = 0;
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			TermsWriter field = (TermsWriter)i$.next();
			if (field.numTerms > 0L)
				nonZeroCount++;
		} while (true);
		long dirStart = out.getFilePointer();
		long indexDirStart = indexOut.getFilePointer();
		out.writeVInt(nonZeroCount);
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			TermsWriter field = (TermsWriter)i$.next();
			if (field.numTerms > 0L)
			{
				out.writeVInt(field.fieldInfo.number);
				out.writeVLong(field.numTerms);
				BytesRef rootCode = (BytesRef)((PendingBlock)field.pending.get(0)).index.getEmptyOutput();
				if (!$assertionsDisabled && rootCode == null)
					throw new AssertionError((new StringBuilder()).append("field=").append(field.fieldInfo.name).append(" numTerms=").append(field.numTerms).toString());
				out.writeVInt(rootCode.length);
				out.writeBytes(rootCode.bytes, rootCode.offset, rootCode.length);
				if (field.fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
					out.writeVLong(field.sumTotalTermFreq);
				out.writeVLong(field.sumDocFreq);
				out.writeVInt(field.docCount);
				indexOut.writeVLong(field.indexStartFP);
			}
		} while (true);
		writeTrailer(out, dirStart);
		writeIndexTrailer(indexOut, indexDirStart);
		IOUtils.closeWhileHandlingException(ioe, new Closeable[] {
			out, indexOut, postingsWriter
		});
		break MISSING_BLOCK_LABEL_447;
		IOException ioe2;
		ioe2;
		ioe = ioe2;
		IOUtils.closeWhileHandlingException(ioe, new Closeable[] {
			out, indexOut, postingsWriter
		});
		break MISSING_BLOCK_LABEL_447;
		Exception exception;
		exception;
		IOUtils.closeWhileHandlingException(ioe, new Closeable[] {
			out, indexOut, postingsWriter
		});
		throw exception;
	}



}
