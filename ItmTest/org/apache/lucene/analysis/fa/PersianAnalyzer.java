// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersianAnalyzer.java

package org.apache.lucene.analysis.fa;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ar.ArabicLetterTokenizer;
import org.apache.lucene.analysis.ar.ArabicNormalizationFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.fa:
//			PersianNormalizationFilter, PersianCharFilter

public final class PersianAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = PersianAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/fa/PersianAnalyzer, "stopwords.txt", "#");
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
	public static final String STOPWORDS_COMMENT = "#";

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public PersianAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public PersianAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		super(matchVersion, stopwords);
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source;
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			source = new StandardTokenizer(matchVersion, reader);
		else
			source = new ArabicLetterTokenizer(matchVersion, reader);
		org.apache.lucene.analysis.TokenStream result = new LowerCaseFilter(matchVersion, source);
		result = new ArabicNormalizationFilter(result);
		result = new PersianNormalizationFilter(result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new StopFilter(matchVersion, result, stopwords));
	}

	protected Reader initReader(String fieldName, Reader reader)
	{
		return ((Reader) (matchVersion.onOrAfter(Version.LUCENE_31) ? new PersianCharFilter(reader) : reader));
	}

}
