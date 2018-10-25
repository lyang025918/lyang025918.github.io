// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileDatePatternConverter.java

package org.apache.log4j.pattern;


// Referenced classes of package org.apache.log4j.pattern:
//			DatePatternConverter, PatternConverter

public final class FileDatePatternConverter
{

	private FileDatePatternConverter()
	{
	}

	public static PatternConverter newInstance(String options[])
	{
		if (options == null || options.length == 0)
			return DatePatternConverter.newInstance(new String[] {
				"yyyy-MM-dd"
			});
		else
			return DatePatternConverter.newInstance(options);
	}
}
