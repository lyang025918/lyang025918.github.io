// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DualFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;

public abstract class DualFloatFunction extends ValueSource
{

	protected final ValueSource a;
	protected final ValueSource b;

	public DualFloatFunction(ValueSource a, ValueSource b)
	{
		this.a = a;
		this.b = b;
	}

	protected abstract String name();

	protected abstract float func(int i, FunctionValues functionvalues, FunctionValues functionvalues1);

	public String description()
	{
		return (new StringBuilder()).append(name()).append("(").append(a.description()).append(",").append(b.description()).append(")").toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final FunctionValues aVals = a.getValues(context, readerContext);
		FunctionValues bVals = b.getValues(context, readerContext);
		return new FloatDocValues(bVals) {

			final FunctionValues val$aVals;
			final FunctionValues val$bVals;
			final DualFloatFunction this$0;

			public float floatVal(int doc)
			{
				return func(doc, aVals, bVals);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(name()).append('(').append(aVals.toString(doc)).append(',').append(bVals.toString(doc)).append(')').toString();
			}

			
			{
				this$0 = DualFloatFunction.this;
				aVals = functionvalues;
				bVals = functionvalues1;
				super(x0);
			}
		};
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		a.createWeight(context, searcher);
		b.createWeight(context, searcher);
	}

	public int hashCode()
	{
		int h = a.hashCode();
		h ^= h << 13 | h >>> 20;
		h += b.hashCode();
		h ^= h << 23 | h >>> 10;
		h += name().hashCode();
		return h;
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			DualFloatFunction other = (DualFloatFunction)o;
			return a.equals(other.a) && b.equals(other.b);
		}
	}
}
