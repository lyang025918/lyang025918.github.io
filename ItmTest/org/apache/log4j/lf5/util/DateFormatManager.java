// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateFormatManager.java

package org.apache.log4j.lf5.util;

import java.text.*;
import java.util.*;

public class DateFormatManager
{

	private TimeZone _timeZone;
	private Locale _locale;
	private String _pattern;
	private DateFormat _dateFormat;

	public DateFormatManager()
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		configure();
	}

	public DateFormatManager(TimeZone timeZone)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_timeZone = timeZone;
		configure();
	}

	public DateFormatManager(Locale locale)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_locale = locale;
		configure();
	}

	public DateFormatManager(String pattern)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_pattern = pattern;
		configure();
	}

	public DateFormatManager(TimeZone timeZone, Locale locale)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_timeZone = timeZone;
		_locale = locale;
		configure();
	}

	public DateFormatManager(TimeZone timeZone, String pattern)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_timeZone = timeZone;
		_pattern = pattern;
		configure();
	}

	public DateFormatManager(Locale locale, String pattern)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_locale = locale;
		_pattern = pattern;
		configure();
	}

	public DateFormatManager(TimeZone timeZone, Locale locale, String pattern)
	{
		_timeZone = null;
		_locale = null;
		_pattern = null;
		_dateFormat = null;
		_timeZone = timeZone;
		_locale = locale;
		_pattern = pattern;
		configure();
	}

	public synchronized TimeZone getTimeZone()
	{
		if (_timeZone == null)
			return TimeZone.getDefault();
		else
			return _timeZone;
	}

	public synchronized void setTimeZone(TimeZone timeZone)
	{
		_timeZone = timeZone;
		configure();
	}

	public synchronized Locale getLocale()
	{
		if (_locale == null)
			return Locale.getDefault();
		else
			return _locale;
	}

	public synchronized void setLocale(Locale locale)
	{
		_locale = locale;
		configure();
	}

	public synchronized String getPattern()
	{
		return _pattern;
	}

	public synchronized void setPattern(String pattern)
	{
		_pattern = pattern;
		configure();
	}

	/**
	 * @deprecated Method getOutputFormat is deprecated
	 */

	public synchronized String getOutputFormat()
	{
		return _pattern;
	}

	/**
	 * @deprecated Method setOutputFormat is deprecated
	 */

	public synchronized void setOutputFormat(String pattern)
	{
		_pattern = pattern;
		configure();
	}

	public synchronized DateFormat getDateFormatInstance()
	{
		return _dateFormat;
	}

	public synchronized void setDateFormatInstance(DateFormat dateFormat)
	{
		_dateFormat = dateFormat;
	}

	public String format(Date date)
	{
		return getDateFormatInstance().format(date);
	}

	public String format(Date date, String pattern)
	{
		DateFormat formatter = null;
		formatter = getDateFormatInstance();
		if (formatter instanceof SimpleDateFormat)
		{
			formatter = (SimpleDateFormat)(SimpleDateFormat)formatter.clone();
			((SimpleDateFormat)formatter).applyPattern(pattern);
		}
		return formatter.format(date);
	}

	public Date parse(String date)
		throws ParseException
	{
		return getDateFormatInstance().parse(date);
	}

	public Date parse(String date, String pattern)
		throws ParseException
	{
		DateFormat formatter = null;
		formatter = getDateFormatInstance();
		if (formatter instanceof SimpleDateFormat)
		{
			formatter = (SimpleDateFormat)(SimpleDateFormat)formatter.clone();
			((SimpleDateFormat)formatter).applyPattern(pattern);
		}
		return formatter.parse(date);
	}

	private synchronized void configure()
	{
		_dateFormat = SimpleDateFormat.getDateTimeInstance(0, 0, getLocale());
		_dateFormat.setTimeZone(getTimeZone());
		if (_pattern != null)
			((SimpleDateFormat)_dateFormat).applyPattern(_pattern);
	}
}
