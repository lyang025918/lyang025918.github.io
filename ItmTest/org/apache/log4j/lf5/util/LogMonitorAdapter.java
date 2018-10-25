// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogMonitorAdapter.java

package org.apache.log4j.lf5.util;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogRecord;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

// Referenced classes of package org.apache.log4j.lf5.util:
//			AdapterLogRecord

public class LogMonitorAdapter
{

	public static final int LOG4J_LOG_LEVELS = 0;
	public static final int JDK14_LOG_LEVELS = 1;
	private LogBrokerMonitor _logMonitor;
	private LogLevel _defaultLevel;

	private LogMonitorAdapter(List userDefinedLevels)
	{
		_defaultLevel = null;
		_defaultLevel = (LogLevel)userDefinedLevels.get(0);
		_logMonitor = new LogBrokerMonitor(userDefinedLevels);
		_logMonitor.setFrameSize(getDefaultMonitorWidth(), getDefaultMonitorHeight());
		_logMonitor.setFontSize(12);
		_logMonitor.show();
	}

	public static LogMonitorAdapter newInstance(int loglevels)
	{
		LogMonitorAdapter adapter;
		if (loglevels == 1)
		{
			adapter = newInstance(LogLevel.getJdk14Levels());
			adapter.setDefaultLevel(LogLevel.FINEST);
			adapter.setSevereLevel(LogLevel.SEVERE);
		} else
		{
			adapter = newInstance(LogLevel.getLog4JLevels());
			adapter.setDefaultLevel(LogLevel.DEBUG);
			adapter.setSevereLevel(LogLevel.FATAL);
		}
		return adapter;
	}

	public static LogMonitorAdapter newInstance(LogLevel userDefined[])
	{
		if (userDefined == null)
			return null;
		else
			return newInstance(Arrays.asList(userDefined));
	}

	public static LogMonitorAdapter newInstance(List userDefinedLevels)
	{
		return new LogMonitorAdapter(userDefinedLevels);
	}

	public void addMessage(LogRecord record)
	{
		_logMonitor.addMessage(record);
	}

	public void setMaxNumberOfRecords(int maxNumberOfRecords)
	{
		_logMonitor.setMaxNumberOfLogRecords(maxNumberOfRecords);
	}

	public void setDefaultLevel(LogLevel level)
	{
		_defaultLevel = level;
	}

	public LogLevel getDefaultLevel()
	{
		return _defaultLevel;
	}

	public void setSevereLevel(LogLevel level)
	{
		AdapterLogRecord.setSevereLevel(level);
	}

	public LogLevel getSevereLevel()
	{
		return AdapterLogRecord.getSevereLevel();
	}

	public void log(String category, LogLevel level, String message, Throwable t, String NDC)
	{
		AdapterLogRecord record = new AdapterLogRecord();
		record.setCategory(category);
		record.setMessage(message);
		record.setNDC(NDC);
		record.setThrown(t);
		if (level == null)
			record.setLevel(getDefaultLevel());
		else
			record.setLevel(level);
		addMessage(record);
	}

	public void log(String category, String message)
	{
		log(category, null, message);
	}

	public void log(String category, LogLevel level, String message, String NDC)
	{
		log(category, level, message, null, NDC);
	}

	public void log(String category, LogLevel level, String message, Throwable t)
	{
		log(category, level, message, t, null);
	}

	public void log(String category, LogLevel level, String message)
	{
		log(category, level, message, null, null);
	}

	protected static int getScreenWidth()
	{
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		Throwable t;
		t;
		return 800;
	}

	protected static int getScreenHeight()
	{
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
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
