// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AndQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			BooleanQueryNode, QueryNode, GroupQueryNode

public class AndQueryNode extends BooleanQueryNode
{

	public AndQueryNode(List clauses)
	{
		super(clauses);
		if (clauses == null || clauses.size() == 0)
			throw new IllegalArgumentException("AND query must have at least one clause");
		else
			return;
	}

	public String toString()
	{
		if (getChildren() == null || getChildren().size() == 0)
			return "<boolean operation='and'/>";
		StringBuilder sb = new StringBuilder();
		sb.append("<boolean operation='and'>");
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
		for (Iterator i$ = getChildren().iterator(); i$.hasNext();)
		{
			QueryNode child = (QueryNode)i$.next();
			sb.append(filler).append(child.toQueryString(escapeSyntaxParser));
			filler = " AND ";
		}

		if (getParent() != null && (getParent() instanceof GroupQueryNode) || isRoot())
			return sb.toString();
		else
			return (new StringBuilder()).append("( ").append(sb.toString()).append(" )").toString();
	}
}
