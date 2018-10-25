// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelIne.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelIne extends BasicModel
{

	public BasicModelIne()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		long N = stats.getNumberOfDocuments();
		long F = stats.getTotalTermFreq();
		double ne = (double)N * (1.0D - Math.pow((double)(N - 1L) / (double)N, F));
		return tfn * (float)SimilarityBase.log2((double)(N + 1L) / (ne + 0.5D));
	}

	public String toString()
	{
		return "I(ne)";
	}
}
