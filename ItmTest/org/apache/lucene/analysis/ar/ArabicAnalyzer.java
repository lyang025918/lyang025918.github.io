// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicAnalyzer.java

package org.apache.lucene.analysis.ar;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.ar:
//			ArabicLetterTokenizer, ArabicNormalizationFilter, ArabicStemFilter

public final class ArabicAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = ArabicAnalyzer.loadStopwordSet(false, org/apache/lucene/analysis/ar/ArabicAnalyzer, "stopwords.txt", "#");
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
	private final CharArraySet stemExclusionSet;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public ArabicAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	public ArabicAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public ArabicAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(matchVersion, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = ((Tokenizer) (matchVersion.onOrAfter(Version.LUCENE_31) ? ((Tokenizer) (new StandardTokenizer(matchVersion, reader))) : ((Tokenizer) (new ArabicLetterTokenizer(matchVersion, reader)))));
		TokenStream result = new LowerCaseFilter(matchVersion, source);
		result = new StopFilter(matchVersion, result, stopwords);
		result = new ArabicNormalizationFilter(result);
		if (!stemExclusionSet.isEmpty())
			result = new KeywordMarkerFilter(result, stemExclusionSet);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new ArabicStemFilter(result));
	}

}
