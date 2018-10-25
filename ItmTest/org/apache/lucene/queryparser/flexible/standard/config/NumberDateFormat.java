// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumberDateFormat.java

package org.apache.lucene.queryparser.flexible.standard.config;

import java.text.*;
import java.util.Date;

public class NumberDateFormat extends NumberFormat
{

	private static final long serialVersionUID = 0xd63be420c5313fbL;
	private final DateFormat dateFormat;

	public NumberDateFormat(DateFormat dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)
	{
		return dateFormat.format(new Date((long)number), toAppendTo, pos);
	}

	public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos)
	{
		return dateFormat.format(new Date(number), toAppendTo, pos);
	}

	public Number parse(String source, ParsePosition parsePosition)
	{
		Date date = dateFormat.parse(source, parsePosition);
		return date != null ? Long.valueOf(date.getTime()) : null;
	}

	public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos)
	{
		return dateFormat.format(number, toAppendTo, pos);
	}
}
