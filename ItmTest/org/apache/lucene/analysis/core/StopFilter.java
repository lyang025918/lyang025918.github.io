// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StopFilter.java

package org.apache.lucene.analysis.core;

import java.util.Arrays;
import java.util.List;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;
import org.apache.lucene.util.Version;

public final class StopFilter extends FilteringTokenFilter
{

	private final CharArraySet stopWords;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public StopFilter(Version matchVersion, TokenStream in, CharArraySet stopWords)
	{
		super(true, in);
		this.stopWords = stopWords;
	}

	public static transient CharArraySet makeStopSet(Version matchVersion, String stopWords[])
	{
		return makeStopSet(matchVersion, stopWords, false);
	}

	public static CharArraySet makeStopSet(Version matchVersion, List stopWords)
	{
		return makeStopSet(matchVersion, stopWords, false);
	}

	public static CharArraySet makeStopSet(Version matchVersion, String stopWords[], boolean ignoreCase)
	{
		CharArraySet stopSet = new CharArraySet(matchVersion, stopWords.length, ignoreCase);
		stopSet.addAll(Arrays.asList(stopWords));
		return stopSet;
	}

	public static CharArraySet makeStopSet(Version matchVersion, List stopWords, boolean ignoreCase)
	{
		CharArraySet stopSet = new CharArraySet(matchVersion, stopWords.size(), ignoreCase);
		stopSet.addAll(stopWords);
		return stopSet;
	}

	protected boolean accept()
	{
		return !stopWords.contains(termAtt.buffer(), 0, termAtt.length());
	}
}
