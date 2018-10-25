// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadNearQuery.java

package org.apache.lucene.search.payloads;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.spans.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search.payloads:
//			AveragePayloadFunction, PayloadFunction

public class PayloadNearQuery extends SpanNearQuery
{
	public class PayloadNearSpanScorer extends SpanScorer
	{

		Spans spans;
		protected float payloadScore;
		private int payloadsSeen;
		BytesRef scratch;
		final PayloadNearQuery this$0;

		public void getPayloads(Spans subSpans[])
			throws IOException
		{
			for (int i = 0; i < subSpans.length; i++)
			{
				if (subSpans[i] instanceof NearSpansOrdered)
				{
					if (((NearSpansOrdered)subSpans[i]).isPayloadAvailable())
						processPayloads(((NearSpansOrdered)subSpans[i]).getPayload(), subSpans[i].start(), subSpans[i].end());
					getPayloads(((NearSpansOrdered)subSpans[i]).getSubSpans());
					continue;
				}
				if (!(subSpans[i] instanceof NearSpansUnordered))
					continue;
				if (((NearSpansUnordered)subSpans[i]).isPayloadAvailable())
					processPayloads(((NearSpansUnordered)subSpans[i]).getPayload(), subSpans[i].start(), subSpans[i].end());
				getPayloads(((NearSpansUnordered)subSpans[i]).getSubSpans());
			}

		}

		protected void processPayloads(Collection payLoads, int start, int end)
		{
			for (Iterator i$ = payLoads.iterator(); i$.hasNext();)
			{
				byte thePayload[] = (byte[])i$.next();
				scratch.bytes = thePayload;
				scratch.offset = 0;
				scratch.length = thePayload.length;
				payloadScore = function.currentScore(doc, fieldName, start, end, payloadsSeen, payloadScore, docScorer.computePayloadFactor(doc, spans.start(), spans.end(), scratch));
				payloadsSeen++;
			}

		}

		protected boolean setFreqCurrentDoc()
			throws IOException
		{
			if (!more)
				return false;
			doc = spans.doc();
			freq = 0.0F;
			payloadScore = 0.0F;
			payloadsSeen = 0;
			do
			{
				int matchLength = spans.end() - spans.start();
				freq += docScorer.computeSlopFactor(matchLength);
				Spans spansArr[] = new Spans[1];
				spansArr[0] = spans;
				getPayloads(spansArr);
				more = spans.next();
			} while (more && doc == spans.doc());
			return true;
		}

		public float score()
			throws IOException
		{
			return super.score() * function.docScore(doc, fieldName, payloadsSeen, payloadScore);
		}


		protected PayloadNearSpanScorer(Spans spans, Weight weight, Similarity similarity, org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer)
			throws IOException
		{
			this$0 = PayloadNearQuery.this;
			super(spans, weight, docScorer);
			scratch = new BytesRef();
			this.spans = spans;
		}
	}

	public class PayloadNearSpanWeight extends SpanWeight
	{

		final PayloadNearQuery this$0;

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			return new PayloadNearSpanScorer(query.getSpans(context, acceptDocs, termContexts), this, similarity, similarity.sloppySimScorer(stats, context));
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			PayloadNearSpanScorer scorer = (PayloadNearSpanScorer)scorer(context, true, false, context.reader().getLiveDocs());
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
					ComplexExplanation result = new ComplexExplanation();
					result.addDetail(expl);
					result.addDetail(payloadExpl);
					result.setValue(expl.getValue() * payloadExpl.getValue());
					result.setDescription("PayloadNearQuery, product of:");
					return result;
				}
			}
			return new ComplexExplanation(false, 0.0F, "no matching term");
		}

		public PayloadNearSpanWeight(SpanQuery query, IndexSearcher searcher)
			throws IOException
		{
			this$0 = PayloadNearQuery.this;
			super(query, searcher);
		}
	}


	protected String fieldName;
	protected PayloadFunction function;

	public PayloadNearQuery(SpanQuery clauses[], int slop, boolean inOrder)
	{
		this(clauses, slop, inOrder, ((PayloadFunction) (new AveragePayloadFunction())));
	}

	public PayloadNearQuery(SpanQuery clauses[], int slop, boolean inOrder, PayloadFunction function)
	{
		super(clauses, slop, inOrder);
		fieldName = clauses[0].getField();
		this.function = function;
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new PayloadNearSpanWeight(this, searcher);
	}

	public PayloadNearQuery clone()
	{
		int sz = clauses.size();
		SpanQuery newClauses[] = new SpanQuery[sz];
		for (int i = 0; i < sz; i++)
			newClauses[i] = (SpanQuery)((SpanQuery)clauses.get(i)).clone();

		PayloadNearQuery boostingNearQuery = new PayloadNearQuery(newClauses, slop, inOrder, function);
		boostingNearQuery.setBoost(getBoost());
		return boostingNearQuery;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("payloadNear([");
		Iterator i = clauses.iterator();
		do
		{
			if (!i.hasNext())
				break;
			SpanQuery clause = (SpanQuery)i.next();
			buffer.append(clause.toString(field));
			if (i.hasNext())
				buffer.append(", ");
		} while (true);
		buffer.append("], ");
		buffer.append(slop);
		buffer.append(", ");
		buffer.append(inOrder);
		buffer.append(")");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
		result = 31 * result + (function != null ? function.hashCode() : 0);
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
		PayloadNearQuery other = (PayloadNearQuery)obj;
		if (fieldName == null)
		{
			if (other.fieldName != null)
				return false;
		} else
		if (!fieldName.equals(other.fieldName))
			return false;
		if (function == null)
		{
			if (other.function != null)
				return false;
		} else
		if (!function.equals(other.function))
			return false;
		return true;
	}

	public volatile SpanNearQuery clone()
	{
		return clone();
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
