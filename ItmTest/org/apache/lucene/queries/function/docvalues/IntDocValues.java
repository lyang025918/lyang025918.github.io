// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntDocValues.java

package org.apache.lucene.queries.function.docvalues;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueInt;

public abstract class IntDocValues extends FunctionValues
{

	protected final ValueSource vs;

	public IntDocValues(ValueSource vs)
	{
		this.vs = vs;
	}

	public byte byteVal(int doc)
	{
		return (byte)intVal(doc);
	}

	public short shortVal(int doc)
	{
		return (short)intVal(doc);
	}

	public float floatVal(int doc)
	{
		return (float)intVal(doc);
	}

	public abstract int intVal(int i);

	public long longVal(int doc)
	{
		return (long)intVal(doc);
	}

	public double doubleVal(int doc)
	{
		return (double)intVal(doc);
	}

	public String strVal(int doc)
	{
		return Integer.toString(intVal(doc));
	}

	public Object objectVal(int doc)
	{
		return exists(doc) ? Integer.valueOf(intVal(doc)) : null;
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append('=').append(strVal(doc)).toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueInt mval = new MutableValueInt();
			final IntDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.value = intVal(doc);
				mval.exists = exists(doc);
			}

			
			{
				this$0 = IntDocValues.this;
				super();
			}
		};
	}
}
