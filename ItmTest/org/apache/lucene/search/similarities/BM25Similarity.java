// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BM25Similarity.java

package org.apache.lucene.search.similarities;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.SmallFloat;

// Referenced classes of package org.apache.lucene.search.similarities:
//			Similarity

public class BM25Similarity extends Similarity
{
	private static class BM25Stats extends Similarity.SimWeight
	{

		private final Explanation idf;
		private final float avgdl;
		private final float queryBoost;
		private float topLevelBoost;
		private float weight;
		private final String field;
		private final float cache[];

		public float getValueForNormalization()
		{
			float queryWeight = idf.getValue() * queryBoost;
			return queryWeight * queryWeight;
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			this.topLevelBoost = topLevelBoost;
			weight = idf.getValue() * queryBoost * topLevelBoost;
		}








		BM25Stats(String field, Explanation idf, float queryBoost, float avgdl, float cache[])
		{
			this.field = field;
			this.idf = idf;
			this.queryBoost = queryBoost;
			this.avgdl = avgdl;
			this.cache = cache;
		}
	}

	private class SloppyBM25DocScorer extends Similarity.SloppySimScorer
	{

		private final BM25Stats stats;
		private final float weightValue;
		private final byte norms[];
		private final float cache[];
		final BM25Similarity this$0;

		public float score(int doc, float freq)
		{
			float norm = norms != null ? cache[norms[doc] & 0xff] : k1;
			return (weightValue * freq) / (freq + norm);
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return explainScore(doc, freq, stats, norms);
		}

		public float computeSlopFactor(int distance)
		{
			return sloppyFreq(distance);
		}

		public float computePayloadFactor(int doc, int start, int end, BytesRef payload)
		{
			return scorePayload(doc, start, end, payload);
		}

		SloppyBM25DocScorer(BM25Stats stats, DocValues norms)
			throws IOException
		{
			this$0 = BM25Similarity.this;
			super();
			this.stats = stats;
			weightValue = stats.weight * (k1 + 1.0F);
			cache = stats.cache;
			this.norms = norms != null ? (byte[])(byte[])norms.getSource().getArray() : null;
		}
	}

	private class ExactBM25DocScorerNoNorms extends Similarity.ExactSimScorer
	{

		private final BM25Stats stats;
		private final float weightValue;
		private static final int SCORE_CACHE_SIZE = 32;
		private float scoreCache[];
		final BM25Similarity this$0;

		public float score(int doc, int freq)
		{
			return freq >= 32 ? (weightValue * (float)freq) / ((float)freq + k1) : scoreCache[freq];
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return explainScore(doc, freq, stats, null);
		}

		ExactBM25DocScorerNoNorms(BM25Stats stats)
		{
			this$0 = BM25Similarity.this;
			super();
			scoreCache = new float[32];
			this.stats = stats;
			weightValue = stats.weight * (k1 + 1.0F);
			for (int i = 0; i < 32; i++)
				scoreCache[i] = (weightValue * (float)i) / ((float)i + k1);

		}
	}

	private class ExactBM25DocScorer extends Similarity.ExactSimScorer
	{

		private final BM25Stats stats;
		private final float weightValue;
		private final byte norms[];
		private final float cache[];
		static final boolean $assertionsDisabled = !org/apache/lucene/search/similarities/BM25Similarity.desiredAssertionStatus();
		final BM25Similarity this$0;

		public float score(int doc, int freq)
		{
			return (weightValue * (float)freq) / ((float)freq + cache[norms[doc] & 0xff]);
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return explainScore(doc, freq, stats, norms);
		}


		ExactBM25DocScorer(BM25Stats stats, DocValues norms)
			throws IOException
		{
			this$0 = BM25Similarity.this;
			super();
			if (!$assertionsDisabled && norms == null)
			{
				throw new AssertionError();
			} else
			{
				this.stats = stats;
				weightValue = stats.weight * (k1 + 1.0F);
				cache = stats.cache;
				this.norms = (byte[])(byte[])norms.getSource().getArray();
				return;
			}
		}
	}


	private final float k1;
	private final float b;
	protected boolean discountOverlaps;
	private static final float NORM_TABLE[];

	public BM25Similarity(float k1, float b)
	{
		discountOverlaps = true;
		this.k1 = k1;
		this.b = b;
	}

	public BM25Similarity()
	{
		discountOverlaps = true;
		k1 = 1.2F;
		b = 0.75F;
	}

	protected float idf(long docFreq, long numDocs)
	{
		return (float)Math.log(1.0D + ((double)(numDocs - docFreq) + 0.5D) / ((double)docFreq + 0.5D));
	}

	protected float sloppyFreq(int distance)
	{
		return 1.0F / (float)(distance + 1);
	}

	protected float scorePayload(int doc, int start, int end, BytesRef bytesref)
	{
		return 1.0F;
	}

	protected float avgFieldLength(CollectionStatistics collectionStats)
	{
		long sumTotalTermFreq = collectionStats.sumTotalTermFreq();
		if (sumTotalTermFreq <= 0L)
			return 1.0F;
		else
			return (float)((double)sumTotalTermFreq / (double)collectionStats.maxDoc());
	}

