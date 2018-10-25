// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DisjunctionMaxQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search:
//			Query, BooleanQuery, IndexSearcher, Weight, 
//			Scorer, DisjunctionMaxScorer, ComplexExplanation, Explanation

public class DisjunctionMaxQuery extends Query
	implements Iterable
{
	protected class DisjunctionMaxWeight extends Weight
	{

		protected ArrayList weights;
		final DisjunctionMaxQuery this$0;

		public Query getQuery()
		{
			return DisjunctionMaxQuery.this;
		}

		public float getValueForNormalization()
			throws IOException
		{
			float max = 0.0F;
			float sum = 0.0F;
			for (Iterator i$ = weights.iterator(); i$.hasNext();)
			{
				Weight currentWeight = (Weight)i$.next();
				float sub = currentWeight.getValueForNormalization();
				sum += sub;
				max = Math.max(max, sub);
			}

			float boost = getBoost();
			return ((sum - max) * tieBreakerMultiplier * tieBreakerMultiplier + max) * boost * boost;
		}

		public void normalize(float norm, float topLevelBoost)
		{
			topLevelBoost *= getBoost();
			Weight wt;
			for (Iterator i$ = weights.iterator(); i$.hasNext(); wt.normalize(norm, topLevelBoost))
				wt = (Weight)i$.next();

		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			Scorer scorers[] = new Scorer[weights.size()];
			int idx = 0;
			Iterator i$ = weights.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Weight w = (Weight)i$.next();
				Scorer subScorer = w.scorer(context, true, false, acceptDocs);
				if (subScorer != null)
					scorers[idx++] = subScorer;
			} while (true);
			if (idx == 0)
			{
				return null;
			} else
			{
				DisjunctionMaxScorer result = new DisjunctionMaxScorer(this, tieBreakerMultiplier, scorers, idx);
				return result;
			}
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			if (disjuncts.size() == 1)
				return ((Weight)weights.get(0)).explain(context, doc);
			ComplexExplanation result = new ComplexExplanation();
			float max = 0.0F;
			float sum = 0.0F;
			result.setDescription(tieBreakerMultiplier != 0.0F ? (new StringBuilder()).append("max plus ").append(tieBreakerMultiplier).append(" times others of:").toString() : "max of:");
			Iterator i$ = weights.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Weight wt = (Weight)i$.next();
				Explanation e = wt.explain(context, doc);
				if (e.isMatch())
				{
					result.setMatch(Boolean.TRUE);
					result.addDetail(e);
					sum += e.getValue();
					max = Math.max(max, e.getValue());
				}
			} while (true);
			result.setValue(max + (sum - max) * tieBreakerMultiplier);
			return result;
		}

		public DisjunctionMaxWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = DisjunctionMaxQuery.this;
			super();
			weights = new ArrayList();
			Query disjunctQuery;
			for (Iterator i$ = disjuncts.iterator(); i$.hasNext(); weights.add(disjunctQuery.createWeight(searcher)))
				disjunctQuery = (Query)i$.next();

		}
	}


	private ArrayList disjuncts;
	private float tieBreakerMultiplier;

	public DisjunctionMaxQuery(float tieBreakerMultiplier)
	{
		disjuncts = new ArrayList();
		this.tieBreakerMultiplier = 0.0F;
		this.tieBreakerMultiplier = tieBreakerMultiplier;
	}

	public DisjunctionMaxQuery(Collection disjuncts, float tieBreakerMultiplier)
	{
		this.disjuncts = new ArrayList();
		this.tieBreakerMultiplier = 0.0F;
		this.tieBreakerMultiplier = tieBreakerMultiplier;
		add(disjuncts);
	}

	public void add(Query query)
	{
		disjuncts.add(query);
	}

	public void add(Collection disjuncts)
	{
		this.disjuncts.addAll(disjuncts);
	}

	public Iterator iterator()
	{
		return disjuncts.iterator();
	}

	public ArrayList getDisjuncts()
	{
		return disjuncts;
	}

	public float getTieBreakerMultiplier()
	{
		return tieBreakerMultiplier;
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new DisjunctionMaxWeight(searcher);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		int numDisjunctions = disjuncts.size();
		if (numDisjunctions == 1)
		{
			Query singleton = (Query)disjuncts.get(0);
			Query result = singleton.rewrite(reader);
			if (getBoost() != 1.0F)
			{
				if (result == singleton)
					result = result.clone();
				result.setBoost(getBoost() * result.getBoost());
			}
			return result;
		}
		DisjunctionMaxQuery clone = null;
		for (int i = 0; i < numDisjunctions; i++)
		{
			Query clause = (Query)disjuncts.get(i);
			Query rewrite = clause.rewrite(reader);
			if (rewrite == clause)
				continue;
			if (clone == null)
				clone = clone();
			clone.disjuncts.set(i, rewrite);
		}

		if (clone != null)
			return clone;
		else
			return this;
	}

	public DisjunctionMaxQuery clone()
	{
		DisjunctionMaxQuery clone = (DisjunctionMaxQuery)super.clone();
		clone.disjuncts = (ArrayList)disjuncts.clone();
		return clone;
	}

	public void extractTerms(Set terms)
	{
		Query query;
		for (Iterator i$ = disjuncts.iterator(); i$.hasNext(); query.extractTerms(terms))
			query = (Query)i$.next();

	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("(");
		int numDisjunctions = disjuncts.size();
		for (int i = 0; i < numDisjunctions; i++)
		{
			Query subquery = (Query)disjuncts.get(i);
			if (subquery instanceof BooleanQuery)
			{
				buffer.append("(");
				buffer.append(subquery.toString(field));
				buffer.append(")");
			} else
			{
				buffer.append(subquery.toString(field));
			}
			if (i != numDisjunctions - 1)
				buffer.append(" | ");
		}

		buffer.append(")");
		if (tieBreakerMultiplier != 0.0F)
		{
			buffer.append("~");
			buffer.append(tieBreakerMultiplier);
		}
		if ((double)getBoost() != 1.0D)
		{
			buffer.append("^");
			buffer.append(getBoost());
		}
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof DisjunctionMaxQuery))
		{
			return false;
		} else
		{
			DisjunctionMaxQuery other = (DisjunctionMaxQuery)o;
			return getBoost() == other.getBoost() && tieBreakerMultiplier == other.tieBreakerMultiplier && disjuncts.equals(other.disjuncts);
		}
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) + Float.floatToIntBits(tieBreakerMultiplier) + disjuncts.hashCode();
	}

	public volatile Query clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}


}
