// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NamePatternConverter.java

package org.apache.log4j.pattern;


// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter, NameAbbreviator

public abstract class NamePatternConverter extends LoggingEventPatternConverter
{

	private final NameAbbreviator abbreviator;

	protected NamePatternConverter(String name, String style, String options[])
	{
		super(name, style);
		if (options != null && options.length > 0)
			abbreviator = NameAbbreviator.getAbbreviator(options[0]);
		else
			abbreviator = NameAbbreviator.getDefaultAbbreviator();
	}

	protected final void abbreviate(int nameStart, StringBuffer buf)
	{
		abbreviator.abbreviate(nameStart, buf);
	}
}
