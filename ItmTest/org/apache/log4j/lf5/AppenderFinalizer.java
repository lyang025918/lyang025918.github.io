// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AppenderFinalizer.java

package org.apache.log4j.lf5;

import java.io.PrintStream;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

public class AppenderFinalizer
{

	protected LogBrokerMonitor _defaultMonitor;

	public AppenderFinalizer(LogBrokerMonitor defaultMonitor)
	{
		_defaultMonitor = null;
		_defaultMonitor = defaultMonitor;
	}

	protected void finalize()
		throws Throwable
	{
		System.out.println("Disposing of the default LogBrokerMonitor instance");
		_defaultMonitor.dispose();
	}
}
