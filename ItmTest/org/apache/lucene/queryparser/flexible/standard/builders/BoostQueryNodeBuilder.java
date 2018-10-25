// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.BoostQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class BoostQueryNodeBuilder
	implements StandardQueryBuilder
{

	public BoostQueryNodeBuilder()
	{
	}

	public Query build(QueryNode queryNode)
		throws QueryNodeException
	{
		BoostQueryNode boostNode = (BoostQueryNode)queryNode;
		QueryNode child = boostNode.getChild();
		if (child == null)
		{
			return null;
		} else
		{
			Query query = (Query)child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
			query.setBoost(boostNode.getValue());
			return query;
		}
	}

	public volatile Object build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}
}
