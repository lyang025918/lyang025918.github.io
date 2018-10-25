// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryBuilderFactory.java

package org.apache.lucene.queryparser.xml;

import java.util.HashMap;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml:
//			QueryBuilder, ParserException

public class QueryBuilderFactory
	implements QueryBuilder
{

	HashMap builders;

	public QueryBuilderFactory()
	{
		builders = new HashMap();
	}

	public Query getQuery(Element n)
		throws ParserException
	{
		QueryBuilder builder = (QueryBuilder)builders.get(n.getNodeName());
		if (builder == null)
			throw new ParserException((new StringBuilder()).append("No QueryObjectBuilder defined for node ").append(n.getNodeName()).toString());
		else
			return builder.getQuery(n);
	}

	public void addBuilder(String nodeName, QueryBuilder builder)
	{
		builders.put(nodeName, builder);
	}

	public QueryBuilder getQueryBuilder(String nodeName)
	{
		return (QueryBuilder)builders.get(nodeName);
	}
}
