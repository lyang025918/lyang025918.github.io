// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FloatDocValues.java

package org.apache.lucene.queries.function.docvalues;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueFloat;

public abstract class FloatDocValues extends FunctionValues
{

	protected final ValueSource vs;

	public FloatDocValues(ValueSource vs)
	{
		this.vs = vs;
	}

	public byte byteVal(int doc)
	{
		return (byte)(int)floatVal(doc);
	}

	public short shortVal(int doc)
	{
		return (short)(int)floatVal(doc);
	}

	public abstract float floatVal(int i);

	public int intVal(int doc)
	{
		return (int)floatVal(doc);
	}

	public long longVal(int doc)
	{
		return (long)floatVal(doc);
	}

	public double doubleVal(int doc)
	{
		return (double)floatVal(doc);
	}

	public String strVal(int doc)
	{
		return Float.toString(floatVal(doc));
	}

	public Object objectVal(int doc)
	{
		return exists(doc) ? Float.valueOf(floatVal(doc)) : null;
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append('=').append(strVal(doc)).toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueFloat mval = new MutableValueFloat();
			final FloatDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.value = floatVal(doc);
				mval.exists = exists(doc);
			}

			
			{
				this$0 = FloatDocValues.this;
				super();
			}
		};
	}
}
