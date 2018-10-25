// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThaiAnalyzer.java

package org.apache.lucene.analysis.th;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.*;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.th:
//			ThaiWordFilter

public final class ThaiAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = ThaiAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/th/ThaiAnalyzer, "stopwords.txt", "#");
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
	private static final String STOPWORDS_COMMENT = "#";

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public ThaiAnalyzer(Version matchVersion)
	{
		this(matchVersion, matchVersion.onOrAfter(Version.LUCENE_36) ? DefaultSetHolder.DEFAULT_STOP_SET : StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}

	public ThaiAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		super(matchVersion, stopwords);
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, source);
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			result = new LowerCaseFilter(matchVersion, result);
		result = new ThaiWordFilter(matchVersion, result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new StopFilter(matchVersion, result, stopwords));
	}

}
