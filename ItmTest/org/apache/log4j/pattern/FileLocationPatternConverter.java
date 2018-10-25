// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileLocationPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class FileLocationPatternConverter extends LoggingEventPatternConverter
{

	private static final FileLocationPatternConverter INSTANCE = new FileLocationPatternConverter();

	private FileLocationPatternConverter()
	{
		super("File Location", "file");
	}

	public static FileLocationPatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer output)
	{
		LocationInfo locationInfo = event.getLocationInformation();
		if (locationInfo != null)
			output.append(locationInfo.getFileName());
	}

}
