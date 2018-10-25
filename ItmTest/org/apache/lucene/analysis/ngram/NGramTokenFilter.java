// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NGramTokenFilter.java

package org.apache.lucene.analysis.ngram;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public final class NGramTokenFilter extends TokenFilter
{

	public static final int DEFAULT_MIN_NGRAM_SIZE = 1;
	public static final int DEFAULT_MAX_NGRAM_SIZE = 2;
	private int minGram;
	private int maxGram;
	private char curTermBuffer[];
	private int curTermLength;
	private int curGramSize;
	private int curPos;
	private int tokStart;
	private int tokEnd;
	private boolean hasIllegalOffsets;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;

	public NGramTokenFilter(TokenStream input, int minGram, int maxGram)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		if (minGram < 1)
			throw new IllegalArgumentException("minGram must be greater than zero");
		if (minGram > maxGram)
		{
			throw new IllegalArgumentException("minGram must not be greater than maxGram");
		} else
		{
			this.minGram = minGram;
			this.maxGram = maxGram;
			return;
		}
	}

	public NGramTokenFilter(TokenStream input)
	{
		this(input, 1, 2);
	}

	public final boolean incrementToken()
		throws IOException
	{
		do
		{
			if (curTermBuffer == null)
			{
				if (!input.incrementToken())
					return false;
				curTermBuffer = (char[])termAtt.buffer().clone();
				curTermLength = termAtt.length();
				curGramSize = minGram;
				curPos = 0;
				tokStart = offsetAtt.startOffset();
				tokEnd = offsetAtt.endOffset();
				hasIllegalOffsets = tokStart + curTermLength != tokEnd;
			}
			while (curGramSize <= maxGram) 
			{
				if (curPos + curGramSize <= curTermLength)
				{
					clearAttributes();
					termAtt.copyBuffer(curTermBuffer, curPos, curGramSize);
					if (hasIllegalOffsets)
						offsetAtt.setOffset(tokStart, tokEnd);
					else
						offsetAtt.setOffset(tokStart + curPos, tokStart + curPos + curGramSize);
					curPos++;
					return true;
				}
				curGramSize++;
				curPos = 0;
			}
			curTermBuffer = null;
		} while (true);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		curTermBuffer = null;
	}
}
