// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixWildcardQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.nodes:
//			WildcardQueryNode

public class PrefixWildcardQueryNode extends WildcardQueryNode
{

	public PrefixWildcardQueryNode(CharSequence field, CharSequence text, int begin, int end)
	{
		super(field, text, begin, end);
	}

	public PrefixWildcardQueryNode(FieldQueryNode fqn)
	{
		this(fqn.getField(), fqn.getText(), fqn.getBegin(), fqn.getEnd());
	}

	public String toString()
	{
		return (new StringBuilder()).append("<prefixWildcard field='").append(field).append("' term='").append(text).append("'/>").toString();
	}

	public PrefixWildcardQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		PrefixWildcardQueryNode clone = (PrefixWildcardQueryNode)super.cloneTree();
		return clone;
	}

	public volatile WildcardQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
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
