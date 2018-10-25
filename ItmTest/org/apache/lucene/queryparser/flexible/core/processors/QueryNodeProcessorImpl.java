// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeProcessorImpl.java

package org.apache.lucene.queryparser.flexible.core.processors;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.processors:
//			QueryNodeProcessor

public abstract class QueryNodeProcessorImpl
	implements QueryNodeProcessor
{
	private static class ChildrenList extends ArrayList
	{

		boolean beingUsed;

		private ChildrenList()
		{
		}

	}


	private ArrayList childrenListPool;
	private QueryConfigHandler queryConfig;

	public QueryNodeProcessorImpl()
	{
		childrenListPool = new ArrayList();
	}

	public QueryNodeProcessorImpl(QueryConfigHandler queryConfigHandler)
	{
		childrenListPool = new ArrayList();
		queryConfig = queryConfigHandler;
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		return processIteration(queryTree);
	}

	private QueryNode processIteration(QueryNode queryTree)
		throws QueryNodeException
	{
		queryTree = preProcessNode(queryTree);
		processChildren(queryTree);
		queryTree = postProcessNode(queryTree);
		return queryTree;
	}

	protected void processChildren(QueryNode queryTree)
		throws QueryNodeException
	{
		List children;
		ChildrenList newChildren;
		children = queryTree.getChildren();
		if (children == null || children.size() <= 0)
			break MISSING_BLOCK_LABEL_119;
		newChildren = allocateChildrenList();
		QueryNode child;
		for (Iterator i$ = children.iterator(); i$.hasNext(); newChildren.add(child))
		{
			child = (QueryNode)i$.next();
			child = processIteration(child);
			if (child == null)
				throw new NullPointerException();
		}

		List orderedChildrenList = setChildrenOrder(newChildren);
		queryTree.set(orderedChildrenList);
		newChildren.beingUsed = false;
		break MISSING_BLOCK_LABEL_119;
		Exception exception;
		exception;
		newChildren.beingUsed = false;
		throw exception;
	}

	private ChildrenList allocateChildrenList()
	{
		ChildrenList list = null;
		Iterator i$ = childrenListPool.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ChildrenList auxList = (ChildrenList)i$.next();
			if (auxList.beingUsed)
				continue;
			list = auxList;
			list.clear();
			break;
		} while (true);
		if (list == null)
		{
			list = new ChildrenList();
			childrenListPool.add(list);
		}
		list.beingUsed = true;
		return list;
	}

	public void setQueryConfigHandler(QueryConfigHandler queryConfigHandler)
	{
		queryConfig = queryConfigHandler;
	}

	public QueryConfigHandler getQueryConfigHandler()
	{
		return queryConfig;
	}

	protected abstract QueryNode preProcessNode(QueryNode querynode)
		throws QueryNodeException;

	protected abstract QueryNode postProcessNode(QueryNode querynode)
		throws QueryNodeException;

	protected abstract List setChildrenOrder(List list)
		throws QueryNodeException;
}
