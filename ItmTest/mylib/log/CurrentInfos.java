// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CurrentInfos.java

package mylib.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentInfos
{

	public CurrentInfos()
	{
	}

	public static String get()
	{
		StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
		StringBuffer buf = new StringBuffer(1024);
		buf.append(traceElement.getFileName()).append(":").append(traceElement.getMethodName()).append(":").append(traceElement.getLineNumber()).append(" | ");
		return buf.toString();
	}

	public static String _FILE_()
	{
		StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
		return traceElement.getFileName();
	}

	public static String _FUNC_()
	{
		StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
		return traceElement.getMethodName();
	}

	public static int _LINE_()
	{
		StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
		return traceElement.getLineNumber();
	}

	public static String _TIME_()
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(now);
	}

	public static void main(String args[])
	{
		String file = _FILE_();
		String func = _FUNC_();
		String time = _TIME_();
		int line = _LINE_();
		String others = get();
	}
}
