// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsFilterBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermsFilter;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.BytesRef;
import org.w3c.dom.Element;

public class TermsFilterBuilder
	implements FilterBuilder
{

	private final Analyzer analyzer;

	public TermsFilterBuilder(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public Filter getFilter(Element e)
		throws ParserException
	{
		TermsFilter tf = new TermsFilter();
		String text = DOMUtils.getNonBlankTextOrFail(e);
		String fieldName = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		try
		{
			TokenStream ts = analyzer.tokenStream(fieldName, new StringReader(text));
			TermToBytesRefAttribute termAtt = (TermToBytesRefAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute);
			Term term = null;
			BytesRef bytes = termAtt.getBytesRef();
			ts.reset();
			for (; ts.incrementToken(); tf.addTerm(term))
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
		return tf;
	}
}
