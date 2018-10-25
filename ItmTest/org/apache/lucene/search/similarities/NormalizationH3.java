// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizationH3.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			Normalization, BasicStats

public class NormalizationH3 extends Normalization
{

	private final float mu;

	public NormalizationH3()
	{
		this(800F);
	}

	public NormalizationH3(float mu)
	{
		this.mu = mu;
	}

	public float tfn(BasicStats stats, float tf, float len)
	{
		return ((tf + mu * (((float)stats.getTotalTermFreq() + 1.0F) / ((float)stats.getNumberOfFieldTokens() + 1.0F))) / (len + mu)) * mu;
	}

	public String toString()
	{
		return (new StringBuilder()).append("3(").append(mu).append(")").toString();
	}

	public float getMu()
	{
		return mu;
	}
}
