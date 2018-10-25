// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustomScoreQuery.java

package org.apache.lucene.queries;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.queries:
//			CustomScoreProvider

public class CustomScoreQuery extends Query
{
	private class CustomScorer extends Scorer
	{

		private final float qWeight;
		private final Scorer subQueryScorer;
		private final Scorer valSrcScorers[];
		private final CustomScoreProvider provider;
		private final float vScores[];
		final CustomScoreQuery this$0;

		public int nextDoc()
			throws IOException
		{
			int doc = subQueryScorer.nextDoc();
			if (doc != 0x7fffffff)
			{
				Scorer arr$[] = valSrcScorers;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Scorer valSrcScorer = arr$[i$];
					valSrcScorer.advance(doc);
				}

			}
			return doc;
		}

		public int docID()
		{
			return subQueryScorer.docID();
		}

		public float score()
			throws IOException
		{
			for (int i = 0; i < valSrcScorers.length; i++)
				vScores[i] = valSrcScorers[i].score();

			return qWeight * provider.customScore(subQueryScorer.docID(), subQueryScorer.score(), vScores);
		}

		public float freq()
			throws IOException
		{
			return subQueryScorer.freq();
		}

		public Collection getChildren()
		{
			return Collections.singleton(new org.apache.lucene.search.Scorer.ChildScorer(subQueryScorer, "CUSTOM"));
		}

