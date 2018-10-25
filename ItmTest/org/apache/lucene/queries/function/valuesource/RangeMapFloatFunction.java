// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RangeMapFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;

public class RangeMapFloatFunction extends ValueSource
{

	protected final ValueSource source;
	protected final float min;
	protected final float max;
	protected final float target;
	protected final Float defaultVal;

	public RangeMapFloatFunction(ValueSource source, float min, float max, float target, Float def)
	{
		this.source = source;
		this.min = min;
		this.max = max;
		this.target = target;
		defaultVal = def;
	}

	public String description()
	{
		return (new StringBuilder()).append("map(").append(source.description()).append(",").append(min).append(",").append(max).append(",").append(target).append(")").toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues vals = source.getValues(context, readerContext);
		return new FloatDocValues(vals) {

			final FunctionValues val$vals;
			final RangeMapFloatFunction this$0;

			public float floatVal(int doc)
			{
				float val = vals.floatVal(doc);
				return val < min || val > max ? defaultVal != null ? defaultVal.floatValue() : val : target;
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append("map(").append(vals.toString(doc)).append(",min=").append(min).append(",max=").append(max).append(",target=").append(target).append(")").toString();
			}

			
			{
				this$0 = RangeMapFloatFunction.this;
				vals = functionvalues;
				super(x0);
			}
		};
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		source.createWeight(context, searcher);
	}

	public int hashCode()
	{
		int h = source.hashCode();
		h ^= h << 10 | h >>> 23;
		h += Float.floatToIntBits(min);
		h ^= h << 14 | h >>> 19;
		h += Float.floatToIntBits(max);
		h ^= h << 13 | h >>> 20;
		h += Float.floatToIntBits(target);
		if (defaultVal != null)
			h += defaultVal.hashCode();
		return h;
	}

	public boolean equals(Object o)
	{
		if (org/apache/lucene/queries/function/valuesource/RangeMapFloatFunction != o.getClass())
		{
			return false;
		} else
		{
			RangeMapFloatFunction other = (RangeMapFloatFunction)o;
			return min == other.min && max == other.max && target == other.target && source.equals(other.source) && (defaultVal == other.defaultVal || defaultVal != null && defaultVal.equals(other.defaultVal));
		}
	}
}
