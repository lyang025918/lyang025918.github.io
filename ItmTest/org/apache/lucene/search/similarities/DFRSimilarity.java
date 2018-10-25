// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DFRSimilarity.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			SimilarityBase, BasicModel, AfterEffect, Normalization, 
//			BasicStats

public class DFRSimilarity extends SimilarityBase
{

	protected final BasicModel basicModel;
	protected final AfterEffect afterEffect;
	protected final Normalization normalization;

	public DFRSimilarity(BasicModel basicModel, AfterEffect afterEffect, Normalization normalization)
	{
		if (basicModel == null || afterEffect == null || normalization == null)
		{
			throw new NullPointerException("null parameters not allowed.");
		} else
		{
			this.basicModel = basicModel;
			this.afterEffect = afterEffect;
			this.normalization = normalization;
			return;
		}
	}

	protected float score(BasicStats stats, float freq, float docLen)
	{
		float tfn = normalization.tfn(stats, freq, docLen);
		return stats.getTotalBoost() * basicModel.score(stats, tfn) * afterEffect.score(stats, tfn);
	}

	protected void explain(Explanation expl, BasicStats stats, int doc, float freq, float docLen)
	{
		if (stats.getTotalBoost() != 1.0F)
			expl.addDetail(new Explanation(stats.getTotalBoost(), "boost"));
		Explanation normExpl = normalization.explain(stats, freq, docLen);
		float tfn = normExpl.getValue();
		expl.addDetail(normExpl);
		expl.addDetail(basicModel.explain(stats, tfn));
		expl.addDetail(afterEffect.explain(stats, tfn));
	}

	public String toString()
	{
		return (new StringBuilder()).append("DFR ").append(basicModel.toString()).append(afterEffect.toString()).append(normalization.toString()).toString();
	}

	public BasicModel getBasicModel()
	{
		return basicModel;
	}

	public AfterEffect getAfterEffect()
	{
		return afterEffect;
	}

	public Normalization getNormalization()
	{
		return normalization;
	}
}
