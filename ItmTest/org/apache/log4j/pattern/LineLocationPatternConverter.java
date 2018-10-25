// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LineLocationPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class LineLocationPatternConverter extends LoggingEventPatternConverter
{

	private static final LineLocationPatternConverter INSTANCE = new LineLocationPatternConverter();

	private LineLocationPatternConverter()
	{
		super("Line", "line");
	}

	public static LineLocationPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer output)
	{
		LocationInfo locationInfo = event.getLocationInformation();
		if (locationInfo != null)
			output.append(locationInfo.getLineNumber());
	}

}
