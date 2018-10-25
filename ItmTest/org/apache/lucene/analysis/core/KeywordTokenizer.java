// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordTokenizer.java

package org.apache.lucene.analysis.core;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

public final class KeywordTokenizer extends Tokenizer
{

	public static final int DEFAULT_BUFFER_SIZE = 256;
	private boolean done;
	private int finalOffset;
	private final CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;

	public KeywordTokenizer(Reader input)
	{
		this(input, 256);
	}

	public KeywordTokenizer(Reader input, int bufferSize)
	{
		super(input);
		done = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		if (bufferSize <= 0)
		{
			throw new IllegalArgumentException("bufferSize must be > 0");
		} else
		{
			termAtt.resizeBuffer(bufferSize);
			return;
		}
	}

	public KeywordTokenizer(AttributeSource source, Reader input, int bufferSize)
	{
		super(source, input);
		done = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		if (bufferSize <= 0)
		{
			throw new IllegalArgumentException("bufferSize must be > 0");
		} else
		{
			termAtt.resizeBuffer(bufferSize);
			return;
		}
	}

	public KeywordTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input, int bufferSize)
	{
		super(factory, input);
		done = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		if (bufferSize <= 0)
		{
			throw new IllegalArgumentException("bufferSize must be > 0");
		} else
		{
			termAtt.resizeBuffer(bufferSize);
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (!done)
		{
			clearAttributes();
			done = true;
			int upto = 0;
			char buffer[] = termAtt.buffer();
			do
			{
				int length = input.read(buffer, upto, buffer.length - upto);
				if (length == -1)
					break;
				upto += length;
				if (upto == buffer.length)
					buffer = termAtt.resizeBuffer(1 + buffer.length);
			} while (true);
			termAtt.setLength(upto);
			finalOffset = correctOffset(upto);
			offsetAtt.setOffset(correctOffset(0), finalOffset);
			return true;
		} else
		{
			return false;
		}
	}

	public final void end()
	{
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	public void reset()
		throws IOException
	{
		done = false;
	}
}
