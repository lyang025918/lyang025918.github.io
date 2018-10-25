// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanFirstBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.spans.SpanFirstQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase, SpanQueryBuilder

public class SpanFirstBuilder extends SpanBuilderBase
{

	private final SpanQueryBuilder factory;

	public SpanFirstBuilder(SpanQueryBuilder factory)
	{
		this.factory = factory;
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		int end = DOMUtils.getAttribute(e, "end", 1);
		Element child = DOMUtils.getFirstChildElement(e);
		SpanQuery q = factory.getSpanQuery(child);
		SpanFirstQuery sfq = new SpanFirstQuery(q, end);
		sfq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return sfq;
	}
}
