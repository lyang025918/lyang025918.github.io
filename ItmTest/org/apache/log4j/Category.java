// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Category.java

package org.apache.log4j;

import java.text.MessageFormat;
import java.util.*;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.NullEnumeration;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			Appender, Hierarchy, Level, LogManager, 
//			Priority, Logger

public class Category
	implements AppenderAttachable
{

	protected String name;
	protected volatile Level level;
	protected volatile Category parent;
	private static final String FQCN;
	protected ResourceBundle resourceBundle;
	protected LoggerRepository repository;
	AppenderAttachableImpl aai;
	protected boolean additive;

	protected Category(String name)
	{
		additive = true;
		this.name = name;
	}

	public synchronized void addAppender(Appender newAppender)
	{
		if (aai == null)
			aai = new AppenderAttachableImpl();
		aai.addAppender(newAppender);
		repository.fireAddAppenderEvent(this, newAppender);
	}

	public void assertLog(boolean assertion, String msg)
	{
		if (!assertion)
			error(msg);
	}

	public void callAppenders(LoggingEvent event)
	{
		int writes;
		Category c;
		writes = 0;
		c = this;
_L3:
label0:
		{
			if (c == null)
				break; /* Loop/switch isn't completed */
			synchronized (c)
			{
				if (c.aai != null)
					writes += c.aai.appendLoopOnAppenders(event);
				if (c.additive)
					break label0;
			}
			break; /* Loop/switch isn't completed */
		}
		category;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		c = c.parent;
		if (true) goto _L3; else goto _L2
_L2:
		if (writes == 0)
			repository.emitNoAppenderWarning(this);
		return;
	}

	synchronized void closeNestedAppenders()
	{
		Enumeration enumeration = getAllAppenders();
		if (enumeration != null)
			do
			{
				if (!enumeration.hasMoreElements())
					break;
				Appender a = (Appender)enumeration.nextElement();
				if (a instanceof AppenderAttachable)
					a.close();
			} while (true);
	}

	public void debug(Object message)
	{
		if (repository.isDisabled(10000))
			return;
		if (Level.DEBUG.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.DEBUG, message, null);
	}

	public void debug(Object message, Throwable t)
	{
		if (repository.isDisabled(10000))
			return;
		if (Level.DEBUG.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.DEBUG, message, t);
	}

	public void error(Object message)
	{
		if (repository.isDisabled(40000))
			return;
		if (Level.ERROR.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.ERROR, message, null);
	}

	public void error(Object message, Throwable t)
	{
		if (repository.isDisabled(40000))
			return;
		if (Level.ERROR.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.ERROR, message, t);
	}

	/**
	 * @deprecated Method exists is deprecated
	 */

	public static Logger exists(String name)
	{
		return LogManager.exists(name);
	}

	public void fatal(Object message)
	{
		if (repository.isDisabled(50000))
			return;
		if (Level.FATAL.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.FATAL, message, null);
	}

	public void fatal(Object message, Throwable t)
	{
		if (repository.isDisabled(50000))
			return;
		if (Level.FATAL.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.FATAL, message, t);
	}

	protected void forcedLog(String fqcn, Priority level, Object message, Throwable t)
	{
		callAppenders(new LoggingEvent(fqcn, this, level, message, t));
	}

	public boolean getAdditivity()
	{
		return additive;
	}

	public synchronized Enumeration getAllAppenders()
	{
		if (aai == null)
			return NullEnumeration.getInstance();
		else
			return aai.getAllAppenders();
	}

	public synchronized Appender getAppender(String name)
	{
		if (aai == null || name == null)
			return null;
		else
			return aai.getAppender(name);
	}

	public Level getEffectiveLevel()
	{
		for (Category c = this; c != null; c = c.parent)
			if (c.level != null)
				return c.level;

		return null;
	}

	/**
	 * @deprecated Method getChainedPriority is deprecated
	 */

	public Priority getChainedPriority()
	{
		for (Category c = this; c != null; c = c.parent)
			if (c.level != null)
				return c.level;

		return null;
	}

	/**
	 * @deprecated Method getCurrentCategories is deprecated
	 */

	public static Enumeration getCurrentCategories()
	{
		return LogManager.getCurrentLoggers();
	}

	/**
	 * @deprecated Method getDefaultHierarchy is deprecated
	 */

	public static LoggerRepository getDefaultHierarchy()
	{
		return LogManager.getLoggerRepository();
	}

	/**
	 * @deprecated Method getHierarchy is deprecated
	 */

	public LoggerRepository getHierarchy()
	{
		return repository;
	}

	public LoggerRepository getLoggerRepository()
	{
		return repository;
	}

	/**
	 * @deprecated Method getInstance is deprecated
	 */

	public static Category getInstance(String name)
	{
		return LogManager.getLogger(name);
	}

	/**
	 * @deprecated Method getInstance is deprecated
	 */

	public static Category getInstance(Class clazz)
	{
		return LogManager.getLogger(clazz);
	}

	public final String getName()
	{
		return name;
	}

	public final Category getParent()
	{
		return parent;
	}

	public final Level getLevel()
	{
		return level;
	}

	/**
	 * @deprecated Method getPriority is deprecated
	 */

	public final Level getPriority()
	{
		return level;
	}

	/**
	 * @deprecated Method getRoot is deprecated
	 */

	public static final Category getRoot()
	{
		return LogManager.getRootLogger();
	}

	public ResourceBundle getResourceBundle()
	{
		for (Category c = this; c != null; c = c.parent)
			if (c.resourceBundle != null)
				return c.resourceBundle;

		return null;
	}

	protected String getResourceBundleString(String key)
	{
		ResourceBundle rb;
		rb = getResourceBundle();
		if (rb == null)
			return null;
		return rb.getString(key);
		MissingResourceException mre;
		mre;
		error("No resource is associated with key \"" + key + "\".");
		return null;
	}

	public void info(Object message)
	{
		if (repository.isDisabled(20000))
			return;
		if (Level.INFO.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.INFO, message, null);
	}

	public void info(Object message, Throwable t)
	{
		if (repository.isDisabled(20000))
			return;
		if (Level.INFO.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.INFO, message, t);
	}

	public boolean isAttached(Appender appender)
	{
		if (appender == null || aai == null)
			return false;
		else
			return aai.isAttached(appender);
	}

	public boolean isDebugEnabled()
	{
		if (repository.isDisabled(10000))
			return false;
		else
			return Level.DEBUG.isGreaterOrEqual(getEffectiveLevel());
	}

	public boolean isEnabledFor(Priority level)
	{
		if (repository.isDisabled(level.level))
			return false;
		else
			return level.isGreaterOrEqual(getEffectiveLevel());
	}

	public boolean isInfoEnabled()
	{
		if (repository.isDisabled(20000))
			return false;
		else
			return Level.INFO.isGreaterOrEqual(getEffectiveLevel());
	}

	public void l7dlog(Priority priority, String key, Throwable t)
	{
		if (repository.isDisabled(priority.level))
			return;
		if (priority.isGreaterOrEqual(getEffectiveLevel()))
		{
			String msg = getResourceBundleString(key);
			if (msg == null)
				msg = key;
			forcedLog(FQCN, priority, msg, t);
		}
	}

	public void l7dlog(Priority priority, String key, Object params[], Throwable t)
	{
		if (repository.isDisabled(priority.level))
			return;
		if (priority.isGreaterOrEqual(getEffectiveLevel()))
		{
			String pattern = getResourceBundleString(key);
			String msg;
			if (pattern == null)
				msg = key;
			else
				msg = MessageFormat.format(pattern, params);
			forcedLog(FQCN, priority, msg, t);
		}
	}

	public void log(Priority priority, Object message, Throwable t)
	{
		if (repository.isDisabled(priority.level))
			return;
		if (priority.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, priority, message, t);
	}

	public void log(Priority priority, Object message)
	{
		if (repository.isDisabled(priority.level))
			return;
		if (priority.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, priority, message, null);
	}

	public void log(String callerFQCN, Priority level, Object message, Throwable t)
	{
		if (repository.isDisabled(level.level))
			return;
		if (level.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(callerFQCN, level, message, t);
	}

	private void fireRemoveAppenderEvent(Appender appender)
	{
		if (appender != null)
			if (repository instanceof Hierarchy)
				((Hierarchy)repository).fireRemoveAppenderEvent(this, appender);
			else
			if (repository instanceof HierarchyEventListener)
				((HierarchyEventListener)repository).removeAppenderEvent(this, appender);
	}

	public synchronized void removeAllAppenders()
	{
		if (aai != null)
		{
			Vector appenders = new Vector();
			for (Enumeration iter = aai.getAllAppenders(); iter != null && iter.hasMoreElements(); appenders.add(iter.nextElement()));
			aai.removeAllAppenders();
			for (Enumeration iter = appenders.elements(); iter.hasMoreElements(); fireRemoveAppenderEvent((Appender)iter.nextElement()));
			aai = null;
		}
	}

	public synchronized void removeAppender(Appender appender)
	{
		if (appender == null || aai == null)
			return;
		boolean wasAttached = aai.isAttached(appender);
		aai.removeAppender(appender);
		if (wasAttached)
			fireRemoveAppenderEvent(appender);
	}

	public synchronized void removeAppender(String name)
	{
		if (name == null || aai == null)
			return;
		Appender appender = aai.getAppender(name);
		aai.removeAppender(name);
		if (appender != null)
			fireRemoveAppenderEvent(appender);
	}

	public void setAdditivity(boolean additive)
	{
		this.additive = additive;
	}

	final void setHierarchy(LoggerRepository repository)
	{
		this.repository = repository;
	}

	public void setLevel(Level level)
	{
		this.level = level;
	}

	/**
	 * @deprecated Method setPriority is deprecated
	 */

	public void setPriority(Priority priority)
	{
		level = (Level)priority;
	}

	public void setResourceBundle(ResourceBundle bundle)
	{
		resourceBundle = bundle;
	}

	/**
	 * @deprecated Method shutdown is deprecated
	 */

	public static void shutdown()
	{
		LogManager.shutdown();
	}

	public void warn(Object message)
	{
		if (repository.isDisabled(30000))
			return;
		if (Level.WARN.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.WARN, message, null);
	}

	public void warn(Object message, Throwable t)
	{
		if (repository.isDisabled(30000))
			return;
		if (Level.WARN.isGreaterOrEqual(getEffectiveLevel()))
			forcedLog(FQCN, Level.WARN, message, t);
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
		FQCN = (org.apache.log4j.Category.class).getName();
	}
}
