// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiSimilarity.java

package org.apache.lucene.search.similarities;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search.similarities:
//			Similarity

public class MultiSimilarity extends Similarity
{
	static class MultiStats extends Similarity.SimWeight
	{

		final Similarity.SimWeight subStats[];

		public float getValueForNormalization()
		{
			float sum = 0.0F;
			Similarity.SimWeight arr$[] = subStats;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Similarity.SimWeight stat = arr$[i$];
				sum += stat.getValueForNormalization();
			}

			return sum / (float)subStats.length;
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			Similarity.SimWeight arr$[] = subStats;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Similarity.SimWeight stat = arr$[i$];
				stat.normalize(queryNorm, topLevelBoost);
			}

		}

		MultiStats(Similarity.SimWeight subStats[])
		{
			this.subStats = subStats;
		}
	}

	static class MultiSloppyDocScorer extends Similarity.SloppySimScorer
	{

		private final Similarity.SloppySimScorer subScorers[];

		public float score(int doc, float freq)
		{
			float sum = 0.0F;
			Similarity.SloppySimScorer arr$[] = subScorers;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Similarity.SloppySimScorer subScorer = arr$[i$];
				sum += subScorer.score(doc, freq);
			}

			return sum;
		}

		public Explanation explain(int doc, Explanation freq)
		{
			Explanation expl = new Explanation(score(doc, freq.getValue()), "sum of:");
			Similarity.SloppySimScorer arr$[] = subScorers;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Similarity.SloppySimScorer subScorer = arr$[i$];
				expl.addDetail(subScorer.explain(doc, freq));
			}

			return expl;
		}

		public float computeSlopFactor(int distance)
		{
			return subScorers[0].computeSlopFactor(distance);
		}

		public float computePayloadFactor(int doc, int start, int end, BytesRef payload)
		{
			return subScorers[0].computePayloadFactor(doc, start, end, payload);
		}

		MultiSloppyDocScorer(Similarity.SloppySimScorer subScorers[])
		{
			this.subScorers = subScorers;
		}
	}

	static class MultiExactDocScorer extends Similarity.ExactSimScorer
	{

		private final Similarity.ExactSimScorer subScorers[];

		public float score(int doc, int freq)
		{
			float sum = 0.0F;
			Similarity.ExactSimScorer arr$[] = subScorers;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Similarity.ExactSimScorer subScorer = arr$[i$];
				sum += subScorer.score(doc, freq);
			}

			return sum;
		}

		public Explanation explain(int doc, Explanation freq)
		{
			Explanation expl = new Explanation(score(doc, (int)freq.getValue()), "sum of:");
			Similarity.ExactSimScorer arr$[] = subScorers;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Similarity.ExactSimScorer subScorer = arr$[i$];
				expl.addDetail(subScorer.explain(doc, freq));
			}

			return expl;
		}

		MultiExactDocScorer(Similarity.ExactSimScorer subScorers[])
		{
			this.subScorers = subScorers;
		}
	}


	protected final Similarity sims[];

	public MultiSimilarity(Similarity sims[])
	{
		this.sims = sims;
	}

	public void computeNorm(FieldInvertState state, Norm norm)
	{
		sims[0].computeNorm(state, norm);
	}

	public transient Similarity.SimWeight computeWeight(float queryBoost, CollectionStatistics collectionStats, TermStatistics termStats[])
	{
		Similarity.SimWeight subStats[] = new Similarity.SimWeight[sims.length];
		for (int i = 0; i < subStats.length; i++)
			subStats[i] = sims[i].computeWeight(queryBoost, collectionStats, termStats);

		return new MultiStats(subStats);
	}

	public Similarity.ExactSimScorer exactSimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		Similarity.ExactSimScorer subScorers[] = new Similarity.ExactSimScorer[sims.length];
		for (int i = 0; i < subScorers.length; i++)
			subScorers[i] = sims[i].exactSimScorer(((MultiStats)stats).subStats[i], context);

		return new MultiExactDocScorer(subScorers);
	}

	public Similarity.SloppySimScorer sloppySimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		Similarity.SloppySimScorer subScorers[] = new Similarity.SloppySimScorer[sims.length];
		for (int i = 0; i < subScorers.length; i++)
			subScorers[i] = sims[i].sloppySimScorer(((MultiStats)stats).subStats[i], context);

		return new MultiSloppyDocScorer(subScorers);
	}
}
