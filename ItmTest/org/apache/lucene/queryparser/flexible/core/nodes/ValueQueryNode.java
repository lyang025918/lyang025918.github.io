// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ValueQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;


// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNode

public interface ValueQueryNode
	extends QueryNode
{

	public abstract void setValue(Object obj);

	public abstract Object getValue();
}
