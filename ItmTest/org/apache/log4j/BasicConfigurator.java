// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicConfigurator.java

package org.apache.log4j;


// Referenced classes of package org.apache.log4j:
//			ConsoleAppender, PatternLayout, Logger, LogManager, 
//			Appender

public class BasicConfigurator
{

	protected BasicConfigurator()
	{
	}

	public static void configure()
	{
		Logger root = Logger.getRootLogger();
		root.addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
	}

	public static void configure(Appender appender)
	{
		Logger root = Logger.getRootLogger();
		root.addAppender(appender);
	}

	public static void resetConfiguration()
	{
		LogManager.resetConfiguration();
	}
}
