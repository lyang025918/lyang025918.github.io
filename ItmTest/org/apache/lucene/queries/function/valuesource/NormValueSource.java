// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormValueSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.TFIDFSimilarity;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			ConstDoubleDocValues, IDFValueSource

public class NormValueSource extends ValueSource
{

	protected final String field;

	public NormValueSource(String field)
	{
		this.field = field;
	}

	public String name()
	{
		return "norm";
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append('(').append(field).append(')').toString();
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
		final TFIDFSimilarity similarity = IDFValueSource.asTFIDF(searcher.getSimilarity(), field);
		if (similarity == null)
			throw new UnsupportedOperationException("requires a TFIDFSimilarity (such as DefaultSimilarity)");
		DocValues dv = readerContext.reader().normValues(field);
		if (dv == null)
		{
			return new ConstDoubleDocValues(0.0D, this);
		} else
		{
			byte norms[] = (byte[])(byte[])dv.getSource().getArray();
			return new FloatDocValues(norms) {

				final TFIDFSimilarity val$similarity;
				final byte val$norms[];
				final NormValueSource this$0;

				public float floatVal(int doc)
				{
					return similarity.decodeNormValue(norms[doc]);
				}

			
			{
				this$0 = NormValueSource.this;
				similarity = tfidfsimilarity;
				norms = abyte0;
				super(x0);
			}
			};
		}
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
			return false;
		else
			return field.equals(((NormValueSource)o).field);
	}

	public int hashCode()
	{
		return getClass().hashCode() + field.hashCode();
	}
}
