// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiPhraseQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

public class MultiPhraseQueryNode extends QueryNodeImpl
	implements FieldableNode
{

	public MultiPhraseQueryNode()
	{
		setLeaf(false);
		allocate();
	}

	public String toString()
	{
		if (getChildren() == null || getChildren().size() == 0)
			return "<multiPhrase/>";
		StringBuilder sb = new StringBuilder();
		sb.append("<multiPhrase>");
		QueryNode child;
		for (Iterator i$ = getChildren().iterator(); i$.hasNext(); sb.append(child.toString()))
		{
			child = (QueryNode)i$.next();
			sb.append("\n");
		}

		sb.append("\n</multiPhrase>");
		return sb.toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (getChildren() == null || getChildren().size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		String filler = "";
		for (Iterator i$ = getChildren().iterator(); i$.hasNext();)
		{
			QueryNode child = (QueryNode)i$.next();
			sb.append(filler).append(child.toQueryString(escapeSyntaxParser));
			filler = ",";
		}

		return (new StringBuilder()).append("[MTP[").append(sb.toString()).append("]]").toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		MultiPhraseQueryNode clone = (MultiPhraseQueryNode)super.cloneTree();
		return clone;
	}

	public CharSequence getField()
	{
		List children = getChildren();
		if (children == null || children.size() == 0)
			return null;
		else
			return ((FieldableNode)children.get(0)).getField();
	}

	public void setField(CharSequence fieldName)
	{
		List children = getChildren();
		if (children != null)
		{
			Iterator i$ = children.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				QueryNode child = (QueryNode)i$.next();
				if (child instanceof FieldableNode)
					((FieldableNode)child).setField(fieldName);
			} while (true);
		}
	}
}
