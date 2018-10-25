// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKTokenizer.java

package org.apache.lucene.analysis.cjk;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.AttributeSource;

/**
 * @deprecated Class CJKTokenizer is deprecated
 */

public final class CJKTokenizer extends Tokenizer
{

	static final int WORD_TYPE = 0;
	static final int SINGLE_TOKEN_TYPE = 1;
	static final int DOUBLE_TOKEN_TYPE = 2;
	static final String TOKEN_TYPE_NAMES[] = {
		"word", "single", "double"
	};
	private static final int MAX_WORD_LEN = 255;
	private static final int IO_BUFFER_SIZE = 256;
	private int offset;
	private int bufferIndex;
	private int dataLen;
	private final char buffer[];
	private final char ioBuffer[];
	private int tokenType;
	private boolean preIsTokened;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final TypeAttribute typeAtt;

	public CJKTokenizer(Reader in)
	{
		super(in);
		offset = 0;
		bufferIndex = 0;
		dataLen = 0;
		buffer = new char[255];
		ioBuffer = new char[256];
		tokenType = 0;
		preIsTokened = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	}

	public CJKTokenizer(AttributeSource source, Reader in)
	{
		super(source, in);
		offset = 0;
		bufferIndex = 0;
		dataLen = 0;
		buffer = new char[255];
		ioBuffer = new char[256];
		tokenType = 0;
		preIsTokened = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	}

	public CJKTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader in)
	{
		super(factory, in);
		offset = 0;
		bufferIndex = 0;
		dataLen = 0;
		buffer = new char[255];
		ioBuffer = new char[256];
		tokenType = 0;
		preIsTokened = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	}

	public boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		do
		{
			int length = 0;
			int start = offset;
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
					if (length > 0)
					{
						if (preIsTokened)
						{
							length = 0;
							preIsTokened = false;
						} else
						{
							offset--;
						}
					} else
					{
						offset--;
						return false;
					}
					break;
				}
				char c = ioBuffer[bufferIndex++];
				Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
				if (ub == Character.UnicodeBlock.BASIC_LATIN || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
				{
					if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
					{
						int i = c;
						if (i >= 65281 && i <= 65374)
						{
							i -= 65248;
							c = (char)i;
						}
					}
					if (Character.isLetterOrDigit(c) || c == '_' || c == '+' || c == '#')
					{
						if (length == 0)
							start = offset - 1;
						else
						if (tokenType == 2)
						{
							offset--;
							bufferIndex--;
							if (preIsTokened)
							{
								length = 0;
								preIsTokened = false;
							}
							break;
						}
						buffer[length++] = Character.toLowerCase(c);
						tokenType = 1;
						if (length == 255)
							break;
						continue;
					}
					if (length <= 0)
						continue;
					if (!preIsTokened)
						break;
					length = 0;
					preIsTokened = false;
					continue;
				}
				if (Character.isLetter(c))
				{
					if (length == 0)
					{
						start = offset - 1;
						buffer[length++] = c;
						tokenType = 2;
						continue;
					}
					if (tokenType == 1)
					{
						offset--;
						bufferIndex--;
						break;
					}
					buffer[length++] = c;
					tokenType = 2;
					if (length != 2)
						continue;
					offset--;
					bufferIndex--;
					preIsTokened = true;
					break;
				}
				if (length <= 0)
					continue;
				if (!preIsTokened)
					break;
				length = 0;
				preIsTokened = false;
			} while (true);
			if (length > 0)
			{
				termAtt.copyBuffer(buffer, 0, length);
				offsetAtt.setOffset(correctOffset(start), correctOffset(start + length));
				typeAtt.setType(TOKEN_TYPE_NAMES[tokenType]);
				return true;
			}
		} while (dataLen != -1);
		offset--;
		return false;
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
		preIsTokened = false;
		tokenType = 0;
	}

}
