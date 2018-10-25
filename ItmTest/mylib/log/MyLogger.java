// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MyLogger.java

package mylib.log;

import java.io.File;
import java.util.Calendar;
import java.util.logging.*;

// Referenced classes of package mylib.log:
//			CurrentInfos

public class MyLogger
{
	class MyFormatter extends Formatter
	{

		final MyLogger this$0;

		public String format(LogRecord record)
		{
			int MAX_BUF_SIZE = 1024;
			StringBuffer buf = new StringBuffer(1024);
			buf.append((new StringBuilder()).append("[").append(CurrentInfos._TIME_()).append("]").toString());
			buf.append((new StringBuilder()).append("[").append(record.getLevel()).append("]").toString());
			buf.append((new StringBuilder()).append("[thrd_").append(Thread.currentThread().getId()).append("]").toString());
			buf.append((new StringBuilder()).append(" ").append(record.getMessage()).toString());
			buf.append("\r\n");
			return buf.toString();
		}

		MyFormatter()
		{
			this.this$0 = MyLogger.this;
			super();
		}
	}


	private static final MyLogger myLogger = new MyLogger();
	private static final Logger abLog = Logger.getLogger(mylib/log/MyLogger.getName());

	public static synchronized MyLogger inst()
	{
		return myLogger;
	}

	public synchronized Logger open(String name)
	{
		String logPath = getLogPath(name);
		abLog.setLevel(Level.ALL);
		try
		{
			FileHandler fh = new FileHandler(logPath, true);
			fh.setLevel(Level.INFO);
			fh.setFormatter(new MyFormatter());
			fh.setEncoding("UTF-8");
			abLog.setUseParentHandlers(false);
			abLog.addHandler(fh);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		printHeader(logPath);
		return abLog;
	}

	public synchronized void close()
	{
		printTail();
	}

	public static String getNowTime()
	{
		String date = "";
		Calendar cd = Calendar.getInstance();
		int y = cd.get(1);
		int m = cd.get(2) + 1;
		int d = cd.get(5);
		int h = cd.get(11);
		int minute = cd.get(12);
		int sec = cd.get(13);
		int msec = cd.get(14);
		date = (new StringBuilder()).append(y).append("/").toString();
		if (m < 10)
			date = (new StringBuilder()).append(date).append(0).toString();
		date = (new StringBuilder()).append(date).append(m).append("/").toString();
		if (d < 10)
			date = (new StringBuilder()).append(date).append(0).toString();
		date = (new StringBuilder()).append(date).append(d).append(" ").toString();
		if (h < 10)
			date = (new StringBuilder()).append(date).append(0).toString();
		date = (new StringBuilder()).append(date).append(h).append(":").toString();
		if (minute < 10)
			date = (new StringBuilder()).append(date).append(0).toString();
		date = (new StringBuilder()).append(date).append(minute).append(":").toString();
		if (sec < 10)
			date = (new StringBuilder()).append(date).append(0).toString();
		date = (new StringBuilder()).append(date).append(sec).append(" ").append(msec).toString();
		return date;
	}

	public static void main(String args[])
	{
		String path = "e:/download/abcd.log";
		Logger ablog = inst().open(path);
		ablog.info("test again");
		inst().close();
	}

	private MyLogger()
	{
	}

	private String getLogPath(String name)
	{
		String path = name;
		char split = '/';
		if (name.contains("\\"))
			split = '\\';
		int pos = name.lastIndexOf(split);
		String tmpDir = name.substring(0, pos);
		(new File(tmpDir)).mkdirs();
		return path;
	}

	private void printHeader(String path)
	{
		abLog.severe("================= Begin Here! =================");
	}

	private void printTail()
	{
		abLog.severe("================= End Here! =================");
	}

}
