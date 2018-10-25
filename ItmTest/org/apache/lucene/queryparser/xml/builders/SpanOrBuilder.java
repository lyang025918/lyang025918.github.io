// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanOrBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.spans.SpanOrQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase, SpanQueryBuilder

public class SpanOrBuilder extends SpanBuilderBase
{

	private final SpanQueryBuilder factory;

	public SpanOrBuilder(SpanQueryBuilder factory)
	{
		this.factory = factory;
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		List clausesList = new ArrayList();
		for (Node kid = e.getFirstChild(); kid != null; kid = kid.getNextSibling())
			if (kid.getNodeType() == 1)
			{
				SpanQuery clause = factory.getSpanQuery((Element)kid);
				clausesList.add(clause);
			}

		SpanQuery clauses[] = (SpanQuery[])clausesList.toArray(new SpanQuery[clausesList.size()]);
		SpanOrQuery soq = new SpanOrQuery(clauses);
		soq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return soq;
	}
}
