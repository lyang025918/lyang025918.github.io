// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbsoluteTimeDateFormat.java

package org.apache.log4j.helpers;

import java.text.*;
import java.util.*;

public class AbsoluteTimeDateFormat extends DateFormat
{

	private static final long serialVersionUID = 0xfa9a8136ad66a872L;
	public static final String ABS_TIME_DATE_FORMAT = "ABSOLUTE";
	public static final String DATE_AND_TIME_DATE_FORMAT = "DATE";
	public static final String ISO8601_DATE_FORMAT = "ISO8601";
	private static long previousTime;
	private static char previousTimeWithoutMillis[] = new char[9];

	public AbsoluteTimeDateFormat()
	{
		setCalendar(Calendar.getInstance());
	}

	public AbsoluteTimeDateFormat(TimeZone timeZone)
	{
		setCalendar(Calendar.getInstance(timeZone));
	}

	public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
	{
		long now = date.getTime();
		int millis = (int)(now % 1000L);
		if (now - (long)millis != previousTime || previousTimeWithoutMillis[0] == 0)
		{
			calendar.setTime(date);
			int start = sbuf.length();
			int hour = calendar.get(11);
			if (hour < 10)
				sbuf.append('0');
			sbuf.append(hour);
			sbuf.append(':');
			int mins = calendar.get(12);
			if (mins < 10)
				sbuf.append('0');
			sbuf.append(mins);
			sbuf.append(':');
			int secs = calendar.get(13);
			if (secs < 10)
				sbuf.append('0');
			sbuf.append(secs);
			sbuf.append(',');
			sbuf.getChars(start, sbuf.length(), previousTimeWithoutMillis, 0);
			previousTime = now - (long)millis;
		} else
		{
			sbuf.append(previousTimeWithoutMillis);
		}
		if (millis < 100)
			sbuf.append('0');
		if (millis < 10)
			sbuf.append('0');
		sbuf.append(millis);
		return sbuf;
	}

	public Date parse(String s, ParsePosition pos)
	{
		return null;
	}

}
