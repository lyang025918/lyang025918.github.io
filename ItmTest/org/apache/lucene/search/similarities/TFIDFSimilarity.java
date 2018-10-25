// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TFIDFSimilarity.java

package org.apache.lucene.search.similarities;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.SmallFloat;

// Referenced classes of package org.apache.lucene.search.similarities:
//			Similarity

public abstract class TFIDFSimilarity extends Similarity
{
	private static class IDFStats extends Similarity.SimWeight
	{

		private final String field;
		private final Explanation idf;
		private float queryNorm;
		private float queryWeight;
		private final float queryBoost;
		private float value;

		public float getValueForNormalization()
		{
			return queryWeight * queryWeight;
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			this.queryNorm = queryNorm * topLevelBoost;
			queryWeight *= this.queryNorm;
			value = queryWeight * idf.getValue();
		}






		public IDFStats(String field, Explanation idf, float queryBoost)
		{
			this.field = field;
			this.idf = idf;
			this.queryBoost = queryBoost;
			queryWeight = idf.getValue() * queryBoost;
		}
	}

	private final class SloppyTFIDFDocScorer extends Similarity.SloppySimScorer
	{

		private final IDFStats stats;
		private final float weightValue;
		private final byte norms[];
		final TFIDFSimilarity this$0;

		public float score(int doc, float freq)
		{
			float raw = tf(freq) * weightValue;
			return norms != null ? raw * decodeNormValue(norms[doc]) : raw;
		}

		public float computeSlopFactor(int distance)
		{
			return sloppyFreq(distance);
		}

		public float computePayloadFactor(int doc, int start, int end, BytesRef payload)
		{
			return scorePayload(doc, start, end, payload);
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return explainScore(doc, freq, stats, norms);
		}

		SloppyTFIDFDocScorer(IDFStats stats, DocValues norms)
			throws IOException
		{
			this$0 = TFIDFSimilarity.this;
			super();
			this.stats = stats;
			weightValue = stats.value;
			this.norms = norms != null ? (byte[])(byte[])norms.getSource().getArray() : null;
		}
	}

	private final class ExactTFIDFDocScorer extends Similarity.ExactSimScorer
	{

		private final IDFStats stats;
		private final float weightValue;
		private final byte norms[];
		private static final int SCORE_CACHE_SIZE = 32;
		private float scoreCache[];
		final TFIDFSimilarity this$0;

		public float score(int doc, int freq)
		{
			float raw = freq >= 32 ? tf(freq) * weightValue : scoreCache[freq];
			return norms != null ? raw * decodeNormValue(norms[doc]) : raw;
		}

		public Explanation explain(int doc, Explanation freq)
		{
			return explainScore(doc, freq, stats, norms);
		}

		ExactTFIDFDocScorer(IDFStats stats, DocValues norms)
			throws IOException
		{
			this$0 = TFIDFSimilarity.this;
			super();
			scoreCache = new float[32];
			this.stats = stats;
			weightValue = stats.value;
			this.norms = norms != null ? (byte[])(byte[])norms.getSource().getArray() : null;
			for (int i = 0; i < 32; i++)
				scoreCache[i] = tf(i) * weightValue;

		}
	}


	private static final float NORM_TABLE[];

	public TFIDFSimilarity()
	{
	}

	public abstract float coord(int i, int j);

	public abstract float queryNorm(float f);

	public float tf(int freq)
	{
		return tf(freq);
	}

	public abstract float tf(float f);

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

	public abstract float idf(long l, long l1);

	public float decodeNormValue(byte b)
	{
		return NORM_TABLE[b & 0xff];
	}

	public byte encodeNormValue(float f)
	{
		return SmallFloat.floatToByte315(f);
	}

	public abstract float sloppyFreq(int i);

	public abstract float scorePayload(int i, int j, int k, BytesRef bytesref);

	public final transient Similarity.SimWeight computeWeight(float queryBoost, CollectionStatistics collectionStats, TermStatistics termStats[])
	{
		Explanation idf = termStats.length != 1 ? idfExplain(collectionStats, termStats) : idfExplain(collectionStats, termStats[0]);
		return new IDFStats(collectionStats.field(), idf, queryBoost);
	}

	public final Similarity.ExactSimScorer exactSimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		IDFStats idfstats = (IDFStats)stats;
		return new ExactTFIDFDocScorer(idfstats, context.reader().normValues(idfstats.field));
	}

	public final Similarity.SloppySimScorer sloppySimScorer(Similarity.SimWeight stats, AtomicReaderContext context)
		throws IOException
	{
		IDFStats idfstats = (IDFStats)stats;
		return new SloppyTFIDFDocScorer(idfstats, context.reader().normValues(idfstats.field));
	}

	private Explanation explainScore(int doc, Explanation freq, IDFStats stats, byte norms[])
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append("score(doc=").append(doc).append(",freq=").append(freq).append("), product of:").toString());
		Explanation queryExpl = new Explanation();
		queryExpl.setDescription("queryWeight, product of:");
		Explanation boostExpl = new Explanation(stats.queryBoost, "boost");
		if (stats.queryBoost != 1.0F)
			queryExpl.addDetail(boostExpl);
		queryExpl.addDetail(stats.idf);
		Explanation queryNormExpl = new Explanation(stats.queryNorm, "queryNorm");
		queryExpl.addDetail(queryNormExpl);
		queryExpl.setValue(boostExpl.getValue() * stats.idf.getValue() * queryNormExpl.getValue());
		result.addDetail(queryExpl);
		Explanation fieldExpl = new Explanation();
		fieldExpl.setDescription((new StringBuilder()).append("fieldWeight in ").append(doc).append(", product of:").toString());
		Explanation tfExplanation = new Explanation();
		tfExplanation.setValue(tf(freq.getValue()));
		tfExplanation.setDescription((new StringBuilder()).append("tf(freq=").append(freq.getValue()).append("), with freq of:").toString());
		tfExplanation.addDetail(freq);
		fieldExpl.addDetail(tfExplanation);
		fieldExpl.addDetail(stats.idf);
		Explanation fieldNormExpl = new Explanation();
		float fieldNorm = norms == null ? 1.0F : decodeNormValue(norms[doc]);
		fieldNormExpl.setValue(fieldNorm);
		fieldNormExpl.setDescription((new StringBuilder()).append("fieldNorm(doc=").append(doc).append(")").toString());
		fieldExpl.addDetail(fieldNormExpl);
		fieldExpl.setValue(tfExplanation.getValue() * stats.idf.getValue() * fieldNormExpl.getValue());
		result.addDetail(fieldExpl);
		result.setValue(queryExpl.getValue() * fieldExpl.getValue());
		if (queryExpl.getValue() == 1.0F)
			return fieldExpl;
		else
			return result;
	}

	static 
	{
		NORM_TABLE = new float[256];
		for (int i = 0; i < 256; i++)
			NORM_TABLE[i] = SmallFloat.byte315ToFloat((byte)i);

	}

}
