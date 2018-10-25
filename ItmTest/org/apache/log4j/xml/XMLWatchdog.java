// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DOMConfigurator.java

package org.apache.log4j.xml;

import org.apache.log4j.LogManager;
import org.apache.log4j.helpers.FileWatchdog;

// Referenced classes of package org.apache.log4j.xml:
//			DOMConfigurator

class XMLWatchdog extends FileWatchdog
{

	XMLWatchdog(String filename)
	{
		super(filename);
	}

	public void doOnChange()
	{
		(new DOMConfigurator()).doConfigure(filename, LogManager.getLoggerRepository());
	}
}
