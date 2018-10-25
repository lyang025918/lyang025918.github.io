// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternTokenizer.java

package org.apache.lucene.analysis.pattern;

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public final class PatternTokenizer extends Tokenizer
{

	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private final StringBuilder str = new StringBuilder();
	private int index;
	private final Pattern pattern;
	private final int group;
	private final Matcher matcher;
	final char buffer[] = new char[8192];

	public PatternTokenizer(Reader input, Pattern pattern, int group)
		throws IOException
	{
		super(input);
		this.pattern = pattern;
		this.group = group;
		matcher = pattern.matcher("");
		if (group >= 0 && group > matcher.groupCount())
			throw new IllegalArgumentException((new StringBuilder()).append("invalid group specified: pattern only has: ").append(matcher.groupCount()).append(" capturing groups").toString());
		else
			return;
	}

	public boolean incrementToken()
	{
		if (index >= str.length())
			return false;
		clearAttributes();
		if (group >= 0)
		{
			while (matcher.find()) 
			{
				index = matcher.start(group);
				int endIndex = matcher.end(group);
				if (index != endIndex)
				{
					termAtt.setEmpty().append(str, index, endIndex);
					offsetAtt.setOffset(correctOffset(index), correctOffset(endIndex));
					return true;
				}
			}
			index = 0x7fffffff;
			return false;
		} else
		{
			while (matcher.find()) 
			{
				if (matcher.start() - index > 0)
				{
					termAtt.setEmpty().append(str, index, matcher.start());
					offsetAtt.setOffset(correctOffset(index), correctOffset(matcher.start()));
					index = matcher.end();
					return true;
				}
				index = matcher.end();
			}
			if (str.length() - index == 0)
			{
				index = 0x7fffffff;
				return false;
			} else
			{
				termAtt.setEmpty().append(str, index, str.length());
				offsetAtt.setOffset(correctOffset(index), correctOffset(str.length()));
				index = 0x7fffffff;
				return true;
			}
		}
	}

	public void end()
	{
		int ofs = correctOffset(str.length());
		offsetAtt.setOffset(ofs, ofs);
	}

	public void reset()
		throws IOException
	{
		fillBuffer(str, input);
		matcher.reset(str);
		index = 0;
	}

	private void fillBuffer(StringBuilder sb, Reader input)
		throws IOException
	{
		sb.setLength(0);
		int len;
		while ((len = input.read(buffer)) > 0) 
			sb.append(buffer, 0, len);
	}
}
