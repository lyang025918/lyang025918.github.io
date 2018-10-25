// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IDFValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			DocFreqValueSource, ConstDoubleDocValues

public class IDFValueSource extends DocFreqValueSource
{

	public IDFValueSource(String field, String val, String indexedField, BytesRef indexedBytes)
	{
		super(field, val, indexedField, indexedBytes);
	}

	public String name()
	{
		return "idf";
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		IndexSearcher searcher = (IndexSearcher)context.get("searcher");
		TFIDFSimilarity sim = asTFIDF(searcher.getSimilarity(), field);
		if (sim == null)
		{
			throw new UnsupportedOperationException("requires a TFIDFSimilarity (such as DefaultSimilarity)");
		} else
		{
			int docfreq = searcher.getIndexReader().docFreq(new Term(indexedField, indexedBytes));
			float idf = sim.idf(docfreq, searcher.getIndexReader().maxDoc());
			return new ConstDoubleDocValues(idf, this);
		}
	}

	static TFIDFSimilarity asTFIDF(Similarity sim, String field)
	{
		for (; sim instanceof PerFieldSimilarityWrapper; sim = ((PerFieldSimilarityWrapper)sim).get(field));
		if (sim instanceof TFIDFSimilarity)
			return (TFIDFSimilarity)sim;
		else
			return null;
	}
}
