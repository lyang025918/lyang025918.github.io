// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerFieldSimilarityWrapper.java

package org.apache.lucene.search.similarities;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;

// Referenced classes of package org.apache.lucene.search.similarities:
//			Similarity

public abstract class PerFieldSimilarityWrapper extends Similarity
{
	static class PerFieldSimWeight extends Similarity.SimWeight
	{

		Similarity delegate;
		Similarity.SimWeight delegateWeight;

		public float getValueForNormalization()
		{
			return delegateWeight.getValueForNormalization();
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			delegateWeight.normalize(queryNorm, topLevelBoost);
		}

		PerFieldSimWeight()
		{
		}
	}


	public PerFieldSimilarityWrapper()
	{
	}

	public final void computeNorm(FieldInvertState state, Norm norm)
	{
		get(state.getName()).computeNorm(state, norm);
	}

	public final transient Similarity.SimWeight computeWeight(float queryBoost, CollectionStatistics collectionStats, TermStatistics termStats[])
	{
		PerFieldSimWeight weight = new PerFieldSimWeight();
		weight.delegate = get(collectionStats.field());
		weight.delegateWeight = weight.delegate.computeWeight(queryBoost, collectionStats, termStats);
		return weight;
	}

	public final Similarity.ExactSimScorer exactSimScorer(Similarity.SimWeight weight, AtomicReaderContext context)
		throws IOException
	{
		PerFieldSimWeight perFieldWeight = (PerFieldSimWeight)weight;
		return perFieldWeight.delegate.exactSimScorer(perFieldWeight.delegateWeight, context);
	}

	public final Similarity.SloppySimScorer sloppySimScorer(Similarity.SimWeight weight, AtomicReaderContext context)
		throws IOException
	{
		PerFieldSimWeight perFieldWeight = (PerFieldSimWeight)weight;
		return perFieldWeight.delegate.sloppySimScorer(perFieldWeight.delegateWeight, context);
	}

	public abstract Similarity get(String s);
}
