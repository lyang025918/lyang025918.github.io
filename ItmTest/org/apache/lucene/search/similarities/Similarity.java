// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Similarity.java

package org.apache.lucene.search.similarities;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;

public abstract class Similarity
{
	public static abstract class SimWeight
	{

		public abstract float getValueForNormalization();

		public abstract void normalize(float f, float f1);

		public SimWeight()
		{
		}
	}

	public static abstract class SloppySimScorer
	{

		public abstract float score(int i, float f);

		public abstract float computeSlopFactor(int i);

		public abstract float computePayloadFactor(int i, int j, int k, BytesRef bytesref);

		public Explanation explain(int doc, Explanation freq)
		{
			Explanation result = new Explanation(score(doc, freq.getValue()), (new StringBuilder()).append("score(doc=").append(doc).append(",freq=").append(freq.getValue()).append("), with freq of:").toString());
			result.addDetail(freq);
			return result;
		}

		public SloppySimScorer()
		{
		}
	}

	public static abstract class ExactSimScorer
	{

		public abstract float score(int i, int j);

		public Explanation explain(int doc, Explanation freq)
		{
			Explanation result = new Explanation(score(doc, (int)freq.getValue()), (new StringBuilder()).append("score(doc=").append(doc).append(",freq=").append(freq.getValue()).append("), with freq of:").toString());
			result.addDetail(freq);
			return result;
		}

		public ExactSimScorer()
		{
		}
	}


	public Similarity()
	{
	}

	public float coord(int overlap, int maxOverlap)
	{
		return 1.0F;
	}

	public float queryNorm(float valueForNormalization)
	{
		return 1.0F;
	}

	public abstract void computeNorm(FieldInvertState fieldinvertstate, Norm norm);

	public transient abstract SimWeight computeWeight(float f, CollectionStatistics collectionstatistics, TermStatistics atermstatistics[]);

	public abstract ExactSimScorer exactSimScorer(SimWeight simweight, AtomicReaderContext atomicreadercontext)
		throws IOException;

	public abstract SloppySimScorer sloppySimScorer(SimWeight simweight, AtomicReaderContext atomicreadercontext)
		throws IOException;
}
