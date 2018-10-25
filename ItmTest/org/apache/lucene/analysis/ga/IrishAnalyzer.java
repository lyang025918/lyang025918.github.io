// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IrishAnalyzer.java

package org.apache.lucene.analysis.ga;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.IrishStemmer;

// Referenced classes of package org.apache.lucene.analysis.ga:
//			IrishLowerCaseFilter

public final class IrishAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = IrishAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/ga/IrishAnalyzer, "stopwords.txt", "#");
			}
			catch (IOException ex)
			{
				throw new RuntimeException("Unable to load default stopword set");
			}
		}

		private DefaultSetHolder()
		{
		}
	}


	private final CharArraySet stemExclusionSet;
	public static final String DEFAULT_STOPWORD_FILE = "stopwords.txt";
	private static final CharArraySet DEFAULT_ARTICLES;
	private static final CharArraySet HYPHENATIONS;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public IrishAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public IrishAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public IrishAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(matchVersion, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, source);
		StopFilter s = new StopFilter(matchVersion, result, HYPHENATIONS);
		s.setEnablePositionIncrements(false);
		result = s;
		result = new ElisionFilter(result, DEFAULT_ARTICLES);
		result = new IrishLowerCaseFilter(result);
		result = new StopFilter(matchVersion, result, stopwords);
		if (!stemExclusionSet.isEmpty())
			result = new KeywordMarkerFilter(result, stemExclusionSet);
		result = new SnowballFilter(result, new IrishStemmer());
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}

	static 
	{
		DEFAULT_ARTICLES = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(new String[] {
			"d", "m", "b"
		}), true));
		HYPHENATIONS = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(new String[] {
			"h", "n", "t"
		}), true));
	}

}
