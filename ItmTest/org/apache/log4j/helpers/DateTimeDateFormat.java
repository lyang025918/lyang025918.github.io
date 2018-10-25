// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateTimeDateFormat.java

package org.apache.log4j.helpers;

import java.text.*;
import java.util.*;

// Referenced classes of package org.apache.log4j.helpers:
//			AbsoluteTimeDateFormat

public class DateTimeDateFormat extends AbsoluteTimeDateFormat
{

	private static final long serialVersionUID = 0x4cfd2b294307279bL;
	String shortMonths[];

	public DateTimeDateFormat()
	{
		shortMonths = (new DateFormatSymbols()).getShortMonths();
	}

	public DateTimeDateFormat(TimeZone timeZone)
	{
		this();
		setCalendar(Calendar.getInstance(timeZone));
	}

	public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
	{
		calendar.setTime(date);
		int day = calendar.get(5);
		if (day < 10)
			sbuf.append('0');
		sbuf.append(day);
		sbuf.append(' ');
		sbuf.append(shortMonths[calendar.get(2)]);
		sbuf.append(' ');
		int year = calendar.get(1);
		sbuf.append(year);
		sbuf.append(' ');
		return super.format(date, sbuf, fieldPosition);
	}

	public Date parse(String s, ParsePosition pos)
	{
		return null;
	}
}
