// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.w3c.dom.Element;

public class TermQueryBuilder
	implements QueryBuilder
{

	public TermQueryBuilder()
	{
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		String field = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		String value = DOMUtils.getNonBlankTextOrFail(e);
		TermQuery tq = new TermQuery(new Term(field, value));
		tq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return tq;
	}
}
