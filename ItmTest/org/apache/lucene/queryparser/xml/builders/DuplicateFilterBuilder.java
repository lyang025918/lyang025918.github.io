// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DuplicateFilterBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.search.Filter;
import org.w3c.dom.Element;

public class DuplicateFilterBuilder
	implements FilterBuilder
{

	public DuplicateFilterBuilder()
	{
	}

	public Filter getFilter(Element e)
		throws ParserException
	{
		String fieldName = DOMUtils.getAttributeWithInheritanceOrFail(e, "fieldName");
		DuplicateFilter df = new DuplicateFilter(fieldName);
		String keepMode = DOMUtils.getAttribute(e, "keepMode", "first");
		if (keepMode.equalsIgnoreCase("first"))
			df.setKeepMode(org.apache.lucene.sandbox.queries.DuplicateFilter.KeepMode.KM_USE_FIRST_OCCURRENCE);
		else
		if (keepMode.equalsIgnoreCase("last"))
			df.setKeepMode(org.apache.lucene.sandbox.queries.DuplicateFilter.KeepMode.KM_USE_LAST_OCCURRENCE);
		else
			throw new ParserException((new StringBuilder()).append("Illegal keepMode attribute in DuplicateFilter:").append(keepMode).toString());
		String processingMode = DOMUtils.getAttribute(e, "processingMode", "full");
		if (processingMode.equalsIgnoreCase("full"))
			df.setProcessingMode(org.apache.lucene.sandbox.queries.DuplicateFilter.ProcessingMode.PM_FULL_VALIDATION);
		else
		if (processingMode.equalsIgnoreCase("fast"))
			df.setProcessingMode(org.apache.lucene.sandbox.queries.DuplicateFilter.ProcessingMode.PM_FAST_INVALIDATION);
		else
			throw new ParserException((new StringBuilder()).append("Illegal processingMode attribute in DuplicateFilter:").append(processingMode).toString());
		return df;
	}
}
