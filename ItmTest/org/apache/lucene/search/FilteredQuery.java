// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilteredQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search:
//			Query, MatchAllDocsQuery, ConstantScoreQuery, Filter, 
//			IndexSearcher, Weight, Explanation, DocIdSet, 
//			DocIdSetIterator, Scorer, Collector

public class FilteredQuery extends Query
{

	private final Query query;
	private final Filter filter;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/FilteredQuery.desiredAssertionStatus();

	public FilteredQuery(Query query, Filter filter)
	{
		if (query == null || filter == null)
		{
			throw new IllegalArgumentException("Query and filter cannot be null.");
		} else
		{
			this.query = query;
			this.filter = filter;
			return;
		}
	}

	protected boolean useRandomAccess(Bits bits, int firstFilterDoc)
	{
		return firstFilterDoc < 100;
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		final Weight weight = query.createWeight(searcher);
		return new Weight() {

			static final boolean $assertionsDisabled = !org/apache/lucene/search/FilteredQuery.desiredAssertionStatus();
			final Weight val$weight;
			final FilteredQuery this$0;

			public boolean scoresDocsOutOfOrder()
			{
				return true;
			}

			public float getValueForNormalization()
				throws IOException
			{
				return weight.getValueForNormalization() * getBoost() * getBoost();
			}

			public void normalize(float norm, float topLevelBoost)
			{
				weight.normalize(norm, topLevelBoost * getBoost());
			}

			public Explanation explain(AtomicReaderContext ir, int i)
				throws IOException
			{
				Explanation inner = weight.explain(ir, i);
				Filter f = filter;
				DocIdSet docIdSet = f.getDocIdSet(ir, ir.reader().getLiveDocs());
				DocIdSetIterator docIdSetIterator = docIdSet != null ? docIdSet.iterator() : DocIdSet.EMPTY_DOCIDSET.iterator();
				if (docIdSetIterator == null)
					docIdSetIterator = DocIdSet.EMPTY_DOCIDSET.iterator();
				if (docIdSetIterator.advance(i) == i)
				{
					return inner;
				} else
				{
					Explanation result = new Explanation(0.0F, (new StringBuilder()).append("failure to match filter: ").append(f.toString()).toString());
					result.addDetail(inner);
					return result;
				}
			}

			public Query getQuery()
			{
				return FilteredQuery.this;
			}

			public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
				throws IOException
			{
				if (!$assertionsDisabled && filter == null)
					throw new AssertionError();
				DocIdSet filterDocIdSet = filter.getDocIdSet(context, acceptDocs);
				if (filterDocIdSet == null)
					return null;
				DocIdSetIterator filterIter = filterDocIdSet.iterator();
				if (filterIter == null)
					return null;
				final int firstFilterDoc = filterIter.nextDoc();
				if (firstFilterDoc == 0x7fffffff)
					return null;
				Bits filterAcceptDocs = filterDocIdSet.bits();
				boolean useRandomAccess = filterAcceptDocs != null && FilteredQuery.this.useRandomAccess(filterAcceptDocs, firstFilterDoc);
				if (useRandomAccess)
					return weight.scorer(context, scoreDocsInOrder, topScorer, filterAcceptDocs);
				if (!$assertionsDisabled && firstFilterDoc <= -1)
				{
					throw new AssertionError();
				} else
				{
					final Scorer scorer = weight.scorer(context, true, false, null);
					return scorer != null ? new Scorer(filterIter) {

						private int scorerDoc;
						private int filterDoc;
						final int val$firstFilterDoc;
						final Scorer val$scorer;
						final DocIdSetIterator val$filterIter;
						final 1 this$1;

						public void score(Collector collector)
							throws IOException
						{
							int filterDoc = firstFilterDoc;
							int scorerDoc = scorer.advance(filterDoc);
							collector.setScorer(scorer);
							do
							{
								while (scorerDoc == filterDoc) 
									if (scorerDoc != 0x7fffffff)
									{
										collector.collect(scorerDoc);
										filterDoc = filterIter.nextDoc();
										scorerDoc = scorer.advance(filterDoc);
									} else
									{
										return;
									}
								if (scorerDoc > filterDoc)
									filterDoc = filterIter.advance(scorerDoc);
								else
									scorerDoc = scorer.advance(filterDoc);
							} while (true);
						}

						private int advanceToNextCommonDoc()
							throws IOException
						{
							do
							{
								for (; scorerDoc < filterDoc; scorerDoc = scorer.advance(filterDoc));
								if (scorerDoc == filterDoc)
									return scorerDoc;
								filterDoc = filterIter.advance(scorerDoc);
							} while (true);
						}

						public int nextDoc()
							throws IOException
						{
							if (scorerDoc != -1)
								filterDoc = filterIter.nextDoc();
							return advanceToNextCommonDoc();
						}

						public int advance(int target)
							throws IOException
						{
							if (target > filterDoc)
								filterDoc = filterIter.advance(target);
							return advanceToNextCommonDoc();
						}

						public int docID()
						{
							return scorerDoc;
						}

						public float score()
							throws IOException
						{
							return scorer.score();
						}

						public float freq()
							throws IOException
						{
							return scorer.freq();
						}

						public Collection getChildren()
						{
							return Collections.singleton(new Scorer.ChildScorer(scorer, "FILTERED"));
						}

					
					{
						this$1 = 1.this;
						firstFilterDoc = i;
						scorer = scorer1;
						filterIter = docidsetiterator;
						super(x0);
						scorerDoc = -1;
						filterDoc = firstFilterDoc;
					}
					} : null;
				}
			}


			
			{
				this$0 = FilteredQuery.this;
				weight = weight1;
				super();
			}
		};
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		Query queryRewritten = query.rewrite(reader);
		if (queryRewritten instanceof MatchAllDocsQuery)
		{
			Query rewritten = new ConstantScoreQuery(filter);
			rewritten.setBoost(getBoost() * queryRewritten.getBoost());
			return rewritten;
		}
		if (queryRewritten != query)
		{
			Query rewritten = new FilteredQuery(queryRewritten, filter);
			rewritten.setBoost(getBoost());
			return rewritten;
		} else
		{
			return this;
		}
	}

	public final Query getQuery()
	{
		return query;
	}

	public final Filter getFilter()
	{
		return filter;
	}

	public void extractTerms(Set terms)
	{
		getQuery().extractTerms(terms);
	}

	public String toString(String s)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("filtered(");
		buffer.append(query.toString(s));
		buffer.append(")->");
		buffer.append(filter);
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!super.equals(o))
			return false;
		if (!$assertionsDisabled && !(o instanceof FilteredQuery))
		{
			throw new AssertionError();
		} else
		{
			FilteredQuery fq = (FilteredQuery)o;
			return fq.query.equals(query) && fq.filter.equals(filter);
		}
	}

	public int hashCode()
	{
		int hash = super.hashCode();
		hash = hash * 31 + query.hashCode();
		hash = hash * 31 + filter.hashCode();
		return hash;
	}


}
