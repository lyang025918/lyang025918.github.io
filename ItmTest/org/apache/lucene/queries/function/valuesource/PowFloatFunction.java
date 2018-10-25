// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PowFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			DualFloatFunction

public class PowFloatFunction extends DualFloatFunction
{

	public PowFloatFunction(ValueSource a, ValueSource b)
	{
		super(a, b);
	}

	protected String name()
	{
		return "pow";
	}

	protected float func(int doc, FunctionValues aVals, FunctionValues bVals)
	{
		return (float)Math.pow(aVals.floatVal(doc), bVals.floatVal(doc));
	}
}
