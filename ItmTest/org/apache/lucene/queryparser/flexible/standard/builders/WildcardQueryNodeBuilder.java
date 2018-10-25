// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WildcardQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class WildcardQueryNodeBuilder
	implements StandardQueryBuilder
{

	public WildcardQueryNodeBuilder()
	{
	}

	public WildcardQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		WildcardQueryNode wildcardNode = (WildcardQueryNode)queryNode;
		WildcardQuery q = new WildcardQuery(new Term(wildcardNode.getFieldAsString(), wildcardNode.getTextAsString()));
		org.apache.lucene.search.MultiTermQuery.RewriteMethod method = (org.apache.lucene.search.MultiTermQuery.RewriteMethod)queryNode.getTag("MultiTermRewriteMethodConfiguration");
		if (method != null)
			q.setRewriteMethod(method);
		return q;
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
