// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RemoveDeletedQueryNodesProcessor.java

package org.apache.lucene.queryparser.flexible.core.processors;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.processors:
//			QueryNodeProcessorImpl

public class RemoveDeletedQueryNodesProcessor extends QueryNodeProcessorImpl
{

	public RemoveDeletedQueryNodesProcessor()
	{
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		queryTree = super.process(queryTree);
		if ((queryTree instanceof DeletedQueryNode) && !(queryTree instanceof MatchNoDocsQueryNode))
			return new MatchNoDocsQueryNode();
		else
			return queryTree;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
label0:
		{
			boolean removeBoolean;
label1:
			{
				if (node.isLeaf())
					break label0;
				List children = node.getChildren();
				removeBoolean = false;
				if (children == null || children.size() == 0)
				{
					removeBoolean = true;
					break label1;
				}
				removeBoolean = true;
				Iterator it = children.iterator();
				do
					if (!it.hasNext())
						break label1;
				while (it.next() instanceof DeletedQueryNode);
				removeBoolean = false;
			}
			if (removeBoolean)
				return new DeletedQueryNode();
		}
		return node;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		for (int i = 0; i < children.size(); i++)
			if (children.get(i) instanceof DeletedQueryNode)
				children.remove(i--);

		return children;
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		return node;
	}
}
