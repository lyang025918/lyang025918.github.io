// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoTokenFoundQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			DeletedQueryNode, QueryNode

public class NoTokenFoundQueryNode extends DeletedQueryNode
{

	public NoTokenFoundQueryNode()
	{
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		return "[NTF]";
	}

	public String toString()
	{
		return "<notokenfound/>";
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		NoTokenFoundQueryNode clone = (NoTokenFoundQueryNode)super.cloneTree();
		return clone;
	}
}
