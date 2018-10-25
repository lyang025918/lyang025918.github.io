// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchAnalyzer.java

package org.apache.lucene.analysis.fr;

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
import org.tartarus.snowball.ext.FrenchStemmer;

// Referenced classes of package org.apache.lucene.analysis.fr:
//			FrenchLightStemFilter, FrenchStemFilter

public final class FrenchAnalyzer extends StopwordAnalyzerBase
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
			DEFAULT_STOP_SET_30 = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(FrenchAnalyzer.FRENCH_STOP_WORDS), false));
			try
			{
				DEFAULT_STOP_SET = WordlistLoader.getSnowballWordSet(IOUtils.getDecodingReader(org/apache/lucene/analysis/snowball/SnowballFilter, "french_stop.txt", IOUtils.CHARSET_UTF_8), Version.LUCENE_CURRENT);
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
	 * @deprecated Field FRENCH_STOP_WORDS is deprecated
	 */
	private static final String FRENCH_STOP_WORDS[] = {
		"a", "afin", "ai", "ainsi", "apr\350s", "attendu", "au", "aujourd", "auquel", "aussi", 
		"autre", "autres", "aux", "auxquelles", "auxquels", "avait", "avant", "avec", "avoir", "c", 
		"car", "ce", "ceci", "cela", "celle", "celles", "celui", "cependant", "certain", "certaine", 
		"certaines", "certains", "ces", "cet", "cette", "ceux", "chez", "ci", "combien", "comme", 
		"comment", "concernant", "contre", "d", "dans", "de", "debout", "dedans", "dehors", "del\340", 
		"depuis", "derri\350re", "des", "d\351sormais", "desquelles", "desquels", "dessous", "dessus", "devant", "devers", 
		"devra", "divers", "diverse", "diverses", "doit", "donc", "dont", "du", "duquel", "durant", 
		"d\350s", "elle", "elles", "en", "entre", "environ", "est", "et", "etc", "etre", 
		"eu", "eux", "except\351", "hormis", "hors", "h\351las", "hui", "il", "ils", "j", 
		"je", "jusqu", "jusque", "l", "la", "laquelle", "le", "lequel", "les", "lesquelles", 
		"lesquels", "leur", "leurs", "lorsque", "lui", "l\340", "ma", "mais", "malgr\351", "me", 
		"merci", "mes", "mien", "mienne", "miennes", "miens", "moi", "moins", "mon", "moyennant", 
		"m\352me", "m\352mes", "n", "ne", "ni", "non", "nos", "notre", "nous", "n\351anmoins", 
		"n\364tre", "n\364tres", "on", "ont", "ou", "outre", "o\371", "par", "parmi", "partant", 
		"pas", "pass\351", "pendant", "plein", "plus", "plusieurs", "pour", "pourquoi", "proche", "pr\350s", 
		"puisque", "qu", "quand", "que", "quel", "quelle", "quelles", "quels", "qui", "quoi", 
		"quoique", "revoici", "revoil\340", "s", "sa", "sans", "sauf", "se", "selon", "seront", 
		"ses", "si", "sien", "sienne", "siennes", "siens", "sinon", "soi", "soit", "son", 
		"sont", "sous", "suivant", "sur", "ta", "te", "tes", "tien", "tienne", "tiennes", 
		"tiens", "toi", "ton", "tous", "tout", "toute", "toutes", "tu", "un", "une", 
		"va", "vers", "voici", "voil\340", "vos", "votre", "vous", "vu", "v\364tre", "v\364tres", 
		"y", "\340", "\347a", "\350s", "\351t\351", "\352tre", "\364"
	};
	public static final String DEFAULT_STOPWORD_FILE = "french_stop.txt";
	public static final CharArraySet DEFAULT_ARTICLES;
	private final CharArraySet excltable;

	public static CharArraySet getDefaultStopSet()
	{
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	public FrenchAnalyzer(Version matchVersion)
	{
		this(matchVersion, matchVersion.onOrAfter(Version.LUCENE_31) ? DefaultSetHolder.DEFAULT_STOP_SET : DefaultSetHolder.DEFAULT_STOP_SET_30);
	}

	public FrenchAnalyzer(Version matchVersion, CharArraySet stopwords)
	{
		this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
	}

	public FrenchAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclutionSet)
	{
		super(matchVersion, stopwords);
		excltable = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stemExclutionSet));
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer source;
		TokenStream result;
		if (matchVersion.onOrAfter(Version.LUCENE_31))
		{
			source = new StandardTokenizer(matchVersion, reader);
			result = new StandardFilter(matchVersion, source);
			result = new ElisionFilter(result, DEFAULT_ARTICLES);
			result = new LowerCaseFilter(matchVersion, result);
			result = new StopFilter(matchVersion, result, stopwords);
			if (!excltable.isEmpty())
				result = new KeywordMarkerFilter(result, excltable);
			if (matchVersion.onOrAfter(Version.LUCENE_36))
				result = new FrenchLightStemFilter(result);
			else
				result = new SnowballFilter(result, new FrenchStemmer());
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, result);
		}
		source = new StandardTokenizer(matchVersion, reader);
		result = new StandardFilter(matchVersion, source);
		result = new StopFilter(matchVersion, result, stopwords);
		if (!excltable.isEmpty())
			result = new KeywordMarkerFilter(result, excltable);
		result = new FrenchStemFilter(result);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new LowerCaseFilter(matchVersion, result));
	}

	static 
	{
		DEFAULT_ARTICLES = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(new String[] {
			"l", "m", "t", "qu", "n", "s", "j"
		}), true));
	}

}
