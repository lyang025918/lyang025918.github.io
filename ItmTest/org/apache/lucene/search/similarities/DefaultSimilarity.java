// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultSimilarity.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.Norm;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search.similarities:
//			TFIDFSimilarity

public class DefaultSimilarity extends TFIDFSimilarity
{

	protected boolean discountOverlaps;

	public DefaultSimilarity()
	{
		discountOverlaps = true;
	}

	public float coord(int overlap, int maxOverlap)
	{
		return (float)overlap / (float)maxOverlap;
	}

	public float queryNorm(float sumOfSquaredWeights)
	{
		return (float)(1.0D / Math.sqrt(sumOfSquaredWeights));
	}

	public void computeNorm(FieldInvertState state, Norm norm)
	{
		int numTerms;
		if (discountOverlaps)
			numTerms = state.getLength() - state.getNumOverlap();
		else
			numTerms = state.getLength();
		norm.setByte(encodeNormValue(state.getBoost() * (float)(1.0D / Math.sqrt(numTerms))));
	}

	public float tf(float freq)
	{
		return (float)Math.sqrt(freq);
	}

	public float sloppyFreq(int distance)
	{
		return 1.0F / (float)(distance + 1);
	}

	public float scorePayload(int doc, int start, int end, BytesRef bytesref)
	{
		return 1.0F;
	}

	public float idf(long docFreq, long numDocs)
	{
		return (float)(Math.log((double)numDocs / (double)(docFreq + 1L)) + 1.0D);
	}

	public void setDiscountOverlaps(boolean v)
	{
		discountOverlaps = v;
	}

	public boolean getDiscountOverlaps()
	{
		return discountOverlaps;
	}

	public String toString()
	{
		return "DefaultSimilarity";
	}
}
