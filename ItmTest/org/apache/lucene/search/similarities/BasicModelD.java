// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelD.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelD extends BasicModel
{

	public BasicModelD()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		double F = (float)(stats.getTotalTermFreq() + 1L) + tfn;
		double phi = (double)tfn / F;
		double nphi = 1.0D - phi;
		double p = 1.0D / (double)(stats.getNumberOfDocuments() + 1L);
		double D = phi * SimilarityBase.log2(phi / p) + nphi * SimilarityBase.log2(nphi / (1.0D - p));
		return (float)(D * F + 0.5D * SimilarityBase.log2(1.0D + 6.2831853071795862D * (double)tfn * nphi));
	}

	public String toString()
	{
		return "D";
	}
}
