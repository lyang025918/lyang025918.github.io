// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanBuilderBase.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanQueryBuilder

public abstract class SpanBuilderBase
	implements SpanQueryBuilder
{

	public SpanBuilderBase()
	{
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		return getSpanQuery(e);
	}
}
