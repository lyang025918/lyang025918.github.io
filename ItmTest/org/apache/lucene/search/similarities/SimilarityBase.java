// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimilarityBase.java

package org.apache.lucene.search.similarities;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.SmallFloat;

// Referenced classes of package org.apache.lucene.search.similarities:
//			Similarity, BasicStats, MultiSimilarity

public abstract class SimilarityBase extends Similarity
{
	private class BasicSloppyDocScorer extends Similarity.SloppySimScorer
	{

		private final BasicStats stats;
		private final byte norms[];
		final SimilarityBase this$0;

		public float score(int doc, float freq)
		{
			return SimilarityBase.this.score(stats, freq, norms != null ? decodeNormValue(norms[doc]) : 1.0F);
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return SimilarityBase.this.explain(stats, doc, freq, norms != null ? decodeNormValue(norms[doc]) : 1.0F);
		}

		public float computeSlopFactor(int distance)
		{
			return 1.0F / (float)(distance + 1);
		}

		public float computePayloadFactor(int doc, int start, int end, BytesRef bytesref)
		{
			return 1.0F;
		}

		BasicSloppyDocScorer(BasicStats stats, DocValues norms)
			throws IOException
		{
			this$0 = SimilarityBase.this;
			super();
			this.stats = stats;
			this.norms = norms != null ? (byte[])(byte[])norms.getSource().getArray() : null;
		}
	}

	private class BasicExactDocScorer extends Similarity.ExactSimScorer
	{

		private final BasicStats stats;
		private final byte norms[];
		final SimilarityBase this$0;

		public float score(int doc, int freq)
		{
			return SimilarityBase.this.score(stats, freq, norms != null ? decodeNormValue(norms[doc]) : 1.0F);
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return SimilarityBase.this.explain(stats, doc, freq, norms != null ? decodeNormValue(norms[doc]) : 1.0F);
		}

		BasicExactDocScorer(BasicStats stats, DocValues norms)
			throws IOException
		{
			this$0 = SimilarityBase.this;
			super();
			this.stats = stats;
			this.norms = norms != null ? (byte[])(byte[])norms.getSource().getArray() : null;
		}
	}


	private static final double LOG_2 = Math.log(2D);
	protected boolean discountOverlaps;
	private static final float NORM_TABLE[];
	static final boolean $assertionsDisabled = !org/apache/lucene/search/similarities/SimilarityBase.desiredAssertionStatus();

	public SimilarityBase()
	{
		discountOverlaps = true;
	}

	public void setDiscountOverlaps(boolean v)
	{
		discountOverlaps = v;
	}

	public boolean getDiscountOverlaps()
	{
		return discountOverlaps;
	}

	public final transient Similarity.SimWeight computeWeight(float queryBoost, CollectionStatistics collectionStats, TermStatistics termStats[])
	{
		BasicStats stats[] = new BasicStats[termStats.length];
		for (int i = 0; i < termStats.length; i++)
		{
			stats[i] = newStats(collectionStats.field(), queryBoost);
			fillBasicStats(stats[i], collectionStats, termStats[i]);
		}

		return ((Similarity.SimWeight) (stats.length != 1 ? new MultiSimilarity.MultiStats(stats) : stats[0]));
	}

	protected BasicStats newStats(String field, float queryBoost)
	{
		return new BasicStats(field, queryBoost);
	}

	protected void fillBasicStats(BasicStats stats, CollectionStatistics collectionStats, TermStatistics termStats)
	{
		if (!$assertionsDisabled && collectionStats.sumTotalTermFreq() != -1L && collectionStats.sumTotalTermFreq() < termStats.totalTermFreq())
			throw new AssertionError();
		long numberOfDocuments = collectionStats.maxDoc();
		long docFreq = termStats.docFreq();
		long totalTermFreq = termStats.totalTermFreq();
		if (totalTermFreq == -1L)
			totalTermFreq = docFreq;
		long sumTotalTermFreq = collectionStats.sumTotalTermFreq();
		long numberOfFieldTokens;
		float avgFieldLength;
		if (sumTotalTermFreq <= 0L)
		{
			numberOfFieldTokens = docFreq;
			avgFieldLength = 1.0F;
		} else
		{
			numberOfFieldTokens = sumTotalTermFreq;
			avgFieldLength = (float)numberOfFieldTokens / (float)numberOfDocuments;
		}
		stats.setNumberOfDocuments(numberOfDocuments);
		stats.setNumberOfFieldTokens(numberOfFieldTokens);
		stats.setAvgFieldLength(avgFieldLength);
		stats.setDocFreq(docFreq);
		stats.setTotalTermFreq(totalTermFreq);
	}

