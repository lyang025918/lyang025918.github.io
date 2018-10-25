// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggerPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			NamePatternConverter

public final class LoggerPatternConverter extends NamePatternConverter
{

	private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);

	private LoggerPatternConverter(String options[])
	{
		super("Logger", "logger", options);
	}

	public static LoggerPatternConverter newInstance(String options[])
	{
		if (options == null || options.length == 0)
			return INSTANCE;
		else
			return new LoggerPatternConverter(options);
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		int initialLength = toAppendTo.length();
		toAppendTo.append(event.getLoggerName());
		abbreviate(initialLength, toAppendTo);
	}

}
