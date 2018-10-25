// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultPhraseSlopQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.MultiPhraseQueryNode;

public class DefaultPhraseSlopQueryNodeProcessor extends QueryNodeProcessorImpl
{

	private boolean processChildren;
	private int defaultPhraseSlop;

	public DefaultPhraseSlopQueryNodeProcessor()
	{
		processChildren = true;
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		QueryConfigHandler queryConfig = getQueryConfigHandler();
		if (queryConfig != null)
		{
			Integer defaultPhraseSlop = (Integer)queryConfig.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.PHRASE_SLOP);
			if (defaultPhraseSlop != null)
			{
				this.defaultPhraseSlop = defaultPhraseSlop.intValue();
				return super.process(queryTree);
			}
		}
		return queryTree;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if ((node instanceof TokenizedPhraseQueryNode) || (node instanceof MultiPhraseQueryNode))
			return new SlopQueryNode(node, defaultPhraseSlop);
		else
			return node;
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof SlopQueryNode)
			processChildren = false;
		return node;
	}

	protected void processChildren(QueryNode queryTree)
		throws QueryNodeException
	{
		if (processChildren)
			super.processChildren(queryTree);
		else
			processChildren = true;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		return children;
	}
}
