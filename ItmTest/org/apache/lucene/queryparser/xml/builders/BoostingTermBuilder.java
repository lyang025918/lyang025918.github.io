// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostingTermBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.payloads.AveragePayloadFunction;
import org.apache.lucene.search.payloads.PayloadTermQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase

public class BoostingTermBuilder extends SpanBuilderBase
{

	public BoostingTermBuilder()
	{
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		String fieldName = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		String value = DOMUtils.getNonBlankTextOrFail(e);
		PayloadTermQuery btq = new PayloadTermQuery(new Term(fieldName, value), new AveragePayloadFunction());
		btq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return btq;
	}
}