	protected abstract float score(BasicStats basicstats, float f, float f1);

	protected void explain(Explanation explanation, BasicStats basicstats, int i, float f, float f1)
	{
	}

	protected Explanation explain(BasicStats stats, int doc, Explanation freq, float docLen)
	{
		Explanation result = new Explanation();
		result.setValue(score(stats, freq.getValue(), docLen));
		result.setDescription((new StringBuilder()).append("score(").append(getClass().getSimpleName()).append(", doc=").append(doc).append(", freq=").append(freq.getValue()).append("), computed from:").toString());
		result.addDetail(freq);
		explain(result, stats, doc, freq.getValue(), docLen);
		return result;
	}

	public Similarity.ExactSimScorer exactSimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		if (stats instanceof MultiSimilarity.MultiStats)
		{
			Similarity.SimWeight subStats[] = ((MultiSimilarity.MultiStats)stats).subStats;
			Similarity.ExactSimScorer subScorers[] = new Similarity.ExactSimScorer[subStats.length];
			for (int i = 0; i < subScorers.length; i++)
			{
				BasicStats basicstats = (BasicStats)subStats[i];
				subScorers[i] = new BasicExactDocScorer(basicstats, context.reader().normValues(basicstats.field));
			}

			return new MultiSimilarity.MultiExactDocScorer(subScorers);
		} else
		{
			BasicStats basicstats = (BasicStats)stats;
			return new BasicExactDocScorer(basicstats, context.reader().normValues(basicstats.field));
		}
	}

	public Similarity.SloppySimScorer sloppySimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		if (stats instanceof MultiSimilarity.MultiStats)
		{
			Similarity.SimWeight subStats[] = ((MultiSimilarity.MultiStats)stats).subStats;
			Similarity.SloppySimScorer subScorers[] = new Similarity.SloppySimScorer[subStats.length];
			for (int i = 0; i < subScorers.length; i++)
			{
				BasicStats basicstats = (BasicStats)subStats[i];
				subScorers[i] = new BasicSloppyDocScorer(basicstats, context.reader().normValues(basicstats.field));
			}

			return new MultiSimilarity.MultiSloppyDocScorer(subScorers);
		} else
		{
			BasicStats basicstats = (BasicStats)stats;
			return new BasicSloppyDocScorer(basicstats, context.reader().normValues(basicstats.field));
		}
	}

	public abstract String toString();

	public void computeNorm(FieldInvertState state, Norm norm)
	{
		float numTerms;
		if (discountOverlaps)
			numTerms = state.getLength() - state.getNumOverlap();
		else
			numTerms = (float)state.getLength() / state.getBoost();
		norm.setByte(encodeNormValue(state.getBoost(), numTerms));
	}

	protected float decodeNormValue(byte norm)
	{
		return NORM_TABLE[norm & 0xff];
	}

	protected byte encodeNormValue(float boost, float length)
	{
		return SmallFloat.floatToByte315(boost / (float)Math.sqrt(length));
	}

	public static double log2(double x)
	{
		return Math.log(x) / LOG_2;
	}

	static 
	{
		NORM_TABLE = new float[256];
		for (int i = 0; i < 256; i++)
		{
			float floatNorm = SmallFloat.byte315ToFloat((byte)i);
			NORM_TABLE[i] = 1.0F / (floatNorm * floatNorm);
		}

	}
}
