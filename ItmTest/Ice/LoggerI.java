// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggerI.java

package Ice;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package Ice:
//			InitializationException, Logger

public class LoggerI
	implements Logger
{

	String _prefix;
	String _file;
	String _lineSeparator;
	DateFormat _date;
	SimpleDateFormat _time;
	FileOutputStream _out;

	public LoggerI(String prefix, String file)
	{
		_prefix = "";
		_file = "";
		_out = null;
		if (prefix.length() > 0)
			_prefix = (new StringBuilder()).append(prefix).append(": ").toString();
		_lineSeparator = System.getProperty("line.separator");
		_date = DateFormat.getDateInstance(3);
		_time = new SimpleDateFormat(" HH:mm:ss:SSS");
		if (file.length() != 0)
		{
			_file = file;
			try
			{
				_out = new FileOutputStream(new File(_file), true);
			}
			catch (FileNotFoundException ex)
			{
				throw new InitializationException((new StringBuilder()).append("FileLogger: cannot open ").append(_file).toString());
			}
		}
	}

	protected void finalize()
	{
		if (_out != null)
			try
			{
				_out.close();
			}
			catch (Exception ex) { }
	}

	public void print(String message)
	{
		StringBuilder s = new StringBuilder(256);
		s.append(message);
		write(s, false);
	}

	public void trace(String category, String message)
	{
		StringBuilder s = new StringBuilder(256);
		s.append("-- ");
		s.append(_date.format(new Date()));
		s.append(_time.format(new Date()));
		s.append(' ');
		s.append(_prefix);
		s.append(category);
		s.append(": ");
		s.append(message);
		write(s, true);
	}

	public void warning(String message)
	{
		StringBuilder s = new StringBuilder(256);
		s.append("-! ");
		s.append(_date.format(new Date()));
		s.append(_time.format(new Date()));
		s.append(' ');
		s.append(_prefix);
		s.append("warning: ");
		s.append(Thread.currentThread().getName());
		s.append(": ");
		s.append(message);
		write(s, true);
	}

	public void error(String message)
	{
		StringBuilder s = new StringBuilder(256);
		s.append("!! ");
		s.append(_date.format(new Date()));
		s.append(_time.format(new Date()));
		s.append(' ');
		s.append(_prefix);
		s.append("error: ");
		s.append(Thread.currentThread().getName());
		s.append(": ");
		s.append(message);
		write(s, true);
	}

	public Logger cloneWithPrefix(String prefix)
	{
		return new LoggerI(prefix, _file);
	}

	private void write(StringBuilder message, boolean indent)
	{
		if (indent)
		{
			for (int idx = 0; (idx = message.indexOf("\n", idx)) != -1; idx++)
				message.insert(idx + 1, "   ");

		}
		message.append(_lineSeparator);
		if (_out == null)
			System.err.print(message.toString());
		else
			try
			{
				_out.write(message.toString().getBytes());
			}
			catch (IOException ex) { }
	}
}
