// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChineseTokenizer.java

package org.apache.lucene.analysis.cn;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

/**
 * @deprecated Class ChineseTokenizer is deprecated
 */

public final class ChineseTokenizer extends Tokenizer
{

	private int offset;
	private int bufferIndex;
	private int dataLen;
	private static final int MAX_WORD_LEN = 255;
	private static final int IO_BUFFER_SIZE = 1024;
	private final char buffer[];
	private final char ioBuffer[];
	private int length;
	private int start;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;

	public ChineseTokenizer(Reader in)
	{
		super(in);
		offset = 0;
		bufferIndex = 0;
		dataLen = 0;
		buffer = new char[255];
		ioBuffer = new char[1024];
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	}

	public ChineseTokenizer(AttributeSource source, Reader in)
	{
		super(source, in);
		offset = 0;
		bufferIndex = 0;
		dataLen = 0;
		buffer = new char[255];
		ioBuffer = new char[1024];
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	}

	public ChineseTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader in)
	{
		super(factory, in);
		offset = 0;
		bufferIndex = 0;
		dataLen = 0;
		buffer = new char[255];
		ioBuffer = new char[1024];
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	}

	private final void push(char c)
	{
		if (length == 0)
			start = offset - 1;
		buffer[length++] = Character.toLowerCase(c);
	}

	private final boolean flush()
	{
		if (length > 0)
		{
			termAtt.copyBuffer(buffer, 0, length);
			offsetAtt.setOffset(correctOffset(start), correctOffset(start + length));
			return true;
		} else
		{
			return false;
		}
	}

	public boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		length = 0;
		start = offset;
label0:
		do
			do
			{
				offset++;
				if (bufferIndex >= dataLen)
				{
					dataLen = input.read(ioBuffer);
					bufferIndex = 0;
				}
				if (dataLen == -1)
				{
					offset--;
					return flush();
				}
				char c = ioBuffer[bufferIndex++];
				switch (Character.getType(c))
				{
				case 3: // '\003'
				case 4: // '\004'
				case 6: // '\006'
				case 7: // '\007'
				case 8: // '\b'
				default:
					continue label0;

				case 1: // '\001'
				case 2: // '\002'
				case 9: // '\t'
					push(c);
					if (length == 255)
						return flush();
					break;

				case 5: // '\005'
					if (length > 0)
					{
						bufferIndex--;
						offset--;
						return flush();
					} else
					{
						push(c);
						return flush();
					}
				}
			} while (true);
		while (length <= 0);
		return flush();
	}

	public final void end()
	{
		int finalOffset = correctOffset(offset);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		offset = bufferIndex = dataLen = 0;
	}
}
