// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.*;
import org.apache.lucene.queries.function.docvalues.IntDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueInt;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class IntFieldSource extends FieldCacheSource
{

	final org.apache.lucene.search.FieldCache.IntParser parser;

	public IntFieldSource(String field)
	{
		this(field, null);
	}

	public IntFieldSource(String field, org.apache.lucene.search.FieldCache.IntParser parser)
	{
		super(field);
		this.parser = parser;
	}

	public String description()
	{
		return (new StringBuilder()).append("int(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final int arr[] = cache.getInts(readerContext.reader(), field, parser, true);
		Bits valid = cache.getDocsWithField(readerContext.reader(), field);
		return new IntDocValues(valid) {

			final MutableValueInt val = new MutableValueInt();
			final int val$arr[];
			final Bits val$valid;
			final IntFieldSource this$0;

			public float floatVal(int doc)
			{
				return (float)arr[doc];
			}

			public int intVal(int doc)
			{
				return arr[doc];
			}

			public long longVal(int doc)
			{
				return (long)arr[doc];
			}

			public double doubleVal(int doc)
			{
				return (double)arr[doc];
			}

			public String strVal(int doc)
			{
				return Float.toString(arr[doc]);
			}

			public Object objectVal(int doc)
			{
				return valid.get(doc) ? Integer.valueOf(arr[doc]) : null;
			}

			public boolean exists(int doc)
			{
				return valid.get(doc);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(description()).append('=').append(intVal(doc)).toString();
			}

			public ValueSourceScorer getRangeScorer(final IndexReader reader, String lowerVal, String upperVal, boolean includeLower, boolean includeUpper)
			{
				int lower;
				if (lowerVal == null)
				{
					lower = 0x80000000;
				} else
				{
					lower = Integer.parseInt(lowerVal);
					if (!includeLower && lower < 0x7fffffff)
						lower++;
				}
				int upper;
				if (upperVal == null)
				{
					upper = 0x7fffffff;
				} else
				{
					upper = Integer.parseInt(upperVal);
					if (!includeUpper && upper > 0x80000000)
						upper--;
				}
				int ll = lower;
				int uu = upper;
				return new ValueSourceScorer(ll, uu) {

					final int val$ll;
					final int val$uu;
					final 1 this$1;

					public boolean matchesValue(int doc)
					{
						int val = arr[doc];
						return val >= ll && val <= uu;
					}

					
					{
						this$1 = 1.this;
						ll = i;
						uu = j;
						super(x0, x1);
					}
				};
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

					private final int intArr[];
					private final MutableValueInt mval = new MutableValueInt();
					final 1 this$1;

					public MutableValue getValue()
					{
						return mval;
					}

					public void fillValue(int doc)
					{
						mval.value = intArr[doc];
						mval.exists = valid.get(doc);
					}

					
					{
						this$1 = 1.this;
						super();
						intArr = arr;
					}
				};
			}

			
			{
				this$0 = IntFieldSource.this;
				arr = ai;
				valid = bits;
				super(x0);
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != org/apache/lucene/queries/function/valuesource/IntFieldSource)
		{
			return false;
		} else
		{
			IntFieldSource other = (IntFieldSource)o;
			return super.equals(other) && (parser != null ? parser.getClass() == other.parser.getClass() : other.parser == null);
		}
	}

	public int hashCode()
	{
		int h = parser != null ? parser.getClass().hashCode() : java/lang/Integer.hashCode();
		h += super.hashCode();
		return h;
	}
}
