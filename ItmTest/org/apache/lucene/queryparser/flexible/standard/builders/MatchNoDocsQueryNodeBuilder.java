// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MatchNoDocsQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.MatchNoDocsQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class MatchNoDocsQueryNodeBuilder
	implements StandardQueryBuilder
{

	public MatchNoDocsQueryNodeBuilder()
	{
	}

	public BooleanQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		if (!(queryNode instanceof MatchNoDocsQueryNode))
			throw new QueryNodeException(new MessageImpl(QueryParserMessages.LUCENE_QUERY_CONVERSION_ERROR, new Object[] {
				queryNode.toQueryString(new EscapeQuerySyntaxImpl()), queryNode.getClass().getName()
			}));
		else
			return new BooleanQuery();
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
