// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKAnalyzer.java

package org.apache.lucene.analysis.cjk;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.cjk:
//			CJKWidthFilter, CJKBigramFilter, CJKTokenizer

public final class CJKAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = CJKAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/cjk/CJKAnalyzer, "stopwords.txt", "#");
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

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public CJKAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public CJKAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		super(matchVersion, stopwords);
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		if (matchVersion.onOrAfter(Version.LUCENE_36))
		{
			org.apache.lucene.analysis.Tokenizer source = new StandardTokenizer(matchVersion, reader);
			org.apache.lucene.analysis.TokenStream result = new CJKWidthFilter(source);
			result = new LowerCaseFilter(matchVersion, result);
			result = new CJKBigramFilter(result);
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new StopFilter(matchVersion, result, stopwords));
		} else
		{
			org.apache.lucene.analysis.Tokenizer source = new CJKTokenizer(reader);
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new StopFilter(matchVersion, source, stopwords));
		}
	}

}
