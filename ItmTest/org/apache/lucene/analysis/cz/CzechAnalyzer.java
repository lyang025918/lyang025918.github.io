// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CzechAnalyzer.java

package org.apache.lucene.analysis.cz;

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

// Referenced classes of package org.apache.lucene.analysis.cz:
//			CzechStemFilter

public final class CzechAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		private static final CharArraySet DEFAULT_SET;

		static 
		{
			try
			{
				DEFAULT_SET = WordlistLoader.getWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/cz/CzechAnalyzer, "stopwords.txt", IOUtils.CHARSET_UTF_8), "#", Version.LUCENE_CURRENT);
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
	private final CharArraySet stemExclusionTable;

	public static final CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_SET;
	}

	public CzechAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_SET);
	}

	public CzechAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public CzechAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionTable)
	{
		super(matchVersion, stopwords);
		this.stemExclusionTable = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionTable));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, source);
		result = new LowerCaseFilter(matchVersion, result);
		result = new StopFilter(matchVersion, result, stopwords);
		if (matchVersion.onOrAfter(Version.LUCENE_31))
		{
			if (!stemExclusionTable.isEmpty())
				result = new KeywordMarkerFilter(result, stemExclusionTable);
			result = new CzechStemFilter(result);
		}
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}
}
