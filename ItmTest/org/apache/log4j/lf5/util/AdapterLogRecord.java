// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterLogRecord.java

package org.apache.log4j.lf5.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogRecord;

public class AdapterLogRecord extends LogRecord
{

	private static LogLevel severeLevel = null;
	private static StringWriter sw;
	private static PrintWriter pw;

	public AdapterLogRecord()
	{
	}

	public void setCategory(String category)
	{
		super.setCategory(category);
		super.setLocation(getLocationInfo(category));
	}

	public boolean isSevereLevel()
	{
		if (severeLevel == null)
			return false;
		else
			return severeLevel.equals(getLevel());
	}

	public static void setSevereLevel(LogLevel level)
	{
		severeLevel = level;
	}

	public static LogLevel getSevereLevel()
	{
		return severeLevel;
	}

	protected String getLocationInfo(String category)
	{
		String stackTrace = stackTraceToString(new Throwable());
		String line = parseLine(stackTrace, category);
		return line;
	}

	protected String stackTraceToString(Throwable t)
	{
		String s = null;
		synchronized (sw)
		{
			t.printStackTrace(pw);
			s = sw.toString();
			sw.getBuffer().setLength(0);
		}
		return s;
	}

	protected String parseLine(String trace, String category)
	{
		int index = trace.indexOf(category);
		if (index == -1)
		{
			return null;
		} else
		{
			trace = trace.substring(index);
			trace = trace.substring(0, trace.indexOf(")") + 1);
			return trace;
		}
	}

	static 
	{
		sw = new StringWriter();
		pw = new PrintWriter(sw);
	}
}
