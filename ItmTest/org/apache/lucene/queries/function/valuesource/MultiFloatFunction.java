// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;
import org.apache.lucene.search.IndexSearcher;

public abstract class MultiFloatFunction extends ValueSource
{

	protected final ValueSource sources[];

	public MultiFloatFunction(ValueSource sources[])
	{
		this.sources = sources;
	}

	protected abstract String name();

	protected abstract float func(int i, FunctionValues afunctionvalues[]);

	public String description()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name()).append('(');
		boolean firstTime = true;
		ValueSource arr$[] = sources;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ValueSource source = arr$[i$];
			if (firstTime)
				firstTime = false;
			else
				sb.append(',');
			sb.append(source);
		}

		sb.append(')');
		return sb.toString();
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues valsArr[] = new FunctionValues[sources.length];
		for (int i = 0; i < sources.length; i++)
			valsArr[i] = sources[i].getValues(context, readerContext);

		return new FloatDocValues(valsArr) {

			final FunctionValues val$valsArr[];
			final MultiFloatFunction this$0;

			public float floatVal(int doc)
			{
				return func(doc, valsArr);
			}

			public String toString(int doc)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(name()).append('(');
				boolean firstTime = true;
				FunctionValues arr$[] = valsArr;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					FunctionValues vals = arr$[i$];
					if (firstTime)
						firstTime = false;
					else
						sb.append(',');
					sb.append(vals.toString(doc));
				}

				sb.append(')');
				return sb.toString();
			}

			
			{
				this$0 = MultiFloatFunction.this;
				valsArr = afunctionvalues;
				super(x0);
			}
		};
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		ValueSource arr$[] = sources;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ValueSource source = arr$[i$];
			source.createWeight(context, searcher);
		}

	}

	public int hashCode()
	{
		return Arrays.hashCode(sources) + name().hashCode();
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			MultiFloatFunction other = (MultiFloatFunction)o;
			return name().equals(other.name()) && Arrays.equals(sources, other.sources);
		}
	}
}
