// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;

public abstract class MultiFunction extends ValueSource
{
	public class Values extends FunctionValues
	{

		final FunctionValues valsArr[];
		final MultiFunction this$0;

		public String toString(int doc)
		{
			return MultiFunction.toString(name(), valsArr, doc);
		}

		public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
		{
			return super.getValueFiller();
		}

		public Values(FunctionValues valsArr[])
		{
			this$0 = MultiFunction.this;
			super();
			this.valsArr = valsArr;
		}
	}


	protected final List sources;

	public MultiFunction(List sources)
	{
		this.sources = sources;
	}

	protected abstract String name();

	public String description()
	{
		return description(name(), sources);
	}

	public static String description(String name, List sources)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name).append('(');
		boolean firstTime = true;
		ValueSource source;
		for (Iterator i$ = sources.iterator(); i$.hasNext(); sb.append(source))
		{
			source = (ValueSource)i$.next();
			if (firstTime)
				firstTime = false;
			else
				sb.append(',');
		}

		sb.append(')');
		return sb.toString();
	}

	public static FunctionValues[] valsArr(List sources, Map fcontext, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues valsArr[] = new FunctionValues[sources.size()];
		int i = 0;
		for (Iterator i$ = sources.iterator(); i$.hasNext();)
		{
			ValueSource source = (ValueSource)i$.next();
			valsArr[i++] = source.getValues(fcontext, readerContext);
		}

		return valsArr;
	}

	public static String toString(String name, FunctionValues valsArr[], int doc)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name).append('(');
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

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		ValueSource source;
		for (Iterator i$ = sources.iterator(); i$.hasNext(); source.createWeight(context, searcher))
			source = (ValueSource)i$.next();

	}

	public int hashCode()
	{
		return sources.hashCode() + name().hashCode();
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			MultiFunction other = (MultiFunction)o;
			return sources.equals(other.sources);
		}
	}
}
