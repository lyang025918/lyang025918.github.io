// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelP.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelP extends BasicModel
{

	protected static double LOG2_E = SimilarityBase.log2(2.7182818284590451D);

	public BasicModelP()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		float lambda = (float)(stats.getTotalTermFreq() + 1L) / (float)(stats.getNumberOfDocuments() + 1L);
		return (float)((double)tfn * SimilarityBase.log2(tfn / lambda) + (double)((lambda + 1.0F / (12F * tfn)) - tfn) * LOG2_E + 0.5D * SimilarityBase.log2(6.2831853071795862D * (double)tfn));
	}

	public String toString()
	{
		return "P";
	}

}
