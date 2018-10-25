// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKBigramFilter.java

package org.apache.lucene.analysis.cjk;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.AttributeSource;

public final class CJKBigramFilter extends TokenFilter
{

	public static final int HAN = 1;
	public static final int HIRAGANA = 2;
	public static final int KATAKANA = 4;
	public static final int HANGUL = 8;
	public static final String DOUBLE_TYPE = "<DOUBLE>";
	public static final String SINGLE_TYPE = "<SINGLE>";
	private static final String HAN_TYPE;
	private static final String HIRAGANA_TYPE;
	private static final String KATAKANA_TYPE;
	private static final String HANGUL_TYPE;
	private static final Object NO = new Object();
	private final Object doHan;
	private final Object doHiragana;
	private final Object doKatakana;
	private final Object doHangul;
	private final boolean outputUnigrams;
	private boolean ngramState;
	private final CharTermAttribute termAtt;
	private final TypeAttribute typeAtt;
	private final OffsetAttribute offsetAtt;
	private final PositionIncrementAttribute posIncAtt;
	private final PositionLengthAttribute posLengthAtt;
	int buffer[];
	int startOffset[];
	int endOffset[];
	int bufferLen;
	int index;
	int lastEndOffset;
	private boolean exhausted;
	private org.apache.lucene.util.AttributeSource.State loneState;

	public CJKBigramFilter(TokenStream in)
	{
		this(in, 15);
	}

	public CJKBigramFilter(TokenStream in, int flags)
	{
		this(in, flags, false);
	}

	public CJKBigramFilter(TokenStream in, int flags, boolean outputUnigrams)
	{
		super(in);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		posLengthAtt = (PositionLengthAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionLengthAttribute);
		buffer = new int[8];
		startOffset = new int[8];
		endOffset = new int[8];
		doHan = (flags & 1) != 0 ? ((Object) (HAN_TYPE)) : NO;
		doHiragana = (flags & 2) != 0 ? ((Object) (HIRAGANA_TYPE)) : NO;
		doKatakana = (flags & 4) != 0 ? ((Object) (KATAKANA_TYPE)) : NO;
		doHangul = (flags & 8) != 0 ? ((Object) (HANGUL_TYPE)) : NO;
		this.outputUnigrams = outputUnigrams;
	}

	public boolean incrementToken()
		throws IOException
	{
		do
		{
			if (hasBufferedBigram())
			{
				if (outputUnigrams)
				{
					if (ngramState)
					{
						flushBigram();
					} else
					{
						flushUnigram();
						index--;
					}
					ngramState = !ngramState;
				} else
				{
					flushBigram();
				}
				return true;
			}
			if (!doNext())
				break;
			String type = typeAtt.type();
			if (type == doHan || type == doHiragana || type == doKatakana || type == doHangul)
			{
				if (offsetAtt.startOffset() != lastEndOffset)
				{
					if (hasBufferedUnigram())
					{
						loneState = captureState();
						flushUnigram();
						return true;
					}
					index = 0;
					bufferLen = 0;
				}
				refill();
			} else
			if (hasBufferedUnigram())
			{
				loneState = captureState();
				flushUnigram();
				return true;
			} else
			{
				return true;
			}
		} while (true);
		if (hasBufferedUnigram())
		{
			flushUnigram();
			return true;
		} else
		{
			return false;
		}
	}

	private boolean doNext()
		throws IOException
	{
		if (loneState != null)
		{
			restoreState(loneState);
			loneState = null;
			return true;
		}
		if (exhausted)
			return false;
		if (input.incrementToken())
		{
			return true;
		} else
		{
			exhausted = true;
			return false;
		}
	}

	private void refill()
	{
		if (bufferLen > 64)
		{
			int last = bufferLen - 1;
			buffer[0] = buffer[last];
			startOffset[0] = startOffset[last];
			endOffset[0] = endOffset[last];
			bufferLen = 1;
			index -= last;
		}
		char termBuffer[] = termAtt.buffer();
		int len = termAtt.length();
		int start = offsetAtt.startOffset();
		int end = offsetAtt.endOffset();
		int newSize = bufferLen + len;
		buffer = ArrayUtil.grow(buffer, newSize);
		startOffset = ArrayUtil.grow(startOffset, newSize);
		endOffset = ArrayUtil.grow(endOffset, newSize);
		lastEndOffset = end;
		if (end - start != len)
		{
			int i = 0;
			int cp = 0;
			for (; i < len; i += Character.charCount(cp))
			{
				cp = buffer[bufferLen] = Character.codePointAt(termBuffer, i, len);
				startOffset[bufferLen] = start;
				endOffset[bufferLen] = end;
				bufferLen++;
			}

		} else
		{
			int i = 0;
			int cp = 0;
			int cpLen = 0;
			for (; i < len; i += cpLen)
			{
				cp = buffer[bufferLen] = Character.codePointAt(termBuffer, i, len);
				cpLen = Character.charCount(cp);
				startOffset[bufferLen] = start;
				start = endOffset[bufferLen] = start + cpLen;
				bufferLen++;
			}

		}
	}

	private void flushBigram()
	{
		clearAttributes();
		char termBuffer[] = termAtt.resizeBuffer(4);
		int len1 = Character.toChars(buffer[index], termBuffer, 0);
		int len2 = len1 + Character.toChars(buffer[index + 1], termBuffer, len1);
		termAtt.setLength(len2);
		offsetAtt.setOffset(startOffset[index], endOffset[index + 1]);
		typeAtt.setType("<DOUBLE>");
		if (outputUnigrams)
		{
			posIncAtt.setPositionIncrement(0);
			posLengthAtt.setPositionLength(2);
		}
		index++;
	}

	private void flushUnigram()
	{
		clearAttributes();
		char termBuffer[] = termAtt.resizeBuffer(2);
		int len = Character.toChars(buffer[index], termBuffer, 0);
		termAtt.setLength(len);
		offsetAtt.setOffset(startOffset[index], endOffset[index]);
		typeAtt.setType("<SINGLE>");
		index++;
	}

	private boolean hasBufferedBigram()
	{
		return bufferLen - index > 1;
	}

	private boolean hasBufferedUnigram()
	{
		if (outputUnigrams)
			return bufferLen - index == 1;
		else
			return bufferLen == 1 && index == 0;
	}

	public void reset()
		throws IOException
	{
		super.reset();
		bufferLen = 0;
		index = 0;
		lastEndOffset = 0;
		loneState = null;
		exhausted = false;
		ngramState = false;
	}

	static 
	{
		HAN_TYPE = StandardTokenizer.TOKEN_TYPES[10];
		HIRAGANA_TYPE = StandardTokenizer.TOKEN_TYPES[11];
		KATAKANA_TYPE = StandardTokenizer.TOKEN_TYPES[12];
		HANGUL_TYPE = StandardTokenizer.TOKEN_TYPES[13];
	}
}
