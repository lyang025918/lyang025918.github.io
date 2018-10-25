// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelG.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelG extends BasicModel
{

	public BasicModelG()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		double F = stats.getTotalTermFreq() + 1L;
		double N = stats.getNumberOfDocuments();
		double lambda = F / (N + F);
		return (float)(SimilarityBase.log2(lambda + 1.0D) + (double)tfn * SimilarityBase.log2((1.0D + lambda) / lambda));
	}

	public String toString()
	{
		return "G";
	}
}
