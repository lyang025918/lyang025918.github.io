// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LengthFilter.java

package org.apache.lucene.analysis.miscellaneous;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public final class LengthFilter extends FilteringTokenFilter
{

	private final int min;
	private final int max;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public LengthFilter(boolean enablePositionIncrements, TokenStream in, int min, int max)
	{
		super(enablePositionIncrements, in);
		this.min = min;
		this.max = max;
	}

	public boolean accept()
	{
		int len = termAtt.length();
		return len >= min && len <= max;
	}
}
