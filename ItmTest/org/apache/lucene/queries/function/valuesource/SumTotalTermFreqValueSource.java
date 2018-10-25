// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SumTotalTermFreqValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.LongDocValues;
import org.apache.lucene.search.IndexSearcher;

public class SumTotalTermFreqValueSource extends ValueSource
{

	protected final String indexedField;

	public SumTotalTermFreqValueSource(String indexedField)
	{
		this.indexedField = indexedField;
	}

	public String name()
	{
		return "sumtotaltermfreq";
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append('(').append(indexedField).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return (FunctionValues)context.get(this);
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		long sumTotalTermFreq = 0L;
		Iterator i$ = searcher.getTopReaderContext().leaves().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			AtomicReaderContext readerContext = (AtomicReaderContext)i$.next();
			Fields fields = readerContext.reader().fields();
			if (fields == null)
				continue;
			Terms terms = fields.terms(indexedField);
			if (terms == null)
				continue;
			long v = terms.getSumTotalTermFreq();
			if (v == -1L)
			{
				sumTotalTermFreq = -1L;
				break;
			}
			sumTotalTermFreq += v;
		} while (true);
		long ttf = sumTotalTermFreq;
		context.put(this, new LongDocValues(ttf) {

			final long val$ttf;
			final SumTotalTermFreqValueSource this$0;

			public long longVal(int doc)
			{
				return ttf;
			}

			
			{
				this$0 = SumTotalTermFreqValueSource.this;
				ttf = l;
				super(x0);
			}
		});
	}

	public int hashCode()
	{
		return getClass().hashCode() + indexedField.hashCode();
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			SumTotalTermFreqValueSource other = (SumTotalTermFreqValueSource)o;
			return indexedField.equals(other.indexedField);
		}
	}
}
