// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RussianAnalyzer.java

package org.apache.lucene.analysis.ru;

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
import org.tartarus.snowball.ext.RussianStemmer;

// Referenced classes of package org.apache.lucene.analysis.ru:
//			RussianLetterTokenizer

public final class RussianAnalyzer extends StopwordAnalyzerBase
{
	private static class DefaultSetHolder
	{

		/**
		 * @deprecated Field DEFAULT_STOP_SET_30 is deprecated
		 */
		static final CharArraySet DEFAULT_STOP_SET_30;
		static final CharArraySet DEFAULT_STOP_SET;

		static 
		{
			DEFAULT_STOP_SET_30 = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(RussianAnalyzer.RUSSIAN_STOP_WORDS_30), false));
			try
			{
				DEFAULT_STOP_SET = WordlistLoader.getSnowballWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/snowball/SnowballFilter, "russian_stop.txt", IOUtils.CHARSET_UTF_8), Version.LUCENE_CURRENT);
			}
			catch (IOException ex)
			{
				throw new RuntimeException("Unable to load default stopword set", ex);
			}
		}

		private DefaultSetHolder()
		{
		}
	}


	/**
	 * @deprecated Field RUSSIAN_STOP_WORDS_30 is deprecated
	 */
	private static final String RUSSIAN_STOP_WORDS_30[] = {
		"§Ñ", "§Ò§Ö§Ù", "§Ò§à§Ý§Ö§Ö", "§Ò§í", "§Ò§í§Ý", "§Ò§í§Ý§Ñ", "§Ò§í§Ý§Ú", "§Ò§í§Ý§à", "§Ò§í§ä§î", "§Ó", 
		"§Ó§Ñ§Þ", "§Ó§Ñ§ã", "§Ó§Ö§ã§î", "§Ó§à", "§Ó§à§ä", "§Ó§ã§Ö", "§Ó§ã§Ö§Ô§à", "§Ó§ã§Ö§ç", "§Ó§í", "§Ô§Õ§Ö", 
		"§Õ§Ñ", "§Õ§Ñ§Ø§Ö", "§Õ§Ý§ñ", "§Õ§à", "§Ö§Ô§à", "§Ö§Ö", "§Ö§Û", "§Ö§ð", "§Ö§ã§Ý§Ú", "§Ö§ã§ä§î", 
		"§Ö§ë§Ö", "§Ø§Ö", "§Ù§Ñ", "§Ù§Õ§Ö§ã§î", "§Ú", "§Ú§Ù", "§Ú§Ý§Ú", "§Ú§Þ", "§Ú§ç", "§Ü", 
		"§Ü§Ñ§Ü", "§Ü§à", "§Ü§à§Ô§Õ§Ñ", "§Ü§ä§à", "§Ý§Ú", "§Ý§Ú§Ò§à", "§Þ§ß§Ö", "§Þ§à§Ø§Ö§ä", "§Þ§í", "§ß§Ñ", 
		"§ß§Ñ§Õ§à", "§ß§Ñ§ê", "§ß§Ö", "§ß§Ö§Ô§à", "§ß§Ö§Ö", "§ß§Ö§ä", "§ß§Ú", "§ß§Ú§ç", "§ß§à", "§ß§å", 
		"§à", "§à§Ò", "§à§Õ§ß§Ñ§Ü§à", "§à§ß", "§à§ß§Ñ", "§à§ß§Ú", "§à§ß§à", "§à§ä", "§à§é§Ö§ß§î", "§á§à", 
		"§á§à§Õ", "§á§â§Ú", "§ã", "§ã§à", "§ä§Ñ§Ü", "§ä§Ñ§Ü§Ø§Ö", "§ä§Ñ§Ü§à§Û", "§ä§Ñ§Þ", "§ä§Ö", "§ä§Ö§Þ", 
		"§ä§à", "§ä§à§Ô§à", "§ä§à§Ø§Ö", "§ä§à§Û", "§ä§à§Ý§î§Ü§à", "§ä§à§Þ", "§ä§í", "§å", "§å§Ø§Ö", "§ç§à§ä§ñ", 
		"§é§Ö§Ô§à", "§é§Ö§Û", "§é§Ö§Þ", "§é§ä§à", "§é§ä§à§Ò§í", "§é§î§Ö", "§é§î§ñ", "§ï§ä§Ñ", "§ï§ä§Ú", "§ï§ä§à", 
		"§ñ"
	};
	public static final String DEFAULT_STOPWORD_FILE = "russian_stop.txt";
	private final CharArraySet stemExclusionSet;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public RussianAnalyzer(Version matchVersion)
	{
		this(matchVersion, matchVersion.onOrAfter(Version.LUCENE_31) ? DefaultSetHolder.DEFAULT_STOP_SET : DefaultSetHolder.DEFAULT_STOP_SET_30);
	}

	public RussianAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public RussianAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet)
	{
		super(matchVersion, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source;
		TokenStream result;
		if (matchVersion.onOrAfter(Version.LUCENE_31))
		{
			source = new StandardTokenizer(matchVersion, reader);
			result = new StandardFilter(matchVersion, source);
			result = new LowerCaseFilter(matchVersion, result);
			result = new StopFilter(matchVersion, result, stopwords);
			if (!stemExclusionSet.isEmpty())
				result = new KeywordMarkerFilter(result, stemExclusionSet);
			result = new SnowballFilter(result, new RussianStemmer());
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
		}
		source = new RussianLetterTokenizer(matchVersion, reader);
		result = new LowerCaseFilter(matchVersion, source);
		result = new StopFilter(matchVersion, result, stopwords);
		if (!stemExclusionSet.isEmpty())
			result = new KeywordMarkerFilter(result, stemExclusionSet);
		result = new SnowballFilter(result, new RussianStemmer());
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}


}
