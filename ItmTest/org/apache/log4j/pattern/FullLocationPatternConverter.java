// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FullLocationPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class FullLocationPatternConverter extends LoggingEventPatternConverter
{

	private static final FullLocationPatternConverter INSTANCE = new FullLocationPatternConverter();

	private FullLocationPatternConverter()
	{
		super("Full Location", "fullLocation");
	}

	public static FullLocationPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer output)
	{
		LocationInfo locationInfo = event.getLocationInformation();
		if (locationInfo != null)
			output.append(locationInfo.fullInfo);
	}

}
