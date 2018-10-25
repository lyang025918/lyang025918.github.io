// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode, GroupQueryNode

public class BooleanQueryNode extends QueryNodeImpl
{

	public BooleanQueryNode(List clauses)
	{
		setLeaf(false);
		allocate();
		set(clauses);
	}

	public String toString()
	{
		if (getChildren() == null || getChildren().size() == 0)
			return "<boolean operation='default'/>";
		StringBuilder sb = new StringBuilder();
		sb.append("<boolean operation='default'>");
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
			filler = " ";
		}

		if (getParent() != null && (getParent() instanceof GroupQueryNode) || isRoot())
			return sb.toString();
		else
			return (new StringBuilder()).append("( ").append(sb.toString()).append(" )").toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		BooleanQueryNode clone = (BooleanQueryNode)super.cloneTree();
		return clone;
	}
}
