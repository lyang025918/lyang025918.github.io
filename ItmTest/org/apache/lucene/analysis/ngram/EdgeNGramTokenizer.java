// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EdgeNGramTokenizer.java

package org.apache.lucene.analysis.ngram;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

public final class EdgeNGramTokenizer extends Tokenizer
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
			return (Side)Enum.valueOf(org/apache/lucene/analysis/ngram/EdgeNGramTokenizer$Side, name);
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
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private int minGram;
	private int maxGram;
	private int gramSize;
	private Side side;
	private boolean started;
	private int inLen;
	private int charsRead;
	private String inStr;

	public EdgeNGramTokenizer(Reader input, Side side, int minGram, int maxGram)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		init(side, minGram, maxGram);
	}

	public EdgeNGramTokenizer(AttributeSource source, Reader input, Side side, int minGram, int maxGram)
	{
		super(source, input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		init(side, minGram, maxGram);
	}

	public EdgeNGramTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input, Side side, int minGram, int maxGram)
	{
		super(factory, input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		init(side, minGram, maxGram);
	}

	public EdgeNGramTokenizer(Reader input, String sideLabel, int minGram, int maxGram)
	{
		this(input, Side.getSide(sideLabel), minGram, maxGram);
	}

	public EdgeNGramTokenizer(AttributeSource source, Reader input, String sideLabel, int minGram, int maxGram)
	{
		this(source, input, Side.getSide(sideLabel), minGram, maxGram);
	}

	public EdgeNGramTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input, String sideLabel, int minGram, int maxGram)
	{
		this(factory, input, Side.getSide(sideLabel), minGram, maxGram);
	}

	private void init(Side side, int minGram, int maxGram)
	{
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

	public boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		if (!started)
		{
			started = true;
			gramSize = minGram;
			char chars[] = new char[1024];
			charsRead = 0;
			do
			{
				if (charsRead >= chars.length)
					break;
				int inc = input.read(chars, charsRead, chars.length - charsRead);
				if (inc == -1)
					break;
				charsRead += inc;
			} while (true);
			inStr = (new String(chars, 0, charsRead)).trim();
			if (charsRead == chars.length)
			{
				char throwaway[] = new char[1024];
				do
				{
					int inc = input.read(throwaway, 0, throwaway.length);
					if (inc == -1)
						break;
					charsRead += inc;
				} while (true);
			}
			inLen = inStr.length();
			if (inLen == 0)
				return false;
		}
		if (gramSize > inLen)
			return false;
		if (gramSize > maxGram)
		{
			return false;
		} else
		{
			int start = side != Side.FRONT ? inLen - gramSize : 0;
			int end = start + gramSize;
			termAtt.setEmpty().append(inStr, start, end);
			offsetAtt.setOffset(correctOffset(start), correctOffset(end));
			gramSize++;
			return true;
		}
	}

	public void end()
	{
		int finalOffset = correctOffset(charsRead);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		started = false;
	}

	static 
	{
		DEFAULT_SIDE = Side.FRONT;
	}
}
