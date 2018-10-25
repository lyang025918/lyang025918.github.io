// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.FieldConfig;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;

public class BoostQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public BoostQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if ((node instanceof FieldableNode) && (node.getParent() == null || !(node.getParent() instanceof FieldableNode)))
		{
			FieldableNode fieldNode = (FieldableNode)node;
			QueryConfigHandler config = getQueryConfigHandler();
			if (config != null)
			{
				CharSequence field = fieldNode.getField();
				FieldConfig fieldConfig = config.getFieldConfig(StringUtils.toString(field));
				if (fieldConfig != null)
				{
					Float boost = (Float)fieldConfig.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.BOOST);
					if (boost != null)
						return new BoostQueryNode(node, boost.floatValue());
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
