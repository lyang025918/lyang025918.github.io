// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanQuery2ModifierNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.BooleanModifierNode;

public class BooleanQuery2ModifierNodeProcessor
	implements QueryNodeProcessor
{

	static final String TAG_REMOVE = "remove";
	static final String TAG_MODIFIER = "wrapWithModifier";
	static final String TAG_BOOLEAN_ROOT = "booleanRoot";
	QueryConfigHandler queryConfigHandler;
	private final ArrayList childrenBuffer = new ArrayList();
	private Boolean usingAnd;

	public BooleanQuery2ModifierNodeProcessor()
	{
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
			return processIteration(queryTree);
		}
	}

	protected void processChildren(QueryNode queryTree)
		throws QueryNodeException
	{
		List children = queryTree.getChildren();
		if (children != null && children.size() > 0)
		{
			for (Iterator i$ = children.iterator(); i$.hasNext();)
			{
				QueryNode child = (QueryNode)i$.next();
				child = processIteration(child);
			}

		}
	}

	private QueryNode processIteration(QueryNode queryTree)
		throws QueryNodeException
	{
		queryTree = preProcessNode(queryTree);
		processChildren(queryTree);
		queryTree = postProcessNode(queryTree);
		return queryTree;
	}

	protected void fillChildrenBufferAndApplyModifiery(QueryNode parent)
	{
		for (Iterator i$ = parent.getChildren().iterator(); i$.hasNext();)
		{
			QueryNode node = (QueryNode)i$.next();
			if (node.containsTag("remove"))
				fillChildrenBufferAndApplyModifiery(node);
			else
			if (node.containsTag("wrapWithModifier"))
				childrenBuffer.add(applyModifier(node, (org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier)node.getTag("wrapWithModifier")));
			else
				childrenBuffer.add(node);
		}

	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node.containsTag("booleanRoot"))
		{
			childrenBuffer.clear();
			fillChildrenBufferAndApplyModifiery(node);
			node.set(childrenBuffer);
		}
		return node;
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		QueryNode parent = node.getParent();
		if (node instanceof BooleanQueryNode)
		{
			if (parent instanceof BooleanQueryNode)
				node.setTag("remove", Boolean.TRUE);
			else
				node.setTag("booleanRoot", Boolean.TRUE);
		} else
		if ((parent instanceof BooleanQueryNode) && ((parent instanceof AndQueryNode) || usingAnd.booleanValue() && isDefaultBooleanQueryNode(parent)))
			tagModifierButDoNotOverride(node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ);
		return node;
	}

	protected boolean isDefaultBooleanQueryNode(QueryNode toTest)
	{
		return toTest != null && org/apache/lucene/queryparser/flexible/core/nodes/BooleanQueryNode.equals(toTest.getClass());
	}

	private QueryNode applyModifier(QueryNode node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier mod)
	{
		if (!(node instanceof ModifierQueryNode))
			return new BooleanModifierNode(node, mod);
		ModifierQueryNode modNode = (ModifierQueryNode)node;
		if (modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
			return new ModifierQueryNode(modNode.getChild(), mod);
		else
			return node;
	}

	protected void tagModifierButDoNotOverride(QueryNode node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier mod)
	{
		if (node instanceof ModifierQueryNode)
		{
			ModifierQueryNode modNode = (ModifierQueryNode)node;
			if (modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
				node.setTag("wrapWithModifier", mod);
		} else
		{
			node.setTag("wrapWithModifier", org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ);
		}
	}

	public void setQueryConfigHandler(QueryConfigHandler queryConfigHandler)
	{
		this.queryConfigHandler = queryConfigHandler;
	}

	public QueryConfigHandler getQueryConfigHandler()
	{
		return queryConfigHandler;
	}
}
