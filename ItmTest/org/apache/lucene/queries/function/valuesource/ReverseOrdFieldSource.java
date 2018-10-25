// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReverseOrdFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;
import org.apache.lucene.search.FieldCache;

public class ReverseOrdFieldSource extends ValueSource
{

	public final String field;
	private static final int hcode = org/apache/lucene/queries/function/valuesource/ReverseOrdFieldSource.hashCode();

	public ReverseOrdFieldSource(String field)
	{
		this.field = field;
	}

	public String description()
	{
		return (new StringBuilder()).append("rord(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		IndexReader topReader = ReaderUtil.getTopLevelContext(readerContext).reader();
		AtomicReader r = ((AtomicReader) ((topReader instanceof CompositeReader) ? ((AtomicReader) (new SlowCompositeReaderWrapper((CompositeReader)topReader))) : (AtomicReader)topReader));
		int off = readerContext.docBase;
		final org.apache.lucene.search.FieldCache.DocTermsIndex sindex = FieldCache.DEFAULT.getTermsIndex(r, field);
		final int end = sindex.numOrd();
		return new IntDocValues(off) {

			final int val$end;
			final org.apache.lucene.search.FieldCache.DocTermsIndex val$sindex;
			final int val$off;
			final ReverseOrdFieldSource this$0;

			public int intVal(int doc)
			{
				return end - sindex.getOrd(doc + off);
			}

			
			{
				this$0 = ReverseOrdFieldSource.this;
				end = i;
				sindex = doctermsindex;
				off = j;
				super(x0);
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o == null || o.getClass() != org/apache/lucene/queries/function/valuesource/ReverseOrdFieldSource)
		{
			return false;
		} else
		{
			ReverseOrdFieldSource other = (ReverseOrdFieldSource)o;
			return field.equals(other.field);
		}
	}

	public int hashCode()
	{
		return hcode + field.hashCode();
	}

}
