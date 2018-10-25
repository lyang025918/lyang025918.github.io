// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FuzzyQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class FuzzyQueryNodeBuilder
	implements StandardQueryBuilder
{

	public FuzzyQueryNodeBuilder()
	{
	}

	public FuzzyQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		FuzzyQueryNode fuzzyNode = (FuzzyQueryNode)queryNode;
		String text = fuzzyNode.getTextAsString();
		int numEdits = FuzzyQuery.floatToEdits(fuzzyNode.getSimilarity(), text.codePointCount(0, text.length()));
		return new FuzzyQuery(new Term(fuzzyNode.getFieldAsString(), fuzzyNode.getTextAsString()), numEdits, fuzzyNode.getPrefixLength());
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
