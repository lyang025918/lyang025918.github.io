// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Layout.java

package org.apache.log4j;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;

public abstract class Layout
	implements OptionHandler
{

	public static final String LINE_SEP = System.getProperty("line.separator");
	public static final int LINE_SEP_LEN = LINE_SEP.length();

	public Layout()
	{
	}

	public abstract String format(LoggingEvent loggingevent);

	public String getContentType()
	{
		return "text/plain";
	}

	public String getHeader()
	{
		return null;
	}

	public String getFooter()
	{
		return null;
	}

	public abstract boolean ignoresThrowable();

}
