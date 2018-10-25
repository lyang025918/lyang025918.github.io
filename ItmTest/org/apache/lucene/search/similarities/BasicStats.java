// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicStats.java

package org.apache.lucene.search.similarities;


// Referenced classes of package org.apache.lucene.search.similarities:
//			Similarity

public class BasicStats extends Similarity.SimWeight
{

	final String field;
	protected long numberOfDocuments;
	protected long numberOfFieldTokens;
	protected float avgFieldLength;
	protected long docFreq;
	protected long totalTermFreq;
	protected final float queryBoost;
	protected float topLevelBoost;
	protected float totalBoost;

	public BasicStats(String field, float queryBoost)
	{
		this.field = field;
		this.queryBoost = queryBoost;
		totalBoost = queryBoost;
	}

	public long getNumberOfDocuments()
	{
		return numberOfDocuments;
	}

	public void setNumberOfDocuments(long numberOfDocuments)
	{
		this.numberOfDocuments = numberOfDocuments;
	}

	public long getNumberOfFieldTokens()
	{
		return numberOfFieldTokens;
	}

	public void setNumberOfFieldTokens(long numberOfFieldTokens)
	{
		this.numberOfFieldTokens = numberOfFieldTokens;
	}

	public float getAvgFieldLength()
	{
		return avgFieldLength;
	}

	public void setAvgFieldLength(float avgFieldLength)
	{
		this.avgFieldLength = avgFieldLength;
	}

	public long getDocFreq()
	{
		return docFreq;
	}

	public void setDocFreq(long docFreq)
	{
		this.docFreq = docFreq;
	}

	public long getTotalTermFreq()
	{
		return totalTermFreq;
	}

	public void setTotalTermFreq(long totalTermFreq)
	{
		this.totalTermFreq = totalTermFreq;
	}

	public float getValueForNormalization()
	{
		float rawValue = rawNormalizationValue();
		return rawValue * rawValue;
	}

	protected float rawNormalizationValue()
	{
		return queryBoost;
	}

	public void normalize(float queryNorm, float topLevelBoost)
	{
		this.topLevelBoost = topLevelBoost;
		totalBoost = queryBoost * topLevelBoost;
	}

	public float getTotalBoost()
	{
		return totalBoost;
	}
}
