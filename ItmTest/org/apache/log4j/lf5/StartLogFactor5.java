// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StartLogFactor5.java

package org.apache.log4j.lf5;

import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

// Referenced classes of package org.apache.log4j.lf5:
//			LogLevel, LF5Appender

public class StartLogFactor5
{

	public StartLogFactor5()
	{
	}

	public static final void main(String args[])
	{
		LogBrokerMonitor monitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
		monitor.setFrameSize(LF5Appender.getDefaultMonitorWidth(), LF5Appender.getDefaultMonitorHeight());
		monitor.setFontSize(12);
		monitor.show();
	}
}
