// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogFileParser.java

package org.apache.log4j.lf5.util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;
import org.apache.log4j.lf5.*;
import org.apache.log4j.lf5.viewer.*;

public class LogFileParser
	implements Runnable
{

	public static final String RECORD_DELIMITER = "[slf5s.start]";
	public static final String ATTRIBUTE_DELIMITER = "[slf5s.";
	public static final String DATE_DELIMITER = "[slf5s.DATE]";
	public static final String THREAD_DELIMITER = "[slf5s.THREAD]";
	public static final String CATEGORY_DELIMITER = "[slf5s.CATEGORY]";
	public static final String LOCATION_DELIMITER = "[slf5s.LOCATION]";
	public static final String MESSAGE_DELIMITER = "[slf5s.MESSAGE]";
	public static final String PRIORITY_DELIMITER = "[slf5s.PRIORITY]";
	public static final String NDC_DELIMITER = "[slf5s.NDC]";
	private static SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,S");
	private LogBrokerMonitor _monitor;
	LogFactor5LoadingDialog _loadDialog;
	private InputStream _in;

	public LogFileParser(File file)
		throws IOException, FileNotFoundException
	{
		this(((InputStream) (new FileInputStream(file))));
	}

	public LogFileParser(InputStream stream)
		throws IOException
	{
		_in = null;
		_in = stream;
	}

	public void parse(LogBrokerMonitor monitor)
		throws RuntimeException
	{
		_monitor = monitor;
		Thread t = new Thread(this);
		t.start();
	}

	public void run()
	{
		int index = 0;
		int counter = 0;
		boolean isLogFile = false;
		_loadDialog = new LogFactor5LoadingDialog(_monitor.getBaseFrame(), "Loading file...");
		try
		{
			String logRecords = loadLogFile(_in);
			while ((counter = logRecords.indexOf("[slf5s.start]", index)) != -1) 
			{
				LogRecord temp = createLogRecord(logRecords.substring(index, counter));
				isLogFile = true;
				if (temp != null)
					_monitor.addMessage(temp);
				index = counter + "[slf5s.start]".length();
			}
			if (index < logRecords.length() && isLogFile)
			{
				LogRecord temp = createLogRecord(logRecords.substring(index));
				if (temp != null)
					_monitor.addMessage(temp);
			}
			if (!isLogFile)
				throw new RuntimeException("Invalid log file format");
			SwingUtilities.invokeLater(new Runnable() {

				public void run()
				{
					destroyDialog();
				}

			
			{
				super();
			}
			});
		}
		catch (RuntimeException e)
		{
			destroyDialog();
			displayError("Error - Invalid log file format.\nPlease see documentation on how to load log files.");
		}
		catch (IOException e)
		{
			destroyDialog();
			displayError("Error - Unable to load log file!");
		}
		_in = null;
	}

	protected void displayError(String message)
	{
		LogFactor5ErrorDialog error = new LogFactor5ErrorDialog(_monitor.getBaseFrame(), message);
	}

	private void destroyDialog()
	{
		_loadDialog.hide();
		_loadDialog.dispose();
	}

	private String loadLogFile(InputStream stream)
		throws IOException
	{
		BufferedInputStream br = new BufferedInputStream(stream);
		int count = 0;
		int size = br.available();
		StringBuffer sb = null;
		if (size > 0)
			sb = new StringBuffer(size);
		else
			sb = new StringBuffer(1024);
		while ((count = br.read()) != -1) 
			sb.append((char)count);
		br.close();
		br = null;
		return sb.toString();
	}

	private String parseAttribute(String name, String record)
	{
		int index = record.indexOf(name);
		if (index == -1)
			return null;
		else
			return getAttribute(index, record);
	}

	private long parseDate(String record)
	{
		String s = parseAttribute("[slf5s.DATE]", record);
		if (s == null)
			return 0L;
		Date d = _sdf.parse(s);
		return d.getTime();
		ParseException e;
		e;
		return 0L;
	}

	private LogLevel parsePriority(String record)
	{
		String temp;
		temp = parseAttribute("[slf5s.PRIORITY]", record);
		if (temp == null)
			break MISSING_BLOCK_LABEL_22;
		return LogLevel.valueOf(temp);
		LogLevelFormatException e;
		e;
		return LogLevel.DEBUG;
		return LogLevel.DEBUG;
	}

	private String parseThread(String record)
	{
		return parseAttribute("[slf5s.THREAD]", record);
	}

	private String parseCategory(String record)
	{
		return parseAttribute("[slf5s.CATEGORY]", record);
	}

	private String parseLocation(String record)
	{
		return parseAttribute("[slf5s.LOCATION]", record);
	}

	private String parseMessage(String record)
	{
		return parseAttribute("[slf5s.MESSAGE]", record);
	}

	private String parseNDC(String record)
	{
		return parseAttribute("[slf5s.NDC]", record);
	}

	private String parseThrowable(String record)
	{
		return getAttribute(record.length(), record);
	}

	private LogRecord createLogRecord(String record)
	{
		if (record == null || record.trim().length() == 0)
		{
			return null;
		} else
		{
			LogRecord lr = new Log4JLogRecord();
			lr.setMillis(parseDate(record));
			lr.setLevel(parsePriority(record));
			lr.setCategory(parseCategory(record));
			lr.setLocation(parseLocation(record));
			lr.setThreadDescription(parseThread(record));
			lr.setNDC(parseNDC(record));
			lr.setMessage(parseMessage(record));
			lr.setThrownStackTrace(parseThrowable(record));
			return lr;
		}
	}

	private String getAttribute(int index, String record)
	{
		int start = record.lastIndexOf("[slf5s.", index - 1);
		if (start == -1)
		{
			return record.substring(0, index);
		} else
		{
			start = record.indexOf("]", start);
			return record.substring(start + 1, index).trim();
		}
	}


}
