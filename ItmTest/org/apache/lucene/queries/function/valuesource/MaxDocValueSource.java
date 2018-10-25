// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MaxDocValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			ConstIntDocValues

public class MaxDocValueSource extends ValueSource
{

	public MaxDocValueSource()
	{
	}

	public String name()
	{
		return "maxdoc";
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append("()").toString();
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		context.put("searcher", searcher);
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		IndexSearcher searcher = (IndexSearcher)context.get("searcher");
		return new ConstIntDocValues(searcher.getIndexReader().maxDoc(), this);
	}

	public boolean equals(Object o)
	{
		return getClass() == o.getClass();
	}

	public int hashCode()
	{
		return getClass().hashCode();
	}
}
