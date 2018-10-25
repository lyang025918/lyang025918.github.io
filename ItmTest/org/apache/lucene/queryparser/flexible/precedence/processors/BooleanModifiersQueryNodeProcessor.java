// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanModifiersQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.precedence.processors;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;

public class BooleanModifiersQueryNodeProcessor extends QueryNodeProcessorImpl
{

	private ArrayList childrenBuffer;
	private Boolean usingAnd;

	public BooleanModifiersQueryNodeProcessor()
	{
		childrenBuffer = new ArrayList();
		usingAnd = Boolean.valueOf(false);
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator op = (org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DEFAULT_OPERATOR);
		if (op == null)
		{
			throw new IllegalArgumentException("StandardQueryConfigHandler.ConfigurationKeys.DEFAULT_OPERATOR should be set on the QueryConfigHandler");
		} else
		{
			usingAnd = Boolean.valueOf(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator.AND == op);
			return super.process(queryTree);
		}
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof AndQueryNode)
		{
			childrenBuffer.clear();
			List children = node.getChildren();
			QueryNode child;
			for (Iterator i$ = children.iterator(); i$.hasNext(); childrenBuffer.add(applyModifier(child, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ)))
				child = (QueryNode)i$.next();

			node.set(childrenBuffer);
		} else
		if (usingAnd.booleanValue() && (node instanceof BooleanQueryNode) && !(node instanceof OrQueryNode))
		{
			childrenBuffer.clear();
			List children = node.getChildren();
			QueryNode child;
			for (Iterator i$ = children.iterator(); i$.hasNext(); childrenBuffer.add(applyModifier(child, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ)))
				child = (QueryNode)i$.next();

			node.set(childrenBuffer);
		}
		return node;
	}

	private QueryNode applyModifier(QueryNode node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier mod)
	{
		if (!(node instanceof ModifierQueryNode))
			return new ModifierQueryNode(node, mod);
		ModifierQueryNode modNode = (ModifierQueryNode)node;
		if (modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
			return new ModifierQueryNode(modNode.getChild(), mod);
		else
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
