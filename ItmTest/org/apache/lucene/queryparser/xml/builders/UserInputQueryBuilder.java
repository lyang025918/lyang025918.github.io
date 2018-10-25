// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserInputQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.w3c.dom.Element;

public class UserInputQueryBuilder
	implements QueryBuilder
{

	private QueryParser unSafeParser;
	private Analyzer analyzer;
	private String defaultField;

	public UserInputQueryBuilder(QueryParser parser)
	{
		unSafeParser = parser;
	}

	public UserInputQueryBuilder(String defaultField, Analyzer analyzer)
	{
		this.analyzer = analyzer;
		this.defaultField = defaultField;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		String text = DOMUtils.getText(e);
		Query q;
		q = null;
		if (unSafeParser != null)
		{
			synchronized (unSafeParser)
			{
				q = unSafeParser.parse(text);
			}
		} else
		{
			String fieldName = DOMUtils.getAttribute(e, "fieldName", defaultField);
			QueryParser parser = createQueryParser(fieldName, analyzer);
			q = parser.parse(text);
		}
		q.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return q;
		ParseException e1;
		e1;
		throw new ParserException(e1.getMessage());
	}

	protected QueryParser createQueryParser(String fieldName, Analyzer analyzer)
	{
		return new QueryParser(Version.LUCENE_CURRENT, fieldName, analyzer);
	}
}
