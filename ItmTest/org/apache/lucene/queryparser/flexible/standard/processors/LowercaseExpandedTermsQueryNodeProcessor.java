// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LowercaseExpandedTermsQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import java.util.Locale;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.core.util.UnescapedCharSequence;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.RegexpQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;

public class LowercaseExpandedTermsQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public LowercaseExpandedTermsQueryNodeProcessor()
	{
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		Boolean lowercaseExpandedTerms = (Boolean)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOWERCASE_EXPANDED_TERMS);
		if (lowercaseExpandedTerms != null && lowercaseExpandedTerms.booleanValue())
			return super.process(queryTree);
		else
			return queryTree;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		Locale locale = (Locale)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOCALE);
		if (locale == null)
			locale = Locale.getDefault();
		if ((node instanceof WildcardQueryNode) || (node instanceof FuzzyQueryNode) || (node instanceof FieldQueryNode) && (node.getParent() instanceof RangeQueryNode) || (node instanceof RegexpQueryNode))
		{
			TextableQueryNode txtNode = (TextableQueryNode)node;
			CharSequence text = txtNode.getText();
			txtNode.setText(text == null ? null : UnescapedCharSequence.toLowerCase(text, locale));
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
