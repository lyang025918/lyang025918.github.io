// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			SingleFunction

public abstract class SimpleFloatFunction extends SingleFunction
{

	public SimpleFloatFunction(ValueSource source)
	{
		super(source);
	}

	protected abstract float func(int i, FunctionValues functionvalues);

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		FunctionValues vals = source.getValues(context, readerContext);
		return new FloatDocValues(vals) {

			final FunctionValues val$vals;
			final SimpleFloatFunction this$0;

			public float floatVal(int doc)
			{
				return func(doc, vals);
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append(name()).append('(').append(vals.toString(doc)).append(')').toString();
			}

			
			{
				this$0 = SimpleFloatFunction.this;
				vals = functionvalues;
				super(x0);
			}
		};
	}
}
