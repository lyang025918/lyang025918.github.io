// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiFieldQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.LinkedList;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;

public class MultiFieldQueryNodeProcessor extends QueryNodeProcessorImpl
{

	private boolean processChildren;

	public MultiFieldQueryNodeProcessor()
	{
		processChildren = true;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
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

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof FieldableNode)
		{
			processChildren = false;
			FieldableNode fieldNode = (FieldableNode)node;
			if (fieldNode.getField() == null)
			{
				CharSequence fields[] = (CharSequence[])getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.MULTI_FIELDS);
				if (fields == null)
					throw new IllegalArgumentException("StandardQueryConfigHandler.ConfigurationKeys.MULTI_FIELDS should be set on the QueryConfigHandler");
				if (fields != null && fields.length > 0)
				{
					fieldNode.setField(fields[0]);
					if (fields.length == 1)
						return fieldNode;
					LinkedList children = new LinkedList();
					children.add(fieldNode);
					for (int i = 1; i < fields.length; i++)
						try
						{
							fieldNode = (FieldableNode)fieldNode.cloneTree();
							fieldNode.setField(fields[i]);
							children.add(fieldNode);
						}
						catch (CloneNotSupportedException e) { }

					return new GroupQueryNode(new OrQueryNode(children));
				}
			}
		}
		return node;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		return children;
	}
}
