// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternReplaceCharFilter.java

package org.apache.lucene.analysis.pattern;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.charfilter.BaseCharFilter;

public class PatternReplaceCharFilter extends BaseCharFilter
{

	/**
	 * @deprecated Field DEFAULT_MAX_BLOCK_CHARS is deprecated
	 */
	public static final int DEFAULT_MAX_BLOCK_CHARS = 10000;
	private final Pattern pattern;
	private final String replacement;
	private Reader transformedInput;

	public PatternReplaceCharFilter(Pattern pattern, String replacement, Reader in)
	{
		super(in);
		this.pattern = pattern;
		this.replacement = replacement;
	}

	/**
	 * @deprecated Method PatternReplaceCharFilter is deprecated
	 */

	public PatternReplaceCharFilter(Pattern pattern, String replacement, int maxBlockChars, String blockDelimiter, Reader in)
	{
		this(pattern, replacement, in);
	}

	public int read(char cbuf[], int off, int len)
		throws IOException
	{
		if (transformedInput == null)
			fill();
		return transformedInput.read(cbuf, off, len);
	}

	private void fill()
		throws IOException
	{
		StringBuilder buffered = new StringBuilder();
		char temp[] = new char[1024];
		for (int cnt = input.read(temp); cnt > 0; cnt = input.read(temp))
			buffered.append(temp, 0, cnt);

		transformedInput = new StringReader(processPattern(buffered).toString());
	}

	public int read()
		throws IOException
	{
		if (transformedInput == null)
			fill();
		return transformedInput.read();
	}

	protected int correct(int currentOff)
	{
		return Math.max(0, super.correct(currentOff));
	}

	CharSequence processPattern(CharSequence input)
	{
		Matcher m = pattern.matcher(input);
		StringBuffer cumulativeOutput = new StringBuffer();
		int cumulative = 0;
		int lastMatchEnd = 0;
		do
		{
			if (!m.find())
				break;
			int groupSize = m.end() - m.start();
			int skippedSize = m.start() - lastMatchEnd;
			lastMatchEnd = m.end();
			int lengthBeforeReplacement = cumulativeOutput.length() + skippedSize;
			m.appendReplacement(cumulativeOutput, replacement);
			int replacementSize = cumulativeOutput.length() - lengthBeforeReplacement;
			if (groupSize != replacementSize)
				if (replacementSize < groupSize)
				{
					cumulative += groupSize - replacementSize;
					int atIndex = lengthBeforeReplacement + replacementSize;
					addOffCorrectMap(atIndex, cumulative);
				} else
				{
					int i = groupSize;
					while (i < replacementSize) 
					{
						addOffCorrectMap(lengthBeforeReplacement + i, --cumulative);
						i++;
					}
				}
		} while (true);
		m.appendTail(cumulativeOutput);
		return cumulativeOutput;
	}
}
