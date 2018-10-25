// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnglishAnalyzer.java

package org.apache.lucene.analysis.en;

import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.en:
//			EnglishPossessiveFilter, PorterStemFilter

public final class EnglishAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			DEFAULT_STOP_SET = StandardAnalyzer.STOP_WORDS_SET;
		}

		private DefaultSetHolder()
		{
		}
	}


	private final CharArraySet stemExclusionSet;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public EnglishAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public EnglishAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public EnglishAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(matchVersion, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, source);
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			result = new EnglishPossessiveFilter(matchVersion, result);
		result = new LowerCaseFilter(matchVersion, result);
		result = new StopFilter(matchVersion, result, stopwords);
		if (!stemExclusionSet.isEmpty())
			result = new KeywordMarkerFilter(result, stemExclusionSet);
		result = new PorterStemFilter(result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}
}
