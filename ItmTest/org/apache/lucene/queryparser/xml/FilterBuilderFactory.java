// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilterBuilderFactory.java

package org.apache.lucene.queryparser.xml;

import java.util.HashMap;
import org.apache.lucene.search.Filter;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml:
//			FilterBuilder, ParserException

public class FilterBuilderFactory
	implements FilterBuilder
{

	HashMap builders;

	public FilterBuilderFactory()
	{
		builders = new HashMap();
	}

	public Filter getFilter(Element n)
		throws ParserException
	{
		FilterBuilder builder = (FilterBuilder)builders.get(n.getNodeName());
		if (builder == null)
			throw new ParserException((new StringBuilder()).append("No FilterBuilder defined for node ").append(n.getNodeName()).toString());
		else
			return builder.getFilter(n);
	}

	public void addBuilder(String nodeName, FilterBuilder builder)
	{
		builders.put(nodeName, builder);
	}

	public FilterBuilder getFilterBuilder(String nodeName)
	{
		return (FilterBuilder)builders.get(nodeName);
	}
}
