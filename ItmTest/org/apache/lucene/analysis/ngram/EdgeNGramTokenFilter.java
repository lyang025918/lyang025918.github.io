// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EdgeNGramTokenFilter.java

package org.apache.lucene.analysis.ngram;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public final class EdgeNGramTokenFilter extends TokenFilter
{
	public static abstract class Side extends Enum
	{

		public static final Side FRONT;
		public static final Side BACK;
		private static final Side $VALUES[];

		public static Side[] values()
		{
			return (Side[])$VALUES.clone();
		}

		public static Side valueOf(String name)
		{
			return (Side)Enum.valueOf(org/apache/lucene/analysis/ngram/EdgeNGramTokenFilter$Side, name);
		}

		public abstract String getLabel();

		public static Side getSide(String sideName)
		{
			if (FRONT.getLabel().equals(sideName))
				return FRONT;
			if (BACK.getLabel().equals(sideName))
				return BACK;
			else
				return null;
		}

		static 
		{
			FRONT = new Side("FRONT", 0) {

				public String getLabel()
				{
					return "front";
				}

			};
			BACK = new Side("BACK", 1) {

				public String getLabel()
				{
					return "back";
				}

			};
			$VALUES = (new Side[] {
				FRONT, BACK
			});
		}

		private Side(String s, int i)
		{
			super(s, i);
		}

	}


	public static final Side DEFAULT_SIDE;
	public static final int DEFAULT_MAX_GRAM_SIZE = 1;
	public static final int DEFAULT_MIN_GRAM_SIZE = 1;
	private final int minGram;
	private final int maxGram;
	private Side side;
	private char curTermBuffer[];
	private int curTermLength;
	private int curGramSize;
	private int tokStart;
	private int tokEnd;
	private boolean hasIllegalOffsets;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;

	public EdgeNGramTokenFilter(TokenStream input, Side side, int minGram, int maxGram)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		if (side == null)
			throw new IllegalArgumentException("sideLabel must be either front or back");
		if (minGram < 1)
			throw new IllegalArgumentException("minGram must be greater than zero");
		if (minGram > maxGram)
		{
			throw new IllegalArgumentException("minGram must not be greater than maxGram");
		} else
		{
			this.minGram = minGram;
			this.maxGram = maxGram;
			this.side = side;
			return;
		}
	}

	public EdgeNGramTokenFilter(TokenStream input, String sideLabel, int minGram, int maxGram)
	{
		this(input, Side.getSide(sideLabel), minGram, maxGram);
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
				tokStart = offsetAtt.startOffset();
				tokEnd = offsetAtt.endOffset();
				hasIllegalOffsets = tokStart + curTermLength != tokEnd;
			}
			if (curGramSize <= maxGram && curGramSize <= curTermLength && curGramSize <= maxGram)
			{
				int start = side != Side.FRONT ? curTermLength - curGramSize : 0;
				int end = start + curGramSize;
				clearAttributes();
				if (hasIllegalOffsets)
					offsetAtt.setOffset(tokStart, tokEnd);
				else
					offsetAtt.setOffset(tokStart + start, tokStart + end);
				termAtt.copyBuffer(curTermBuffer, start, curGramSize);
				curGramSize++;
				return true;
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

	static 
	{
		DEFAULT_SIDE = Side.FRONT;
	}
}
