// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReciprocalFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;

public class ReciprocalFloatFunction extends ValueSource
{

	protected final ValueSource source;
	protected final float m;
	protected final float a;
	protected final float b;

	public ReciprocalFloatFunction(ValueSource source, float m, float a, float b)
	{
		this.source = source;
		this.m = m;
		this.a = a;
		this.b = b;
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues vals = source.getValues(context, readerContext);
		return new FloatDocValues(vals) {

			final FunctionValues val$vals;
			final ReciprocalFloatFunction this$0;

			public float floatVal(int doc)
			{
				return a / (m * vals.floatVal(doc) + b);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(Float.toString(a)).append("/(").append(m).append("*float(").append(vals.toString(doc)).append(')').append('+').append(b).append(')').toString();
			}

			
			{
				this$0 = ReciprocalFloatFunction.this;
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

	public String description()
	{
		return (new StringBuilder()).append(Float.toString(a)).append("/(").append(m).append("*float(").append(source.description()).append(")").append("+").append(b).append(')').toString();
	}

	public int hashCode()
	{
		int h = Float.floatToIntBits(a) + Float.floatToIntBits(m);
		h ^= h << 13 | h >>> 20;
		return h + Float.floatToIntBits(b) + source.hashCode();
	}

	public boolean equals(Object o)
	{
		if (org/apache/lucene/queries/function/valuesource/ReciprocalFloatFunction != o.getClass())
		{
			return false;
		} else
		{
			ReciprocalFloatFunction other = (ReciprocalFloatFunction)o;
			return m == other.m && a == other.a && b == other.b && source.equals(other.source);
		}
	}
}
