// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParserBase.java

package org.apache.lucene.queryparser.classic;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.standard.CommonQueryParserConfiguration;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.queryparser.classic:
//			FastCharStream, ParseException, TokenMgrError, Token, 
//			QueryParser, CharStream

public abstract class QueryParserBase
	implements CommonQueryParserConfiguration
{
	public static class MethodRemovedUseAnother extends Throwable
	{

		public MethodRemovedUseAnother()
		{
		}
	}


	static final int CONJ_NONE = 0;
	static final int CONJ_AND = 1;
	static final int CONJ_OR = 2;
	static final int MOD_NONE = 0;
	static final int MOD_NOT = 10;
	static final int MOD_REQ = 11;
	public static final QueryParser.Operator AND_OPERATOR;
	public static final QueryParser.Operator OR_OPERATOR;
	QueryParser.Operator operator;
	boolean lowercaseExpandedTerms;
	org.apache.lucene.search.MultiTermQuery.RewriteMethod multiTermRewriteMethod;
	boolean allowLeadingWildcard;
	boolean enablePositionIncrements;
	Analyzer analyzer;
	String field;
	int phraseSlop;
	float fuzzyMinSim;
	int fuzzyPrefixLength;
	Locale locale;
	TimeZone timeZone;
	org.apache.lucene.document.DateTools.Resolution dateResolution;
	Map fieldToDateResolution;
	boolean analyzeRangeTerms;
	boolean autoGeneratePhraseQueries;
	static final boolean $assertionsDisabled = !org/apache/lucene/queryparser/classic/QueryParserBase.desiredAssertionStatus();

	protected QueryParserBase()
	{
		operator = OR_OPERATOR;
		lowercaseExpandedTerms = true;
		multiTermRewriteMethod = MultiTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT;
		allowLeadingWildcard = false;
		enablePositionIncrements = true;
		phraseSlop = 0;
		fuzzyMinSim = 2.0F;
		fuzzyPrefixLength = 0;
		locale = Locale.getDefault();
		timeZone = TimeZone.getDefault();
		dateResolution = null;
		fieldToDateResolution = null;
		analyzeRangeTerms = false;
	}

	public void init(Version matchVersion, String f, Analyzer a)
	{
		analyzer = a;
		field = f;
		if (matchVersion.onOrAfter(Version.LUCENE_31))
			setAutoGeneratePhraseQueries(false);
		else
			setAutoGeneratePhraseQueries(true);
	}

	public abstract void ReInit(CharStream charstream);

	public abstract Query TopLevelQuery(String s)
		throws ParseException;

	public Query parse(String query)
		throws ParseException
	{
		ReInit(new FastCharStream(new StringReader(query)));
		Query res = TopLevelQuery(field);
		return ((Query) (res == null ? newBooleanQuery(false) : res));
		ParseException tme;
		tme;
		ParseException e = new ParseException((new StringBuilder()).append("Cannot parse '").append(query).append("': ").append(tme.getMessage()).toString());
		e.initCause(tme);
		throw e;
		tme;
		ParseException e = new ParseException((new StringBuilder()).append("Cannot parse '").append(query).append("': ").append(tme.getMessage()).toString());
		e.initCause(tme);
		throw e;
		org.apache.lucene.search.BooleanQuery.TooManyClauses tmc;
		tmc;
		ParseException e = new ParseException((new StringBuilder()).append("Cannot parse '").append(query).append("': too many boolean clauses").toString());
		e.initCause(tmc);
		throw e;
	}

	public Analyzer getAnalyzer()
	{
		return analyzer;
	}

	public String getField()
	{
		return field;
	}

	public final boolean getAutoGeneratePhraseQueries()
	{
		return autoGeneratePhraseQueries;
	}

	public final void setAutoGeneratePhraseQueries(boolean value)
	{
		autoGeneratePhraseQueries = value;
	}

	public float getFuzzyMinSim()
	{
		return fuzzyMinSim;
	}

	public void setFuzzyMinSim(float fuzzyMinSim)
	{
		this.fuzzyMinSim = fuzzyMinSim;
	}

	public int getFuzzyPrefixLength()
	{
		return fuzzyPrefixLength;
	}

	public void setFuzzyPrefixLength(int fuzzyPrefixLength)
	{
		this.fuzzyPrefixLength = fuzzyPrefixLength;
	}

	public void setPhraseSlop(int phraseSlop)
	{
		this.phraseSlop = phraseSlop;
	}

	public int getPhraseSlop()
	{
		return phraseSlop;
	}

	public void setAllowLeadingWildcard(boolean allowLeadingWildcard)
	{
		this.allowLeadingWildcard = allowLeadingWildcard;
	}

	public boolean getAllowLeadingWildcard()
	{
		return allowLeadingWildcard;
	}

	public void setEnablePositionIncrements(boolean enable)
	{
		enablePositionIncrements = enable;
	}

	public boolean getEnablePositionIncrements()
	{
		return enablePositionIncrements;
	}

	public void setDefaultOperator(QueryParser.Operator op)
	{
		operator = op;
	}

	public QueryParser.Operator getDefaultOperator()
	{
		return operator;
	}

	public void setLowercaseExpandedTerms(boolean lowercaseExpandedTerms)
	{
		this.lowercaseExpandedTerms = lowercaseExpandedTerms;
	}

	public boolean getLowercaseExpandedTerms()
	{
		return lowercaseExpandedTerms;
	}

	public void setMultiTermRewriteMethod(org.apache.lucene.search.MultiTermQuery.RewriteMethod method)
	{
		multiTermRewriteMethod = method;
	}

	public org.apache.lucene.search.MultiTermQuery.RewriteMethod getMultiTermRewriteMethod()
	{
		return multiTermRewriteMethod;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public void setTimeZone(TimeZone timeZone)
	{
		this.timeZone = timeZone;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public void setDateResolution(org.apache.lucene.document.DateTools.Resolution dateResolution)
	{
		this.dateResolution = dateResolution;
	}

	public void setDateResolution(String fieldName, org.apache.lucene.document.DateTools.Resolution dateResolution)
	{
		if (fieldName == null)
			throw new IllegalArgumentException("Field cannot be null.");
		if (fieldToDateResolution == null)
			fieldToDateResolution = new HashMap();
		fieldToDateResolution.put(fieldName, dateResolution);
	}

	public org.apache.lucene.document.DateTools.Resolution getDateResolution(String fieldName)
	{
		if (fieldName == null)
			throw new IllegalArgumentException("Field cannot be null.");
		if (fieldToDateResolution == null)
			return dateResolution;
		org.apache.lucene.document.DateTools.Resolution resolution = (org.apache.lucene.document.DateTools.Resolution)fieldToDateResolution.get(fieldName);
		if (resolution == null)
			resolution = dateResolution;
		return resolution;
	}

	public void setAnalyzeRangeTerms(boolean analyzeRangeTerms)
	{
		this.analyzeRangeTerms = analyzeRangeTerms;
	}

	public boolean getAnalyzeRangeTerms()
	{
		return analyzeRangeTerms;
	}

	protected void addClause(List clauses, int conj, int mods, Query q)
	{
		if (clauses.size() > 0 && conj == 1)
		{
			BooleanClause c = (BooleanClause)clauses.get(clauses.size() - 1);
			if (!c.isProhibited())
				c.setOccur(org.apache.lucene.search.BooleanClause.Occur.MUST);
		}
		if (clauses.size() > 0 && operator == AND_OPERATOR && conj == 2)
		{
			BooleanClause c = (BooleanClause)clauses.get(clauses.size() - 1);
			if (!c.isProhibited())
				c.setOccur(org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		}
		if (q == null)
			return;
		boolean required;
		boolean prohibited;
		if (operator == OR_OPERATOR)
		{
			prohibited = mods == 10;
			required = mods == 11;
			if (conj == 1 && !prohibited)
				required = true;
		} else
		{
			prohibited = mods == 10;
			required = !prohibited && conj != 2;
		}
		if (required && !prohibited)
			clauses.add(newBooleanClause(q, org.apache.lucene.search.BooleanClause.Occur.MUST));
		else
		if (!required && !prohibited)
			clauses.add(newBooleanClause(q, org.apache.lucene.search.BooleanClause.Occur.SHOULD));
		else
		if (!required && prohibited)
			clauses.add(newBooleanClause(q, org.apache.lucene.search.BooleanClause.Occur.MUST_NOT));
		else
			throw new RuntimeException("Clause cannot be both required and prohibited");
	}

	protected Query getFieldQuery(String field, String queryText, boolean quoted)
		throws ParseException
	{
		return newFieldQuery(analyzer, field, queryText, quoted);
	}

	protected Query newFieldQuery(Analyzer analyzer, String field, String queryText, boolean quoted)
		throws ParseException
	{
		TokenStream source;
		try
		{
			source = analyzer.tokenStream(field, new StringReader(queryText));
			source.reset();
		}
		catch (IOException e)
		{
			ParseException p = new ParseException("Unable to initialize TokenStream to analyze query text");
			p.initCause(e);
			throw p;
		}
		CachingTokenFilter buffer = new CachingTokenFilter(source);
		TermToBytesRefAttribute termAtt = null;
		PositionIncrementAttribute posIncrAtt = null;
		int numTokens = 0;
		buffer.reset();
		if (buffer.hasAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute))
			termAtt = (TermToBytesRefAttribute)buffer.getAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute);
		if (buffer.hasAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute))
			posIncrAtt = (PositionIncrementAttribute)buffer.getAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		int positionCount = 0;
		boolean severalTokensAtSamePosition = false;
		boolean hasMoreTokens = false;
		if (termAtt != null)
			try
			{
				for (hasMoreTokens = buffer.incrementToken(); hasMoreTokens; hasMoreTokens = buffer.incrementToken())
				{
					numTokens++;
					int positionIncrement = posIncrAtt == null ? 1 : posIncrAtt.getPositionIncrement();
					if (positionIncrement != 0)
						positionCount += positionIncrement;
					else
						severalTokensAtSamePosition = true;
				}

			}
			catch (IOException e) { }
		try
		{
			buffer.reset();
			source.close();
		}
		catch (IOException e)
		{
			ParseException p = new ParseException("Cannot close TokenStream analyzing query text");
			p.initCause(e);
			throw p;
		}
		BytesRef bytes = termAtt != null ? termAtt.getBytesRef() : null;
		if (numTokens == 0)
			return null;
		if (numTokens == 1)
		{
			try
			{
				boolean hasNext = buffer.incrementToken();
				if (!$assertionsDisabled && !hasNext)
					throw new AssertionError();
				termAtt.fillBytesRef();
			}
			catch (IOException e) { }
			return newTermQuery(new Term(field, BytesRef.deepCopyOf(bytes)));
		}
		if (severalTokensAtSamePosition || !quoted && !autoGeneratePhraseQueries)
		{
			if (positionCount == 1 || !quoted && !autoGeneratePhraseQueries)
			{
				BooleanQuery q = newBooleanQuery(positionCount == 1);
				org.apache.lucene.search.BooleanClause.Occur occur = positionCount <= 1 || operator != AND_OPERATOR ? org.apache.lucene.search.BooleanClause.Occur.SHOULD : org.apache.lucene.search.BooleanClause.Occur.MUST;
				for (int i = 0; i < numTokens; i++)
				{
					try
					{
						boolean hasNext = buffer.incrementToken();
						if (!$assertionsDisabled && !hasNext)
							throw new AssertionError();
						termAtt.fillBytesRef();
					}
					catch (IOException e) { }
					Query currentQuery = newTermQuery(new Term(field, BytesRef.deepCopyOf(bytes)));
					q.add(currentQuery, occur);
				}

				return q;
			}
			MultiPhraseQuery mpq = newMultiPhraseQuery();
			mpq.setSlop(phraseSlop);
			List multiTerms = new ArrayList();
			int position = -1;
			for (int i = 0; i < numTokens; i++)
			{
				int positionIncrement = 1;
				try
				{
					boolean hasNext = buffer.incrementToken();
					if (!$assertionsDisabled && !hasNext)
						throw new AssertionError();
					termAtt.fillBytesRef();
					if (posIncrAtt != null)
						positionIncrement = posIncrAtt.getPositionIncrement();
				}
				catch (IOException e) { }
				if (positionIncrement > 0 && multiTerms.size() > 0)
				{
					if (enablePositionIncrements)
						mpq.add((Term[])multiTerms.toArray(new Term[0]), position);
					else
						mpq.add((Term[])multiTerms.toArray(new Term[0]));
					multiTerms.clear();
				}
				position += positionIncrement;
				multiTerms.add(new Term(field, BytesRef.deepCopyOf(bytes)));
			}

			if (enablePositionIncrements)
				mpq.add((Term[])multiTerms.toArray(new Term[0]), position);
			else
				mpq.add((Term[])multiTerms.toArray(new Term[0]));
			return mpq;
		}
		PhraseQuery pq = newPhraseQuery();
		pq.setSlop(phraseSlop);
		int position = -1;
		for (int i = 0; i < numTokens; i++)
		{
			int positionIncrement = 1;
			try
			{
				boolean hasNext = buffer.incrementToken();
				if (!$assertionsDisabled && !hasNext)
					throw new AssertionError();
				termAtt.fillBytesRef();
				if (posIncrAtt != null)
					positionIncrement = posIncrAtt.getPositionIncrement();
			}
			catch (IOException e) { }
			if (enablePositionIncrements)
			{
				position += positionIncrement;
				pq.add(new Term(field, BytesRef.deepCopyOf(bytes)), position);
			} else
			{
				pq.add(new Term(field, BytesRef.deepCopyOf(bytes)));
			}
		}

		return pq;
	}

	protected Query getFieldQuery(String field, String queryText, int slop)
		throws ParseException
	{
		Query query = getFieldQuery(field, queryText, true);
		if (query instanceof PhraseQuery)
			((PhraseQuery)query).setSlop(slop);
		if (query instanceof MultiPhraseQuery)
			((MultiPhraseQuery)query).setSlop(slop);
		return query;
	}

	protected Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
		throws ParseException
	{
		if (lowercaseExpandedTerms)
		{
			part1 = part1 != null ? part1.toLowerCase(locale) : null;
			part2 = part2 != null ? part2.toLowerCase(locale) : null;
		}
		DateFormat df = DateFormat.getDateInstance(3, locale);
		df.setLenient(true);
		org.apache.lucene.document.DateTools.Resolution resolution = getDateResolution(field);
		try
		{
			part1 = DateTools.dateToString(df.parse(part1), resolution);
		}
		catch (Exception e) { }
		try
		{
			Date d2 = df.parse(part2);
			if (endInclusive)
			{
				Calendar cal = Calendar.getInstance(timeZone, locale);
				cal.setTime(d2);
				cal.set(11, 23);
				cal.set(12, 59);
				cal.set(13, 59);
				cal.set(14, 999);
				d2 = cal.getTime();
			}
			part2 = DateTools.dateToString(d2, resolution);
		}
		catch (Exception e) { }
		return newRangeQuery(field, part1, part2, startInclusive, endInclusive);
	}

	protected BooleanQuery newBooleanQuery(boolean disableCoord)
	{
		return new BooleanQuery(disableCoord);
	}

	protected BooleanClause newBooleanClause(Query q, org.apache.lucene.search.BooleanClause.Occur occur)
	{
		return new BooleanClause(q, occur);
	}

	protected Query newTermQuery(Term term)
	{
		return new TermQuery(term);
	}

	protected PhraseQuery newPhraseQuery()
	{
		return new PhraseQuery();
	}

	protected MultiPhraseQuery newMultiPhraseQuery()
	{
		return new MultiPhraseQuery();
	}

	protected Query newPrefixQuery(Term prefix)
	{
		PrefixQuery query = new PrefixQuery(prefix);
		query.setRewriteMethod(multiTermRewriteMethod);
		return query;
	}

	protected Query newRegexpQuery(Term regexp)
	{
		RegexpQuery query = new RegexpQuery(regexp);
		query.setRewriteMethod(multiTermRewriteMethod);
		return query;
	}

	protected Query newFuzzyQuery(Term term, float minimumSimilarity, int prefixLength)
	{
		String text = term.text();
		int numEdits = FuzzyQuery.floatToEdits(minimumSimilarity, text.codePointCount(0, text.length()));
		return new FuzzyQuery(term, numEdits, prefixLength);
	}

	private BytesRef analyzeMultitermTerm(String field, String part)
	{
		return analyzeMultitermTerm(field, part, analyzer);
	}

	protected BytesRef analyzeMultitermTerm(String field, String part, Analyzer analyzerIn)
	{
		if (analyzerIn == null)
			analyzerIn = analyzer;
		TokenStream source;
		try
		{
			source = analyzerIn.tokenStream(field, new StringReader(part));
			source.reset();
		}
		catch (IOException e)
		{
			throw new RuntimeException((new StringBuilder()).append("Unable to initialize TokenStream to analyze multiTerm term: ").append(part).toString(), e);
		}
		TermToBytesRefAttribute termAtt = (TermToBytesRefAttribute)source.getAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute);
		BytesRef bytes = termAtt.getBytesRef();
		try
		{
			if (!source.incrementToken())
				throw new IllegalArgumentException((new StringBuilder()).append("analyzer returned no terms for multiTerm term: ").append(part).toString());
			termAtt.fillBytesRef();
			if (source.incrementToken())
				throw new IllegalArgumentException((new StringBuilder()).append("analyzer returned too many terms for multiTerm term: ").append(part).toString());
		}
		catch (IOException e)
		{
			throw new RuntimeException((new StringBuilder()).append("error analyzing range part: ").append(part).toString(), e);
		}
		try
		{
			source.end();
			source.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException((new StringBuilder()).append("Unable to end & close TokenStream after analyzing multiTerm term: ").append(part).toString(), e);
		}
		return BytesRef.deepCopyOf(bytes);
	}

	protected Query newRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
	{
		BytesRef start;
		if (part1 == null)
			start = null;
		else
			start = analyzeRangeTerms ? analyzeMultitermTerm(field, part1) : new BytesRef(part1);
		BytesRef end;
		if (part2 == null)
			end = null;
		else
			end = analyzeRangeTerms ? analyzeMultitermTerm(field, part2) : new BytesRef(part2);
		TermRangeQuery query = new TermRangeQuery(field, start, end, startInclusive, endInclusive);
		query.setRewriteMethod(multiTermRewriteMethod);
		return query;
	}

	protected Query newMatchAllDocsQuery()
	{
		return new MatchAllDocsQuery();
	}

	protected Query newWildcardQuery(Term t)
	{
		WildcardQuery query = new WildcardQuery(t);
		query.setRewriteMethod(multiTermRewriteMethod);
		return query;
	}

	protected Query getBooleanQuery(List clauses)
		throws ParseException
	{
		return getBooleanQuery(clauses, false);
	}

	protected Query getBooleanQuery(List clauses, boolean disableCoord)
		throws ParseException
	{
		if (clauses.size() == 0)
			return null;
		BooleanQuery query = newBooleanQuery(disableCoord);
		BooleanClause clause;
		for (Iterator i$ = clauses.iterator(); i$.hasNext(); query.add(clause))
			clause = (BooleanClause)i$.next();

		return query;
	}

	protected Query getWildcardQuery(String field, String termStr)
		throws ParseException
	{
		if ("*".equals(field) && "*".equals(termStr))
			return newMatchAllDocsQuery();
		if (!allowLeadingWildcard && (termStr.startsWith("*") || termStr.startsWith("?")))
			throw new ParseException("'*' or '?' not allowed as first character in WildcardQuery");
		if (lowercaseExpandedTerms)
			termStr = termStr.toLowerCase(locale);
		Term t = new Term(field, termStr);
		return newWildcardQuery(t);
	}

	protected Query getRegexpQuery(String field, String termStr)
		throws ParseException
	{
		if (lowercaseExpandedTerms)
			termStr = termStr.toLowerCase(locale);
		Term t = new Term(field, termStr);
		return newRegexpQuery(t);
	}

	protected Query getPrefixQuery(String field, String termStr)
		throws ParseException
	{
		if (!allowLeadingWildcard && termStr.startsWith("*"))
			throw new ParseException("'*' not allowed as first character in PrefixQuery");
		if (lowercaseExpandedTerms)
			termStr = termStr.toLowerCase(locale);
		Term t = new Term(field, termStr);
		return newPrefixQuery(t);
	}

	protected Query getFuzzyQuery(String field, String termStr, float minSimilarity)
		throws ParseException
	{
		if (lowercaseExpandedTerms)
			termStr = termStr.toLowerCase(locale);
		Term t = new Term(field, termStr);
		return newFuzzyQuery(t, minSimilarity, fuzzyPrefixLength);
	}

	Query handleBareTokenQuery(String qfield, Token term, Token fuzzySlop, boolean prefix, boolean wildcard, boolean fuzzy, boolean regexp)
		throws ParseException
	{
		String termImage = discardEscapeChar(term.image);
		Query q;
		if (wildcard)
			q = getWildcardQuery(qfield, term.image);
		else
		if (prefix)
			q = getPrefixQuery(qfield, discardEscapeChar(term.image.substring(0, term.image.length() - 1)));
		else
		if (regexp)
			q = getRegexpQuery(qfield, term.image.substring(1, term.image.length() - 1));
		else
		if (fuzzy)
		{
			float fms = fuzzyMinSim;
			try
			{
				fms = Float.valueOf(fuzzySlop.image.substring(1)).floatValue();
			}
			catch (Exception ignored) { }
			if (fms < 0.0F)
				throw new ParseException("Minimum similarity for a FuzzyQuery has to be between 0.0f and 1.0f !");
			if (fms >= 1.0F && fms != (float)(int)fms)
				throw new ParseException("Fractional edit distances are not allowed!");
			q = getFuzzyQuery(qfield, termImage, fms);
		} else
		{
			q = getFieldQuery(qfield, termImage, false);
		}
		return q;
	}

	Query handleQuotedTerm(String qfield, Token term, Token fuzzySlop)
		throws ParseException
	{
		int s = phraseSlop;
		if (fuzzySlop != null)
			try
			{
				s = Float.valueOf(fuzzySlop.image.substring(1)).intValue();
			}
			catch (Exception ignored) { }
		return getFieldQuery(qfield, discardEscapeChar(term.image.substring(1, term.image.length() - 1)), s);
	}

	Query handleBoost(Query q, Token boost)
	{
		if (boost != null)
		{
			float f = 1.0F;
			try
			{
				f = Float.valueOf(boost.image).floatValue();
			}
			catch (Exception ignored) { }
			if (q != null)
				q.setBoost(f);
		}
		return q;
	}

	String discardEscapeChar(String input)
		throws ParseException
	{
		char output[] = new char[input.length()];
		int length = 0;
		boolean lastCharWasEscapeChar = false;
		int codePointMultiplier = 0;
		int codePoint = 0;
		for (int i = 0; i < input.length(); i++)
		{
			char curChar = input.charAt(i);
			if (codePointMultiplier > 0)
			{
				codePoint += hexToInt(curChar) * codePointMultiplier;
				codePointMultiplier >>>= 4;
				if (codePointMultiplier == 0)
				{
					output[length++] = (char)codePoint;
					codePoint = 0;
				}
				continue;
			}
			if (lastCharWasEscapeChar)
			{
				if (curChar == 'u')
				{
					codePointMultiplier = 4096;
				} else
				{
					output[length] = curChar;
					length++;
				}
				lastCharWasEscapeChar = false;
				continue;
			}
			if (curChar == '\\')
			{
				lastCharWasEscapeChar = true;
			} else
			{
				output[length] = curChar;
				length++;
			}
		}

		if (codePointMultiplier > 0)
			throw new ParseException("Truncated unicode escape sequence.");
		if (lastCharWasEscapeChar)
			throw new ParseException("Term can not end with escape character.");
		else
			return new String(output, 0, length);
	}

	static final int hexToInt(char c)
		throws ParseException
	{
		if ('0' <= c && c <= '9')
			return c - 48;
		if ('a' <= c && c <= 'f')
			return (c - 97) + 10;
		if ('A' <= c && c <= 'F')
			return (c - 65) + 10;
		else
			throw new ParseException((new StringBuilder()).append("Non-hex character in Unicode escape sequence: ").append(c).toString());
	}

	public static String escape(String s)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^' || c == '[' || c == ']' || c == '"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?' || c == '|' || c == '&' || c == '/')
				sb.append('\\');
			sb.append(c);
		}

		return sb.toString();
	}

	static 
	{
		AND_OPERATOR = QueryParser.Operator.AND;
		OR_OPERATOR = QueryParser.Operator.OR;
	}
}
