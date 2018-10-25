// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharTokenizer.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharacterUtils

public abstract class CharTokenizer extends Tokenizer
{

	private int offset;
	private int bufferIndex;
	private int dataLen;
	private int finalOffset;
	private static final int MAX_WORD_LEN = 255;
	private static final int IO_BUFFER_SIZE = 4096;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final CharacterUtils charUtils;
	private final CharacterUtils.CharacterBuffer ioBuffer;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/util/CharTokenizer.desiredAssertionStatus();

	public CharTokenizer(Version matchVersion, Reader input)
	{
		super(input);
		offset = 0;
		bufferIndex = -1;
		dataLen = 0;
		finalOffset = 0;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		ioBuffer = CharacterUtils.newCharacterBuffer(4096);
		charUtils = CharacterUtils.getInstance(matchVersion);
	}

	public CharTokenizer(Version matchVersion, AttributeSource source, Reader input)
	{
		super(source, input);
		offset = 0;
		bufferIndex = -1;
		dataLen = 0;
		finalOffset = 0;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		ioBuffer = CharacterUtils.newCharacterBuffer(4096);
		charUtils = CharacterUtils.getInstance(matchVersion);
	}

	public CharTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input)
	{
		super(factory, input);
		offset = 0;
		bufferIndex = -1;
		dataLen = 0;
		finalOffset = 0;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		ioBuffer = CharacterUtils.newCharacterBuffer(4096);
		charUtils = CharacterUtils.getInstance(matchVersion);
	}

	protected abstract boolean isTokenChar(int i);

	protected int normalize(int c)
	{
		return c;
	}

	public final boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		int length = 0;
		int start = -1;
		int end = -1;
		char buffer[] = termAtt.buffer();
label0:
		do
		{
			do
			{
				if (bufferIndex >= dataLen)
				{
					offset += dataLen;
					if (!charUtils.fill(ioBuffer, input))
					{
						dataLen = 0;
						if (length <= 0)
						{
							finalOffset = correctOffset(offset);
							return false;
						}
						break;
					}
					dataLen = ioBuffer.getLength();
					bufferIndex = 0;
				}
				int c = charUtils.codePointAt(ioBuffer.getBuffer(), bufferIndex);
				int charCount = Character.charCount(c);
				bufferIndex += charCount;
				if (!isTokenChar(c))
					continue label0;
				if (length == 0)
				{
					if (!$assertionsDisabled && start != -1)
						throw new AssertionError();
					start = (offset + bufferIndex) - charCount;
					end = start;
				} else
				if (length >= buffer.length - 1)
					buffer = termAtt.resizeBuffer(2 + length);
				end += charCount;
				length += Character.toChars(normalize(c), buffer, length);
			} while (length < 255);
			break;
		} while (length <= 0);
		termAtt.setLength(length);
		if (!$assertionsDisabled && start == -1)
		{
			throw new AssertionError();
		} else
		{
			offsetAtt.setOffset(correctOffset(start), finalOffset = correctOffset(end));
			return true;
		}
	}

	public final void end()
	{
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	public void reset()
		throws IOException
	{
		bufferIndex = 0;
		offset = 0;
		dataLen = 0;
		finalOffset = 0;
		ioBuffer.reset();
	}

}
