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
		"��", "�ҧ֧�", "�ҧ�ݧ֧�", "�ҧ�", "�ҧ��", "�ҧ�ݧ�", "�ҧ�ݧ�", "�ҧ�ݧ�", "�ҧ���", "��", 
		"�ӧѧ�", "�ӧѧ�", "�ӧ֧��", "�ӧ�", "�ӧ��", "�ӧ��", "�ӧ�֧ԧ�", "�ӧ�֧�", "�ӧ�", "�ԧէ�", 
		"�է�", "�էѧا�", "�էݧ�", "�է�", "�֧ԧ�", "�֧�", "�֧�", "�֧�", "�֧�ݧ�", "�֧���", 
		"�֧��", "�ا�", "�٧�", "�٧է֧��", "��", "�ڧ�", "�ڧݧ�", "�ڧ�", "�ڧ�", "��", 
		"�ܧѧ�", "�ܧ�", "�ܧ�ԧէ�", "�ܧ��", "�ݧ�", "�ݧڧҧ�", "�ާߧ�", "�ާ�ا֧�", "�ާ�", "�ߧ�", 
		"�ߧѧէ�", "�ߧѧ�", "�ߧ�", "�ߧ֧ԧ�", "�ߧ֧�", "�ߧ֧�", "�ߧ�", "�ߧڧ�", "�ߧ�", "�ߧ�", 
		"��", "���", "��էߧѧܧ�", "���", "��ߧ�", "��ߧ�", "��ߧ�", "���", "���֧ߧ�", "���", 
		"����", "����", "��", "���", "��ѧ�", "��ѧܧا�", "��ѧܧ��", "��ѧ�", "���", "��֧�", 
		"���", "���ԧ�", "���ا�", "����", "���ݧ�ܧ�", "����", "���", "��", "��ا�", "�����", 
		"��֧ԧ�", "��֧�", "��֧�", "����", "����ҧ�", "����", "����", "����", "����", "����", 
		"��"
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
