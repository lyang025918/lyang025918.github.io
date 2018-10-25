// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Distribution.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicStats

public abstract class Distribution
{

	public Distribution()
	{
	}

	public abstract float score(BasicStats basicstats, float f, float f1);

	public Explanation explain(BasicStats stats, float tfn, float lambda)
	{
		return new Explanation(score(stats, tfn, lambda), getClass().getSimpleName());
	}

	public abstract String toString();
}
