// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BrazilianAnalyzer.java

package org.apache.lucene.analysis.br;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.br:
//			BrazilianStemFilter

public final class BrazilianAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = WordlistLoader.getWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/br/BrazilianAnalyzer, "stopwords.txt", IOUtils.CHARSET_UTF_8), "#", Version.LUCENE_CURRENT);
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


	public static final String DEFAULT_STOPWORD_FILE = "stopwords.txt";
	private CharArraySet excltable;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public BrazilianAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public BrazilianAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		super(matchVersion, stopwords);
		excltable = CharArraySet.EMPTY_SET;
	}

	public BrazilianAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		this(matchVersion, stopwords);
		excltable = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new LowerCaseFilter(matchVersion, source);
		result = new StandardFilter(matchVersion, result);
		result = new StopFilter(matchVersion, result, stopwords);
		if (excltable != null && !excltable.isEmpty())
			result = new KeywordMarkerFilter(result, excltable);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new BrazilianStemFilter(result));
	}
}
