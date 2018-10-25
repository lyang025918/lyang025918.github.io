// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LikeThisQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

public class LikeThisQueryBuilder
	implements QueryBuilder
{

	private static final int DEFAULT_MAX_QUERY_TERMS = 20;
	private static final int DEFAULT_MIN_TERM_FREQUENCY = 1;
	private static final float DEFAULT_PERCENT_TERMS_TO_MATCH = 30F;
	private final Analyzer analyzer;
	private final String defaultFieldNames[];

	public LikeThisQueryBuilder(Analyzer analyzer, String defaultFieldNames[])
	{
		this.analyzer = analyzer;
		this.defaultFieldNames = defaultFieldNames;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		String fieldsList = e.getAttribute("fieldNames");
		String fields[] = defaultFieldNames;
		if (fieldsList != null && fieldsList.trim().length() > 0)
		{
			fields = fieldsList.trim().split(",");
			for (int i = 0; i < fields.length; i++)
				fields[i] = fields[i].trim();

		}
		String stopWords = e.getAttribute("stopWords");
		Set stopWordsSet = null;
		if (stopWords != null && fields != null)
		{
			stopWordsSet = new HashSet();
			String arr$[] = fields;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String field = arr$[i$];
				try
				{
					TokenStream ts = analyzer.tokenStream(field, new StringReader(stopWords));
					CharTermAttribute termAtt = (CharTermAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
					ts.reset();
					for (; ts.incrementToken(); stopWordsSet.add(termAtt.toString()));
					ts.end();
					ts.close();
				}
				catch (IOException ioe)
				{
					throw new ParserException((new StringBuilder()).append("IoException parsing stop words list in ").append(getClass().getName()).append(":").append(ioe.getLocalizedMessage()).toString());
				}
			}

		}
		MoreLikeThisQuery mlt = new MoreLikeThisQuery(DOMUtils.getText(e), fields, analyzer, fields[0]);
		mlt.setMaxQueryTerms(DOMUtils.getAttribute(e, "maxQueryTerms", 20));
		mlt.setMinTermFrequency(DOMUtils.getAttribute(e, "minTermFrequency", 1));
		mlt.setPercentTermsToMatch(DOMUtils.getAttribute(e, "percentTermsToMatch", 30F) / 100F);
		mlt.setStopWords(stopWordsSet);
		int minDocFreq = DOMUtils.getAttribute(e, "minDocFreq", -1);
		if (minDocFreq >= 0)
			mlt.setMinDocFreq(minDocFreq);
		mlt.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return mlt;
	}
}
