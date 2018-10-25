// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RelativeTimePatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public class RelativeTimePatternConverter extends LoggingEventPatternConverter
{
	private static final class CachedTimestamp
	{

		private final long timestamp;
		private final String formatted;

		public boolean format(long newTimestamp, StringBuffer toAppendTo)
		{
			if (newTimestamp == timestamp)
			{
				toAppendTo.append(formatted);
				return true;
			} else
			{
				return false;
			}
		}

		public CachedTimestamp(long timestamp, String formatted)
		{
			this.timestamp = timestamp;
			this.formatted = formatted;
		}
	}


	private CachedTimestamp lastTimestamp;

	public RelativeTimePatternConverter()
	{
		super("Time", "time");
		lastTimestamp = new CachedTimestamp(0L, "");
	}

	public static RelativeTimePatternConverter newInstance(String options[])
	{
		return new RelativeTimePatternConverter();
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		long timestamp = event.timeStamp;
		if (!lastTimestamp.format(timestamp, toAppendTo))
		{
			String formatted = Long.toString(timestamp - LoggingEvent.getStartTime());
			toAppendTo.append(formatted);
			lastTimestamp = new CachedTimestamp(timestamp, formatted);
		}
	}
}
