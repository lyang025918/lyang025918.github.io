// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DoubleDocValues.java

package org.apache.lucene.queries.function.docvalues;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueDouble;

public abstract class DoubleDocValues extends FunctionValues
{

	protected final ValueSource vs;

	public DoubleDocValues(ValueSource vs)
	{
		this.vs = vs;
	}

	public byte byteVal(int doc)
	{
		return (byte)(int)doubleVal(doc);
	}

	public short shortVal(int doc)
	{
		return (short)(int)doubleVal(doc);
	}

	public float floatVal(int doc)
	{
		return (float)doubleVal(doc);
	}

	public int intVal(int doc)
	{
		return (int)doubleVal(doc);
	}

	public long longVal(int doc)
	{
		return (long)doubleVal(doc);
	}

	public boolean boolVal(int doc)
	{
		return doubleVal(doc) != 0.0D;
	}

	public abstract double doubleVal(int i);

	public String strVal(int doc)
	{
		return Double.toString(doubleVal(doc));
	}

	public Object objectVal(int doc)
	{
		return exists(doc) ? Double.valueOf(doubleVal(doc)) : null;
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append('=').append(strVal(doc)).toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueDouble mval = new MutableValueDouble();
			final DoubleDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.value = doubleVal(doc);
				mval.exists = exists(doc);
			}

			
			{
				this$0 = DoubleDocValues.this;
				super();
			}
		};
	}
}
