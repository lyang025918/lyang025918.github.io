// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermRangeQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.nodes:
//			AbstractRangeQueryNode

public class TermRangeQueryNode extends AbstractRangeQueryNode
{

	public TermRangeQueryNode(FieldQueryNode lower, FieldQueryNode upper, boolean lowerInclusive, boolean upperInclusive)
	{
		setBounds(lower, upper, lowerInclusive, upperInclusive);
	}
}
