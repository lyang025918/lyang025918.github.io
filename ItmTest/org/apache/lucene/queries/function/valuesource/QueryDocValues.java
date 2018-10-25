// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueFloat;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			QueryValueSource

class QueryDocValues extends FloatDocValues
{

	final AtomicReaderContext readerContext;
	final Bits acceptDocs;
	final Weight weight;
	final float defVal;
	final Map fcontext;
	final Query q;
	Scorer scorer;
	int scorerDoc;
	boolean noMatches;
	int lastDocRequested;

	public QueryDocValues(QueryValueSource vs, AtomicReaderContext readerContext, Map fcontext)
		throws IOException
	{
		super(vs);
		noMatches = false;
		lastDocRequested = 0x7fffffff;
		this.readerContext = readerContext;
		acceptDocs = readerContext.reader().getLiveDocs();
		defVal = vs.defVal;
		q = vs.q;
		this.fcontext = fcontext;
		Weight w = fcontext != null ? (Weight)fcontext.get(vs) : null;
		if (w == null)
		{
			IndexSearcher weightSearcher;
			if (fcontext == null)
			{
				weightSearcher = new IndexSearcher(ReaderUtil.getTopLevelContext(readerContext));
			} else
			{
				weightSearcher = (IndexSearcher)fcontext.get("searcher");
				if (weightSearcher == null)
					weightSearcher = new IndexSearcher(ReaderUtil.getTopLevelContext(readerContext));
			}
			vs.createWeight(fcontext, weightSearcher);
			w = (Weight)fcontext.get(vs);
		}
		weight = w;
	}

	public float floatVal(int doc)
	{
		if (doc >= lastDocRequested)
			break MISSING_BLOCK_LABEL_63;
		if (noMatches)
			return defVal;
		scorer = weight.scorer(readerContext, true, false, acceptDocs);
		if (scorer != null)
			break MISSING_BLOCK_LABEL_58;
		noMatches = true;
		return defVal;
		scorerDoc = -1;
		lastDocRequested = doc;
		if (scorerDoc < doc)
			scorerDoc = scorer.advance(doc);
		if (scorerDoc > doc)
			return defVal;
		return scorer.score();
		IOException e;
		e;
		throw new RuntimeException((new StringBuilder()).append("caught exception in QueryDocVals(").append(q).append(") doc=").append(doc).toString(), e);
	}

	public boolean exists(int doc)
	{
		if (doc >= lastDocRequested)
			break MISSING_BLOCK_LABEL_57;
		if (noMatches)
			return false;
		scorer = weight.scorer(readerContext, true, false, acceptDocs);
		scorerDoc = -1;
		if (scorer != null)
			break MISSING_BLOCK_LABEL_57;
		noMatches = true;
		return false;
		lastDocRequested = doc;
		if (scorerDoc < doc)
			scorerDoc = scorer.advance(doc);
		if (scorerDoc > doc)
			return false;
		return true;
		IOException e;
		e;
		throw new RuntimeException((new StringBuilder()).append("caught exception in QueryDocVals(").append(q).append(") doc=").append(doc).toString(), e);
	}

	public Object objectVal(int doc)
	{
		return exists(doc) ? Float.valueOf(scorer.score()) : null;
		IOException e;
		e;
		throw new RuntimeException((new StringBuilder()).append("caught exception in QueryDocVals(").append(q).append(") doc=").append(doc).toString(), e);
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueFloat mval = new MutableValueFloat();
			final QueryDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				try
				{
					if (noMatches)
					{
						mval.value = defVal;
						mval.exists = false;
						return;
					}
				}
				catch (IOException e)
				{
					throw new RuntimeException((new StringBuilder()).append("caught exception in QueryDocVals(").append(q).append(") doc=").append(doc).toString(), e);
				}
				scorer = weight.scorer(readerContext, true, false, acceptDocs);
				scorerDoc = -1;
				if (scorer == null)
				{
					noMatches = true;
					mval.value = defVal;
					mval.exists = false;
					return;
				}
				lastDocRequested = doc;
				if (scorerDoc < doc)
					scorerDoc = scorer.advance(doc);
				if (scorerDoc > doc)
				{
					mval.value = defVal;
					mval.exists = false;
					return;
				}
				mval.value = scorer.score();
				mval.exists = true;
			}

			
			{
				this$0 = QueryDocValues.this;
				super();
			}
		};
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append("query(").append(q).append(",def=").append(defVal).append(")=").append(floatVal(doc)).toString();
	}
}
