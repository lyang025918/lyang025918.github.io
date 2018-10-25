// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DoubleFieldSource.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.*;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueDouble;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			FieldCacheSource

public class DoubleFieldSource extends FieldCacheSource
{

	protected final org.apache.lucene.search.FieldCache.DoubleParser parser;

	public DoubleFieldSource(String field)
	{
		this(field, null);
	}

	public DoubleFieldSource(String field, org.apache.lucene.search.FieldCache.DoubleParser parser)
	{
		super(field);
		this.parser = parser;
	}

	public String description()
	{
		return (new StringBuilder()).append("double(").append(field).append(')').toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final double arr[] = cache.getDoubles(readerContext.reader(), field, parser, true);
		Bits valid = cache.getDocsWithField(readerContext.reader(), field);
		return new DoubleDocValues(valid) {

			final double val$arr[];
			final Bits val$valid;
			final DoubleFieldSource this$0;

			public double doubleVal(int doc)
			{
				return arr[doc];
			}

			public boolean exists(int doc)
			{
				return valid.get(doc);
			}

			public ValueSourceScorer getRangeScorer(final IndexReader reader, String lowerVal, String upperVal, boolean includeLower, boolean includeUpper)
			{
				double lower;
				if (lowerVal == null)
					lower = (-1.0D / 0.0D);
				else
					lower = Double.parseDouble(lowerVal);
				double upper;
				if (upperVal == null)
					upper = (1.0D / 0.0D);
				else
					upper = Double.parseDouble(upperVal);
				double l = lower;
				double u = upper;
				if (includeLower && includeUpper)
					return new ValueSourceScorer(l, u) {

						final double val$l;
						final double val$u;
						final 1 this$1;

						public boolean matchesValue(int doc)
						{
							double docVal = doubleVal(doc);
							return docVal >= l && docVal <= u;
						}

					
					{
						this$1 = 1.this;
						l = d;
						u = d1;
						super(x0, x1);
					}
					};
				if (includeLower && !includeUpper)
					return new ValueSourceScorer(l, u) {

						final double val$l;
						final double val$u;
						final 1 this$1;

						public boolean matchesValue(int doc)
						{
							double docVal = doubleVal(doc);
							return docVal >= l && docVal < u;
						}

					
					{
						this$1 = 1.this;
						l = d;
						u = d1;
						super(x0, x1);
					}
					};
				if (!includeLower && includeUpper)
					return new ValueSourceScorer(l, u) {

						final double val$l;
						final double val$u;
						final 1 this$1;

						public boolean matchesValue(int doc)
						{
							double docVal = doubleVal(doc);
							return docVal > l && docVal <= u;
						}

					
					{
						this$1 = 1.this;
						l = d;
						u = d1;
						super(x0, x1);
					}
					};
				else
					return new ValueSourceScorer(l, u) {

						final double val$l;
						final double val$u;
						final 1 this$1;

						public boolean matchesValue(int doc)
						{
							double docVal = doubleVal(doc);
							return docVal > l && docVal < u;
						}

					
					{
						this$1 = 1.this;
						l = d;
						u = d1;
						super(x0, x1);
					}
					};
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

					private final double doubleArr[];
					private final MutableValueDouble mval = new MutableValueDouble();
					final 1 this$1;

					public MutableValue getValue()
					{
						return mval;
					}

					public void fillValue(int doc)
					{
						mval.value = doubleArr[doc];
						mval.exists = valid.get(doc);
					}

					
					{
						this$1 = 1.this;
						super();
						doubleArr = arr;
					}
				};
			}

			
			{
				this$0 = DoubleFieldSource.this;
				arr = ad;
				valid = bits;
				super(x0);
			}
		};
	}

	public boolean equals(Object o)
	{
		if (o.getClass() != org/apache/lucene/queries/function/valuesource/DoubleFieldSource)
		{
			return false;
		} else
		{
			DoubleFieldSource other = (DoubleFieldSource)o;
			return super.equals(other) && (parser != null ? parser.getClass() == other.parser.getClass() : other.parser == null);
		}
	}

	public int hashCode()
	{
		int h = parser != null ? parser.getClass().hashCode() : java/lang/Double.hashCode();
		h += super.hashCode();
		return h;
	}
}
