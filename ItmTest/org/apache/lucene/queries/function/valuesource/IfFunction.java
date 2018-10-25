// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IfFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			BoolFunction

public class IfFunction extends BoolFunction
{

	private final ValueSource ifSource;
	private final ValueSource trueSource;
	private final ValueSource falseSource;

	public IfFunction(ValueSource ifSource, ValueSource trueSource, ValueSource falseSource)
	{
		this.ifSource = ifSource;
		this.trueSource = trueSource;
		this.falseSource = falseSource;
	}

	public FunctionValues getValues(Map context, AtomicReaderContext readerContext)
		throws IOException
	{
		final FunctionValues ifVals = ifSource.getValues(context, readerContext);
		final FunctionValues trueVals = trueSource.getValues(context, readerContext);
		final FunctionValues falseVals = falseSource.getValues(context, readerContext);
		return new FunctionValues() {

			final FunctionValues val$ifVals;
			final FunctionValues val$trueVals;
			final FunctionValues val$falseVals;
			final IfFunction this$0;

			public byte byteVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.byteVal(doc) : falseVals.byteVal(doc);
			}

			public short shortVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.shortVal(doc) : falseVals.shortVal(doc);
			}

			public float floatVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.floatVal(doc) : falseVals.floatVal(doc);
			}

			public int intVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.intVal(doc) : falseVals.intVal(doc);
			}

			public long longVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.longVal(doc) : falseVals.longVal(doc);
			}

			public double doubleVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.doubleVal(doc) : falseVals.doubleVal(doc);
			}

			public String strVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.strVal(doc) : falseVals.strVal(doc);
			}

			public boolean boolVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.boolVal(doc) : falseVals.boolVal(doc);
			}

			public boolean bytesVal(int doc, BytesRef target)
			{
				return ifVals.boolVal(doc) ? trueVals.bytesVal(doc, target) : falseVals.bytesVal(doc, target);
			}

			public Object objectVal(int doc)
			{
				return ifVals.boolVal(doc) ? trueVals.objectVal(doc) : falseVals.objectVal(doc);
			}

			public boolean exists(int doc)
			{
				return true;
			}

			public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
			{
				return super.getValueFiller();
			}

			public String toString(int doc)
			{
				return (new StringBuilder()).append("if(").append(ifVals.toString(doc)).append(',').append(trueVals.toString(doc)).append(',').append(falseVals.toString(doc)).append(')').toString();
			}

			
			{
				this$0 = IfFunction.this;
				ifVals = functionvalues;
				trueVals = functionvalues1;
				falseVals = functionvalues2;
				super();
			}
		};
	}

	public String description()
	{
		return (new StringBuilder()).append("if(").append(ifSource.description()).append(',').append(trueSource.description()).append(',').append(falseSource).append(')').toString();
	}

	public int hashCode()
	{
		int h = ifSource.hashCode();
		h = h * 31 + trueSource.hashCode();
		h = h * 31 + falseSource.hashCode();
		return h;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof IfFunction))
		{
			return false;
		} else
		{
			IfFunction other = (IfFunction)o;
			return ifSource.equals(other.ifSource) && trueSource.equals(other.trueSource) && falseSource.equals(other.falseSource);
		}
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		ifSource.createWeight(context, searcher);
		trueSource.createWeight(context, searcher);
		falseSource.createWeight(context, searcher);
	}
}
