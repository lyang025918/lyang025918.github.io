// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WildcardQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

public class WildcardQueryNode extends FieldQueryNode
{

	public WildcardQueryNode(CharSequence field, CharSequence text, int begin, int end)
	{
		super(field, text, begin, end);
	}

	public WildcardQueryNode(FieldQueryNode fqn)
	{
		this(fqn.getField(), fqn.getText(), fqn.getBegin(), fqn.getEnd());
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		if (isDefaultField(field))
			return text;
		else
			return (new StringBuilder()).append(field).append(":").append(text).toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("<wildcard field='").append(field).append("' term='").append(text).append("'/>").toString();
	}

	public WildcardQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		WildcardQueryNode clone = (WildcardQueryNode)super.cloneTree();
		return clone;
	}

	public volatile FieldQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}

	public volatile QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}
}
