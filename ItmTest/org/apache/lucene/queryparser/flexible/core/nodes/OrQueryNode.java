// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			BooleanQueryNode, QueryNode, GroupQueryNode

public class OrQueryNode extends BooleanQueryNode
{

	public OrQueryNode(List clauses)
	{
		super(clauses);
		if (clauses == null || clauses.size() == 0)
			throw new IllegalArgumentException("OR query must have at least one clause");
		else
			return;
	}

	public String toString()
	{
		if (getChildren() == null || getChildren().size() == 0)
			return "<boolean operation='or'/>";
		StringBuilder sb = new StringBuilder();
		sb.append("<boolean operation='or'>");
		QueryNode child;
		for (Iterator i$ = getChildren().iterator(); i$.hasNext(); sb.append(child.toString()))
		{
			child = (QueryNode)i$.next();
			sb.append("\n");
		}

		sb.append("\n</boolean>");
		return sb.toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (getChildren() == null || getChildren().size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		String filler = "";
		for (Iterator it = getChildren().iterator(); it.hasNext();)
		{
			sb.append(filler).append(((QueryNode)it.next()).toQueryString(escapeSyntaxParser));
			filler = " OR ";
		}

		if (getParent() != null && (getParent() instanceof GroupQueryNode) || isRoot())
			return sb.toString();
		else
			return (new StringBuilder()).append("( ").append(sb.toString()).append(" )").toString();
	}
}
