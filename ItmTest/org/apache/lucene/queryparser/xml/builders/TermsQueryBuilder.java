// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.w3c.dom.Element;

public class TermsQueryBuilder
	implements QueryBuilder
{

	private final Analyzer analyzer;

	public TermsQueryBuilder(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		String fieldName = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		String text = DOMUtils.getNonBlankTextOrFail(e);
		BooleanQuery bq = new BooleanQuery(DOMUtils.getAttribute(e, "disableCoord", false));
		bq.setMinimumNumberShouldMatch(DOMUtils.getAttribute(e, "minimumNumberShouldMatch", 0));
		try
		{
			TokenStream ts = analyzer.tokenStream(fieldName, new StringReader(text));
			TermToBytesRefAttribute termAtt = (TermToBytesRefAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute);
			Term term = null;
			BytesRef bytes = termAtt.getBytesRef();
			ts.reset();
			for (; ts.incrementToken(); bq.add(new BooleanClause(new TermQuery(term), org.apache.lucene.search.BooleanClause.Occur.SHOULD)))
			{
				termAtt.fillBytesRef();
				term = new Term(fieldName, BytesRef.deepCopyOf(bytes));
			}

			ts.end();
			ts.close();
		}
		catch (IOException ioe)
		{
			throw new RuntimeException((new StringBuilder()).append("Error constructing terms from index:").append(ioe).toString());
		}
		bq.setBoost(DOMUtils.getAttribute(e, "boost", 1.0F));
		return bq;
	}
}
