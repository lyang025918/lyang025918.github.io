// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PathHierarchyTokenizer.java

package org.apache.lucene.analysis.path;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;

public class PathHierarchyTokenizer extends Tokenizer
{

	private static final int DEFAULT_BUFFER_SIZE = 1024;
	public static final char DEFAULT_DELIMITER = 47;
	public static final int DEFAULT_SKIP = 0;
	private final char delimiter;
	private final char replacement;
	private final int skip;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final PositionIncrementAttribute posAtt;
	private int startPosition;
	private int skipped;
	private boolean endDelimiter;
	private StringBuilder resultToken;
	private int charsRead;

	public PathHierarchyTokenizer(Reader input)
	{
		this(input, 1024, '/', '/', 0);
	}

	public PathHierarchyTokenizer(Reader input, int skip)
	{
		this(input, 1024, '/', '/', skip);
	}

	public PathHierarchyTokenizer(Reader input, int bufferSize, char delimiter)
	{
		this(input, bufferSize, delimiter, delimiter, 0);
	}

	public PathHierarchyTokenizer(Reader input, char delimiter, char replacement)
	{
		this(input, 1024, delimiter, replacement, 0);
	}

	public PathHierarchyTokenizer(Reader input, char delimiter, char replacement, int skip)
	{
		this(input, 1024, delimiter, replacement, skip);
	}

	public PathHierarchyTokenizer(Reader input, int bufferSize, char delimiter, char replacement, int skip)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		startPosition = 0;
		skipped = 0;
		endDelimiter = false;
		charsRead = 0;
		if (bufferSize < 0)
			throw new IllegalArgumentException("bufferSize cannot be negative");
		if (skip < 0)
		{
			throw new IllegalArgumentException("skip cannot be negative");
		} else
		{
			termAtt.resizeBuffer(bufferSize);
			this.delimiter = delimiter;
			this.replacement = replacement;
			this.skip = skip;
			resultToken = new StringBuilder(bufferSize);
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		termAtt.append(resultToken);
		if (resultToken.length() == 0)
			posAtt.setPositionIncrement(1);
		else
			posAtt.setPositionIncrement(0);
		int length = 0;
		boolean added = false;
		if (endDelimiter)
		{
			termAtt.append(replacement);
			length++;
			endDelimiter = false;
			added = true;
		}
		do
		{
			int c = input.read();
			if (c >= 0)
				charsRead++;
			else
			if (skipped > skip)
			{
				length += resultToken.length();
				termAtt.setLength(length);
				offsetAtt.setOffset(correctOffset(startPosition), correctOffset(startPosition + length));
				if (added)
				{
					resultToken.setLength(0);
					resultToken.append(termAtt.buffer(), 0, length);
				}
				return added;
			} else
			{
				return false;
			}
			if (!added)
			{
				added = true;
				skipped++;
				if (skipped > skip)
				{
					termAtt.append(c != delimiter ? (char)c : replacement);
					length++;
				} else
				{
					startPosition++;
				}
				continue;
			}
			if (c == delimiter)
			{
				if (skipped > skip)
				{
					endDelimiter = true;
					break;
				}
				skipped++;
				if (skipped > skip)
				{
					termAtt.append(replacement);
					length++;
				} else
				{
					startPosition++;
				}
			} else
			if (skipped > skip)
			{
				termAtt.append((char)c);
				length++;
			} else
			{
				startPosition++;
			}
		} while (true);
		length += resultToken.length();
		termAtt.setLength(length);
		offsetAtt.setOffset(correctOffset(startPosition), correctOffset(startPosition + length));
		resultToken.setLength(0);
		resultToken.append(termAtt.buffer(), 0, length);
		return true;
	}

	public final void end()
	{
		int finalOffset = correctOffset(charsRead);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		resultToken.setLength(0);
		charsRead = 0;
		endDelimiter = false;
		skipped = 0;
		startPosition = 0;
	}
}
