// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RemoveEmptyNonLeafQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.MatchNoDocsQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;

public class RemoveEmptyNonLeafQueryNodeProcessor extends QueryNodeProcessorImpl
{

	private LinkedList childrenBuffer;

	public RemoveEmptyNonLeafQueryNodeProcessor()
	{
		childrenBuffer = new LinkedList();
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		queryTree = super.process(queryTree);
		if (!queryTree.isLeaf())
		{
			List children = queryTree.getChildren();
			if (children == null || children.size() == 0)
				return new MatchNoDocsQueryNode();
		}
		return queryTree;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
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
		Iterator i$ = children.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			QueryNode child = (QueryNode)i$.next();
			if (!child.isLeaf())
			{
				List grandChildren = child.getChildren();
				if (grandChildren != null && grandChildren.size() > 0)
					childrenBuffer.add(child);
			} else
			{
				childrenBuffer.add(child);
			}
		} while (true);
		children.clear();
		children.addAll(childrenBuffer);
		childrenBuffer.clear();
		break MISSING_BLOCK_LABEL_121;
		Exception exception;
		exception;
		childrenBuffer.clear();
		throw exception;
		return children;
	}
}
