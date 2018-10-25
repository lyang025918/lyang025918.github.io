// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoolDocValues.java

package org.apache.lucene.queries.function.docvalues;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.util.mutable.MutableValue;
import org.apache.lucene.util.mutable.MutableValueBool;

public abstract class BoolDocValues extends FunctionValues
{

	protected final ValueSource vs;

	public BoolDocValues(ValueSource vs)
	{
		this.vs = vs;
	}

	public abstract boolean boolVal(int i);

	public byte byteVal(int doc)
	{
		return ((byte)(boolVal(doc) ? 1 : 0));
	}

	public short shortVal(int doc)
	{
		return boolVal(doc) ? 1 : 0;
	}

	public float floatVal(int doc)
	{
		return boolVal(doc) ? 1.0F : 0.0F;
	}

	public int intVal(int doc)
	{
		return boolVal(doc) ? 1 : 0;
	}

	public long longVal(int doc)
	{
		return boolVal(doc) ? 1L : 0L;
	}

	public double doubleVal(int doc)
	{
		return boolVal(doc) ? 1.0D : 0.0D;
	}

	public String strVal(int doc)
	{
		return Boolean.toString(boolVal(doc));
	}

	public Object objectVal(int doc)
	{
		return exists(doc) ? Boolean.valueOf(boolVal(doc)) : null;
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(vs.description()).append('=').append(strVal(doc)).toString();
	}

	public org.apache.lucene.queries.function.FunctionValues.ValueFiller getValueFiller()
	{
		return new org.apache.lucene.queries.function.FunctionValues.ValueFiller() {

			private final MutableValueBool mval = new MutableValueBool();
			final BoolDocValues this$0;

			public MutableValue getValue()
			{
				return mval;
			}

			public void fillValue(int doc)
			{
				mval.value = boolVal(doc);
				mval.exists = exists(doc);
			}

			
			{
				this$0 = BoolDocValues.this;
				super();
			}
		};
	}
}
