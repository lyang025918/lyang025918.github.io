// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelBE.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelBE extends BasicModel
{

	public BasicModelBE()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		double F = (float)(stats.getTotalTermFreq() + 1L) + tfn;
		double N = F + (double)stats.getNumberOfDocuments();
		return (float)((-SimilarityBase.log2((N - 1.0D) * 2.7182818284590451D) + f((N + F) - 1.0D, (N + F) - (double)tfn - 2D)) - f(F, F - (double)tfn));
	}

	private final double f(double n, double m)
	{
		return (m + 0.5D) * SimilarityBase.log2(n / m) + (n - m) * SimilarityBase.log2(n);
	}

	public String toString()
	{
		return "Be";
	}
}
