// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TextableQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;


public interface TextableQueryNode
{

	public abstract CharSequence getText();

	public abstract void setText(CharSequence charsequence);
}
