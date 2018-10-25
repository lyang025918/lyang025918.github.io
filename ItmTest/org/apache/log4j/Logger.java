// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Logger.java

package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;

// Referenced classes of package org.apache.log4j:
//			Category, LogManager, Level

public class Logger extends Category
{

	private static final String FQCN;

	protected Logger(String name)
	{
		super(name);
	}

	public static Logger getLogger(String name)
	{
		return LogManager.getLogger(name);
	}

	public static Logger getLogger(Class clazz)
	{
		return LogManager.getLogger(clazz.getName());
	}

	public static Logger getRootLogger()
	{
		return LogManager.getRootLogger();
	}

	public static Logger getLogger(String name, LoggerFactory factory)
	{
		return LogManager.getLogger(name, factory);
	}

	public void trace(Object message)
	{
		if (repository.isDisabled(5000))
			return;
		if (Level.TRACE.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.TRACE, message, null);
	}

	public void trace(Object message, Throwable t)
	{
		if (repository.isDisabled(5000))
			return;
		if (Level.TRACE.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.TRACE, message, t);
	}

	public boolean isTraceEnabled()
	{
		if (repository.isDisabled(5000))
			return false;
		else
			return Level.TRACE.isGreaterOrEqual(getEffectiveLevel());
	}

	static Class class$(String x0)
	{
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw (new NoClassDefFoundError()).initCause(x1);
	}

	static 
	{
		FQCN = (org.apache.log4j.Logger.class).getName();
	}
}
