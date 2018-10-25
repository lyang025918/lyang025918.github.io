// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternReplaceFilter.java

package org.apache.lucene.analysis.pattern;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public final class PatternReplaceFilter extends TokenFilter
{

	private final Pattern p;
	private final String replacement;
	private final boolean all;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final Matcher m;

	public PatternReplaceFilter(TokenStream in, Pattern p, String replacement, boolean all)
	{
		super(in);
		this.p = p;
		this.replacement = null != replacement ? replacement : "";
		this.all = all;
		m = p.matcher(termAtt);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		m.reset();
		if (m.find())
		{
			String transformed = all ? m.replaceAll(replacement) : m.replaceFirst(replacement);
			termAtt.setEmpty().append(transformed);
		}
		return true;
	}
}
