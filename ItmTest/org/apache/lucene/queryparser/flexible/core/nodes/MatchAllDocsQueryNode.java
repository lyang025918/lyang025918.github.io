// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MatchAllDocsQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode

public class MatchAllDocsQueryNode extends QueryNodeImpl
{

	public MatchAllDocsQueryNode()
	{
	}

	public String toString()
	{
		return "<matchAllDocs field='*' term='*'/>";
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		return "*:*";
	}

	public MatchAllDocsQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		MatchAllDocsQueryNode clone = (MatchAllDocsQueryNode)super.cloneTree();
		return clone;
	}

	public volatile QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}
}