		public int advance(int target)
			throws IOException
		{
			int doc = subQueryScorer.advance(target);
			if (doc != 0x7fffffff)
			{
				Scorer arr$[] = valSrcScorers;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Scorer valSrcScorer = arr$[i$];
					valSrcScorer.advance(doc);
				}

			}
			return doc;
		}

		private CustomScorer(CustomScoreProvider provider, CustomWeight w, float qWeight, Scorer subQueryScorer, Scorer valSrcScorers[])
		{
			this$0 = CustomScoreQuery.this;
			super(w);
			this.qWeight = qWeight;
			this.subQueryScorer = subQueryScorer;
			this.valSrcScorers = valSrcScorers;
			vScores = new float[valSrcScorers.length];
			this.provider = provider;
		}

	}

	private class CustomWeight extends Weight
	{

		Weight subQueryWeight;
		Weight valSrcWeights[];
		boolean qStrict;
		final CustomScoreQuery this$0;

		public Query getQuery()
		{
			return CustomScoreQuery.this;
		}

		public float getValueForNormalization()
			throws IOException
		{
			float sum = subQueryWeight.getValueForNormalization();
			Weight arr$[] = valSrcWeights;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Weight valSrcWeight = arr$[i$];
				if (qStrict)
					valSrcWeight.getValueForNormalization();
				else
					sum += valSrcWeight.getValueForNormalization();
			}

			sum *= getBoost() * getBoost();
			return sum;
		}

		public void normalize(float norm, float topLevelBoost)
		{
			topLevelBoost *= getBoost();
			subQueryWeight.normalize(norm, topLevelBoost);
			Weight arr$[] = valSrcWeights;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Weight valSrcWeight = arr$[i$];
				if (qStrict)
					valSrcWeight.normalize(1.0F, 1.0F);
				else
					valSrcWeight.normalize(norm, topLevelBoost);
			}

		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			Scorer subQueryScorer = subQueryWeight.scorer(context, true, false, acceptDocs);
			if (subQueryScorer == null)
				return null;
			Scorer valSrcScorers[] = new Scorer[valSrcWeights.length];
			for (int i = 0; i < valSrcScorers.length; i++)
				valSrcScorers[i] = valSrcWeights[i].scorer(context, true, topScorer, acceptDocs);

			return new CustomScorer(getCustomScoreProvider(context), this, getBoost(), subQueryScorer, valSrcScorers);
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			Explanation explain = doExplain(context, doc);
			return explain != null ? explain : new Explanation(0.0F, "no matching docs");
		}

		private Explanation doExplain(AtomicReaderContext info, int doc)
			throws IOException
		{
			Explanation subQueryExpl = subQueryWeight.explain(info, doc);
			if (!subQueryExpl.isMatch())
				return subQueryExpl;
			Explanation valSrcExpls[] = new Explanation[valSrcWeights.length];
			for (int i = 0; i < valSrcWeights.length; i++)
				valSrcExpls[i] = valSrcWeights[i].explain(info, doc);

			Explanation customExp = getCustomScoreProvider(info).customExplain(doc, subQueryExpl, valSrcExpls);
			float sc = getBoost() * customExp.getValue();
			Explanation res = new ComplexExplanation(true, sc, (new StringBuilder()).append(toString()).append(", product of:").toString());
			res.addDetail(customExp);
			res.addDetail(new Explanation(getBoost(), "queryBoost"));
			return res;
		}

		public boolean scoresDocsOutOfOrder()
		{
			return false;
		}

		public CustomWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = CustomScoreQuery.this;
			super();
			subQueryWeight = subQuery.createWeight(searcher);
			valSrcWeights = new Weight[scoringQueries.length];
			for (int i = 0; i < scoringQueries.length; i++)
				valSrcWeights[i] = scoringQueries[i].createWeight(searcher);

			qStrict = strict;
		}
	}


	private Query subQuery;
	private Query scoringQueries[];
	private boolean strict;

	public CustomScoreQuery(Query subQuery)
	{
		this(subQuery, new Query[0]);
	}

	public CustomScoreQuery(Query subQuery, Query scoringQuery)
	{
		this(subQuery, scoringQuery == null ? new Query[0] : (new Query[] {
			scoringQuery
		}));
	}

	public transient CustomScoreQuery(Query subQuery, Query scoringQueries[])
	{
		strict = false;
		this.subQuery = subQuery;
		this.scoringQueries = scoringQueries == null ? new Query[0] : scoringQueries;
		if (subQuery == null)
			throw new IllegalArgumentException("<subquery> must not be null!");
		else
			return;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		CustomScoreQuery clone = null;
		Query sq = subQuery.rewrite(reader);
		if (sq != subQuery)
		{
			clone = clone();
			clone.subQuery = sq;
		}
		for (int i = 0; i < scoringQueries.length; i++)
		{
			Query v = scoringQueries[i].rewrite(reader);
			if (v == scoringQueries[i])
				continue;
			if (clone == null)
				clone = clone();
			clone.scoringQueries[i] = v;
		}

		return clone != null ? clone : this;
	}

	public void extractTerms(Set terms)
	{
		subQuery.extractTerms(terms);
		Query arr$[] = scoringQueries;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Query scoringQuery = arr$[i$];
			scoringQuery.extractTerms(terms);
		}

	}

	public CustomScoreQuery clone()
	{
		CustomScoreQuery clone = (CustomScoreQuery)super.clone();
		clone.subQuery = subQuery.clone();
		clone.scoringQueries = new Query[scoringQueries.length];
		for (int i = 0; i < scoringQueries.length; i++)
			clone.scoringQueries[i] = scoringQueries[i].clone();

		return clone;
	}

	public String toString(String field)
	{
		StringBuilder sb = (new StringBuilder(name())).append("(");
		sb.append(subQuery.toString(field));
		Query arr$[] = scoringQueries;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Query scoringQuery = arr$[i$];
			sb.append(", ").append(scoringQuery.toString(field));
		}

		sb.append(")");
		sb.append(strict ? " STRICT" : "");
		return (new StringBuilder()).append(sb.toString()).append(ToStringUtils.boost(getBoost())).toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!super.equals(o))
			return false;
		if (getClass() != o.getClass())
			return false;
		CustomScoreQuery other = (CustomScoreQuery)o;
		if (getBoost() != other.getBoost() || !subQuery.equals(other.subQuery) || strict != other.strict || scoringQueries.length != other.scoringQueries.length)
			return false;
		else
			return Arrays.equals(scoringQueries, other.scoringQueries);
	}

	public int hashCode()
	{
		return getClass().hashCode() + subQuery.hashCode() + Arrays.hashCode(scoringQueries) ^ Float.floatToIntBits(getBoost()) ^ (strict ? 0x4d2 : '\u10E1');
	}

	protected CustomScoreProvider getCustomScoreProvider(AtomicReaderContext context)
		throws IOException
	{
		return new CustomScoreProvider(context);
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new CustomWeight(searcher);
	}

	public boolean isStrict()
	{
		return strict;
	}

	public void setStrict(boolean strict)
	{
		this.strict = strict;
	}

	public String name()
	{
		return "custom";
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
