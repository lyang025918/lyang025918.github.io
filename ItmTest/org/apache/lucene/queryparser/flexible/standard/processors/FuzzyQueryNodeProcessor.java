// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FuzzyQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.FuzzyConfig;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;

public class FuzzyQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public FuzzyQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		return node;
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof FuzzyQueryNode)
		{
			FuzzyQueryNode fuzzyNode = (FuzzyQueryNode)node;
			QueryConfigHandler config = getQueryConfigHandler();
			FuzzyConfig fuzzyConfig = null;
			if (config != null && (fuzzyConfig = (FuzzyConfig)config.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG)) != null)
			{
				fuzzyNode.setPrefixLength(fuzzyConfig.getPrefixLength());
				if (fuzzyNode.getSimilarity() < 0.0F)
					fuzzyNode.setSimilarity(fuzzyConfig.getMinSimilarity());
			} else
			if (fuzzyNode.getSimilarity() < 0.0F)
				throw new IllegalArgumentException("No FUZZY_CONFIG set in the config");
		}
		return node;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		return children;
	}
}
