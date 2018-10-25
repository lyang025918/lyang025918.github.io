// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiBoolFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.BoolDocValues;
import org.apache.lucene.search.IndexSearcher;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			BoolFunction

public abstract class MultiBoolFunction extends BoolFunction
{

	protected final List sources;

	public MultiBoolFunction(List sources)
	{
		this.sources = sources;
	}

	protected abstract String name();

	protected abstract boolean func(int i, FunctionValues afunctionvalues[]);

	public BoolDocValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues vals[] = new FunctionValues[sources.size()];
		int i = 0;
		for (Iterator i$ = sources.iterator(); i$.hasNext();)
		{
			ValueSource source = (ValueSource)i$.next();
			vals[i++] = source.getValues(context, readerContext);
		}

		return new BoolDocValues(vals) {

			final FunctionValues val$vals[];
			final MultiBoolFunction this$0;

			public boolean boolVal(int doc)
			{
				return func(doc, vals);
			}

			public String toString(int doc)
			{
				StringBuilder sb = new StringBuilder(name());
				sb.append('(');
				boolean first = true;
				FunctionValues arr$[] = vals;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					FunctionValues dv = arr$[i$];
					if (first)
						first = false;
					else
						sb.append(',');
					sb.append(dv.toString(doc));
				}

				return sb.toString();
			}

			
			{
				this$0 = MultiBoolFunction.this;
				vals = afunctionvalues;
				super(x0);
			}
		};
	}

	public String description()
	{
		StringBuilder sb = new StringBuilder(name());
		sb.append('(');
		boolean first = true;
		ValueSource source;
		for (Iterator i$ = sources.iterator(); i$.hasNext(); sb.append(source.description()))
		{
			source = (ValueSource)i$.next();
			if (first)
				first = false;
			else
				sb.append(',');
		}

		return sb.toString();
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
			MultiBoolFunction other = (MultiBoolFunction)o;
			return sources.equals(other.sources);
		}
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		ValueSource source;
		for (Iterator i$ = sources.iterator(); i$.hasNext(); source.createWeight(context, searcher))
			source = (ValueSource)i$.next();

	}

	public volatile FunctionValues getValues(Map x0, AtomicReaderContext x1)
		throws IOException
	{
		return getValues(x0, x1);
	}
}
