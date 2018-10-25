// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardBooleanQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.StandardBooleanQueryNode;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class StandardBooleanQueryNodeBuilder
	implements StandardQueryBuilder
{

	public StandardBooleanQueryNodeBuilder()
	{
	}

	public BooleanQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		StandardBooleanQueryNode booleanNode = (StandardBooleanQueryNode)queryNode;
		BooleanQuery bQuery = new BooleanQuery(booleanNode.isDisableCoord());
		List children = booleanNode.getChildren();
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
						bQuery.add(query, getModifierValue(child));
					}
					catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ex)
					{
						throw new QueryNodeException(new MessageImpl(QueryParserMessages.TOO_MANY_BOOLEAN_CLAUSES, new Object[] {
							Integer.valueOf(BooleanQuery.getMaxClauseCount()), queryNode.toQueryString(new EscapeQuerySyntaxImpl())
						}), ex);
					}
				}
			} while (true);
		}
		return bQuery;
	}

	private static org.apache.lucene.search.BooleanClause.Occur getModifierValue(QueryNode node)
	{
		if (node instanceof ModifierQueryNode)
		{
			ModifierQueryNode mNode = (ModifierQueryNode)node;
			org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier modifier = mNode.getModifier();
			if (org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE.equals(modifier))
				return org.apache.lucene.search.BooleanClause.Occur.SHOULD;
			if (org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NOT.equals(modifier))
				return org.apache.lucene.search.BooleanClause.Occur.MUST_NOT;
			else
				return org.apache.lucene.search.BooleanClause.Occur.MUST;
		} else
		{
			return org.apache.lucene.search.BooleanClause.Occur.SHOULD;
		}
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
