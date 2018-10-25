// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiTermRewriteMethodProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.*;
import org.apache.lucene.search.MultiTermQuery;

public class MultiTermRewriteMethodProcessor extends QueryNodeProcessorImpl
{

	public static final String TAG_ID = "MultiTermRewriteMethodConfiguration";

	public MultiTermRewriteMethodProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
	{
		if ((node instanceof WildcardQueryNode) || (node instanceof AbstractRangeQueryNode) || (node instanceof RegexpQueryNode))
		{
			org.apache.lucene.search.MultiTermQuery.RewriteMethod rewriteMethod = (org.apache.lucene.search.MultiTermQuery.RewriteMethod)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.MULTI_TERM_REWRITE_METHOD);
			if (rewriteMethod == null)
				throw new IllegalArgumentException("StandardQueryConfigHandler.ConfigurationKeys.MULTI_TERM_REWRITE_METHOD should be set on the QueryConfigHandler");
			node.setTag("MultiTermRewriteMethodConfiguration", rewriteMethod);
		}
		return node;
	}

	protected QueryNode preProcessNode(QueryNode node)
	{
		return node;
	}

	protected List setChildrenOrder(List children)
	{
		return children;
	}
}