	protected byte encodeNormValue(float boost, int fieldLength)
	{
		return SmallFloat.floatToByte315(boost / (float)Math.sqrt(fieldLength));
	}

	protected float decodeNormValue(byte b)
	{
		return NORM_TABLE[b & 0xff];
	}

	public void setDiscountOverlaps(boolean v)
	{
		discountOverlaps = v;
	}

	public boolean getDiscountOverlaps()
	{
		return discountOverlaps;
	}

	public final void computeNorm(FieldInvertState state, Norm norm)
	{
		int numTerms = discountOverlaps ? state.getLength() - state.getNumOverlap() : state.getLength();
		norm.setByte(encodeNormValue(state.getBoost(), numTerms));
	}

	public Explanation idfExplain(CollectionStatistics collectionStats, TermStatistics termStats)
	{
		long df = termStats.docFreq();
		long max = collectionStats.maxDoc();
		float idf = idf(df, max);
		return new Explanation(idf, (new StringBuilder()).append("idf(docFreq=").append(df).append(", maxDocs=").append(max).append(")").toString());
	}

	public Explanation idfExplain(CollectionStatistics collectionStats, TermStatistics termStats[])
	{
		long max = collectionStats.maxDoc();
		float idf = 0.0F;
		Explanation exp = new Explanation();
		exp.setDescription("idf(), sum of:");
		TermStatistics arr$[] = termStats;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			TermStatistics stat = arr$[i$];
			long df = stat.docFreq();
			float termIdf = idf(df, max);
			exp.addDetail(new Explanation(termIdf, (new StringBuilder()).append("idf(docFreq=").append(df).append(", maxDocs=").append(max).append(")").toString()));
			idf += termIdf;
		}

		exp.setValue(idf);
		return exp;
	}

	public final transient Similarity.SimWeight computeWeight(float queryBoost, CollectionStatistics collectionStats, TermStatistics termStats[])
	{
		Explanation idf = termStats.length != 1 ? idfExplain(collectionStats, termStats) : idfExplain(collectionStats, termStats[0]);
		float avgdl = avgFieldLength(collectionStats);
		float cache[] = new float[256];
		for (int i = 0; i < cache.length; i++)
			cache[i] = k1 * ((1.0F - b) + (b * decodeNormValue((byte)i)) / avgdl);

		return new BM25Stats(collectionStats.field(), idf, queryBoost, avgdl, cache);
	}

	public final Similarity.ExactSimScorer exactSimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		BM25Stats bm25stats = (BM25Stats)stats;
		DocValues norms = context.reader().normValues(bm25stats.field);
		return ((Similarity.ExactSimScorer) (norms != null ? new ExactBM25DocScorer(bm25stats, norms) : new ExactBM25DocScorerNoNorms(bm25stats)));
	}

	public final Similarity.SloppySimScorer sloppySimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		BM25Stats bm25stats = (BM25Stats)stats;
		return new SloppyBM25DocScorer(bm25stats, context.reader().normValues(bm25stats.field));
	}

	private Explanation explainScore(int doc, Explanation freq, BM25Stats stats, byte norms[])
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append("score(doc=").append(doc).append(",freq=").append(freq).append("), product of:").toString());
		Explanation boostExpl = new Explanation(stats.queryBoost * stats.topLevelBoost, "boost");
		if (boostExpl.getValue() != 1.0F)
			result.addDetail(boostExpl);
		result.addDetail(stats.idf);
		Explanation tfNormExpl = new Explanation();
		tfNormExpl.setDescription("tfNorm, computed from:");
		tfNormExpl.addDetail(freq);
		tfNormExpl.addDetail(new Explanation(k1, "parameter k1"));
		if (norms == null)
		{
			tfNormExpl.addDetail(new Explanation(0.0F, "parameter b (norms omitted for field)"));
			tfNormExpl.setValue((freq.getValue() * (k1 + 1.0F)) / (freq.getValue() + k1));
		} else
		{
			float doclen = decodeNormValue(norms[doc]);
			tfNormExpl.addDetail(new Explanation(b, "parameter b"));
			tfNormExpl.addDetail(new Explanation(stats.avgdl, "avgFieldLength"));
			tfNormExpl.addDetail(new Explanation(doclen, "fieldLength"));
			tfNormExpl.setValue((freq.getValue() * (k1 + 1.0F)) / (freq.getValue() + k1 * ((1.0F - b) + (b * doclen) / stats.avgdl)));
		}
		result.addDetail(tfNormExpl);
		result.setValue(boostExpl.getValue() * stats.idf.getValue() * tfNormExpl.getValue());
		return result;
	}

	public String toString()
	{
		return (new StringBuilder()).append("BM25(k1=").append(k1).append(",b=").append(b).append(")").toString();
	}

	public float getK1()
	{
		return k1;
	}

	public float getB()
	{
		return b;
	}

	static 
	{
		NORM_TABLE = new float[256];
		for (int i = 0; i < 256; i++)
		{
			float f = SmallFloat.byte315ToFloat((byte)i);
			NORM_TABLE[i] = 1.0F / (f * f);
		}

	}


}
