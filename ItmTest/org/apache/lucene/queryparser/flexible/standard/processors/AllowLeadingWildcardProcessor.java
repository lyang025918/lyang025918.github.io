// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AllowLeadingWildcardProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.core.util.UnescapedCharSequence;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;

public class AllowLeadingWildcardProcessor extends QueryNodeProcessorImpl
{

	public AllowLeadingWildcardProcessor()
	{
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		Boolean allowsLeadingWildcard = (Boolean)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ALLOW_LEADING_WILDCARD);
		if (allowsLeadingWildcard != null && !allowsLeadingWildcard.booleanValue())
			return super.process(queryTree);
		else
			return queryTree;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof WildcardQueryNode)
		{
			WildcardQueryNode wildcardNode = (WildcardQueryNode)node;
			if (wildcardNode.getText().length() > 0)
			{
				if (UnescapedCharSequence.wasEscaped(wildcardNode.getText(), 0))
					return node;
				switch (wildcardNode.getText().charAt(0))
				{
				case 42: // '*'
				case 63: // '?'
					throw new QueryNodeException(new MessageImpl(QueryParserMessages.LEADING_WILDCARD_NOT_ALLOWED, new Object[] {
						node.toQueryString(new EscapeQuerySyntaxImpl())
					}));
				}
			}
		}
		return node;
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		return node;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		return children;
	}
}
