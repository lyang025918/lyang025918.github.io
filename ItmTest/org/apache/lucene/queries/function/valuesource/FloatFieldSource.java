// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FloatFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueFloat;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class FloatFieldSource extends FieldCacheSource
{

	protected final org.apache.lucene.search.FieldCache.FloatParser parser;

	public FloatFieldSource(String field)
	{
		this(field, null);
	}

	public FloatFieldSource(String field, org.apache.lucene.search.FieldCache.FloatParser parser)
	{
		super(field);
		this.parser = parser;
	}

	public String description()
	{
		return (new StringBuilder()).append("float(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final float arr[] = cache.getFloats(readerContext.reader(), field, parser, true);
		Bits valid = cache.getDocsWithField(readerContext.reader(), field);
		return new FloatDocValues(valid) {

			final float val$arr[];
			final Bits val$valid;
			final FloatFieldSource this$0;

			public float floatVal(int doc)
			{
				return arr[doc];
			}

			public Object objectVal(int doc)
			{
				return valid.get(doc) ? Float.valueOf(arr[doc]) : null;
			}

			public boolean exists(int doc)
			{
				return valid.get(doc);
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

					private final float floatArr[];
					private final MutableValueFloat mval = new MutableValueFloat();
					final 1 this$1;

					public MutableValue getValue()
					{
						return mval;
					}

					public void fillValue(int doc)
					{
						mval.value = floatArr[doc];
						mval.exists = valid.get(doc);
					}

					
					{
						this$1 = 1.this;
						super();
						floatArr = arr;
					}
				};
			}

			
			{
				this$0 = FloatFieldSource.this;
				arr = af;
				valid = bits;
				super(x0);
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != org/apache/lucene/queries/function/valuesource/FloatFieldSource)
		{
			return false;
		} else
		{
			FloatFieldSource other = (FloatFieldSource)o;
			return super.equals(other) && (parser != null ? parser.getClass() == other.parser.getClass() : other.parser == null);
		}
	}

	public int hashCode()
	{
		int h = parser != null ? parser.getClass().hashCode() : java/lang/Float.hashCode();
		h += super.hashCode();
		return h;
	}
}
