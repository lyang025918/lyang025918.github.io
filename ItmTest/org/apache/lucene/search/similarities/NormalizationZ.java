// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizationZ.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			Normalization, BasicStats

public class NormalizationZ extends Normalization
{

	final float z;

	public NormalizationZ()
	{
		this(0.3F);
	}

	public NormalizationZ(float z)
	{
		this.z = z;
	}

	public float tfn(BasicStats stats, float tf, float len)
	{
		return (float)((double)tf * Math.pow(stats.avgFieldLength / len, z));
	}

	public String toString()
	{
		return (new StringBuilder()).append("Z(").append(z).append(")").toString();
	}

	public float getZ()
	{
		return z;
	}
}
