// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermRangeQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class TermRangeQueryNodeBuilder
	implements StandardQueryBuilder
{

	public TermRangeQueryNodeBuilder()
	{
	}

	public TermRangeQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		TermRangeQueryNode rangeNode = (TermRangeQueryNode)queryNode;
		FieldQueryNode upper = (FieldQueryNode)rangeNode.getUpperBound();
		FieldQueryNode lower = (FieldQueryNode)rangeNode.getLowerBound();
		String field = StringUtils.toString(rangeNode.getField());
		String lowerText = lower.getTextAsString();
		String upperText = upper.getTextAsString();
		if (lowerText.length() == 0)
			lowerText = null;
		if (upperText.length() == 0)
			upperText = null;
		TermRangeQuery rangeQuery = TermRangeQuery.newStringRange(field, lowerText, upperText, rangeNode.isLowerInclusive(), rangeNode.isUpperInclusive());
		org.apache.lucene.search.MultiTermQuery.RewriteMethod method = (org.apache.lucene.search.MultiTermQuery.RewriteMethod)queryNode.getTag("MultiTermRewriteMethodConfiguration");
		if (method != null)
			rangeQuery.setRewriteMethod(method);
		return rangeQuery;
	}

	public volatile Query build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}

	public volatile Object build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}
}
