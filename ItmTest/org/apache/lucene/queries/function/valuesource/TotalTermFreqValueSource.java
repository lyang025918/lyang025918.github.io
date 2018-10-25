// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TotalTermFreqValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.LongDocValues;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.BytesRef;

public class TotalTermFreqValueSource extends ValueSource
{

	protected final String field;
	protected final String indexedField;
	protected final String val;
	protected final BytesRef indexedBytes;

	public TotalTermFreqValueSource(String field, String val, String indexedField, BytesRef indexedBytes)
	{
		this.field = field;
		this.val = val;
		this.indexedField = indexedField;
		this.indexedBytes = indexedBytes;
	}

	public String name()
	{
		return "totaltermfreq";
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append('(').append(field).append(',').append(val).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		return (FunctionValues)context.get(this);
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		long totalTermFreq = 0L;
		Iterator i$ = searcher.getTopReaderContext().leaves().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			AtomicReaderContext readerContext = (AtomicReaderContext)i$.next();
			long val = readerContext.reader().totalTermFreq(new Term(indexedField, indexedBytes));
			if (val == -1L)
			{
				totalTermFreq = -1L;
				break;
			}
			totalTermFreq += val;
		} while (true);
		long ttf = totalTermFreq;
		context.put(this, new LongDocValues(ttf) {

			final long val$ttf;
			final TotalTermFreqValueSource this$0;

			public long longVal(int doc)
			{
				return ttf;
			}

			
			{
				this$0 = TotalTermFreqValueSource.this;
				ttf = l;
				super(x0);
			}
		});
	}

	public int hashCode()
	{
		return getClass().hashCode() + indexedField.hashCode() * 29 + indexedBytes.hashCode();
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			TotalTermFreqValueSource other = (TotalTermFreqValueSource)o;
			return indexedField.equals(other.indexedField) && indexedBytes.equals(other.indexedBytes);
		}
	}
}
