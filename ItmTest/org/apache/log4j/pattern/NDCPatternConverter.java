// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NDCPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class NDCPatternConverter extends LoggingEventPatternConverter
{

	private static final NDCPatternConverter INSTANCE = new NDCPatternConverter();

	private NDCPatternConverter()
	{
		super("NDC", "ndc");
	}

	public static NDCPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		toAppendTo.append(event.getNDC());
	}

}
