// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PortugueseAnalyzer.java

package org.apache.lucene.analysis.pt;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.PortugueseStemmer;

// Referenced classes of package org.apache.lucene.analysis.pt:
//			PortugueseLightStemFilter

public final class PortugueseAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = WordlistLoader.getSnowballWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/snowball/SnowballFilter, "portuguese_stop.txt", IOUtils.CHARSET_UTF_8), Version.LUCENE_CURRENT);
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
	public static final String DEFAULT_STOPWORD_FILE = "portuguese_stop.txt";

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public PortugueseAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public PortugueseAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public PortugueseAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(matchVersion, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, source);
		result = new LowerCaseFilter(matchVersion, result);
		result = new StopFilter(matchVersion, result, stopwords);
		if (!stemExclusionSet.isEmpty())
			result = new KeywordMarkerFilter(result, stemExclusionSet);
		if (matchVersion.onOrAfter(Version.LUCENE_36))
			result = new PortugueseLightStemFilter(result);
		else
			result = new SnowballFilter(result, new PortugueseStemmer());
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}
}
