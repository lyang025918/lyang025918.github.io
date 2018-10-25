// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RangeFilterBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.TermRangeFilter;
import org.w3c.dom.Element;

public class RangeFilterBuilder
	implements FilterBuilder
{

	public RangeFilterBuilder()
	{
	}

	public Filter getFilter(Element e)
		throws ParserException
	{
		String fieldName = DOMUtils.getAttributeWithInheritance(e, "fieldName");
		String lowerTerm = e.getAttribute("lowerTerm");
		String upperTerm = e.getAttribute("upperTerm");
		boolean includeLower = DOMUtils.getAttribute(e, "includeLower", true);
		boolean includeUpper = DOMUtils.getAttribute(e, "includeUpper", true);
		return TermRangeFilter.newStringRange(fieldName, lowerTerm, upperTerm, includeLower, includeUpper);
	}
}
