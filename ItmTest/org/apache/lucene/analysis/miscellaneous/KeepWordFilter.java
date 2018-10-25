// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeepWordFilter.java

package org.apache.lucene.analysis.miscellaneous;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public final class KeepWordFilter extends FilteringTokenFilter
{

	private final CharArraySet words;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public KeepWordFilter(boolean enablePositionIncrements, TokenStream in, CharArraySet words)
	{
		super(enablePositionIncrements, in);
		this.words = words;
	}

	public boolean accept()
	{
		return words.contains(termAtt.buffer(), 0, termAtt.length());
	}
}
