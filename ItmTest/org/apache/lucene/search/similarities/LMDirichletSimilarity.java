// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LMDirichletSimilarity.java

package org.apache.lucene.search.similarities;

import java.util.Locale;
import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			LMSimilarity, BasicStats

public class LMDirichletSimilarity extends LMSimilarity
{

	private final float mu;

	public LMDirichletSimilarity(LMSimilarity.CollectionModel collectionModel, float mu)
	{
		super(collectionModel);
		this.mu = mu;
	}

	public LMDirichletSimilarity(float mu)
	{
		this.mu = mu;
	}

	public LMDirichletSimilarity(LMSimilarity.CollectionModel collectionModel)
	{
		this(collectionModel, 2000F);
	}

	public LMDirichletSimilarity()
	{
		this(2000F);
	}

	protected float score(BasicStats stats, float freq, float docLen)
	{
		float score = stats.getTotalBoost() * (float)(Math.log(1.0F + freq / (mu * ((LMSimilarity.LMStats)stats).getCollectionProbability())) + Math.log(mu / (docLen + mu)));
		return score <= 0.0F ? 0.0F : score;
	}

	protected void explain(Explanation expl, BasicStats stats, int doc, float freq, float docLen)
	{
		if (stats.getTotalBoost() != 1.0F)
			expl.addDetail(new Explanation(stats.getTotalBoost(), "boost"));
		expl.addDetail(new Explanation(mu, "mu"));
		Explanation weightExpl = new Explanation();
		weightExpl.setValue((float)Math.log(1.0F + freq / (mu * ((LMSimilarity.LMStats)stats).getCollectionProbability())));
		weightExpl.setDescription("term weight");
		expl.addDetail(weightExpl);
		expl.addDetail(new Explanation((float)Math.log(mu / (docLen + mu)), "document norm"));
		super.explain(expl, stats, doc, freq, docLen);
	}

	public float getMu()
	{
		return mu;
	}

	public String getName()
	{
		return String.format(Locale.ROOT, "Dirichlet(%f)", new Object[] {
			Float.valueOf(getMu())
		});
	}
}
