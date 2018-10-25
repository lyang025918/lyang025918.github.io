// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DistributionSPL.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			Distribution, BasicStats

public class DistributionSPL extends Distribution
{

	public DistributionSPL()
	{
	}

	public final float score(BasicStats stats, float tfn, float lambda)
	{
		if (lambda == 1.0F)
			lambda = 0.99F;
		return (float)(-Math.log((Math.pow(lambda, tfn / (tfn + 1.0F)) - (double)lambda) / (double)(1.0F - lambda)));
	}

	public String toString()
	{
		return "SPL";
	}
}
