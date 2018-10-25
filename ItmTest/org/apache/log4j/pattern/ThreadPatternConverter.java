// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public class ThreadPatternConverter extends LoggingEventPatternConverter
{

	private static final ThreadPatternConverter INSTANCE = new ThreadPatternConverter();

	private ThreadPatternConverter()
	{
		super("Thread", "thread");
	}

	public static ThreadPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		toAppendTo.append(event.getThreadName());
	}

}
