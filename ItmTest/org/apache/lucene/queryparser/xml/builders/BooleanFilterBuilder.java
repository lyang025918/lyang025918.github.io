// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanFilterBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import org.apache.lucene.queries.BooleanFilter;
import org.apache.lucene.queries.FilterClause;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Filter;
import org.w3c.dom.*;

// Referenced classes of package org.apache.lucene.queryparser.xml.builders:
//			BooleanQueryBuilder

public class BooleanFilterBuilder
	implements FilterBuilder
{

	private final FilterBuilder factory;

	public BooleanFilterBuilder(FilterBuilder factory)
	{
		this.factory = factory;
	}

	public Filter getFilter(Element e)
		throws ParserException
	{
		BooleanFilter bf = new BooleanFilter();
		NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++)
		{
			Node node = nl.item(i);
			if (node.getNodeName().equals("Clause"))
			{
				Element clauseElem = (Element)node;
				org.apache.lucene.search.BooleanClause.Occur occurs = BooleanQueryBuilder.getOccursValue(clauseElem);
				Element clauseFilter = DOMUtils.getFirstChildOrFail(clauseElem);
				Filter f = factory.getFilter(clauseFilter);
				bf.add(new FilterClause(f, occurs));
			}
		}

		return bf;
	}
}
