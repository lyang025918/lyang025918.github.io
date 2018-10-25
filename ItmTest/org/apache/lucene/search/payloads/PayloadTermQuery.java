// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadTermQuery.java

package org.apache.lucene.search.payloads;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.spans.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search.payloads:
//			PayloadFunction

public class PayloadTermQuery extends SpanTermQuery
{
	protected class PayloadTermWeight extends SpanWeight
	{
		protected class PayloadTermSpanScorer extends SpanScorer
		{

			protected BytesRef payload;
			protected float payloadScore;
			protected int payloadsSeen;
			private final TermSpans termSpans;
			final PayloadTermWeight this$1;

			protected boolean setFreqCurrentDoc()
				throws IOException
			{
				if (!more)
					return false;
				doc = spans.doc();
				freq = 0.0F;
				payloadScore = 0.0F;
				payloadsSeen = 0;
				for (; more && doc == spans.doc(); more = spans.next())
				{
					int matchLength = spans.end() - spans.start();
					freq += docScorer.computeSlopFactor(matchLength);
					processPayload(PayloadTermWeight.this.this$1);
				}

				return more || freq != 0.0F;
			}

			protected void processPayload(Similarity similarity)
				throws IOException
			{
				if (termSpans.isPayloadAvailable())
				{
					DocsAndPositionsEnum postings = termSpans.getPostings();
					payload = postings.getPayload();
					if (payload != null)
						payloadScore = function.currentScore(doc, 0.this$1.field(), spans.start(), spans.end(), payloadsSeen, payloadScore, docScorer.computePayloadFactor(doc, spans.start(), spans.end(), payload));
					else
						payloadScore = function.currentScore(doc, 0.this$1.field(), spans.start(), spans.end(), payloadsSeen, payloadScore, 1.0F);
					payloadsSeen++;
				}
			}

			public float score()
				throws IOException
			{
				return includeSpanScore ? getSpanScore() * getPayloadScore() : getPayloadScore();
			}

			protected float getSpanScore()
				throws IOException
			{
				return super.score();
			}

			protected float getPayloadScore()
			{
				return function.docScore(doc, 0.this$1.field(), payloadsSeen, payloadScore);
			}

			public PayloadTermSpanScorer(TermSpans spans, Weight weight, org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer)
				throws IOException
			{
				this$1 = PayloadTermWeight.this;
				super(spans, weight, docScorer);
				termSpans = spans;
			}
		}


		final PayloadTermQuery this$0;

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			return new PayloadTermSpanScorer((TermSpans)query.getSpans(context, acceptDocs, termContexts), this, similarity.sloppySimScorer(stats, context));
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			PayloadTermSpanScorer scorer = (PayloadTermSpanScorer)scorer(context, true, false, context.reader().getLiveDocs());
			if (scorer != null)
			{
				int newDoc = scorer.advance(doc);
				if (newDoc == doc)
				{
					float freq = scorer.freq();
					org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer = similarity.sloppySimScorer(stats, context);
					Explanation expl = new Explanation();
					expl.setDescription((new StringBuilder()).append("weight(").append(getQuery()).append(" in ").append(doc).append(") [").append(similarity.getClass().getSimpleName()).append("], result of:").toString());
					Explanation scoreExplanation = docScorer.explain(doc, new Explanation(freq, (new StringBuilder()).append("phraseFreq=").append(freq).toString()));
					expl.addDetail(scoreExplanation);
					expl.setValue(scoreExplanation.getValue());
					String field = ((SpanQuery)getQuery()).getField();
					Explanation payloadExpl = function.explain(doc, field, scorer.payloadsSeen, scorer.payloadScore);
					payloadExpl.setValue(scorer.getPayloadScore());
					ComplexExplanation result = new ComplexExplanation();
					if (includeSpanScore)
					{
						result.addDetail(expl);
						result.addDetail(payloadExpl);
						result.setValue(expl.getValue() * payloadExpl.getValue());
						result.setDescription("btq, product of:");
					} else
					{
						result.addDetail(payloadExpl);
						result.setValue(payloadExpl.getValue());
						result.setDescription("btq(includeSpanScore=false), result of:");
					}
					result.setMatch(Boolean.valueOf(true));
					return result;
				}
			}
			return new ComplexExplanation(false, 0.0F, "no matching term");
		}


		public PayloadTermWeight(PayloadTermQuery query, IndexSearcher searcher)
			throws IOException
		{
			this$0 = PayloadTermQuery.this;
			super(query, searcher);
		}
	}


	protected PayloadFunction function;
	private boolean includeSpanScore;

	public PayloadTermQuery(Term term, PayloadFunction function)
	{
		this(term, function, true);
	}

	public PayloadTermQuery(Term term, PayloadFunction function, boolean includeSpanScore)
	{
		super(term);
		this.function = function;
		this.includeSpanScore = includeSpanScore;
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new PayloadTermWeight(this, searcher);
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (function != null ? function.hashCode() : 0);
		result = 31 * result + (includeSpanScore ? 1231 : '\u04D5');
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PayloadTermQuery other = (PayloadTermQuery)obj;
		if (function == null)
		{
			if (other.function != null)
				return false;
		} else
		if (!function.equals(other.function))
			return false;
		return includeSpanScore == other.includeSpanScore;
	}




}
