// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComplexPhraseQueryParser.java

package org.apache.lucene.queryparser.complexPhrase;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.*;
import org.apache.lucene.util.Version;

public class ComplexPhraseQueryParser extends QueryParser
{
	static class ComplexPhraseQuery extends Query
	{

		String field;
		String phrasedQueryStringContents;
		int slopFactor;
		private Query contents;

		protected void parsePhraseElements(QueryParser qp)
			throws ParseException
		{
			contents = qp.parse(phrasedQueryStringContents);
		}

		public Query rewrite(IndexReader reader)
			throws IOException
		{
			if (contents instanceof TermQuery)
				return contents;
			int numNegatives = 0;
			if (!(contents instanceof BooleanQuery))
				throw new IllegalArgumentException((new StringBuilder()).append("Unknown query type \"").append(contents.getClass().getName()).append("\" found in phrase query string \"").append(phrasedQueryStringContents).append("\"").toString());
			BooleanQuery bq = (BooleanQuery)contents;
			BooleanClause bclauses[] = bq.getClauses();
			SpanQuery allSpanClauses[] = new SpanQuery[bclauses.length];
			for (int i = 0; i < bclauses.length; i++)
			{
				Query qc = bclauses[i].getQuery();
				qc = qc.rewrite(reader);
				if (bclauses[i].getOccur().equals(org.apache.lucene.search.BooleanClause.Occur.MUST_NOT))
					numNegatives++;
				if (qc instanceof BooleanQuery)
				{
					ArrayList sc = new ArrayList();
					addComplexPhraseClause(sc, (BooleanQuery)qc);
					if (sc.size() > 0)
						allSpanClauses[i] = (SpanQuery)sc.get(0);
					else
						allSpanClauses[i] = new SpanTermQuery(new Term(field, "Dummy clause because no terms found - must match nothing"));
					continue;
				}
				if (qc instanceof TermQuery)
				{
					TermQuery tq = (TermQuery)qc;
					allSpanClauses[i] = new SpanTermQuery(tq.getTerm());
				} else
				{
					throw new IllegalArgumentException((new StringBuilder()).append("Unknown query type \"").append(qc.getClass().getName()).append("\" found in phrase query string \"").append(phrasedQueryStringContents).append("\"").toString());
				}
			}

			if (numNegatives == 0)
				return new SpanNearQuery(allSpanClauses, slopFactor, true);
			ArrayList positiveClauses = new ArrayList();
			for (int j = 0; j < allSpanClauses.length; j++)
				if (!bclauses[j].getOccur().equals(org.apache.lucene.search.BooleanClause.Occur.MUST_NOT))
					positiveClauses.add(allSpanClauses[j]);

			SpanQuery includeClauses[] = (SpanQuery[])positiveClauses.toArray(new SpanQuery[positiveClauses.size()]);
			SpanQuery include = null;
			if (includeClauses.length == 1)
				include = includeClauses[0];
			else
				include = new SpanNearQuery(includeClauses, slopFactor + numNegatives, true);
			SpanNearQuery exclude = new SpanNearQuery(allSpanClauses, slopFactor, true);
			SpanNotQuery snot = new SpanNotQuery(include, exclude);
			return snot;
		}

		private void addComplexPhraseClause(List spanClauses, BooleanQuery qc)
		{
			ArrayList ors = new ArrayList();
			ArrayList nots = new ArrayList();
			BooleanClause bclauses[] = qc.getClauses();
			for (int i = 0; i < bclauses.length; i++)
			{
				Query childQuery = bclauses[i].getQuery();
				ArrayList chosenList = ors;
				if (bclauses[i].getOccur() == org.apache.lucene.search.BooleanClause.Occur.MUST_NOT)
					chosenList = nots;
				if (childQuery instanceof TermQuery)
				{
					TermQuery tq = (TermQuery)childQuery;
					SpanTermQuery stq = new SpanTermQuery(tq.getTerm());
					stq.setBoost(tq.getBoost());
					chosenList.add(stq);
					continue;
				}
				if (childQuery instanceof BooleanQuery)
				{
					BooleanQuery cbq = (BooleanQuery)childQuery;
					addComplexPhraseClause(((List) (chosenList)), cbq);
				} else
				{
					throw new IllegalArgumentException((new StringBuilder()).append("Unknown query type:").append(childQuery.getClass().getName()).toString());
				}
			}

			if (ors.size() == 0)
				return;
			SpanOrQuery soq = new SpanOrQuery((SpanQuery[])ors.toArray(new SpanQuery[ors.size()]));
			if (nots.size() == 0)
			{
				spanClauses.add(soq);
			} else
			{
				SpanOrQuery snqs = new SpanOrQuery((SpanQuery[])nots.toArray(new SpanQuery[nots.size()]));
				SpanNotQuery snq = new SpanNotQuery(soq, snqs);
				spanClauses.add(snq);
			}
		}

