// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilteredQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

public class FilteredQueryBuilder
	implements QueryBuilder
{

	private final FilterBuilder filterFactory;
	private final QueryBuilder queryFactory;

	public FilteredQueryBuilder(FilterBuilder filterFactory, QueryBuilder queryFactory)
	{
		this.filterFactory = filterFactory;
		this.queryFactory = queryFactory;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		Element filterElement = DOMUtils.getChildByTagOrFail(e, "Filter");
		filterElement = DOMUtils.getFirstChildOrFail(filterElement);
		org.apache.lucene.search.Filter f = filterFactory.getFilter(filterElement);
		Element queryElement = DOMUtils.getChildByTagOrFail(e, "Query");
		queryElement = DOMUtils.getFirstChildOrFail(queryElement);
		Query q = queryFactory.getQuery(queryElement);
		FilteredQuery fq = new FilteredQuery(q, f);
		fq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return fq;
	}
}
