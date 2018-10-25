// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegexpQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.RegexpQueryNode;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class RegexpQueryNodeBuilder
	implements StandardQueryBuilder
{

	public RegexpQueryNodeBuilder()
	{
	}

	public RegexpQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		RegexpQueryNode regexpNode = (RegexpQueryNode)queryNode;
		RegexpQuery q = new RegexpQuery(new Term(regexpNode.getFieldAsString(), regexpNode.textToBytesRef()));
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
