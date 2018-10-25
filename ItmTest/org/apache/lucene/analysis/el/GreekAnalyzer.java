// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GreekAnalyzer.java

package org.apache.lucene.analysis.el;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.el:
//			GreekLowerCaseFilter, GreekStemFilter

public final class GreekAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		private static final CharArraySet DEFAULT_SET;

		static 
		{
			try
			{
				DEFAULT_SET = GreekAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/el/GreekAnalyzer, "stopwords.txt", "#");
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

	public static final CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_SET;
	}

	public GreekAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_SET);
	}

	public GreekAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		super(matchVersion, stopwords);
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new GreekLowerCaseFilter(matchVersion, source);
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			result = new StandardFilter(matchVersion, result);
		result = new StopFilter(matchVersion, result, stopwords);
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			result = new GreekStemFilter(result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}

}
