// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocFreqValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			ConstIntDocValues

public class DocFreqValueSource extends ValueSource
{

	protected final String field;
	protected final String indexedField;
	protected final String val;
	protected final BytesRef indexedBytes;

	public DocFreqValueSource(String field, String val, String indexedField, BytesRef indexedBytes)
	{
		this.field = field;
		this.val = val;
		this.indexedField = indexedField;
		this.indexedBytes = indexedBytes;
	}

	public String name()
	{
		return "docfreq";
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append('(').append(field).append(',').append(val).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		IndexSearcher searcher = (IndexSearcher)context.get("searcher");
		int docfreq = searcher.getIndexReader().docFreq(new Term(indexedField, indexedBytes));
		return new ConstIntDocValues(docfreq, this);
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		context.put("searcher", searcher);
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
			DocFreqValueSource other = (DocFreqValueSource)o;
			return indexedField.equals(other.indexedField) && indexedBytes.equals(other.indexedBytes);
		}
	}
}
