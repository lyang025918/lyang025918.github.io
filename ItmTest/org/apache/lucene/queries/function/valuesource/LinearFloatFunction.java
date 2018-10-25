// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LinearFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;

public class LinearFloatFunction extends ValueSource
{

	protected final ValueSource source;
	protected final float slope;
	protected final float intercept;

	public LinearFloatFunction(ValueSource source, float slope, float intercept)
	{
		this.source = source;
		this.slope = slope;
		this.intercept = intercept;
	}

	public String description()
	{
		return (new StringBuilder()).append(slope).append("*float(").append(source.description()).append(")+").append(intercept).toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues vals = source.getValues(context, readerContext);
		return new FloatDocValues(vals) {

			final FunctionValues val$vals;
			final LinearFloatFunction this$0;

			public float floatVal(int doc)
			{
				return vals.floatVal(doc) * slope + intercept;
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(slope).append("*float(").append(vals.toString(doc)).append(")+").append(intercept).toString();
			}

			
			{
				this$0 = LinearFloatFunction.this;
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
		int h = Float.floatToIntBits(slope);
		h = h >>> 2 | h << 30;
		h += Float.floatToIntBits(intercept);
		h ^= h << 14 | h >>> 19;
		return h + source.hashCode();
	}

	public boolean equals(Object o)
	{
		if (org/apache/lucene/queries/function/valuesource/LinearFloatFunction != o.getClass())
		{
			return false;
		} else
		{
			LinearFloatFunction other = (LinearFloatFunction)o;
			return slope == other.slope && intercept == other.intercept && source.equals(other.source);
		}
	}
}
