// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.*;
import org.w3c.dom.*;

public class BooleanQueryBuilder
	implements QueryBuilder
{

	private final QueryBuilder factory;

	public BooleanQueryBuilder(QueryBuilder factory)
	{
		this.factory = factory;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		BooleanQuery bq = new BooleanQuery(DOMUtils.getAttribute(e, "disableCoord", false));
		bq.setMinimumNumberShouldMatch(DOMUtils.getAttribute(e, "minimumNumberShouldMatch", 0));
		bq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++)
		{
			Node node = nl.item(i);
			if (node.getNodeName().equals("Clause"))
			{
				Element clauseElem = (Element)node;
				org.apache.lucene.search.BooleanClause.Occur occurs = getOccursValue(clauseElem);
				Element clauseQuery = DOMUtils.getFirstChildOrFail(clauseElem);
				Query q = factory.getQuery(clauseQuery);
				bq.add(new BooleanClause(q, occurs));
			}
		}

		return bq;
	}

	static org.apache.lucene.search.BooleanClause.Occur getOccursValue(Element clauseElem)
		throws ParserException
	{
		String occs = clauseElem.getAttribute("occurs");
		org.apache.lucene.search.BooleanClause.Occur occurs = org.apache.lucene.search.BooleanClause.Occur.SHOULD;
		if ("must".equalsIgnoreCase(occs))
			occurs = org.apache.lucene.search.BooleanClause.Occur.MUST;
		else
		if ("mustNot".equalsIgnoreCase(occs))
			occurs = org.apache.lucene.search.BooleanClause.Occur.MUST_NOT;
		else
		if ("should".equalsIgnoreCase(occs) || "".equals(occs))
			occurs = org.apache.lucene.search.BooleanClause.Occur.SHOULD;
		else
		if (occs != null)
			throw new ParserException((new StringBuilder()).append("Invalid value for \"occurs\" attribute of clause:").append(occs).toString());
		return occurs;
	}
}
