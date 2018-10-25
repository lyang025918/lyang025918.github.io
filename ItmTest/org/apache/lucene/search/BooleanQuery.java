// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search:
//			Query, BooleanClause, IndexSearcher, Weight, 
//			ComplexExplanation, Explanation, BooleanScorer, BooleanScorer2, 
//			TermScorer, ConjunctionTermScorer, Scorer, TermQuery

public class BooleanQuery extends Query
	implements Iterable
{
	protected class BooleanWeight extends Weight
	{

		protected Similarity similarity;
		protected ArrayList weights;
		protected int maxCoord;
		private final boolean disableCoord;
		private final boolean termConjunction;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/BooleanQuery.desiredAssertionStatus();
		final BooleanQuery this$0;

		public Query getQuery()
		{
			return BooleanQuery.this;
		}

		public float getValueForNormalization()
			throws IOException
		{
			float sum = 0.0F;
			for (int i = 0; i < weights.size(); i++)
			{
				float s = ((Weight)weights.get(i)).getValueForNormalization();
				if (!((BooleanClause)clauses.get(i)).isProhibited())
					sum += s;
			}

			sum *= getBoost() * getBoost();
			return sum;
		}

		public float coord(int overlap, int maxOverlap)
		{
			return maxOverlap != 1 ? similarity.coord(overlap, maxOverlap) : 1.0F;
		}

		public void normalize(float norm, float topLevelBoost)
		{
			topLevelBoost *= getBoost();
			Weight w;
			for (Iterator i$ = weights.iterator(); i$.hasNext(); w.normalize(norm, topLevelBoost))
				w = (Weight)i$.next();

		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			int minShouldMatch = getMinimumNumberShouldMatch();
			ComplexExplanation sumExpl = new ComplexExplanation();
			sumExpl.setDescription("sum of:");
			int coord = 0;
			float sum = 0.0F;
			boolean fail = false;
			int shouldMatchCount = 0;
			Iterator cIter = clauses.iterator();
			Iterator wIter = weights.iterator();
			do
			{
				if (!wIter.hasNext())
					break;
				Weight w = (Weight)wIter.next();
				BooleanClause c = (BooleanClause)cIter.next();
				if (w.scorer(context, true, true, context.reader().getLiveDocs()) == null)
				{
					if (c.isRequired())
					{
						fail = true;
						Explanation r = new Explanation(0.0F, (new StringBuilder()).append("no match on required clause (").append(c.getQuery().toString()).append(")").toString());
						sumExpl.addDetail(r);
					}
				} else
				{
					Explanation e = w.explain(context, doc);
					if (e.isMatch())
					{
						if (!c.isProhibited())
						{
							sumExpl.addDetail(e);
							sum += e.getValue();
							coord++;
						} else
						{
							Explanation r = new Explanation(0.0F, (new StringBuilder()).append("match on prohibited clause (").append(c.getQuery().toString()).append(")").toString());
							r.addDetail(e);
							sumExpl.addDetail(r);
							fail = true;
						}
						if (c.getOccur() == BooleanClause.Occur.SHOULD)
							shouldMatchCount++;
					} else
					if (c.isRequired())
					{
						Explanation r = new Explanation(0.0F, (new StringBuilder()).append("no match on required clause (").append(c.getQuery().toString()).append(")").toString());
						r.addDetail(e);
						sumExpl.addDetail(r);
						fail = true;
					}
				}
			} while (true);
			if (fail)
			{
				sumExpl.setMatch(Boolean.FALSE);
				sumExpl.setValue(0.0F);
				sumExpl.setDescription("Failure to meet condition(s) of required/prohibited clause(s)");
				return sumExpl;
			}
			if (shouldMatchCount < minShouldMatch)
			{
				sumExpl.setMatch(Boolean.FALSE);
				sumExpl.setValue(0.0F);
				sumExpl.setDescription((new StringBuilder()).append("Failure to match minimum number of optional clauses: ").append(minShouldMatch).toString());
				return sumExpl;
			}
			sumExpl.setMatch(0 >= coord ? Boolean.FALSE : Boolean.TRUE);
			sumExpl.setValue(sum);
			float coordFactor = disableCoord ? 1.0F : coord(coord, maxCoord);
			if (coordFactor == 1.0F)
			{
				return sumExpl;
			} else
			{
				ComplexExplanation result = new ComplexExplanation(sumExpl.isMatch(), sum * coordFactor, "product of:");
				result.addDetail(sumExpl);
				result.addDetail(new Explanation(coordFactor, (new StringBuilder()).append("coord(").append(coord).append("/").append(maxCoord).append(")").toString()));
				return result;
			}
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			if (termConjunction)
				return createConjunctionTermScorer(context, acceptDocs);
			List required = new ArrayList();
			List prohibited = new ArrayList();
			List optional = new ArrayList();
			Iterator cIter = clauses.iterator();
			Iterator i$ = weights.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Weight w = (Weight)i$.next();
				BooleanClause c = (BooleanClause)cIter.next();
				Scorer subScorer = w.scorer(context, true, false, acceptDocs);
				if (subScorer == null)
				{
					if (c.isRequired())
						return null;
				} else
				if (c.isRequired())
					required.add(subScorer);
				else
				if (c.isProhibited())
					prohibited.add(subScorer);
				else
					optional.add(subScorer);
			} while (true);
			if (!scoreDocsInOrder && topScorer && required.size() == 0)
				return new BooleanScorer(this, disableCoord, minNrShouldMatch, optional, prohibited, maxCoord);
			if (required.size() == 0 && optional.size() == 0)
				return null;
			if (optional.size() < minNrShouldMatch)
				return null;
			else
				return new BooleanScorer2(this, disableCoord, minNrShouldMatch, required, prohibited, optional, maxCoord);
		}

		private Scorer createConjunctionTermScorer(AtomicReaderContext context, Bits acceptDocs)
			throws IOException
		{
			ConjunctionTermScorer.DocsAndFreqs docsAndFreqs[] = new ConjunctionTermScorer.DocsAndFreqs[weights.size()];
			for (int i = 0; i < docsAndFreqs.length; i++)
			{
				TermQuery.TermWeight weight = (TermQuery.TermWeight)weights.get(i);
				Scorer scorer = weight.scorer(context, true, false, acceptDocs);
				if (scorer == null)
					return null;
				if (!$assertionsDisabled && !(scorer instanceof TermScorer))
					throw new AssertionError();
				docsAndFreqs[i] = new ConjunctionTermScorer.DocsAndFreqs((TermScorer)scorer);
			}

			return new ConjunctionTermScorer(this, disableCoord ? 1.0F : coord(docsAndFreqs.length, docsAndFreqs.length), docsAndFreqs);
		}

		public boolean scoresDocsOutOfOrder()
		{
			for (Iterator i$ = clauses.iterator(); i$.hasNext();)
			{
				BooleanClause c = (BooleanClause)i$.next();
				if (c.isRequired())
					return false;
			}

			return true;
		}


		public BooleanWeight(IndexSearcher searcher, boolean disableCoord)
			throws IOException
		{
			this$0 = BooleanQuery.this;
			super();
			similarity = searcher.getSimilarity();
			this.disableCoord = disableCoord;
			weights = new ArrayList(clauses.size());
			boolean termConjunction = !clauses.isEmpty() && minNrShouldMatch == 0;
			for (int i = 0; i < clauses.size(); i++)
			{
				BooleanClause c = (BooleanClause)clauses.get(i);
				Weight w = c.getQuery().createWeight(searcher);
				if (!c.isRequired() || !(w instanceof TermQuery.TermWeight))
					termConjunction = false;
				weights.add(w);
				if (!c.isProhibited())
					maxCoord++;
			}

			this.termConjunction = termConjunction;
		}
	}

	public static class TooManyClauses extends RuntimeException
	{

		public TooManyClauses()
		{
			super((new StringBuilder()).append("maxClauseCount is set to ").append(BooleanQuery.maxClauseCount).toString());
		}
	}


	private static int maxClauseCount = 1024;
	private ArrayList clauses;
	private final boolean disableCoord;
	protected int minNrShouldMatch;

	public static int getMaxClauseCount()
	{
		return maxClauseCount;
	}

	public static void setMaxClauseCount(int maxClauseCount)
	{
		if (maxClauseCount < 1)
		{
			throw new IllegalArgumentException("maxClauseCount must be >= 1");
		} else
		{
			maxClauseCount = maxClauseCount;
			return;
		}
	}

	public BooleanQuery()
	{
		clauses = new ArrayList();
		minNrShouldMatch = 0;
		disableCoord = false;
	}

	public BooleanQuery(boolean disableCoord)
	{
		clauses = new ArrayList();
		minNrShouldMatch = 0;
		this.disableCoord = disableCoord;
	}

	public boolean isCoordDisabled()
	{
		return disableCoord;
	}

	public void setMinimumNumberShouldMatch(int min)
	{
		minNrShouldMatch = min;
	}

	public int getMinimumNumberShouldMatch()
	{
		return minNrShouldMatch;
	}

	public void add(Query query, BooleanClause.Occur occur)
	{
		add(new BooleanClause(query, occur));
	}

	public void add(BooleanClause clause)
	{
		if (clauses.size() >= maxClauseCount)
		{
			throw new TooManyClauses();
		} else
		{
			clauses.add(clause);
			return;
		}
	}

	public BooleanClause[] getClauses()
	{
		return (BooleanClause[])clauses.toArray(new BooleanClause[clauses.size()]);
	}

	public List clauses()
	{
		return clauses;
	}

	public final Iterator iterator()
	{
		return clauses().iterator();
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new BooleanWeight(searcher, disableCoord);
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		if (minNrShouldMatch == 0 && clauses.size() == 1)
		{
			BooleanClause c = (BooleanClause)clauses.get(0);
			if (!c.isProhibited())
			{
				Query query = c.getQuery().rewrite(reader);
				if (getBoost() != 1.0F)
				{
					if (query == c.getQuery())
						query = query.clone();
					query.setBoost(getBoost() * query.getBoost());
				}
				return query;
			}
		}
		BooleanQuery clone = null;
		for (int i = 0; i < clauses.size(); i++)
		{
			BooleanClause c = (BooleanClause)clauses.get(i);
			Query query = c.getQuery().rewrite(reader);
			if (query == c.getQuery())
				continue;
			if (clone == null)
				clone = clone();
			clone.clauses.set(i, new BooleanClause(query, c.getOccur()));
		}

		if (clone != null)
			return clone;
		else
			return this;
	}

	public void extractTerms(Set terms)
	{
		BooleanClause clause;
		for (Iterator i$ = clauses.iterator(); i$.hasNext(); clause.getQuery().extractTerms(terms))
			clause = (BooleanClause)i$.next();

	}

	public BooleanQuery clone()
	{
		BooleanQuery clone = (BooleanQuery)super.clone();
		clone.clauses = (ArrayList)clauses.clone();
		return clone;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		boolean needParens = (double)getBoost() != 1.0D || getMinimumNumberShouldMatch() > 0;
		if (needParens)
			buffer.append("(");
		for (int i = 0; i < clauses.size(); i++)
		{
			BooleanClause c = (BooleanClause)clauses.get(i);
			if (c.isProhibited())
				buffer.append("-");
			else
			if (c.isRequired())
				buffer.append("+");
			Query subQuery = c.getQuery();
			if (subQuery != null)
			{
				if (subQuery instanceof BooleanQuery)
				{
					buffer.append("(");
					buffer.append(subQuery.toString(field));
					buffer.append(")");
				} else
				{
					buffer.append(subQuery.toString(field));
				}
			} else
			{
				buffer.append("null");
			}
			if (i != clauses.size() - 1)
				buffer.append(" ");
		}

		if (needParens)
			buffer.append(")");
		if (getMinimumNumberShouldMatch() > 0)
		{
			buffer.append('~');
			buffer.append(getMinimumNumberShouldMatch());
		}
		if (getBoost() != 1.0F)
			buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof BooleanQuery))
		{
			return false;
		} else
		{
			BooleanQuery other = (BooleanQuery)o;
			return getBoost() == other.getBoost() && clauses.equals(other.clauses) && getMinimumNumberShouldMatch() == other.getMinimumNumberShouldMatch() && disableCoord == other.disableCoord;
		}
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) ^ clauses.hashCode() + getMinimumNumberShouldMatch() + (disableCoord ? 17 : 0);
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
