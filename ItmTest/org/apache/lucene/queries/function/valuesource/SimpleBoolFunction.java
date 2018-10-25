// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleBoolFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.BoolDocValues;
import org.apache.lucene.search.IndexSearcher;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			BoolFunction

public abstract class SimpleBoolFunction extends BoolFunction
{

	protected final ValueSource source;

	public SimpleBoolFunction(ValueSource source)
	{
		this.source = source;
	}

	protected abstract String name();

	protected abstract boolean func(int i, FunctionValues functionvalues);

	public BoolDocValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues vals = source.getValues(context, readerContext);
		return new BoolDocValues(vals) {

			final FunctionValues val$vals;
			final SimpleBoolFunction this$0;

			public boolean boolVal(int doc)
			{
				return func(doc, vals);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(name()).append('(').append(vals.toString(doc)).append(')').toString();
			}

			
			{
				this$0 = SimpleBoolFunction.this;
				vals = functionvalues;
				super(x0);
			}
		};
	}

	public String description()
	{
		return (new StringBuilder()).append(name()).append('(').append(source.description()).append(')').toString();
	}

	public int hashCode()
	{
		return source.hashCode() + name().hashCode();
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			SimpleBoolFunction other = (SimpleBoolFunction)o;
			return source.equals(other.source);
		}
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		source.createWeight(context, searcher);
	}

	public volatile FunctionValues getValues(Map x0, AtomicReaderContext x1)
		throws IOException
	{
		return getValues(x0, x1);
	}
}
