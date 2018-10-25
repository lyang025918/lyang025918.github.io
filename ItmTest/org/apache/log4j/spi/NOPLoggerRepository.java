// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NOPLoggerRepository.java

package org.apache.log4j.spi;

import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.*;

// Referenced classes of package org.apache.log4j.spi:
//			NOPLogger, LoggerRepository, HierarchyEventListener, LoggerFactory

public final class NOPLoggerRepository
	implements LoggerRepository
{

	public NOPLoggerRepository()
	{
	}

	public void addHierarchyEventListener(HierarchyEventListener hierarchyeventlistener)
	{
	}

	public boolean isDisabled(int level)
	{
		return true;
	}

	public void setThreshold(Level level1)
	{
	}

	public void setThreshold(String s)
	{
	}

	public void emitNoAppenderWarning(Category category)
	{
	}

	public Level getThreshold()
	{
		return Level.OFF;
	}

	public Logger getLogger(String name)
	{
		return new NOPLogger(this, name);
	}

	public Logger getLogger(String name, LoggerFactory factory)
	{
		return new NOPLogger(this, name);
	}

	public Logger getRootLogger()
	{
		return new NOPLogger(this, "root");
	}

	public Logger exists(String name)
	{
		return null;
	}

	public void shutdown()
	{
	}

	public Enumeration getCurrentLoggers()
	{
		return (new Vector()).elements();
	}

	public Enumeration getCurrentCategories()
	{
		return getCurrentLoggers();
	}

	public void fireAddAppenderEvent(Category category, Appender appender1)
	{
	}

	public void resetConfiguration()
	{
	}
}
