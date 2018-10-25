// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WildcardQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.core.util.UnescapedCharSequence;
import org.apache.lucene.queryparser.flexible.standard.nodes.*;

public class WildcardQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public WildcardQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if ((node instanceof FieldQueryNode) || (node instanceof FuzzyQueryNode))
		{
			FieldQueryNode fqn = (FieldQueryNode)node;
			CharSequence text = fqn.getText();
			if ((fqn.getParent() instanceof TermRangeQueryNode) || (fqn instanceof QuotedFieldQueryNode) || text.length() <= 0)
				return node;
			if (isPrefixWildcard(text))
			{
				PrefixWildcardQueryNode prefixWildcardQN = new PrefixWildcardQueryNode(fqn);
				return prefixWildcardQN;
			}
			if (isWildcard(text))
			{
				WildcardQueryNode wildcardQN = new WildcardQueryNode(fqn);
				return wildcardQN;
			}
		}
		return node;
	}

	private boolean isWildcard(CharSequence text)
	{
		if (text == null || text.length() <= 0)
			return false;
		for (int i = text.length() - 1; i >= 0; i--)
			if ((text.charAt(i) == '*' || text.charAt(i) == '?') && !UnescapedCharSequence.wasEscaped(text, i))
				return true;

		return false;
	}

	private boolean isPrefixWildcard(CharSequence text)
	{
		if (text == null || text.length() <= 0 || !isWildcard(text))
			return false;
		if (text.charAt(text.length() - 1) != '*')
			return false;
		if (UnescapedCharSequence.wasEscaped(text, text.length() - 1))
			return false;
		if (text.length() == 1)
			return false;
		for (int i = 0; i < text.length(); i++)
		{
			if (text.charAt(i) == '?')
				return false;
			if (text.charAt(i) == '*' && !UnescapedCharSequence.wasEscaped(text, i))
				return i == text.length() - 1;
		}

		return false;
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
