// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanOrTermsBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.DOMUtils;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.spans.*;
import org.apache.lucene.util.BytesRef;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			SpanBuilderBase

public class SpanOrTermsBuilder extends SpanBuilderBase
{

	private final Analyzer analyzer;

	public SpanOrTermsBuilder(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public SpanQuery getSpanQuery(Element e)
		throws ParserException
	{
		String fieldName;
		String value;
		fieldName = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		value = DOMUtils.getNonBlankTextOrFail(e);
		SpanOrQuery soq;
		List clausesList = new ArrayList();
		TokenStream ts = analyzer.tokenStream(fieldName, new StringReader(value));
		TermToBytesRefAttribute termAtt = (TermToBytesRefAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute);
		BytesRef bytes = termAtt.getBytesRef();
		ts.reset();
		SpanTermQuery stq;
		for (; ts.incrementToken(); clausesList.add(stq))
		{
			termAtt.fillBytesRef();
			stq = new SpanTermQuery(new Term(fieldName, BytesRef.deepCopyOf(bytes)));
		}

		ts.end();
		ts.close();
		soq = new SpanOrQuery((SpanQuery[])clausesList.toArray(new SpanQuery[clausesList.size()]));
		soq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return soq;
		IOException ioe;
		ioe;
		throw new ParserException((new StringBuilder()).append("IOException parsing value:").append(value).toString());
	}
}
