// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MatchAllDocsQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;

public class MatchAllDocsQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public MatchAllDocsQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof FieldQueryNode)
		{
			FieldQueryNode fqn = (FieldQueryNode)node;
			if (fqn.getField().toString().equals("*") && fqn.getText().toString().equals("*"))
				return new MatchAllDocsQueryNode();
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
