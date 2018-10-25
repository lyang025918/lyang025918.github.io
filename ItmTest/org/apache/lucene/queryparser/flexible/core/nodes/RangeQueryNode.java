// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RangeQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;


// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			FieldableNode, FieldValuePairQueryNode

public interface RangeQueryNode
	extends FieldableNode
{

	public abstract FieldValuePairQueryNode getLowerBound();

	public abstract FieldValuePairQueryNode getUpperBound();

	public abstract boolean isLowerInclusive();

	public abstract boolean isUpperInclusive();
}
