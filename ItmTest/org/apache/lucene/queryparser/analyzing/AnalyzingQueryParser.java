// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnalyzingQueryParser.java

package org.apache.lucene.queryparser.analyzing;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

public class AnalyzingQueryParser extends QueryParser
{

	public AnalyzingQueryParser(Version matchVersion, String field, Analyzer analyzer)
	{
		super(matchVersion, field, analyzer);
		setAnalyzeRangeTerms(true);
	}

	protected Query getWildcardQuery(String field, String termStr)
		throws ParseException
	{
		List tlist = new ArrayList();
		List wlist = new ArrayList();
		boolean isWithinToken = !termStr.startsWith("?") && !termStr.startsWith("*");
		StringBuilder tmpBuffer = new StringBuilder();
		char chars[] = termStr.toCharArray();
		for (int i = 0; i < termStr.length(); i++)
		{
			if (chars[i] == '?' || chars[i] == '*')
			{
				if (isWithinToken)
				{
					tlist.add(tmpBuffer.toString());
					tmpBuffer.setLength(0);
				}
				isWithinToken = false;
			} else
			{
				if (!isWithinToken)
				{
					wlist.add(tmpBuffer.toString());
					tmpBuffer.setLength(0);
				}
				isWithinToken = true;
			}
			tmpBuffer.append(chars[i]);
		}

		if (isWithinToken)
			tlist.add(tmpBuffer.toString());
		else
			wlist.add(tmpBuffer.toString());
		int countTokens = 0;
		TokenStream source;
		try
		{
			source = getAnalyzer().tokenStream(field, new StringReader(termStr));
			source.reset();
		}
		catch (IOException e1)
		{
			throw new RuntimeException(e1);
		}
		CharTermAttribute termAtt = (CharTermAttribute)source.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		do
		{
			try
			{
				if (!source.incrementToken())
					break;
			}
			catch (IOException e)
			{
				break;
			}
			String term = termAtt.toString();
			if (!"".equals(term))
				try
				{
					tlist.set(countTokens++, term);
				}
				catch (IndexOutOfBoundsException ioobe)
				{
					countTokens = -1;
				}
		} while (true);
		try
		{
			source.end();
			source.close();
		}
		catch (IOException e) { }
		if (countTokens != tlist.size())
			throw new ParseException((new StringBuilder()).append("Cannot build WildcardQuery with analyzer ").append(getAnalyzer().getClass()).append(" - tokens added or lost").toString());
		if (tlist.size() == 0)
			return null;
		if (tlist.size() == 1)
			if (wlist != null && wlist.size() == 1)
				return super.getWildcardQuery(field, (new StringBuilder()).append((String)tlist.get(0)).append(((String)wlist.get(0)).toString()).toString());
			else
				throw new IllegalArgumentException("getWildcardQuery called without wildcard");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tlist.size(); i++)
		{
			sb.append((String)tlist.get(i));
			if (wlist != null && wlist.size() > i)
				sb.append((String)wlist.get(i));
		}

		return super.getWildcardQuery(field, sb.toString());
	}

	protected Query getPrefixQuery(String field, String termStr)
		throws ParseException
	{
		TokenStream source;
		List tlist;
		CharTermAttribute termAtt;
		tlist = new ArrayList();
		try
		{
			source = getAnalyzer().tokenStream(field, new StringReader(termStr));
			source.reset();
		}
		catch (IOException e1)
		{
			throw new RuntimeException(e1);
		}
		termAtt = (CharTermAttribute)source.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		IOException e;
		for (; source.incrementToken(); tlist.add(termAtt.toString()));
		break MISSING_BLOCK_LABEL_91;
		e;
		try
		{
			source.end();
			source.close();
		}
		catch (IOException e) { }
		if (tlist.size() == 1)
			return super.getPrefixQuery(field, (String)tlist.get(0));
		else
			throw new ParseException((new StringBuilder()).append("Cannot build PrefixQuery with analyzer ").append(getAnalyzer().getClass()).append(tlist.size() <= 1 ? " - token consumed" : " - token(s) added").toString());
	}

	protected Query getFuzzyQuery(String field, String termStr, float minSimilarity)
		throws ParseException
	{
		TokenStream source = null;
		String nextToken = null;
		boolean multipleTokens = false;
		try
		{
			source = getAnalyzer().tokenStream(field, new StringReader(termStr));
			CharTermAttribute termAtt = (CharTermAttribute)source.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			source.reset();
			if (source.incrementToken())
				nextToken = termAtt.toString();
			multipleTokens = source.incrementToken();
		}
		catch (IOException e)
		{
			nextToken = null;
		}
		try
		{
			source.end();
			source.close();
		}
		catch (IOException e) { }
		if (multipleTokens)
			throw new ParseException((new StringBuilder()).append("Cannot build FuzzyQuery with analyzer ").append(getAnalyzer().getClass()).append(" - tokens were added").toString());
		else
			return nextToken != null ? super.getFuzzyQuery(field, nextToken, minSimilarity) : null;
	}
}
