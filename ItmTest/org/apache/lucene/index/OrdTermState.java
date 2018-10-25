// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrdTermState.java

package org.apache.lucene.index;


// Referenced classes of package org.apache.lucene.index:
//			TermState

public class OrdTermState extends TermState
{

	public long ord;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/OrdTermState.desiredAssertionStatus();

	public OrdTermState()
	{
	}

	public void copyFrom(TermState other)
	{
		if (!$assertionsDisabled && !(other instanceof OrdTermState))
		{
			throw new AssertionError((new StringBuilder()).append("can not copy from ").append(other.getClass().getName()).toString());
		} else
		{
			ord = ((OrdTermState)other).ord;
			return;
		}
	}

	public String toString()
	{
		return (new StringBuilder()).append("OrdTermState ord=").append(ord).toString();
	}

}
