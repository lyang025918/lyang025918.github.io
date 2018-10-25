// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternAnalyzer.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

/**
 * @deprecated Class PatternAnalyzer is deprecated
 */

public final class PatternAnalyzer extends Analyzer
{
	static final class FastStringReader extends StringReader
	{

		private final String s;

		String getString()
		{
			return s;
		}

		FastStringReader(String s)
		{
			super(s);
			this.s = s;
		}
	}

	private static final class FastStringTokenizer extends Tokenizer
	{

		private String str;
		private int pos;
		private final boolean isLetter;
		private final boolean toLowerCase;
		private final CharArraySet stopWords;
		private static final Locale locale = Locale.getDefault();
		private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);

		public boolean incrementToken()
		{
			clearAttributes();
			String s = str;
			int len = s.length();
			int i = pos;
			boolean letter = isLetter;
			int start = 0;
			String text;
			do
			{
				text = null;
				for (; i < len && !isTokenChar(s.charAt(i), letter); i++);
				if (i < len)
				{
					start = i;
					for (; i < len && isTokenChar(s.charAt(i), letter); i++);
					text = s.substring(start, i);
					if (toLowerCase)
						text = text.toLowerCase(locale);
				}
			} while (text != null && isStopWord(text));
			pos = i;
			if (text == null)
			{
				return false;
			} else
			{
				termAtt.setEmpty().append(text);
				offsetAtt.setOffset(correctOffset(start), correctOffset(i));
				return true;
			}
		}

		public final void end()
		{
			int finalOffset = str.length();
			offsetAtt.setOffset(correctOffset(finalOffset), correctOffset(finalOffset));
		}

		private boolean isTokenChar(char c, boolean isLetter)
		{
			return isLetter ? Character.isLetter(c) : !Character.isWhitespace(c);
		}

		private boolean isStopWord(String text)
		{
			return stopWords != null && stopWords.contains(text);
		}

		public void reset()
			throws IOException
		{
			super.reset();
			str = PatternAnalyzer.toString(input);
			pos = 0;
		}


