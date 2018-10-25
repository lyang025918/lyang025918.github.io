// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lambda.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicStats

public abstract class Lambda
{

	public Lambda()
	{
	}

	public abstract float lambda(BasicStats basicstats);

	public abstract Explanation explain(BasicStats basicstats);

	public abstract String toString();
}
