// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FuzzyConfig.java

package org.apache.lucene.queryparser.flexible.standard.config;


public class FuzzyConfig
{

	private int prefixLength;
	private float minSimilarity;

	public FuzzyConfig()
	{
		prefixLength = 0;
		minSimilarity = 2.0F;
	}

	public int getPrefixLength()
	{
		return prefixLength;
	}

	public void setPrefixLength(int prefixLength)
	{
		this.prefixLength = prefixLength;
	}

	public float getMinSimilarity()
	{
		return minSimilarity;
	}

	public void setMinSimilarity(float minSimilarity)
	{
		this.minSimilarity = minSimilarity;
	}
}
