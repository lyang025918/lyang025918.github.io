// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanNotBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.spans.SpanNotQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase, SpanQueryBuilder

public class SpanNotBuilder extends SpanBuilderBase
{

	private final SpanQueryBuilder factory;

	public SpanNotBuilder(SpanQueryBuilder factory)
	{
		this.factory = factory;
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		Element includeElem = DOMUtils.getChildByTagOrFail(e, "Include");
		includeElem = DOMUtils.getFirstChildOrFail(includeElem);
		Element excludeElem = DOMUtils.getChildByTagOrFail(e, "Exclude");
		excludeElem = DOMUtils.getFirstChildOrFail(excludeElem);
		SpanQuery include = factory.getSpanQuery(includeElem);
		SpanQuery exclude = factory.getSpanQuery(excludeElem);
		SpanNotQuery snq = new SpanNotQuery(include, exclude);
		snq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return snq;
	}
}
