// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnyQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.AnyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class AnyQueryNodeBuilder
	implements StandardQueryBuilder
{

	public AnyQueryNodeBuilder()
	{
	}

	public BooleanQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		AnyQueryNode andNode = (AnyQueryNode)queryNode;
		BooleanQuery bQuery = new BooleanQuery();
		List children = andNode.getChildren();
		if (children != null)
		{
			Iterator i$ = children.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				QueryNode child = (QueryNode)i$.next();
				Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
				if (obj != null)
				{
					Query query = (Query)obj;
					try
					{
						bQuery.add(query, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
					}
					catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ex)
					{
						throw new QueryNodeException(new MessageImpl(QueryParserMessages.EMPTY_MESSAGE), ex);
					}
				}
			} while (true);
		}
		bQuery.setMinimumNumberShouldMatch(andNode.getMinimumMatchingElements());
		return bQuery;
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
