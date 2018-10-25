// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermState.java

package org.apache.lucene.index;


public abstract class TermState
	implements Cloneable
{

	protected TermState()
	{
	}

	public abstract void copyFrom(TermState termstate);

	public TermState clone()
	{
		return (TermState)super.clone();
		CloneNotSupportedException cnse;
		cnse;
		throw new RuntimeException(cnse);
	}

	public String toString()
	{
		return "TermState";
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
