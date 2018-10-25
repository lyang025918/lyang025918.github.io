// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReversePathHierarchyTokenizer.java

package org.apache.lucene.analysis.path;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;

public class ReversePathHierarchyTokenizer extends Tokenizer
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
	private int endPosition;
	private int finalOffset;
	private int skipped;
	private StringBuilder resultToken;
	private List delimiterPositions;
	private int delimitersCount;
	private char resultTokenBuffer[];

	public ReversePathHierarchyTokenizer(Reader input)
	{
		this(input, 1024, '/', '/', 0);
	}

	public ReversePathHierarchyTokenizer(Reader input, int skip)
	{
		this(input, 1024, '/', '/', skip);
	}

	public ReversePathHierarchyTokenizer(Reader input, int bufferSize, char delimiter)
	{
		this(input, bufferSize, delimiter, delimiter, 0);
	}

	public ReversePathHierarchyTokenizer(Reader input, char delimiter, char replacement)
	{
		this(input, 1024, delimiter, replacement, 0);
	}

	public ReversePathHierarchyTokenizer(Reader input, int bufferSize, char delimiter, char replacement)
	{
		this(input, bufferSize, delimiter, replacement, 0);
	}

	public ReversePathHierarchyTokenizer(Reader input, char delimiter, int skip)
	{
		this(input, 1024, delimiter, delimiter, skip);
	}

	public ReversePathHierarchyTokenizer(Reader input, char delimiter, char replacement, int skip)
	{
		this(input, 1024, delimiter, replacement, skip);
	}

	public ReversePathHierarchyTokenizer(Reader input, int bufferSize, char delimiter, char replacement, int skip)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		endPosition = 0;
		finalOffset = 0;
		skipped = 0;
		delimitersCount = -1;
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
			resultTokenBuffer = new char[bufferSize];
			delimiterPositions = new ArrayList(bufferSize / 10);
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		if (delimitersCount == -1)
		{
			int length = 0;
			delimiterPositions.add(Integer.valueOf(0));
			do
			{
				int c = input.read();
				if (c < 0)
					break;
				length++;
				if (c == delimiter)
				{
					delimiterPositions.add(Integer.valueOf(length));
					resultToken.append(replacement);
				} else
				{
					resultToken.append((char)c);
				}
			} while (true);
			delimitersCount = delimiterPositions.size();
			if (((Integer)delimiterPositions.get(delimitersCount - 1)).intValue() < length)
			{
				delimiterPositions.add(Integer.valueOf(length));
				delimitersCount++;
			}
			if (resultTokenBuffer.length < resultToken.length())
				resultTokenBuffer = new char[resultToken.length()];
			resultToken.getChars(0, resultToken.length(), resultTokenBuffer, 0);
			resultToken.setLength(0);
			int idx = delimitersCount - 1 - skip;
			if (idx >= 0)
				endPosition = ((Integer)delimiterPositions.get(idx)).intValue();
			finalOffset = correctOffset(length);
			posAtt.setPositionIncrement(1);
		} else
		{
			posAtt.setPositionIncrement(0);
		}
		if (skipped < delimitersCount - skip - 1)
		{
			int start = ((Integer)delimiterPositions.get(skipped)).intValue();
			termAtt.copyBuffer(resultTokenBuffer, start, endPosition - start);
			offsetAtt.setOffset(correctOffset(start), correctOffset(endPosition));
			skipped++;
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
		super.reset();
		resultToken.setLength(0);
		finalOffset = 0;
		endPosition = 0;
		skipped = 0;
		delimitersCount = -1;
		delimiterPositions.clear();
	}
}
