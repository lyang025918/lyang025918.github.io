// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixWildcardQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.PrefixWildcardQueryNode;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class PrefixWildcardQueryNodeBuilder
	implements StandardQueryBuilder
{

	public PrefixWildcardQueryNodeBuilder()
	{
	}

	public PrefixQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		PrefixWildcardQueryNode wildcardNode = (PrefixWildcardQueryNode)queryNode;
		String text = wildcardNode.getText().subSequence(0, wildcardNode.getText().length() - 1).toString();
		PrefixQuery q = new PrefixQuery(new Term(wildcardNode.getFieldAsString(), text));
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
