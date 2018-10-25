// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoChildOptimizationQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.core.processors;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.processors:
//			QueryNodeProcessorImpl

public class NoChildOptimizationQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public NoChildOptimizationQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
label0:
		{
label1:
			{
				if (!(node instanceof BooleanQueryNode) && !(node instanceof BoostQueryNode) && !(node instanceof TokenizedPhraseQueryNode) && !(node instanceof ModifierQueryNode))
					break label0;
				List children = node.getChildren();
				if (children == null || children.size() <= 0)
					break label1;
				Iterator i$ = children.iterator();
				QueryNode child;
				do
				{
					if (!i$.hasNext())
						break label1;
					child = (QueryNode)i$.next();
				} while (child instanceof DeletedQueryNode);
				return node;
			}
			return new MatchNoDocsQueryNode();
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
