// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanSingleChildOptimizationQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.BooleanModifierNode;

public class BooleanSingleChildOptimizationQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public BooleanSingleChildOptimizationQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof BooleanQueryNode)
		{
			List children = node.getChildren();
			if (children != null && children.size() == 1)
			{
				QueryNode child = (QueryNode)children.get(0);
				if (child instanceof ModifierQueryNode)
				{
					ModifierQueryNode modNode = (ModifierQueryNode)child;
					if ((modNode instanceof BooleanModifierNode) || modNode.getModifier() == org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
						return child;
				} else
				{
					return child;
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
