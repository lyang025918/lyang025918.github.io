// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LongDocValues.java

package org.apache.lucene.queries.function.docvalues;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueLong;

public abstract class LongDocValues extends FunctionValues
{

	protected final ValueSource vs;

	public LongDocValues(ValueSource vs)
	{
		this.vs = vs;
	}

	public byte byteVal(int doc)
	{
		return (byte)(int)longVal(doc);
	}

	public short shortVal(int doc)
	{
		return (short)(int)longVal(doc);
	}

	public float floatVal(int doc)
	{
		return (float)longVal(doc);
	}

	public int intVal(int doc)
	{
		return (int)longVal(doc);
	}

	public abstract long longVal(int i);

	public double doubleVal(int doc)
	{
		return (double)longVal(doc);
	}

	public boolean boolVal(int doc)
	{
		return longVal(doc) != 0L;
	}

	public String strVal(int doc)
	{
		return Long.toString(longVal(doc));
	}

	public Object objectVal(int doc)
	{
		return exists(doc) ? Long.valueOf(longVal(doc)) : null;
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append('=').append(strVal(doc)).toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueLong mval = new MutableValueLong();
			final LongDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.value = longVal(doc);
				mval.exists = exists(doc);
			}

			
			{
				this$0 = LongDocValues.this;
				super();
			}
		};
	}
}
