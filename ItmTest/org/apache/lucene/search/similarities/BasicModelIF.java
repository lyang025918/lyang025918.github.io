// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelIF.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelIF extends BasicModel
{

	public BasicModelIF()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		long N = stats.getNumberOfDocuments();
		long F = stats.getTotalTermFreq();
		return tfn * (float)SimilarityBase.log2(1.0D + (double)(N + 1L) / ((double)F + 0.5D));
	}

	public String toString()
	{
		return "I(F)";
	}
}
