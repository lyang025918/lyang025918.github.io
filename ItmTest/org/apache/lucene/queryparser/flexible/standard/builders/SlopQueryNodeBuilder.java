// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlopQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.SlopQueryNode;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class SlopQueryNodeBuilder
	implements StandardQueryBuilder
{

	public SlopQueryNodeBuilder()
	{
	}

	public Query build(QueryNode queryNode)
		throws QueryNodeException
	{
		SlopQueryNode phraseSlopNode = (SlopQueryNode)queryNode;
		Query query = (Query)phraseSlopNode.getChild().getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
		if (query instanceof PhraseQuery)
			((PhraseQuery)query).setSlop(phraseSlopNode.getValue());
		else
			((MultiPhraseQuery)query).setSlop(phraseSlopNode.getValue());
		return query;
	}

	public volatile Object build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}
}
