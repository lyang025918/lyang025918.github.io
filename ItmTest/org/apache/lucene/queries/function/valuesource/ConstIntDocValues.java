// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocFreqValueSource.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;

class ConstIntDocValues extends IntDocValues
{

	final int ival;
	final float fval;
	final double dval;
	final long lval;
	final String sval;
	final ValueSource parent;

	ConstIntDocValues(int val, ValueSource parent)
	{
		super(parent);
		ival = val;
		fval = val;
		dval = val;
		lval = val;
		sval = Integer.toString(val);
		this.parent = parent;
	}

	public float floatVal(int doc)
	{
		return fval;
	}

	public int intVal(int doc)
	{
		return ival;
	}

	public long longVal(int doc)
	{
		return lval;
	}

	public double doubleVal(int doc)
	{
		return dval;
	}

	public String strVal(int doc)
	{
		return sval;
	}

	public String toString(int doc)
	{
		return (new StringBuilder()).append(parent.description()).append('=').append(sval).toString();
	}
}
