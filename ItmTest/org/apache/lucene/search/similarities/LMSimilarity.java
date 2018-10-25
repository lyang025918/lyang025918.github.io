// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LMSimilarity.java

package org.apache.lucene.search.similarities;

import java.util.Locale;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.search.similarities:
//			SimilarityBase, BasicStats

public abstract class LMSimilarity extends SimilarityBase
{
	public static class DefaultCollectionModel
		implements CollectionModel
	{

		public float computeProbability(BasicStats stats)
		{
			return ((float)stats.getTotalTermFreq() + 1.0F) / ((float)stats.getNumberOfFieldTokens() + 1.0F);
		}

		public String getName()
		{
			return null;
		}

		public DefaultCollectionModel()
		{
		}
	}

	public static interface CollectionModel
	{

		public abstract float computeProbability(BasicStats basicstats);

		public abstract String getName();
	}

	public static class LMStats extends BasicStats
	{

		private float collectionProbability;

		public final float getCollectionProbability()
		{
			return collectionProbability;
		}

		public final void setCollectionProbability(float collectionProbability)
		{
			this.collectionProbability = collectionProbability;
		}

		public LMStats(String field, float queryBoost)
		{
			super(field, queryBoost);
		}
	}


	protected final CollectionModel collectionModel;

	public LMSimilarity(CollectionModel collectionModel)
	{
		this.collectionModel = collectionModel;
	}

	public LMSimilarity()
	{
		this(((CollectionModel) (new DefaultCollectionModel())));
	}

	protected BasicStats newStats(String field, float queryBoost)
	{
		return new LMStats(field, queryBoost);
	}

	protected void fillBasicStats(BasicStats stats, CollectionStatistics collectionStats, TermStatistics termStats)
	{
		super.fillBasicStats(stats, collectionStats, termStats);
		LMStats lmStats = (LMStats)stats;
		lmStats.setCollectionProbability(collectionModel.computeProbability(stats));
	}

	protected void explain(Explanation expl, BasicStats stats, int doc, float freq, float docLen)
	{
		expl.addDetail(new Explanation(collectionModel.computeProbability(stats), "collection probability"));
	}

	public abstract String getName();

	public String toString()
	{
		String coll = collectionModel.getName();
		if (coll != null)
			return String.format(Locale.ROOT, "LM %s - %s", new Object[] {
				getName(), coll
			});
		else
			return String.format(Locale.ROOT, "LM %s", new Object[] {
				getName()
			});
	}
}
