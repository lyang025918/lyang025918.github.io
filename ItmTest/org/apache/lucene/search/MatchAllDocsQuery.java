// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MatchAllDocsQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Set;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search:
//			Query, IndexSearcher, Weight, ComplexExplanation, 
//			Explanation, Scorer

public class MatchAllDocsQuery extends Query
{
	private class MatchAllDocsWeight extends Weight
	{

		private float queryWeight;
		private float queryNorm;
		final MatchAllDocsQuery this$0;

		public String toString()
		{
			return (new StringBuilder()).append("weight(").append(MatchAllDocsQuery.this).append(")").toString();
		}

		public Query getQuery()
		{
			return MatchAllDocsQuery.this;
		}

		public float getValueForNormalization()
		{
			queryWeight = getBoost();
			return queryWeight * queryWeight;
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			this.queryNorm = queryNorm * topLevelBoost;
			queryWeight *= this.queryNorm;
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			return new MatchAllScorer(context.reader(), acceptDocs, this, queryWeight);
		}

		public Explanation explain(AtomicReaderContext context, int doc)
		{
			Explanation queryExpl = new ComplexExplanation(true, queryWeight, "MatchAllDocsQuery, product of:");
			if (getBoost() != 1.0F)
				queryExpl.addDetail(new Explanation(getBoost(), "boost"));
			queryExpl.addDetail(new Explanation(queryNorm, "queryNorm"));
			return queryExpl;
		}

		public MatchAllDocsWeight(IndexSearcher searcher)
		{
			this$0 = MatchAllDocsQuery.this;
			super();
		}
	}

	private class MatchAllScorer extends Scorer
	{

		final float score;
		private int doc;
		private final int maxDoc;
		private final Bits liveDocs;
		final MatchAllDocsQuery this$0;

		public int docID()
		{
			return doc;
		}

		public int nextDoc()
			throws IOException
		{
			for (doc++; liveDocs != null && doc < maxDoc && !liveDocs.get(doc); doc++);
			if (doc == maxDoc)
				doc = 0x7fffffff;
			return doc;
		}

		public float score()
		{
			return score;
		}

		public float freq()
		{
			return 1.0F;
		}

		public int advance(int target)
			throws IOException
		{
			doc = target - 1;
			return nextDoc();
		}

		MatchAllScorer(IndexReader reader, Bits liveDocs, Weight w, float score)
		{
			this$0 = MatchAllDocsQuery.this;
			super(w);
			doc = -1;
			this.liveDocs = liveDocs;
			this.score = score;
			maxDoc = reader.maxDoc();
		}
	}


	public MatchAllDocsQuery()
	{
	}

	public Weight createWeight(IndexSearcher searcher)
	{
		return new MatchAllDocsWeight(searcher);
	}

	public void extractTerms(Set set)
	{
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("*:*");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof MatchAllDocsQuery))
		{
			return false;
		} else
		{
			MatchAllDocsQuery other = (MatchAllDocsQuery)o;
			return getBoost() == other.getBoost();
		}
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) ^ 0x1aa71190;
	}
}
