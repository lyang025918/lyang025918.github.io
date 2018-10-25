// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostingQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queries.BoostingQuery;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

public class BoostingQueryBuilder
	implements QueryBuilder
{

	private static float DEFAULT_BOOST = 0.01F;
	private final QueryBuilder factory;

	public BoostingQueryBuilder(QueryBuilder factory)
	{
		this.factory = factory;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		Element mainQueryElem = DOMUtils.getChildByTagOrFail(e, "Query");
		mainQueryElem = DOMUtils.getFirstChildOrFail(mainQueryElem);
		Query mainQuery = factory.getQuery(mainQueryElem);
		Element boostQueryElem = DOMUtils.getChildByTagOrFail(e, "BoostQuery");
		float boost = DOMUtils.getAttribute(boostQueryElem, "boost", DEFAULT_BOOST);
		boostQueryElem = DOMUtils.getFirstChildOrFail(boostQueryElem);
		Query boostQuery = factory.getQuery(boostQueryElem);
		BoostingQuery bq = new BoostingQuery(mainQuery, boostQuery, boost);
		bq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return bq;
	}

}
