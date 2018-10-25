// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RelativeTimeDateFormat.java

package org.apache.log4j.helpers;

import java.text.*;
import java.util.Date;

public class RelativeTimeDateFormat extends DateFormat
{

	private static final long serialVersionUID = 0x61eb10b423babbd0L;
	protected final long startTime = System.currentTimeMillis();

	public RelativeTimeDateFormat()
	{
	}

	public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
	{
		return sbuf.append(date.getTime() - startTime);
	}

	public Date parse(String s, ParsePosition pos)
	{
		return null;
	}
}
