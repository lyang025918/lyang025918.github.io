// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThrowableInformationPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public class ThrowableInformationPatternConverter extends LoggingEventPatternConverter
{

	private int maxLines;

	private ThrowableInformationPatternConverter(String options[])
	{
		super("Throwable", "throwable");
		maxLines = 0x7fffffff;
		if (options != null && options.length > 0)
			if ("none".equals(options[0]))
				maxLines = 0;
			else
			if ("short".equals(options[0]))
				maxLines = 1;
			else
				try
				{
					maxLines = Integer.parseInt(options[0]);
				}
				catch (NumberFormatException ex) { }
	}

	public static ThrowableInformationPatternConverter newInstance(String options[])
	{
		return new ThrowableInformationPatternConverter(options);
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		if (maxLines != 0)
		{
			ThrowableInformation information = event.getThrowableInformation();
			if (information != null)
			{
				String stringRep[] = information.getThrowableStrRep();
				int length = stringRep.length;
				if (maxLines < 0)
					length += maxLines;
				else
				if (length > maxLines)
					length = maxLines;
				for (int i = 0; i < length; i++)
				{
					String string = stringRep[i];
					toAppendTo.append(string).append("\n");
				}

			}
		}
	}

	public boolean handlesThrowable()
	{
		return true;
	}
}
