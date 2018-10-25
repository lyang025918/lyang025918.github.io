// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanQueryBuilderFactory.java

package org.apache.lucene.queryparser.xml.builders;

import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanQueryBuilder

public class SpanQueryBuilderFactory
	implements SpanQueryBuilder
{

	private final Map builders = new HashMap();

	public SpanQueryBuilderFactory()
	{
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		return getSpanQuery(e);
	}

	public void addBuilder(String nodeName, SpanQueryBuilder builder)
	{
		builders.put(nodeName, builder);
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		SpanQueryBuilder builder = (SpanQueryBuilder)builders.get(e.getNodeName());
		if (builder == null)
			throw new ParserException((new StringBuilder()).append("No SpanQueryObjectBuilder defined for node ").append(e.getNodeName()).toString());
		else
			return builder.getSpanQuery(e);
	}
}
