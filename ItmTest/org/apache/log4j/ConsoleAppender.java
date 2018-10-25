// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConsoleAppender.java

package org.apache.log4j;

import java.io.*;
import org.apache.log4j.helpers.LogLog;

// Referenced classes of package org.apache.log4j:
//			WriterAppender, Layout

public class ConsoleAppender extends WriterAppender
{
	private static class SystemOutStream extends OutputStream
	{

		public void close()
		{
		}

		public void flush()
		{
			System.out.flush();
		}

		public void write(byte b[])
			throws IOException
		{
			System.out.write(b);
		}

		public void write(byte b[], int off, int len)
			throws IOException
		{
			System.out.write(b, off, len);
		}

		public void write(int b)
			throws IOException
		{
			System.out.write(b);
		}

		public SystemOutStream()
		{
		}
	}

	private static class SystemErrStream extends OutputStream
	{

		public void close()
		{
		}

		public void flush()
		{
			System.err.flush();
		}

		public void write(byte b[])
			throws IOException
		{
			System.err.write(b);
		}

		public void write(byte b[], int off, int len)
			throws IOException
		{
			System.err.write(b, off, len);
		}

		public void write(int b)
			throws IOException
		{
			System.err.write(b);
		}

		public SystemErrStream()
		{
		}
	}


	public static final String SYSTEM_OUT = "System.out";
	public static final String SYSTEM_ERR = "System.err";
	protected String target;
	private boolean follow;

	public ConsoleAppender()
	{
		target = "System.out";
		follow = false;
	}

	public ConsoleAppender(Layout layout)
	{
		this(layout, "System.out");
	}

	public ConsoleAppender(Layout layout, String target)
	{
		this.target = "System.out";
		follow = false;
		setLayout(layout);
		setTarget(target);
		activateOptions();
	}

	public void setTarget(String value)
	{
		String v = value.trim();
		if ("System.out".equalsIgnoreCase(v))
			target = "System.out";
		else
		if ("System.err".equalsIgnoreCase(v))
			target = "System.err";
		else
			targetWarn(value);
	}

	public String getTarget()
	{
		return target;
	}

	public final void setFollow(boolean newValue)
	{
		follow = newValue;
	}

	public final boolean getFollow()
	{
		return follow;
	}

	void targetWarn(String val)
	{
		LogLog.warn("[" + val + "] should be System.out or System.err.");
		LogLog.warn("Using previously set target, System.out by default.");
	}

	public void activateOptions()
	{
		if (follow)
		{
			if (target.equals("System.err"))
				setWriter(createWriter(new SystemErrStream()));
			else
				setWriter(createWriter(new SystemOutStream()));
		} else
		if (target.equals("System.err"))
			setWriter(createWriter(System.err));
		else
			setWriter(createWriter(System.out));
		super.activateOptions();
	}

	protected final void closeWriter()
	{
		if (follow)
			super.closeWriter();
	}
}
