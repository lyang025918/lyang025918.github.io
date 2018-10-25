// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeletedQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode

public class DeletedQueryNode extends QueryNodeImpl
{

	public DeletedQueryNode()
	{
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		return "[DELETEDCHILD]";
	}

	public String toString()
	{
		return "<deleted/>";
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		DeletedQueryNode clone = (DeletedQueryNode)super.cloneTree();
		return clone;
	}
}
