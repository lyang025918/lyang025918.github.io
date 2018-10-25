// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.BooleanModifierNode;

/**
 * @deprecated Class GroupQueryNodeProcessor is deprecated
 */

public class GroupQueryNodeProcessor
	implements QueryNodeProcessor
{

	private ArrayList queryNodeList;
	private boolean latestNodeVerified;
	private QueryConfigHandler queryConfig;
	private Boolean usingAnd;

	public GroupQueryNodeProcessor()
	{
		usingAnd = Boolean.valueOf(false);
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator defaultOperator = (org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DEFAULT_OPERATOR);
		if (defaultOperator == null)
			throw new IllegalArgumentException("DEFAULT_OPERATOR should be set on the QueryConfigHandler");
		usingAnd = Boolean.valueOf(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator.AND == defaultOperator);
		if (queryTree instanceof GroupQueryNode)
			queryTree = ((GroupQueryNode)queryTree).getChild();
		queryNodeList = new ArrayList();
		latestNodeVerified = false;
		readTree(queryTree);
		List actualQueryNodeList = queryNodeList;
		for (int i = 0; i < actualQueryNodeList.size(); i++)
		{
			QueryNode node = (QueryNode)actualQueryNodeList.get(i);
			if (node instanceof GroupQueryNode)
				actualQueryNodeList.set(i, process(node));
		}

		usingAnd = Boolean.valueOf(false);
		if (queryTree instanceof BooleanQueryNode)
		{
			queryTree.set(actualQueryNodeList);
			return queryTree;
		} else
		{
			return new BooleanQueryNode(actualQueryNodeList);
		}
	}

	private QueryNode applyModifier(QueryNode node, QueryNode parent)
	{
		if (usingAnd.booleanValue())
		{
			if (parent instanceof OrQueryNode)
			{
				if (node instanceof ModifierQueryNode)
				{
					ModifierQueryNode modNode = (ModifierQueryNode)node;
					if (modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ)
						return modNode.getChild();
				}
			} else
			if (node instanceof ModifierQueryNode)
			{
				ModifierQueryNode modNode = (ModifierQueryNode)node;
				if (modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
					return new BooleanModifierNode(modNode.getChild(), org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ);
			} else
			{
				return new BooleanModifierNode(node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ);
			}
		} else
		if (node.getParent() instanceof AndQueryNode)
			if (node instanceof ModifierQueryNode)
			{
				ModifierQueryNode modNode = (ModifierQueryNode)node;
				if (modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
					return new BooleanModifierNode(modNode.getChild(), org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ);
			} else
			{
				return new BooleanModifierNode(node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ);
			}
		return node;
	}

	private void readTree(QueryNode node)
	{
		if (node instanceof BooleanQueryNode)
		{
			List children = node.getChildren();
			if (children != null && children.size() > 0)
			{
				for (int i = 0; i < children.size() - 1; i++)
					readTree((QueryNode)children.get(i));

				processNode(node);
				readTree((QueryNode)children.get(children.size() - 1));
			} else
			{
				processNode(node);
			}
		} else
		{
			processNode(node);
		}
	}

	private void processNode(QueryNode node)
	{
		if ((node instanceof AndQueryNode) || (node instanceof OrQueryNode))
		{
			if (!latestNodeVerified && !queryNodeList.isEmpty())
			{
				queryNodeList.add(applyModifier((QueryNode)queryNodeList.remove(queryNodeList.size() - 1), node));
				latestNodeVerified = true;
			}
		} else
		if (!(node instanceof BooleanQueryNode))
		{
			queryNodeList.add(applyModifier(node, node.getParent()));
			latestNodeVerified = false;
		}
	}

	public QueryConfigHandler getQueryConfigHandler()
	{
		return queryConfig;
	}

	public void setQueryConfigHandler(QueryConfigHandler queryConfigHandler)
	{
		queryConfig = queryConfigHandler;
	}
}
