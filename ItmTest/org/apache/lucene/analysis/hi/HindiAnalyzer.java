// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HindiAnalyzer.java

package org.apache.lucene.analysis.hi;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.in.IndicNormalizationFilter;
import org.apache.lucene.analysis.in.IndicTokenizer;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.hi:
//			HindiNormalizationFilter, HindiStemFilter

public final class HindiAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = HindiAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/hi/HindiAnalyzer, "stopwords.txt", "#");
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
	private static final String STOPWORDS_COMMENT = "#";

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public HindiAnalyzer(Version version, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(version, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	public HindiAnalyzer(Version version, CharArraySet stopwords)
	{
		this(version, stopwords, CharArraySet.EMPTY_SET);
	}

	public HindiAnalyzer(Version version)
	{
		this(version, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source;
		if (matchVersion.onOrAfter(Version.LUCENE_36))
			source = new StandardTokenizer(matchVersion, reader);
		else
			source = new IndicTokenizer(matchVersion, reader);
		TokenStream result = new LowerCaseFilter(matchVersion, source);
		if (!stemExclusionSet.isEmpty())
			result = new KeywordMarkerFilter(result, stemExclusionSet);
		result = new IndicNormalizationFilter(result);
		result = new HindiNormalizationFilter(result);
		result = new StopFilter(matchVersion, result, stopwords);
		result = new HindiStemFilter(result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}

}
