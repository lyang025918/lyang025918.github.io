// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MaxFloatFunction.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;

// Referenced classes of package org.apache.lucene.queries.function.valuesource:
//			MultiFloatFunction

public class MaxFloatFunction extends MultiFloatFunction
{

	public MaxFloatFunction(ValueSource sources[])
	{
		super(sources);
	}

	protected String name()
	{
		return "max";
	}

	protected float func(int doc, FunctionValues valsArr[])
	{
		boolean first = true;
		float val = 0.0F;
		FunctionValues arr$[] = valsArr;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			FunctionValues vals = arr$[i$];
			if (first)
			{
				first = false;
				val = vals.floatVal(doc);
			} else
			{
				val = Math.max(vals.floatVal(doc), val);
			}
		}

		return val;
	}
}
