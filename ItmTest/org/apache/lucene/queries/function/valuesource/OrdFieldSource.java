// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrdFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueInt;

public class OrdFieldSource extends ValueSource
{

	protected final String field;
	private static final int hcode = org/apache/lucene/queries/function/valuesource/OrdFieldSource.hashCode();

	public OrdFieldSource(String field)
	{
		this.field = field;
	}

	public String description()
	{
		return (new StringBuilder()).append("ord(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		int off = readerContext.docBase;
		IndexReader topReader = ReaderUtil.getTopLevelContext(readerContext).reader();
		AtomicReader r = ((AtomicReader) ((topReader instanceof CompositeReader) ? ((AtomicReader) (new SlowCompositeReaderWrapper((CompositeReader)topReader))) : (AtomicReader)topReader));
		final org.apache.lucene.search.FieldCache.DocTermsIndex sindex = FieldCache.DEFAULT.getTermsIndex(r, field);
		return new IntDocValues(off) {

			final org.apache.lucene.search.FieldCache.DocTermsIndex val$sindex;
			final int val$off;
			final OrdFieldSource this$0;

			protected String toTerm(String readableValue)
			{
				return readableValue;
			}

			public int intVal(int doc)
			{
				return sindex.getOrd(doc + off);
			}

			public int ordVal(int doc)
			{
				return sindex.getOrd(doc + off);
			}

			public int numOrd()
			{
				return sindex.numOrd();
			}

			public boolean exists(int doc)
			{
				return sindex.getOrd(doc + off) != 0;
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

					private final MutableValueInt mval = new MutableValueInt();
					final 1 this$1;

					public MutableValue getValue()
					{
						return mval;
					}

					public void fillValue(int doc)
					{
						mval.value = sindex.getOrd(doc);
						mval.exists = mval.value != 0;
					}

					
					{
						this$1 = 1.this;
						super();
					}
				};
			}

			
			{
				this$0 = OrdFieldSource.this;
				sindex = doctermsindex;
				off = i;
				super(x0);
			}
		};
	}

	public boolean equals(Object o)
	{
		return o != null && o.getClass() == org/apache/lucene/queries/function/valuesource/OrdFieldSource && field.equals(((OrdFieldSource)o).field);
	}

	public int hashCode()
	{
		return hcode + field.hashCode();
	}

}
