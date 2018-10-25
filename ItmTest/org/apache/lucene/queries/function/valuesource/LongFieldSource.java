// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LongFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.*;
import org.apache.lucene.queries.function.docvalues.LongDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueLong;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class LongFieldSource extends FieldCacheSource
{

	protected final org.apache.lucene.search.FieldCache.LongParser parser;

	public LongFieldSource(String field)
	{
		this(field, null);
	}

	public LongFieldSource(String field, org.apache.lucene.search.FieldCache.LongParser parser)
	{
		super(field);
		this.parser = parser;
	}

	public String description()
	{
		return (new StringBuilder()).append("long(").append(field).append(')').toString();
	}

	public long externalToLong(String extVal)
	{
		return Long.parseLong(extVal);
	}

	public Object longToObject(long val)
	{
		return Long.valueOf(val);
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final long arr[] = cache.getLongs(readerContext.reader(), field, parser, true);
		Bits valid = cache.getDocsWithField(readerContext.reader(), field);
		return new LongDocValues(valid) {

			final long val$arr[];
			final Bits val$valid;
			final LongFieldSource this$0;

			public long longVal(int doc)
			{
				return arr[doc];
			}

			public boolean exists(int doc)
			{
				return valid.get(doc);
			}

			public Object objectVal(int doc)
			{
				return valid.get(doc) ? longToObject(arr[doc]) : null;
			}

			public ValueSourceScorer getRangeScorer(final IndexReader reader, String lowerVal, String upperVal, boolean includeLower, boolean includeUpper)
			{
				long lower;
				if (lowerVal == null)
				{
					lower = 0x8000000000000000L;
				} else
				{
					lower = externalToLong(lowerVal);
					if (!includeLower && lower < 0x7fffffffffffffffL)
						lower++;
				}
				long upper;
				if (upperVal == null)
				{
					upper = 0x7fffffffffffffffL;
				} else
				{
					upper = externalToLong(upperVal);
					if (!includeUpper && upper > 0x8000000000000000L)
						upper--;
				}
				long ll = lower;
				long uu = upper;
				return new ValueSourceScorer(ll, uu) {

					final long val$ll;
					final long val$uu;
					final 1 this$1;

					public boolean matchesValue(int doc)
					{
						long val = arr[doc];
						return val >= ll && val <= uu;
					}

					
					{
						this$1 = 1.this;
						ll = l;
						uu = l1;
						super(x0, x1);
					}
				};
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

					private final long longArr[];
					private final MutableValueLong mval;
					final 1 this$1;

					public MutableValue getValue()
					{
						return mval;
					}

					public void fillValue(int doc)
					{
						mval.value = longArr[doc];
						mval.exists = valid.get(doc);
					}

					
					{
						this$1 = 1.this;
						super();
						longArr = arr;
						mval = newMutableValueLong();
					}
				};
			}

			
			{
				this$0 = LongFieldSource.this;
				arr = al;
				valid = bits;
				super(x0);
			}
		};
	}

	protected MutableValueLong newMutableValueLong()
	{
		return new MutableValueLong();
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != getClass())
		{
			return false;
		} else
		{
			LongFieldSource other = (LongFieldSource)o;
			return super.equals(other) && (parser != null ? parser.getClass() == other.parser.getClass() : other.parser == null);
		}
	}

	public int hashCode()
	{
		int h = parser != null ? parser.getClass().hashCode() : getClass().hashCode();
		h += super.hashCode();
		return h;
	}
}
