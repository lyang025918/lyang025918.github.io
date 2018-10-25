// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostedQuery.java

package org.apache.lucene.queries.function;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.queries.function:
//			ValueSource, FunctionValues

public class BoostedQuery extends Query
{
	private class CustomScorer extends Scorer
	{

		private final BoostedWeight weight;
		private final float qWeight;
		private final Scorer scorer;
		private final FunctionValues vals;
		private final AtomicReaderContext readerContext;
		final BoostedQuery this$0;

		public int docID()
		{
			return scorer.docID();
		}

		public int advance(int target)
			throws IOException
		{
			return scorer.advance(target);
		}

		public int nextDoc()
			throws IOException
		{
			return scorer.nextDoc();
		}

		public float score()
			throws IOException
		{
			float score = qWeight * scorer.score() * vals.floatVal(scorer.docID());
			return score <= (-1.0F / 0.0F) ? -3.402823E+038F : score;
		}

		public float freq()
			throws IOException
		{
			return scorer.freq();
		}

		public Collection getChildren()
		{
			return Collections.singleton(new org.apache.lucene.search.Scorer.ChildScorer(scorer, "CUSTOM"));
		}

		public Explanation explain(int doc)
			throws IOException
		{
			Explanation subQueryExpl = weight.qWeight.explain(readerContext, doc);
			if (!subQueryExpl.isMatch())
			{
				return subQueryExpl;
			} else
			{
				float sc = subQueryExpl.getValue() * vals.floatVal(doc);
				Explanation res = new ComplexExplanation(true, sc, (new StringBuilder()).append(toString()).append(", product of:").toString());
				res.addDetail(subQueryExpl);
				res.addDetail(vals.explain(doc));
				return res;
			}
		}

		private CustomScorer(AtomicReaderContext readerContext, BoostedWeight w, float qWeight, Scorer scorer, ValueSource vs)
			throws IOException
		{
			this$0 = BoostedQuery.this;
			super(w);
			weight = w;
			this.qWeight = qWeight;
			this.scorer = scorer;
			this.readerContext = readerContext;
			vals = vs.getValues(weight.fcontext, readerContext);
		}

	}

	private class BoostedWeight extends Weight
	{

		final IndexSearcher searcher;
		Weight qWeight;
		Map fcontext;
		final BoostedQuery this$0;

		public Query getQuery()
		{
			return BoostedQuery.this;
		}

		public float getValueForNormalization()
			throws IOException
		{
			float sum = qWeight.getValueForNormalization();
			sum *= getBoost() * getBoost();
			return sum;
		}

		public void normalize(float norm, float topLevelBoost)
		{
			topLevelBoost *= getBoost();
			qWeight.normalize(norm, topLevelBoost);
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			Scorer subQueryScorer = qWeight.scorer(context, true, false, acceptDocs);
			if (subQueryScorer == null)
				return null;
			else
				return new CustomScorer(context, this, getBoost(), subQueryScorer, boostVal);
		}

		public Explanation explain(AtomicReaderContext readerContext, int doc)
			throws IOException
		{
			Explanation subQueryExpl = qWeight.explain(readerContext, doc);
			if (!subQueryExpl.isMatch())
			{
				return subQueryExpl;
			} else
			{
				FunctionValues vals = boostVal.getValues(fcontext, readerContext);
				float sc = subQueryExpl.getValue() * vals.floatVal(doc);
				Explanation res = new ComplexExplanation(true, sc, (new StringBuilder()).append(toString()).append(", product of:").toString());
				res.addDetail(subQueryExpl);
				res.addDetail(vals.explain(doc));
				return res;
			}
		}

		public BoostedWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = BoostedQuery.this;
			super();
			this.searcher = searcher;
			qWeight = q.createWeight(searcher);
			fcontext = ValueSource.newContext(searcher);
			boostVal.createWeight(fcontext, searcher);
		}
	}


	private Query q;
	private final ValueSource boostVal;

	public BoostedQuery(Query subQuery, ValueSource boostVal)
	{
		q = subQuery;
		this.boostVal = boostVal;
	}

	public Query getQuery()
	{
		return q;
	}

	public ValueSource getValueSource()
	{
		return boostVal;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		Query newQ = q.rewrite(reader);
		if (newQ == q)
		{
			return this;
		} else
		{
			BoostedQuery bq = (BoostedQuery)clone();
			bq.q = newQ;
			return bq;
		}
	}

	public void extractTerms(Set terms)
	{
		q.extractTerms(terms);
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new BoostedWeight(searcher);
	}

	public String toString(String field)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("boost(").append(q.toString(field)).append(',').append(boostVal).append(')');
		sb.append(ToStringUtils.boost(getBoost()));
		return sb.toString();
	}

	public boolean equals(Object o)
	{
		if (!super.equals(o))
		{
			return false;
		} else
		{
			BoostedQuery other = (BoostedQuery)o;
			return q.equals(other.q) && boostVal.equals(other.boostVal);
		}
	}

	public int hashCode()
	{
		int h = q.hashCode();
		h ^= h << 17 | h >>> 16;
		h += boostVal.hashCode();
		h ^= h << 8 | h >>> 25;
		h += Float.floatToIntBits(getBoost());
		return h;
	}


}
