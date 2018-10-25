// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassNamePatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			NamePatternConverter

public final class ClassNamePatternConverter extends NamePatternConverter
{

	private ClassNamePatternConverter(String options[])
	{
		super("Class Name", "class name", options);
	}

	public static ClassNamePatternConverter newInstance(String options[])
	{
		return new ClassNamePatternConverter(options);
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		int initialLength = toAppendTo.length();
		LocationInfo li = event.getLocationInformation();
		if (li == null)
			toAppendTo.append("?");
		else
			toAppendTo.append(li.getClassName());
		abbreviate(initialLength, toAppendTo);
	}
}