		public FastStringTokenizer(Reader input, boolean isLetter, boolean toLowerCase, CharArraySet stopWords)
		{
			super(input);
			this.isLetter = isLetter;
			this.toLowerCase = toLowerCase;
			this.stopWords = stopWords;
		}
	}

	private static final class PatternTokenizer extends Tokenizer
	{

		private final Pattern pattern;
		private String str;
		private final boolean toLowerCase;
		private Matcher matcher;
		private int pos;
		private static final Locale locale = Locale.getDefault();
		private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);

		public final boolean incrementToken()
		{
			if (matcher == null)
				return false;
			clearAttributes();
			boolean isMatch;
			do
			{
				int start = pos;
				isMatch = matcher.find();
				int end;
				if (isMatch)
				{
					end = matcher.start();
					pos = matcher.end();
				} else
				{
					end = str.length();
					matcher = null;
				}
				if (start != end)
				{
					String text = str.substring(start, end);
					if (toLowerCase)
						text = text.toLowerCase(locale);
					termAtt.setEmpty().append(text);
					offsetAtt.setOffset(correctOffset(start), correctOffset(end));
					return true;
				}
			} while (isMatch);
			return false;
		}

		public final void end()
		{
			int finalOffset = correctOffset(str.length());
			offsetAtt.setOffset(finalOffset, finalOffset);
		}

		public void reset()
			throws IOException
		{
			super.reset();
			str = PatternAnalyzer.toString(input);
			matcher = pattern.matcher(str);
			pos = 0;
		}


		public PatternTokenizer(Reader input, Pattern pattern, boolean toLowerCase)
		{
			super(input);
			pos = 0;
			this.pattern = pattern;
			matcher = pattern.matcher("");
			this.toLowerCase = toLowerCase;
		}
	}


	public static final Pattern NON_WORD_PATTERN;
	public static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	private static final CharArraySet EXTENDED_ENGLISH_STOP_WORDS;
	public static final PatternAnalyzer DEFAULT_ANALYZER;
	public static final PatternAnalyzer EXTENDED_ANALYZER;
	private final Pattern pattern;
	private final boolean toLowerCase;
	private final CharArraySet stopWords;
	private final Version matchVersion;

	public PatternAnalyzer(Version matchVersion, Pattern pattern, boolean toLowerCase, CharArraySet stopWords)
	{
		if (pattern == null)
			throw new IllegalArgumentException("pattern must not be null");
		if (eqPattern(NON_WORD_PATTERN, pattern))
			pattern = NON_WORD_PATTERN;
		else
		if (eqPattern(WHITESPACE_PATTERN, pattern))
			pattern = WHITESPACE_PATTERN;
		if (stopWords != null && stopWords.size() == 0)
			stopWords = null;
		this.pattern = pattern;
		this.toLowerCase = toLowerCase;
		this.stopWords = stopWords;
		this.matchVersion = matchVersion;
	}

	public org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader, String text)
	{
		if (reader == null)
			reader = new FastStringReader(text);
		if (pattern == NON_WORD_PATTERN)
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new FastStringTokenizer(reader, true, toLowerCase, stopWords));
		if (pattern == WHITESPACE_PATTERN)
		{
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new FastStringTokenizer(reader, false, toLowerCase, stopWords));
		} else
		{
			Tokenizer tokenizer = new PatternTokenizer(reader, pattern, toLowerCase);
			TokenStream result = ((TokenStream) (stopWords == null ? ((TokenStream) (tokenizer)) : ((TokenStream) (new StopFilter(matchVersion, tokenizer, stopWords)))));
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(tokenizer, result);
		}
	}

	public org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		return createComponents(fieldName, reader, null);
	}

	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (this == DEFAULT_ANALYZER && other == EXTENDED_ANALYZER)
			return false;
		if (other == DEFAULT_ANALYZER && this == EXTENDED_ANALYZER)
			return false;
		if (other instanceof PatternAnalyzer)
		{
			PatternAnalyzer p2 = (PatternAnalyzer)other;
			return toLowerCase == p2.toLowerCase && eqPattern(pattern, p2.pattern) && eq(stopWords, p2.stopWords);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		if (this == DEFAULT_ANALYZER)
			return 0xb760690e;
		if (this == EXTENDED_ANALYZER)
		{
			return 0x4db1f077;
		} else
		{
			int h = 1;
			h = 31 * h + pattern.pattern().hashCode();
			h = 31 * h + pattern.flags();
			h = 31 * h + (toLowerCase ? 1231 : '\u04D5');
			h = 31 * h + (stopWords == null ? 0 : stopWords.hashCode());
			return h;
		}
	}

	private static boolean eq(Object o1, Object o2)
	{
		return o1 == o2 || o1 != null && o1.equals(o2);
	}

	private static boolean eqPattern(Pattern p1, Pattern p2)
	{
		return p1 == p2 || p1.flags() == p2.flags() && p1.pattern().equals(p2.pattern());
	}

	private static String toString(Reader input)
		throws IOException
	{
		if (input instanceof FastStringReader)
			return ((FastStringReader)input).getString();
		String s;
		int len = 256;
		char buffer[] = new char[len];
		char output[] = new char[len];
		int n;
		for (len = 0; (n = input.read(buffer)) >= 0; len += n)
			if (len + n > output.length)
			{
				char tmp[] = new char[Math.max(output.length << 1, len + n)];
				System.arraycopy(output, 0, tmp, 0, len);
				System.arraycopy(buffer, 0, tmp, len, n);
				buffer = output;
				output = tmp;
			} else
			{
				System.arraycopy(buffer, 0, output, len, n);
			}

		s = new String(output, 0, len);
		input.close();
		return s;
		Exception exception;
		exception;
		input.close();
		throw exception;
	}

	static 
	{
		NON_WORD_PATTERN = Pattern.compile("\\W+");
		EXTENDED_ENGLISH_STOP_WORDS = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(new String[] {
			"a", "about", "above", "across", "adj", "after", "afterwards", "again", "against", "albeit", 
			"all", "almost", "alone", "along", "already", "also", "although", "always", "among", "amongst", 
			"an", "and", "another", "any", "anyhow", "anyone", "anything", "anywhere", "are", "around", 
			"as", "at", "be", "became", "because", "become", "becomes", "becoming", "been", "before", 
			"beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "both", "but", 
			"by", "can", "cannot", "co", "could", "down", "during", "each", "eg", "either", 
			"else", "elsewhere", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", 
			"except", "few", "first", "for", "former", "formerly", "from", "further", "had", "has", 
			"have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", 
			"herself", "him", "himself", "his", "how", "however", "i", "ie", "if", "in", 
			"inc", "indeed", "into", "is", "it", "its", "itself", "last", "latter", "latterly", 
			"least", "less", "ltd", "many", "may", "me", "meanwhile", "might", "more", "moreover", 
			"most", "mostly", "much", "must", "my", "myself", "namely", "neither", "never", "nevertheless", 
			"next", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", 
			"of", "off", "often", "on", "once one", "only", "onto", "or", "other", "others", 
			"otherwise", "our", "ours", "ourselves", "out", "over", "own", "per", "perhaps", "rather", 
			"s", "same", "seem", "seemed", "seeming", "seems", "several", "she", "should", "since", 
			"so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", 
			"t", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", 
			"thereafter", "thereby", "therefor", "therein", "thereupon", "these", "they", "this", "those", "though", 
			"through", "throughout", "thru", "thus", "to", "together", "too", "toward", "towards", "under", 
			"until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", 
			"what", "whatever", "whatsoever", "when", "whence", "whenever", "whensoever", "where", "whereafter", "whereas", 
			"whereat", "whereby", "wherefrom", "wherein", "whereinto", "whereof", "whereon", "whereto", "whereunto", "whereupon", 
			"wherever", "wherewith", "whether", "which", "whichever", "whichsoever", "while", "whilst", "whither", "who", 
			"whoever", "whole", "whom", "whomever", "whomsoever", "whose", "whosoever", "why", "will", "with", 
			"within", "without", "would", "xsubj", "xcal", "xauthor", "xother ", "xnote", "yet", "you", 
			"your", "yours", "yourself", "yourselves"
		}), true));
		DEFAULT_ANALYZER = new PatternAnalyzer(Version.LUCENE_CURRENT, NON_WORD_PATTERN, true, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		EXTENDED_ANALYZER = new PatternAnalyzer(Version.LUCENE_CURRENT, NON_WORD_PATTERN, true, EXTENDED_ENGLISH_STOP_WORDS);
	}

}
