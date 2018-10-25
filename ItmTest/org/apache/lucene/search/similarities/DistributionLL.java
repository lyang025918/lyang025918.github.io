// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DistributionLL.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			Distribution, BasicStats

public class DistributionLL extends Distribution
{

	public DistributionLL()
	{
	}

	public final float score(BasicStats stats, float tfn, float lambda)
	{
		return (float)(-Math.log(lambda / (tfn + lambda)));
	}

	public String toString()
	{
		return "LL";
	}
}
