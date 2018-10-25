// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NGramTokenizer.java

package org.apache.lucene.analysis.ngram;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

public final class NGramTokenizer extends Tokenizer
{

	public static final int DEFAULT_MIN_NGRAM_SIZE = 1;
	public static final int DEFAULT_MAX_NGRAM_SIZE = 2;
	private int minGram;
	private int maxGram;
	private int gramSize;
	private int pos;
	private int inLen;
	private int charsRead;
	private String inStr;
	private boolean started;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;

	public NGramTokenizer(Reader input, int minGram, int maxGram)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		init(minGram, maxGram);
	}

	public NGramTokenizer(AttributeSource source, Reader input, int minGram, int maxGram)
	{
		super(source, input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		init(minGram, maxGram);
	}

	public NGramTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input, int minGram, int maxGram)
	{
		super(factory, input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		init(minGram, maxGram);
	}

	public NGramTokenizer(Reader input)
	{
		this(input, 1, 2);
	}

	private void init(int minGram, int maxGram)
	{
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
		if (pos + gramSize > inLen)
		{
			pos = 0;
			gramSize++;
			if (gramSize > maxGram)
				return false;
			if (pos + gramSize > inLen)
				return false;
		}
		int oldPos = pos;
		pos++;
		termAtt.setEmpty().append(inStr, oldPos, oldPos + gramSize);
		offsetAtt.setOffset(correctOffset(oldPos), correctOffset(oldPos + gramSize));
		return true;
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
		pos = 0;
	}
}
