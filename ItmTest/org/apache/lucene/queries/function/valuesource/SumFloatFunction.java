// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SumFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			MultiFloatFunction

public class SumFloatFunction extends MultiFloatFunction
{

	public SumFloatFunction(ValueSource sources[])
	{
		super(sources);
	}

	protected String name()
	{
		return "sum";
	}

	protected float func(int doc, FunctionValues valsArr[])
	{
		float val = 0.0F;
		FunctionValues arr$[] = valsArr;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			FunctionValues vals = arr$[i$];
			val += vals.floatVal(doc);
		}

		return val;
	}
}
