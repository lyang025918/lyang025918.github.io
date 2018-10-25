// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LineSeparatorPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class LineSeparatorPatternConverter extends LoggingEventPatternConverter
{

	private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
	private final String lineSep;

	private LineSeparatorPatternConverter()
	{
		super("Line Sep", "lineSep");
		lineSep = Layout.LINE_SEP;
	}

	public static LineSeparatorPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		toAppendTo.append(lineSep);
	}

	public void format(Object obj, StringBuffer toAppendTo)
	{
		toAppendTo.append(lineSep);
	}

}
