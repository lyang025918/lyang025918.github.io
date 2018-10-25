// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldableNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;


// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNode

public interface FieldableNode
	extends QueryNode
{

	public abstract CharSequence getField();

	public abstract void setField(CharSequence charsequence);
}
