// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CapitalizationFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

public final class CapitalizationFilter extends TokenFilter
{

	public static final int DEFAULT_MAX_WORD_COUNT = 0x7fffffff;
	public static final int DEFAULT_MAX_TOKEN_LENGTH = 0x7fffffff;
	private final boolean onlyFirstWord;
	private final CharArraySet keep;
	private final boolean forceFirstLetter;
	private final Collection okPrefix;
	private final int minWordLength;
	private final int maxWordCount;
	private final int maxTokenLength;
	private final CharTermAttribute termAtt;

	public CapitalizationFilter(TokenStream in)
	{
		this(in, true, null, true, null, 0, 0x7fffffff, 0x7fffffff);
	}

	public CapitalizationFilter(TokenStream in, boolean onlyFirstWord, CharArraySet keep, boolean forceFirstLetter, Collection okPrefix, int minWordLength, int maxWordCount, 
			int maxTokenLength)
	{
		super(in);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		this.onlyFirstWord = onlyFirstWord;
		this.keep = keep;
		this.forceFirstLetter = forceFirstLetter;
		this.okPrefix = okPrefix;
		this.minWordLength = minWordLength;
		this.maxWordCount = maxWordCount;
		this.maxTokenLength = maxTokenLength;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		char termBuffer[] = termAtt.buffer();
		int termBufferLength = termAtt.length();
		char backup[] = null;
		if (maxWordCount < 0x7fffffff)
		{
			backup = new char[termBufferLength];
			System.arraycopy(termBuffer, 0, backup, 0, termBufferLength);
		}
		if (termBufferLength < maxTokenLength)
		{
			int wordCount = 0;
			int lastWordStart = 0;
			for (int i = 0; i < termBufferLength; i++)
			{
				char c = termBuffer[i];
				if (c > ' ' && c != '.')
					continue;
				int len = i - lastWordStart;
				if (len > 0)
				{
					processWord(termBuffer, lastWordStart, len, wordCount++);
					lastWordStart = i + 1;
					i++;
				}
			}

			if (lastWordStart < termBufferLength)
				processWord(termBuffer, lastWordStart, termBufferLength - lastWordStart, wordCount++);
			if (wordCount > maxWordCount)
				termAtt.copyBuffer(backup, 0, termBufferLength);
		}
		return true;
	}

	private void processWord(char buffer[], int offset, int length, int wordCount)
	{
label0:
		{
			if (length < 1)
				return;
			if (onlyFirstWord && wordCount > 0)
			{
				for (int i = 0; i < length; i++)
					buffer[offset + i] = Character.toLowerCase(buffer[offset + i]);

				return;
			}
			if (keep != null && keep.contains(buffer, offset, length))
			{
				if (wordCount == 0 && forceFirstLetter)
					buffer[offset] = Character.toUpperCase(buffer[offset]);
				return;
			}
			if (length < minWordLength)
				return;
			if (okPrefix == null)
				break label0;
			Iterator i$ = okPrefix.iterator();
			boolean match;
			do
			{
				char prefix[];
				do
				{
					if (!i$.hasNext())
						break label0;
					prefix = (char[])i$.next();
				} while (length < prefix.length);
				match = true;
				int i = 0;
				do
				{
					if (i >= prefix.length)
						break;
					if (prefix[i] != buffer[offset + i])
					{
						match = false;
						break;
					}
					i++;
				} while (true);
			} while (!match);
			return;
		}
		buffer[offset] = Character.toUpperCase(buffer[offset]);
		for (int i = 1; i < length; i++)
			buffer[offset + i] = Character.toLowerCase(buffer[offset + i]);

	}
}
