// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntegerPatternConverter.java

package org.apache.log4j.pattern;

import java.util.Date;

// Referenced classes of package org.apache.log4j.pattern:
//			PatternConverter

public final class IntegerPatternConverter extends PatternConverter
{

	private static final IntegerPatternConverter INSTANCE = new IntegerPatternConverter();

	private IntegerPatternConverter()
	{
		super("Integer", "integer");
	}

	public static IntegerPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(Object obj, StringBuffer toAppendTo)
	{
		if (obj instanceof Integer)
			toAppendTo.append(obj.toString());
		if (obj instanceof Date)
			toAppendTo.append(Long.toString(((Date)obj).getTime()));
	}

}
