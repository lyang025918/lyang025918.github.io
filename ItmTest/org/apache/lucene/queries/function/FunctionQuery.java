// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FunctionQuery.java

package org.apache.lucene.queries.function;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.queries.function:
//			ValueSource, FunctionValues

public class FunctionQuery extends Query
{
	protected class AllScorer extends Scorer
	{

		final IndexReader reader;
		final FunctionWeight weight;
		final int maxDoc;
		final float qWeight;
		int doc;
		final FunctionValues vals;
		final Bits acceptDocs;
		final FunctionQuery this$0;

		public int docID()
		{
			return doc;
		}

		public int nextDoc()
			throws IOException
		{
			do
			{
				doc++;
				if (doc >= maxDoc)
					return doc = 0x7fffffff;
			} while (acceptDocs != null && !acceptDocs.get(doc));
			return doc;
		}

		public int advance(int target)
			throws IOException
		{
			doc = target - 1;
			return nextDoc();
		}

		public float score()
			throws IOException
		{
			float score = qWeight * vals.floatVal(doc);
			return score <= (-1.0F / 0.0F) ? -3.402823E+038F : score;
		}

		public float freq()
			throws IOException
		{
			return 1.0F;
		}

		public Explanation explain(int doc)
			throws IOException
		{
			float sc = qWeight * vals.floatVal(doc);
			Explanation result = new ComplexExplanation(true, sc, (new StringBuilder()).append("FunctionQuery(").append(func).append("), product of:").toString());
			result.addDetail(vals.explain(doc));
			result.addDetail(new Explanation(getBoost(), "boost"));
			result.addDetail(new Explanation(weight.queryNorm, "queryNorm"));
			return result;
		}

		public AllScorer(AtomicReaderContext context, Bits acceptDocs, FunctionWeight w, float qWeight)
			throws IOException
		{
			this$0 = FunctionQuery.this;
			super(w);
			doc = -1;
			weight = w;
			this.qWeight = qWeight;
			reader = context.reader();
			maxDoc = reader.maxDoc();
			this.acceptDocs = acceptDocs;
			vals = func.getValues(weight.context, context);
		}
	}

	protected class FunctionWeight extends Weight
	{

		protected final IndexSearcher searcher;
		protected float queryNorm;
		protected float queryWeight;
		protected final Map context;
		final FunctionQuery this$0;

		public Query getQuery()
		{
			return FunctionQuery.this;
		}

		public float getValueForNormalization()
			throws IOException
		{
			queryWeight = getBoost();
			return queryWeight * queryWeight;
		}

		public void normalize(float norm, float topLevelBoost)
		{
			queryNorm = norm * topLevelBoost;
			queryWeight *= queryNorm;
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			return new AllScorer(context, acceptDocs, this, queryWeight);
		}

		public Explanation explain(AtomicReaderContext context, int doc)
			throws IOException
		{
			return ((AllScorer)scorer(context, true, true, context.reader().getLiveDocs())).explain(doc);
		}

		public FunctionWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = FunctionQuery.this;
			super();
			this.searcher = searcher;
			context = ValueSource.newContext(searcher);
			func.createWeight(context, searcher);
		}
	}


	final ValueSource func;

	public FunctionQuery(ValueSource func)
	{
		this.func = func;
	}

	public ValueSource getValueSource()
	{
		return func;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		return this;
	}

	public void extractTerms(Set set)
	{
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new FunctionWeight(searcher);
	}

	public String toString(String field)
	{
		float boost = getBoost();
		return (new StringBuilder()).append((double)boost == 1.0D ? "" : "(").append(func.toString()).append((double)boost != 1.0D ? (new StringBuilder()).append(")^").append(boost).toString() : "").toString();
	}

	public boolean equals(Object o)
	{
		if (!org/apache/lucene/queries/function/FunctionQuery.isInstance(o))
		{
			return false;
		} else
		{
			FunctionQuery other = (FunctionQuery)o;
			return getBoost() == other.getBoost() && func.equals(other.func);
		}
	}

	public int hashCode()
	{
		return func.hashCode() * 31 + Float.floatToIntBits(getBoost());
	}
}
