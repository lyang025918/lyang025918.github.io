// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LMJelinekMercerSimilarity.java

package org.apache.lucene.search.similarities;

import java.util.Locale;
import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			LMSimilarity, BasicStats

public class LMJelinekMercerSimilarity extends LMSimilarity
{

	private final float lambda;

	public LMJelinekMercerSimilarity(LMSimilarity.CollectionModel collectionModel, float lambda)
	{
		super(collectionModel);
		this.lambda = lambda;
	}

	public LMJelinekMercerSimilarity(float lambda)
	{
		this.lambda = lambda;
	}

	protected float score(BasicStats stats, float freq, float docLen)
	{
		return stats.getTotalBoost() * (float)Math.log(1.0F + ((1.0F - lambda) * freq) / docLen / (lambda * ((LMSimilarity.LMStats)stats).getCollectionProbability()));
	}

	protected void explain(Explanation expl, BasicStats stats, int doc, float freq, float docLen)
	{
		if (stats.getTotalBoost() != 1.0F)
			expl.addDetail(new Explanation(stats.getTotalBoost(), "boost"));
		expl.addDetail(new Explanation(lambda, "lambda"));
		super.explain(expl, stats, doc, freq, docLen);
	}

	public float getLambda()
	{
		return lambda;
	}

	public String getName()
	{
		return String.format(Locale.ROOT, "Jelinek-Mercer(%f)", new Object[] {
			Float.valueOf(getLambda())
		});
	}
}
