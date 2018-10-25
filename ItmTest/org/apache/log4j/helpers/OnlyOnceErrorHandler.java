// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OnlyOnceErrorHandler.java

package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.helpers:
//			LogLog

public class OnlyOnceErrorHandler
	implements ErrorHandler
{

	final String WARN_PREFIX = "log4j warning: ";
	final String ERROR_PREFIX = "log4j error: ";
	boolean firstTime;

	public OnlyOnceErrorHandler()
	{
		firstTime = true;
	}

	public void setLogger(Logger logger1)
	{
	}

	public void activateOptions()
	{
	}

	public void error(String message, Exception e, int errorCode)
	{
		error(message, e, errorCode, null);
	}

	public void error(String message, Exception e, int errorCode, LoggingEvent event)
	{
		if ((e instanceof InterruptedIOException) || (e instanceof InterruptedException))
			Thread.currentThread().interrupt();
		if (firstTime)
		{
			LogLog.error(message, e);
			firstTime = false;
		}
	}

	public void error(String message)
	{
		if (firstTime)
		{
			LogLog.error(message);
			firstTime = false;
		}
	}

	public void setAppender(Appender appender1)
	{
	}

	public void setBackupAppender(Appender appender1)
	{
	}
}
