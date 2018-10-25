// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StopAnalyzer.java

package org.apache.lucene.analysis.core;

import java.io.*;
import java.util.Arrays;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.core:
//			LowerCaseTokenizer, StopFilter

public final class StopAnalyzer extends StopwordAnalyzerBase
{

	public static final CharArraySet ENGLISH_STOP_WORDS_SET;

	public StopAnalyzer(Version matchVersion)
	{
		this(matchVersion, ENGLISH_STOP_WORDS_SET);
	}

	public StopAnalyzer(Version matchVersion, CharArraySet stopWords)
	{
		super(matchVersion, stopWords);
	}

	public StopAnalyzer(Version matchVersion, File stopwordsFile)
		throws IOException
	{
		this(matchVersion, loadStopwordSet(stopwordsFile, matchVersion));
	}

	public StopAnalyzer(Version matchVersion, Reader stopwords)
		throws IOException
	{
		this(matchVersion, loadStopwordSet(stopwords, matchVersion));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		org.apache.lucene.analysis.Tokenizer source = new LowerCaseTokenizer(matchVersion, reader);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new StopFilter(matchVersion, source, stopwords));
	}

	static 
	{
		java.util.List stopWords = Arrays.asList(new String[] {
			"a", "an", "and", "are", "as", "at", "be", "but", "by", "for", 
			"if", "in", "into", "is", "it", "no", "not", "of", "on", "or", 
			"such", "that", "the", "their", "then", "there", "these", "they", "this", "to", 
			"was", "will", "with"
		});
		CharArraySet stopSet = new CharArraySet(Version.LUCENE_CURRENT, stopWords, false);
		ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
	}
}
