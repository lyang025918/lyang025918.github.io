// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanNearBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase, SpanQueryBuilder

public class SpanNearBuilder extends SpanBuilderBase
{

	private final SpanQueryBuilder factory;

	public SpanNearBuilder(SpanQueryBuilder factory)
	{
		this.factory = factory;
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		String slopString = DOMUtils.getAttributeOrFail(e, "slop");
		int slop = Integer.parseInt(slopString);
		boolean inOrder = DOMUtils.getAttribute(e, "inOrder", false);
		List spans = new ArrayList();
		for (Node kid = e.getFirstChild(); kid != null; kid = kid.getNextSibling())
			if (kid.getNodeType() == 1)
				spans.add(factory.getSpanQuery((Element)kid));

		SpanQuery spanQueries[] = (SpanQuery[])spans.toArray(new SpanQuery[spans.size()]);
		return new SpanNearQuery(spanQueries, slop, inOrder);
	}
}
