// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DisjunctionMaxQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.DisjunctionMaxQuery;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DisjunctionMaxQueryBuilder
	implements QueryBuilder
{

	private final QueryBuilder factory;

	public DisjunctionMaxQueryBuilder(QueryBuilder factory)
	{
		this.factory = factory;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		float tieBreaker = DOMUtils.getAttribute(e, "tieBreaker", 0.0F);
		DisjunctionMaxQuery dq = new DisjunctionMaxQuery(tieBreaker);
		dq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++)
		{
			org.w3c.dom.Node node = nl.item(i);
			if (node instanceof Element)
			{
				Element queryElem = (Element)node;
				Query q = factory.getQuery(queryElem);
				dq.add(q);
			}
		}

		return dq;
	}
}
