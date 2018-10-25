// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeQueryBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

public class NumericRangeQueryBuilder
	implements QueryBuilder
{

	public NumericRangeQueryBuilder()
	{
	}

	public Query getQuery(Element e)
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
		Query filter;
		if (type.equalsIgnoreCase("int"))
			filter = NumericRangeQuery.newIntRange(field, precisionStep, Integer.valueOf(lowerTerm), Integer.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
		if (type.equalsIgnoreCase("long"))
			filter = NumericRangeQuery.newLongRange(field, precisionStep, Long.valueOf(lowerTerm), Long.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
		if (type.equalsIgnoreCase("double"))
			filter = NumericRangeQuery.newDoubleRange(field, precisionStep, Double.valueOf(lowerTerm), Double.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
		if (type.equalsIgnoreCase("float"))
			filter = NumericRangeQuery.newFloatRange(field, precisionStep, Float.valueOf(lowerTerm), Float.valueOf(upperTerm), lowerInclusive, upperInclusive);
		else
			throw new ParserException("type attribute must be one of: [long, int, double, float]");
		return filter;
		NumberFormatException nfe;
		nfe;
		throw new ParserException("Could not parse lowerTerm or upperTerm into a number", nfe);
	}
}
