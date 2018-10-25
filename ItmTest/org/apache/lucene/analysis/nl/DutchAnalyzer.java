// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DutchAnalyzer.java

package org.apache.lucene.analysis.nl;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.miscellaneous.StemmerOverrideFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.DutchStemmer;

// Referenced classes of package org.apache.lucene.analysis.nl:
//			DutchStemFilter

public final class DutchAnalyzer extends Analyzer
{
	private static class DefaultSetHolder
	{

		static final CharArraySet DEFAULT_STOP_SET;
		static final CharArrayMap DEFAULT_STEM_DICT;

		static 
		{
			try
			{
				DEFAULT_STOP_SET = WordlistLoader.getSnowballWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/snowball/SnowballFilter, "dutch_stop.txt", IOUtils.CHARSET_UTF_8), Version.LUCENE_CURRENT);
			}
			catch (IOException ex)
			{
				throw new RuntimeException("Unable to load default stopword set");
			}
			DEFAULT_STEM_DICT = new CharArrayMap(Version.LUCENE_CURRENT, 4, false);
			DEFAULT_STEM_DICT.put("fiets", "fiets");
			DEFAULT_STEM_DICT.put("bromfiets", "bromfiets");
			DEFAULT_STEM_DICT.put("ei", "eier");
			DEFAULT_STEM_DICT.put("kind", "kinder");
		}

		private DefaultSetHolder()
		{
		}
	}


	public static final String DEFAULT_STOPWORD_FILE = "dutch_stop.txt";
	private final CharArraySet stoptable;
	private CharArraySet excltable;
	private final CharArrayMap stemdict;
	private final Version matchVersion;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public DutchAnalyzer(Version matchVersion)
	{
		this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET, CharArraySet.EMPTY_SET, DefaultSetHolder.DEFAULT_STEM_DICT);
	}

	public DutchAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET, matchVersion.onOrAfter(Version.LUCENE_36) ? DefaultSetHolder.DEFAULT_STEM_DICT : CharArrayMap.emptyMap());
	}

	public DutchAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionTable)
	{
		this(matchVersion, stopwords, stemExclusionTable, matchVersion.onOrAfter(Version.LUCENE_36) ? DefaultSetHolder.DEFAULT_STEM_DICT : CharArrayMap.emptyMap());
	}

	public DutchAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionTable, CharArrayMap stemOverrideDict)
	{
		excltable = CharArraySet.EMPTY_SET;
		this.matchVersion = matchVersion;
		stoptable = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stopwords));
		excltable = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclusionTable));
		stemdict = CharArrayMap.unmodifiableMap(CharArrayMap.copy(matchVersion, stemOverrideDict));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader aReader)
	{
		Tokenizer source;
		TokenStream result;
		if (matchVersion.onOrAfter(Version.LUCENE_31))
		{
			source = new StandardTokenizer(matchVersion, aReader);
			result = new StandardFilter(matchVersion, source);
			result = new LowerCaseFilter(matchVersion, result);
			result = new StopFilter(matchVersion, result, stoptable);
			if (!excltable.isEmpty())
				result = new KeywordMarkerFilter(result, excltable);
			if (!stemdict.isEmpty())
				result = new StemmerOverrideFilter(matchVersion, result, stemdict);
			result = new SnowballFilter(result, new DutchStemmer());
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
		}
		source = new StandardTokenizer(matchVersion, aReader);
		result = new StandardFilter(matchVersion, source);
		result = new StopFilter(matchVersion, result, stoptable);
		if (!excltable.isEmpty())
			result = new KeywordMarkerFilter(result, excltable);
		result = new DutchStemFilter(result, stemdict);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
	}
}