		public String toString(String field)
		{
			return (new StringBuilder()).append("\"").append(phrasedQueryStringContents).append("\"").toString();
		}

		public int hashCode()
		{
			int prime = 31;
			int result = 1;
			result = 31 * result + (field != null ? field.hashCode() : 0);
			result = 31 * result + (phrasedQueryStringContents != null ? phrasedQueryStringContents.hashCode() : 0);
			result = 31 * result + slopFactor;
			return result;
		}

		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComplexPhraseQuery other = (ComplexPhraseQuery)obj;
			if (field == null)
			{
				if (other.field != null)
					return false;
			} else
			if (!field.equals(other.field))
				return false;
			if (phrasedQueryStringContents == null)
			{
				if (other.phrasedQueryStringContents != null)
					return false;
			} else
			if (!phrasedQueryStringContents.equals(other.phrasedQueryStringContents))
				return false;
			return slopFactor == other.slopFactor;
		}

		public ComplexPhraseQuery(String field, String phrasedQueryStringContents, int slopFactor)
		{
			this.field = field;
			this.phrasedQueryStringContents = phrasedQueryStringContents;
			this.slopFactor = slopFactor;
		}
	}


	private ArrayList complexPhrases;
	private boolean isPass2ResolvingPhrases;
	private ComplexPhraseQuery currentPhraseQuery;

	public ComplexPhraseQueryParser(Version matchVersion, String f, Analyzer a)
	{
		super(matchVersion, f, a);
		complexPhrases = null;
		currentPhraseQuery = null;
	}

	protected Query getFieldQuery(String field, String queryText, int slop)
	{
		ComplexPhraseQuery cpq = new ComplexPhraseQuery(field, queryText, slop);
		complexPhrases.add(cpq);
		return cpq;
	}

	public Query parse(String query)
		throws ParseException
	{
		org.apache.lucene.search.MultiTermQuery.RewriteMethod oldMethod;
		if (!isPass2ResolvingPhrases)
			break MISSING_BLOCK_LABEL_42;
		oldMethod = getMultiTermRewriteMethod();
		Query query1;
		setMultiTermRewriteMethod(MultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
		query1 = super.parse(query);
		setMultiTermRewriteMethod(oldMethod);
		return query1;
		Exception exception;
		exception;
		setMultiTermRewriteMethod(oldMethod);
		throw exception;
		Query q;
		complexPhrases = new ArrayList();
		q = super.parse(query);
		isPass2ResolvingPhrases = true;
		for (Iterator iterator = complexPhrases.iterator(); iterator.hasNext(); currentPhraseQuery.parsePhraseElements(this))
			currentPhraseQuery = (ComplexPhraseQuery)iterator.next();

		isPass2ResolvingPhrases = false;
		break MISSING_BLOCK_LABEL_123;
		Exception exception1;
		exception1;
		isPass2ResolvingPhrases = false;
		throw exception1;
		return q;
	}

	protected Query newTermQuery(Term term)
	{
		if (isPass2ResolvingPhrases)
			try
			{
				checkPhraseClauseIsForSameField(term.field());
			}
			catch (ParseException pe)
			{
				throw new RuntimeException("Error parsing complex phrase", pe);
			}
		return super.newTermQuery(term);
	}

	private void checkPhraseClauseIsForSameField(String field)
		throws ParseException
	{
		if (!field.equals(currentPhraseQuery.field))
			throw new ParseException((new StringBuilder()).append("Cannot have clause for field \"").append(field).append("\" nested in phrase ").append(" for field \"").append(currentPhraseQuery.field).append("\"").toString());
		else
			return;
	}

	protected Query getWildcardQuery(String field, String termStr)
		throws ParseException
	{
		if (isPass2ResolvingPhrases)
			checkPhraseClauseIsForSameField(field);
		return super.getWildcardQuery(field, termStr);
	}

	protected Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
		throws ParseException
	{
		if (isPass2ResolvingPhrases)
			checkPhraseClauseIsForSameField(field);
		return super.getRangeQuery(field, part1, part2, startInclusive, endInclusive);
	}

	protected Query newRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
	{
		if (isPass2ResolvingPhrases)
		{
			TermRangeQuery rangeQuery = TermRangeQuery.newStringRange(field, part1, part2, startInclusive, endInclusive);
			rangeQuery.setRewriteMethod(MultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
			return rangeQuery;
		} else
		{
			return super.newRangeQuery(field, part1, part2, startInclusive, endInclusive);
		}
	}

	protected Query getFuzzyQuery(String field, String termStr, float minSimilarity)
		throws ParseException
	{
		if (isPass2ResolvingPhrases)
			checkPhraseClauseIsForSameField(field);
		return super.getFuzzyQuery(field, termStr, minSimilarity);
	}
}
