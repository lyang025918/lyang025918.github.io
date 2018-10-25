// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanTermBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase

public class SpanTermBuilder extends SpanBuilderBase
{

	public SpanTermBuilder()
	{
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		String fieldName = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		String value = DOMUtils.getNonBlankTextOrFail(e);
		SpanTermQuery stq = new SpanTermQuery(new Term(fieldName, value));
		stq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return stq;
	}
}
