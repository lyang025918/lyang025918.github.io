// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DailyRollingFileAppender.java

package org.apache.log4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			FileAppender, RollingCalendar, Layout

public class DailyRollingFileAppender extends FileAppender
{

	static final int TOP_OF_TROUBLE = -1;
	static final int TOP_OF_MINUTE = 0;
	static final int TOP_OF_HOUR = 1;
	static final int HALF_DAY = 2;
	static final int TOP_OF_DAY = 3;
	static final int TOP_OF_WEEK = 4;
	static final int TOP_OF_MONTH = 5;
	private String datePattern;
	private String scheduledFilename;
	private long nextCheck;
	Date now;
	SimpleDateFormat sdf;
	RollingCalendar rc;
	int checkPeriod;
	static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

	public DailyRollingFileAppender()
	{
		datePattern = "'.'yyyy-MM-dd";
		nextCheck = System.currentTimeMillis() - 1L;
		now = new Date();
		rc = new RollingCalendar();
		checkPeriod = -1;
	}

	public DailyRollingFileAppender(Layout layout, String filename, String datePattern)
		throws IOException
	{
		super(layout, filename, true);
		this.datePattern = "'.'yyyy-MM-dd";
		nextCheck = System.currentTimeMillis() - 1L;
		now = new Date();
		rc = new RollingCalendar();
		checkPeriod = -1;
		this.datePattern = datePattern;
		activateOptions();
	}

	public void setDatePattern(String pattern)
	{
		datePattern = pattern;
	}

	public String getDatePattern()
	{
		return datePattern;
	}

	public void activateOptions()
	{
		super.activateOptions();
		if (datePattern != null && fileName != null)
		{
			now.setTime(System.currentTimeMillis());
			sdf = new SimpleDateFormat(datePattern);
			int type = computeCheckPeriod();
			printPeriodicity(type);
			rc.setType(type);
			File file = new File(fileName);
			scheduledFilename = fileName + sdf.format(new Date(file.lastModified()));
		} else
		{
			LogLog.error("Either File or DatePattern options are not set for appender [" + name + "].");
		}
	}

	void printPeriodicity(int type)
	{
		switch (type)
		{
		case 0: // '\0'
			LogLog.debug("Appender [" + name + "] to be rolled every minute.");
			break;

		case 1: // '\001'
			LogLog.debug("Appender [" + name + "] to be rolled on top of every hour.");
			break;

		case 2: // '\002'
			LogLog.debug("Appender [" + name + "] to be rolled at midday and midnight.");
			break;

		case 3: // '\003'
			LogLog.debug("Appender [" + name + "] to be rolled at midnight.");
			break;

		case 4: // '\004'
			LogLog.debug("Appender [" + name + "] to be rolled at start of week.");
			break;

		case 5: // '\005'
			LogLog.debug("Appender [" + name + "] to be rolled at start of every month.");
			break;

		default:
			LogLog.warn("Unknown periodicity for appender [" + name + "].");
			break;
		}
	}

	int computeCheckPeriod()
	{
		RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.getDefault());
		Date epoch = new Date(0L);
		if (datePattern != null)
		{
			for (int i = 0; i <= 5; i++)
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
				simpleDateFormat.setTimeZone(gmtTimeZone);
				String r0 = simpleDateFormat.format(epoch);
				rollingCalendar.setType(i);
				Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
				String r1 = simpleDateFormat.format(next);
				if (r0 != null && r1 != null && !r0.equals(r1))
					return i;
			}

		}
		return -1;
	}

	void rollOver()
		throws IOException
	{
		if (datePattern == null)
		{
			errorHandler.error("Missing DatePattern option in rollOver().");
			return;
		}
		String datedFilename = fileName + sdf.format(now);
		if (scheduledFilename.equals(datedFilename))
			return;
		closeFile();
		File target = new File(scheduledFilename);
		if (target.exists())
			target.delete();
		File file = new File(fileName);
		boolean result = file.renameTo(target);
		if (result)
			LogLog.debug(fileName + " -> " + scheduledFilename);
		else
			LogLog.error("Failed to rename [" + fileName + "] to [" + scheduledFilename + "].");
		try
		{
			setFile(fileName, true, bufferedIO, bufferSize);
		}
		catch (IOException e)
		{
			errorHandler.error("setFile(" + fileName + ", true) call failed.");
		}
		scheduledFilename = datedFilename;
	}

	protected void subAppend(LoggingEvent event)
	{
		long n = System.currentTimeMillis();
		if (n >= nextCheck)
		{
			now.setTime(n);
			nextCheck = rc.getNextCheckMillis(now);
			try
			{
				rollOver();
			}
			catch (IOException ioe)
			{
				if (ioe instanceof InterruptedIOException)
					Thread.currentThread().interrupt();
				LogLog.error("rollOver() failed.", ioe);
			}
		}
		super.subAppend(event);
	}

}
