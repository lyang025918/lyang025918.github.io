// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.queryparser.xml.QueryBuilder;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;

public interface SpanQueryBuilder
	extends QueryBuilder
{

	public abstract SpanQuery getSpanQuery(Element element)
		throws ParserException;
}
