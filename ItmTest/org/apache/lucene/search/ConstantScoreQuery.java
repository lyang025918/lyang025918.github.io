// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConstantScoreQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Set;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search:
//			Query, Filter, IndexSearcher, Weight, 
//			Scorer, DocIdSetIterator, Collector, ComplexExplanation, 
//			Explanation, DocIdSet

public class ConstantScoreQuery extends Query
{
	protected class ConstantScorer extends Scorer
	{

		final DocIdSetIterator docIdSetIterator;
		final float theScore;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/ConstantScoreQuery.desiredAssertionStatus();
		final ConstantScoreQuery this$0;

		public int nextDoc()
			throws IOException
		{
			return docIdSetIterator.nextDoc();
		}

		public int docID()
		{
			return docIdSetIterator.docID();
		}

		public float score()
			throws IOException
		{
			if (!$assertionsDisabled && docIdSetIterator.docID() == 0x7fffffff)
				throw new AssertionError();
			else
				return theScore;
		}

		public float freq()
			throws IOException
		{
			return 1.0F;
		}

		public int advance(int target)
			throws IOException
		{
			return docIdSetIterator.advance(target);
		}

		private Collector wrapCollector(final Collector collector)
		{
			return new Collector() {

				final Collector val$collector;
				final ConstantScorer this$1;

				public void setScorer(Scorer scorer)
					throws IOException
				{
					collector.setScorer(new ConstantScorer(scorer, weight, theScore));
				}

				public void collect(int doc)
					throws IOException
				{
					collector.collect(doc);
				}

				public void setNextReader(AtomicReaderContext context)
					throws IOException
				{
					collector.setNextReader(context);
				}

				public boolean acceptsDocsOutOfOrder()
				{
					return collector.acceptsDocsOutOfOrder();
				}

				
				{
					this$1 = ConstantScorer.this;
					collector = collector1;
					super();
				}
			};
		}

		public void score(Collector collector)
			throws IOException
		{
			if (docIdSetIterator instanceof Scorer)
				((Scorer)docIdSetIterator).score(wrapCollector(collector));
			else
				super.score(collector);
		}

		public boolean score(Collector collector, int max, int firstDocID)
			throws IOException
		{
			if (docIdSetIterator instanceof Scorer)
				return ((Scorer)docIdSetIterator).score(wrapCollector(collector), max, firstDocID);
			else
				return super.score(collector, max, firstDocID);
		}


		public ConstantScorer(DocIdSetIterator docIdSetIterator, Weight w, float theScore)
		{
			this$0 = ConstantScoreQuery.this;
			super(w);
			this.theScore = theScore;
			this.docIdSetIterator = docIdSetIterator;
		}
	}

	protected class ConstantWeight extends Weight
	{

		private final Weight innerWeight;
		private float queryNorm;
		private float queryWeight;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/ConstantScoreQuery.desiredAssertionStatus();
		final ConstantScoreQuery this$0;

		public Query getQuery()
		{
			return ConstantScoreQuery.this;
		}

		public float getValueForNormalization()
			throws IOException
		{
			if (innerWeight != null)
				innerWeight.getValueForNormalization();
			queryWeight = getBoost();
			return queryWeight * queryWeight;
		}

		public void normalize(float norm, float topLevelBoost)
		{
			queryNorm = norm * topLevelBoost;
			queryWeight *= queryNorm;
			if (innerWeight != null)
				innerWeight.normalize(norm, topLevelBoost);
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			DocIdSetIterator disi;
			if (filter != null)
			{
				if (!$assertionsDisabled && query != null)
					throw new AssertionError();
				DocIdSet dis = filter.getDocIdSet(context, acceptDocs);
				if (dis == null)
					return null;
				disi = dis.iterator();
			} else
			{
				if (!$assertionsDisabled && (query == null || innerWeight == null))
					throw new AssertionError();
				disi = innerWeight.scorer(context, scoreDocsInOrder, topScorer, acceptDocs);
			}
			if (disi == null)
				return null;
			else
				return new ConstantScorer(disi, this, queryWeight);
		}

		public boolean scoresDocsOutOfOrder()
		{
			return innerWeight == null ? false : innerWeight.scoresDocsOutOfOrder();
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			Scorer cs = scorer(context, true, false, context.reader().getLiveDocs());
			boolean exists = cs != null && cs.advance(doc) == doc;
			ComplexExplanation result = new ComplexExplanation();
			if (exists)
			{
				result.setDescription((new StringBuilder()).append(toString()).append(", product of:").toString());
				result.setValue(queryWeight);
				result.setMatch(Boolean.TRUE);
				result.addDetail(new Explanation(getBoost(), "boost"));
				result.addDetail(new Explanation(queryNorm, "queryNorm"));
			} else
			{
				result.setDescription((new StringBuilder()).append(toString()).append(" doesn't match id ").append(doc).toString());
				result.setValue(0.0F);
				result.setMatch(Boolean.FALSE);
			}
			return result;
		}


		public ConstantWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = ConstantScoreQuery.this;
			super();
			innerWeight = query != null ? query.createWeight(searcher) : null;
		}
	}


	protected final Filter filter;
	protected final Query query;

	public ConstantScoreQuery(Query query)
	{
		if (query == null)
		{
			throw new NullPointerException("Query may not be null");
		} else
		{
			filter = null;
			this.query = query;
			return;
		}
	}

	public ConstantScoreQuery(Filter filter)
	{
		if (filter == null)
		{
			throw new NullPointerException("Filter may not be null");
		} else
		{
			this.filter = filter;
			query = null;
			return;
		}
	}

	public Filter getFilter()
	{
		return filter;
	}

	public Query getQuery()
	{
		return query;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		if (query != null)
		{
			Query rewritten = query.rewrite(reader);
			if (rewritten != query)
			{
				rewritten = new ConstantScoreQuery(rewritten);
				rewritten.setBoost(getBoost());
				return rewritten;
			}
		}
		return this;
	}

	public void extractTerms(Set terms)
	{
		if (query != null)
			query.extractTerms(terms);
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new ConstantWeight(searcher);
	}

	public String toString(String field)
	{
		return (new StringBuilder("ConstantScore(")).append(query != null ? query.toString(field) : filter.toString()).append(')').append(ToStringUtils.boost(getBoost())).toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!super.equals(o))
			return false;
		if (o instanceof ConstantScoreQuery)
		{
			ConstantScoreQuery other = (ConstantScoreQuery)o;
			return (filter != null ? filter.equals(other.filter) : other.filter == null) && (query != null ? query.equals(other.query) : other.query == null);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return 31 * super.hashCode() + (query != null ? query : filter).hashCode();
	}
}
