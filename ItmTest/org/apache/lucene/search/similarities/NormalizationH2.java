// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizationH2.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			Normalization, BasicStats, SimilarityBase

public class NormalizationH2 extends Normalization
{

	private final float c;

	public NormalizationH2(float c)
	{
		this.c = c;
	}

	public NormalizationH2()
	{
		this(1.0F);
	}

	public final float tfn(BasicStats stats, float tf, float len)
	{
		return (float)((double)tf * SimilarityBase.log2(1.0F + (c * stats.getAvgFieldLength()) / len));
	}

	public String toString()
	{
		return "2";
	}

	public float getC()
	{
		return c;
	}
}
