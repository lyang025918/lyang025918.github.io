// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ISO8601DateFormat.java

package org.apache.log4j.helpers;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.*;

// Referenced classes of package org.apache.log4j.helpers:
//			AbsoluteTimeDateFormat

public class ISO8601DateFormat extends AbsoluteTimeDateFormat
{

	private static final long serialVersionUID = 0xf57480de32998120L;
	private static long lastTime;
	private static char lastTimeString[] = new char[20];

	public ISO8601DateFormat()
	{
	}

	public ISO8601DateFormat(TimeZone timeZone)
	{
		super(timeZone);
	}

	public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
	{
		long now = date.getTime();
		int millis = (int)(now % 1000L);
		if (now - (long)millis != lastTime || lastTimeString[0] == 0)
		{
			calendar.setTime(date);
			int start = sbuf.length();
			int year = calendar.get(1);
			sbuf.append(year);
			String month;
			switch (calendar.get(2))
			{
			case 0: // '\0'
				month = "-01-";
				break;

			case 1: // '\001'
				month = "-02-";
				break;

			case 2: // '\002'
				month = "-03-";
				break;

			case 3: // '\003'
				month = "-04-";
				break;

			case 4: // '\004'
				month = "-05-";
				break;

			case 5: // '\005'
				month = "-06-";
				break;

			case 6: // '\006'
				month = "-07-";
				break;

			case 7: // '\007'
				month = "-08-";
				break;

			case 8: // '\b'
				month = "-09-";
				break;

			case 9: // '\t'
				month = "-10-";
				break;

			case 10: // '\n'
				month = "-11-";
				break;

			case 11: // '\013'
				month = "-12-";
				break;

			default:
				month = "-NA-";
				break;
			}
			sbuf.append(month);
			int day = calendar.get(5);
			if (day < 10)
				sbuf.append('0');
			sbuf.append(day);
			sbuf.append(' ');
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
			sbuf.getChars(start, sbuf.length(), lastTimeString, 0);
			lastTime = now - (long)millis;
		} else
		{
			sbuf.append(lastTimeString);
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
