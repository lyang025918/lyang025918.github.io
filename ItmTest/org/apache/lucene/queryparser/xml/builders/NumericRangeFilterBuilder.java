// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeFilterBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.w3c.dom.Element;

public class NumericRangeFilterBuilder
	implements FilterBuilder
{
	static class NoMatchFilter extends Filter
	{

		public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
			throws IOException
		{
			return null;
		}

		NoMatchFilter()
		{
		}
	}


	private static final NoMatchFilter NO_MATCH_FILTER = new NoMatchFilter();
	private boolean strictMode;

	public NumericRangeFilterBuilder()
	{
		strictMode = false;
	}

	public void setStrictMode(boolean strictMode)
	{
		this.strictMode = strictMode;
	}

	public Filter getFilter(Element e)
		throws ParserException
	{
		String field;
		String lowerTerm;
		String upperTerm;
		boolean lowerInclusive;
		boolean upperInclusive;
		int precisionStep;
		String type;
		field = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		lowerTerm = DOMUtils.getAttributeOrFail(e, "lowerTerm");
		upperTerm = DOMUtils.getAttributeOrFail(e, "upperTerm");
		lowerInclusive = DOMUtils.getAttribute(e, "includeLower", true);
		upperInclusive = DOMUtils.getAttribute(e, "includeUpper", true);
		precisionStep = DOMUtils.getAttribute(e, "precisionStep", 4);
		type = DOMUtils.getAttribute(e, "type", "int");
		Filter filter;
		if (type.equalsIgnoreCase("int"))
			filter = NumericRangeFilter.newIntRange(field, precisionStep, Integer.valueOf(lowerTerm), Integer.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
		if (type.equalsIgnoreCase("long"))
			filter = NumericRangeFilter.newLongRange(field, precisionStep, Long.valueOf(lowerTerm), Long.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
		if (type.equalsIgnoreCase("double"))
			filter = NumericRangeFilter.newDoubleRange(field, precisionStep, Double.valueOf(lowerTerm), Double.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
		if (type.equalsIgnoreCase("float"))
			filter = NumericRangeFilter.newFloatRange(field, precisionStep, Float.valueOf(lowerTerm), Float.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
			throw new ParserException("type attribute must be one of: [long, int, double, float]");
		return filter;
		NumberFormatException nfe;
		nfe;
		if (strictMode)
			throw new ParserException("Could not parse lowerTerm or upperTerm into a number", nfe);
		else
			return NO_MATCH_FILTER;
	}

}
