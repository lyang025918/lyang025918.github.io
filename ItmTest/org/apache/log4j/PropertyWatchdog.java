// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyConfigurator.java

package org.apache.log4j;

import org.apache.log4j.helpers.FileWatchdog;

// Referenced classes of package org.apache.log4j:
//			PropertyConfigurator, LogManager

class PropertyWatchdog extends FileWatchdog
{

	PropertyWatchdog(String filename)
	{
		super(filename);
	}

	public void doOnChange()
	{
		(new PropertyConfigurator()).doConfigure(filename, LogManager.getLoggerRepository());
	}
}
