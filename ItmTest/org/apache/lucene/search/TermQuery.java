// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Set;
import org.apache.lucene.index.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search:
//			Query, IndexSearcher, Weight, TermStatistics, 
//			TermScorer, ComplexExplanation, Explanation, Scorer

public class TermQuery extends Query
{
	final class TermWeight extends Weight
	{

		private final Similarity similarity;
		private final org.apache.lucene.search.similarities.Similarity.SimWeight stats;
		private final TermContext termStates;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/TermQuery.desiredAssertionStatus();
		final TermQuery this$0;

		public String toString()
		{
			return (new StringBuilder()).append("weight(").append(TermQuery.this).append(")").toString();
		}

		public Query getQuery()
		{
			return TermQuery.this;
		}

		public float getValueForNormalization()
		{
			return stats.getValueForNormalization();
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			stats.normalize(queryNorm, topLevelBoost);
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			if (!$assertionsDisabled && termStates.topReaderContext != ReaderUtil.getTopLevelContext(context))
				throw new AssertionError((new StringBuilder()).append("The top-reader used to create Weight (").append(termStates.topReaderContext).append(") is not the same as the current reader's top-reader (").append(ReaderUtil.getTopLevelContext(context)).toString());
			TermsEnum termsEnum = getTermsEnum(context);
			if (termsEnum == null)
				return null;
			DocsEnum docs = termsEnum.docs(acceptDocs, null);
			if (!$assertionsDisabled && docs == null)
				throw new AssertionError();
			else
				return new TermScorer(this, docs, similarity.exactSimScorer(stats, context), termsEnum.docFreq());
		}

		private TermsEnum getTermsEnum(AtomicReaderContext context)
			throws IOException
		{
			TermState state = termStates.get(context.ord);
			if (state == null)
			{
				if (!$assertionsDisabled && !termNotInReader(context.reader(), term))
					throw new AssertionError((new StringBuilder()).append("no termstate found but term exists in reader term=").append(term).toString());
				else
					return null;
			} else
			{
				TermsEnum termsEnum = context.reader().terms(term.field()).iterator(null);
				termsEnum.seekExact(term.bytes(), state);
				return termsEnum;
			}
		}

		private boolean termNotInReader(AtomicReader reader, Term term)
			throws IOException
		{
			return reader.docFreq(term) == 0;
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			Scorer scorer = scorer(context, true, false, context.reader().getLiveDocs());
			if (scorer != null)
			{
				int newDoc = scorer.advance(doc);
				if (newDoc == doc)
				{
					float freq = scorer.freq();
					org.apache.lucene.search.similarities.Similarity.ExactSimScorer docScorer = similarity.exactSimScorer(stats, context);
					ComplexExplanation result = new ComplexExplanation();
					result.setDescription((new StringBuilder()).append("weight(").append(getQuery()).append(" in ").append(doc).append(") [").append(similarity.getClass().getSimpleName()).append("], result of:").toString());
					Explanation scoreExplanation = docScorer.explain(doc, new Explanation(freq, (new StringBuilder()).append("termFreq=").append(freq).toString()));
					result.addDetail(scoreExplanation);
					result.setValue(scoreExplanation.getValue());
					result.setMatch(Boolean.valueOf(true));
					return result;
				}
			}
			return new ComplexExplanation(false, 0.0F, "no matching term");
		}


		public TermWeight(IndexSearcher searcher, TermContext termStates)
			throws IOException
		{
			this$0 = TermQuery.this;
			super();
			if (!$assertionsDisabled && termStates == null)
			{
				throw new AssertionError("TermContext must not be null");
			} else
			{
				this.termStates = termStates;
				similarity = searcher.getSimilarity();
				stats = similarity.computeWeight(getBoost(), searcher.collectionStatistics(term.field()), new TermStatistics[] {
					searcher.termStatistics(term, termStates)
				});
				return;
			}
		}
	}


	private final Term term;
	private final int docFreq;
	private final TermContext perReaderTermState;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/TermQuery.desiredAssertionStatus();

	public TermQuery(Term t)
	{
		this(t, -1);
	}

	public TermQuery(Term t, int docFreq)
	{
		term = t;
		this.docFreq = docFreq;
		perReaderTermState = null;
	}

	public TermQuery(Term t, TermContext states)
	{
		if (!$assertionsDisabled && states == null)
		{
			throw new AssertionError();
		} else
		{
			term = t;
			docFreq = states.docFreq();
			perReaderTermState = states;
			return;
		}
	}

	public Term getTerm()
	{
		return term;
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		IndexReaderContext context = searcher.getTopReaderContext();
		TermContext termState;
		if (perReaderTermState == null || perReaderTermState.topReaderContext != context)
			termState = TermContext.build(context, term, true);
		else
			termState = perReaderTermState;
		if (docFreq != -1)
			termState.setDocFreq(docFreq);
		return new TermWeight(searcher, termState);
	}

	public void extractTerms(Set terms)
	{
		terms.add(getTerm());
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!term.field().equals(field))
		{
			buffer.append(term.field());
			buffer.append(":");
		}
		buffer.append(term.text());
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof TermQuery))
		{
			return false;
		} else
		{
			TermQuery other = (TermQuery)o;
			return getBoost() == other.getBoost() && term.equals(other.term);
		}
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) ^ term.hashCode();
	}


}
