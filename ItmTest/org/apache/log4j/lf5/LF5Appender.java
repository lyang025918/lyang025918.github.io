// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LF5Appender.java

package org.apache.log4j.lf5;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.lf5:
//			Log4JLogRecord, LogLevelFormatException, AppenderFinalizer, LogLevel

public class LF5Appender extends AppenderSkeleton
{

	protected LogBrokerMonitor _logMonitor;
	protected static LogBrokerMonitor _defaultLogMonitor;
	protected static AppenderFinalizer _finalizer;

	public LF5Appender()
	{
		this(getDefaultInstance());
	}

	public LF5Appender(LogBrokerMonitor monitor)
	{
		if (monitor != null)
			_logMonitor = monitor;
	}

	public void append(LoggingEvent event)
	{
		String category = event.getLoggerName();
		String logMessage = event.getRenderedMessage();
		String nestedDiagnosticContext = event.getNDC();
		String threadDescription = event.getThreadName();
		String level = event.getLevel().toString();
		long time = event.timeStamp;
		LocationInfo locationInfo = event.getLocationInformation();
		Log4JLogRecord record = new Log4JLogRecord();
		record.setCategory(category);
		record.setMessage(logMessage);
		record.setLocation(locationInfo.fullInfo);
		record.setMillis(time);
		record.setThreadDescription(threadDescription);
		if (nestedDiagnosticContext != null)
			record.setNDC(nestedDiagnosticContext);
		else
			record.setNDC("");
		if (event.getThrowableInformation() != null)
			record.setThrownStackTrace(event.getThrowableInformation());
		try
		{
			record.setLevel(LogLevel.valueOf(level));
		}
		catch (LogLevelFormatException e)
		{
			record.setLevel(LogLevel.WARN);
		}
		if (_logMonitor != null)
			_logMonitor.addMessage(record);
	}

	public void close()
	{
	}

	public boolean requiresLayout()
	{
		return false;
	}

	public void setCallSystemExitOnClose(boolean callSystemExitOnClose)
	{
		_logMonitor.setCallSystemExitOnClose(callSystemExitOnClose);
	}

	public boolean equals(LF5Appender compareTo)
	{
		return _logMonitor == compareTo.getLogBrokerMonitor();
	}

	public LogBrokerMonitor getLogBrokerMonitor()
	{
		return _logMonitor;
	}

	public static void main(String args[])
	{
		new LF5Appender();
	}

	public void setMaxNumberOfRecords(int maxNumberOfRecords)
	{
		_defaultLogMonitor.setMaxNumberOfLogRecords(maxNumberOfRecords);
	}

	protected static synchronized LogBrokerMonitor getDefaultInstance()
	{
		if (_defaultLogMonitor == null)
			try
			{
				_defaultLogMonitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
				_finalizer = new AppenderFinalizer(_defaultLogMonitor);
				_defaultLogMonitor.setFrameSize(getDefaultMonitorWidth(), getDefaultMonitorHeight());
				_defaultLogMonitor.setFontSize(12);
				_defaultLogMonitor.show();
			}
			catch (SecurityException e)
			{
				_defaultLogMonitor = null;
			}
		return _defaultLogMonitor;
	}

	protected static int getScreenWidth()
	{
		return Toolkit.getDefaultToolkit().getScreenSize().width;
		Throwable t;
		t;
		return 800;
	}

	protected static int getScreenHeight()
	{
		return Toolkit.getDefaultToolkit().getScreenSize().height;
		Throwable t;
		t;
		return 600;
	}

	protected static int getDefaultMonitorWidth()
	{
		return (3 * getScreenWidth()) / 4;
	}

	protected static int getDefaultMonitorHeight()
	{
		return (3 * getScreenHeight()) / 4;
	}
}
