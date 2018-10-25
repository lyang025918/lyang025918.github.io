// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanAnalyzer.java

package org.apache.lucene.analysis.de;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
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
import org.tartarus.snowball.ext.German2Stemmer;

// Referenced classes of package org.apache.lucene.analysis.de:
//			GermanNormalizationFilter, GermanLightStemFilter, GermanStemFilter

public final class GermanAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		/**
		 * @deprecated Field DEFAULT_SET_30 is deprecated
		 */
		private static final CharArraySet DEFAULT_SET_30;
		private static final CharArraySet DEFAULT_SET;

		static 
		{
			DEFAULT_SET_30 = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(GermanAnalyzer.GERMAN_STOP_WORDS), false));
			try
			{
				DEFAULT_SET = WordlistLoader.getSnowballWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/snowball/SnowballFilter, "german_stop.txt", IOUtils.CHARSET_UTF_8), Version.LUCENE_CURRENT);
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


	/**
	 * @deprecated Field GERMAN_STOP_WORDS is deprecated
	 */
	private static final String GERMAN_STOP_WORDS[] = {
		"einer", "eine", "eines", "einem", "einen", "der", "die", "das", "dass", "da\337", 
		"du", "er", "sie", "es", "was", "wer", "wie", "wir", "und", "oder", 
		"ohne", "mit", "am", "im", "in", "aus", "auf", "ist", "sein", "war", 
		"wird", "ihr", "ihre", "ihres", "als", "f\374r", "von", "mit", "dich", "dir", 
		"mich", "mir", "mein", "sein", "kein", "durch", "wegen", "wird"
	};
	public static final String DEFAULT_STOPWORD_FILE = "german_stop.txt";
	private final CharArraySet exclusionSet;

	public static final CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_SET;
	}

	public GermanAnalyzer(Version matchVersion)
	{
		this(matchVersion, matchVersion.onOrAfter(Version.LUCENE_31) ? DefaultSetHolder.DEFAULT_SET : DefaultSetHolder.DEFAULT_SET_30);
	}

	public GermanAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public GermanAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(matchVersion, stopwords);
		exclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, source);
		result = new LowerCaseFilter(matchVersion, result);
		result = new StopFilter(matchVersion, result, stopwords);
		result = new KeywordMarkerFilter(result, exclusionSet);
		if (matchVersion.onOrAfter(Version.LUCENE_36))
		{
			result = new GermanNormalizationFilter(result);
			result = new GermanLightStemFilter(result);
		} else
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			result = new SnowballFilter(result, new German2Stemmer());
		else
			result = new GermanStemFilter(result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}


}
