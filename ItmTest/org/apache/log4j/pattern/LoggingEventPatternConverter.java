// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggingEventPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			PatternConverter

public abstract class LoggingEventPatternConverter extends PatternConverter
{

	protected LoggingEventPatternConverter(String name, String style)
	{
		super(name, style);
	}

	public abstract void format(LoggingEvent loggingevent, StringBuffer stringbuffer);

	public void format(Object obj, StringBuffer output)
	{
		if (obj instanceof LoggingEvent)
			format((LoggingEvent)obj, output);
	}

	public boolean handlesThrowable()
	{
		return false;
	}
}
