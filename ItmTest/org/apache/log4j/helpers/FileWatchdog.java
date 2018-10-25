// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileWatchdog.java

package org.apache.log4j.helpers;

import java.io.File;

// Referenced classes of package org.apache.log4j.helpers:
//			LogLog

public abstract class FileWatchdog extends Thread
{

	public static final long DEFAULT_DELAY = 60000L;
	protected String filename;
	protected long delay;
	File file;
	long lastModif;
	boolean warnedAlready;
	boolean interrupted;

	protected FileWatchdog(String filename)
	{
		super("FileWatchdog");
		delay = 60000L;
		lastModif = 0L;
		warnedAlready = false;
		interrupted = false;
		this.filename = filename;
		file = new File(filename);
		setDaemon(true);
		checkAndConfigure();
	}

	public void setDelay(long delay)
	{
		this.delay = delay;
	}

	protected abstract void doOnChange();

	protected void checkAndConfigure()
	{
		boolean fileExists;
		try
		{
			fileExists = file.exists();
		}
		catch (SecurityException e)
		{
			LogLog.warn("Was not allowed to read check file existance, file:[" + filename + "].");
			interrupted = true;
			return;
		}
		if (fileExists)
		{
			long l = file.lastModified();
			if (l > lastModif)
			{
				lastModif = l;
				doOnChange();
				warnedAlready = false;
			}
		} else
		if (!warnedAlready)
		{
			LogLog.debug("[" + filename + "] does not exist.");
			warnedAlready = true;
		}
	}

	public void run()
	{
		while (!interrupted) 
		{
			try
			{
				Thread.sleep(delay);
			}
			catch (InterruptedException e) { }
			checkAndConfigure();
		}
	}
}
