// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConstantScoreQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

public class ConstantScoreQueryBuilder
	implements QueryBuilder
{

	private final FilterBuilderFactory filterFactory;

	public ConstantScoreQueryBuilder(FilterBuilderFactory filterFactory)
	{
		this.filterFactory = filterFactory;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		Element filterElem = DOMUtils.getFirstChildOrFail(e);
		Query q = new ConstantScoreQuery(filterFactory.getFilter(filterElem));
		q.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return q;
	}
}
