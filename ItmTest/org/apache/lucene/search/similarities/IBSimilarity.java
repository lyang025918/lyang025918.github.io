// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IBSimilarity.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			SimilarityBase, BasicStats, Normalization, Lambda, 
//			Distribution

public class IBSimilarity extends SimilarityBase
{

	protected final Distribution distribution;
	protected final Lambda lambda;
	protected final Normalization normalization;

	public IBSimilarity(Distribution distribution, Lambda lambda, Normalization normalization)
	{
		this.distribution = distribution;
		this.lambda = lambda;
		this.normalization = normalization;
	}

	protected float score(BasicStats stats, float freq, float docLen)
	{
		return stats.getTotalBoost() * distribution.score(stats, normalization.tfn(stats, freq, docLen), lambda.lambda(stats));
	}

	protected void explain(Explanation expl, BasicStats stats, int doc, float freq, float docLen)
	{
		if (stats.getTotalBoost() != 1.0F)
			expl.addDetail(new Explanation(stats.getTotalBoost(), "boost"));
		Explanation normExpl = normalization.explain(stats, freq, docLen);
		Explanation lambdaExpl = lambda.explain(stats);
		expl.addDetail(normExpl);
		expl.addDetail(lambdaExpl);
		expl.addDetail(distribution.explain(stats, normExpl.getValue(), lambdaExpl.getValue()));
	}

	public String toString()
	{
		return (new StringBuilder()).append("IB ").append(distribution.toString()).append("-").append(lambda.toString()).append(normalization.toString()).toString();
	}

	public Distribution getDistribution()
	{
		return distribution;
	}

	public Lambda getLambda()
	{
		return lambda;
	}

	public Normalization getNormalization()
	{
		return normalization;
	}
}
