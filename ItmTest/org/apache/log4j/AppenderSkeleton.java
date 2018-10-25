// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AppenderSkeleton.java

package org.apache.log4j;

import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OnlyOnceErrorHandler;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;

// Referenced classes of package org.apache.log4j:
//			Appender, Priority, Layout

public abstract class AppenderSkeleton
	implements Appender, OptionHandler
{

	protected Layout layout;
	protected String name;
	protected Priority threshold;
	protected ErrorHandler errorHandler;
	protected Filter headFilter;
	protected Filter tailFilter;
	protected boolean closed;

	public AppenderSkeleton()
	{
		errorHandler = new OnlyOnceErrorHandler();
		closed = false;
	}

	protected AppenderSkeleton(boolean isActive)
	{
		errorHandler = new OnlyOnceErrorHandler();
		closed = false;
	}

	public void activateOptions()
	{
	}

	public void addFilter(Filter newFilter)
	{
		if (headFilter == null)
		{
			headFilter = tailFilter = newFilter;
		} else
		{
			tailFilter.setNext(newFilter);
			tailFilter = newFilter;
		}
	}

	protected abstract void append(LoggingEvent loggingevent);

	public void clearFilters()
	{
		headFilter = tailFilter = null;
	}

	public void finalize()
	{
		if (closed)
		{
			return;
		} else
		{
			LogLog.debug("Finalizing appender named [" + name + "].");
			close();
			return;
		}
	}

	public ErrorHandler getErrorHandler()
	{
		return errorHandler;
	}

	public Filter getFilter()
	{
		return headFilter;
	}

	public final Filter getFirstFilter()
	{
		return headFilter;
	}

	public Layout getLayout()
	{
		return layout;
	}

	public final String getName()
	{
		return name;
	}

	public Priority getThreshold()
	{
		return threshold;
	}

	public boolean isAsSevereAsThreshold(Priority priority)
	{
		return threshold == null || priority.isGreaterOrEqual(threshold);
	}

	public synchronized void doAppend(LoggingEvent event)
	{
		if (closed)
		{
			LogLog.error("Attempted to append to closed appender named [" + name + "].");
			return;
		}
		if (!isAsSevereAsThreshold(event.getLevel()))
			return;
		Filter f = headFilter;
label0:
		do
		{
			if (f == null)
				break;
			switch (f.decide(event))
			{
			default:
				break;

			case -1: 
				return;

			case 1: // '\001'
				break label0;

			case 0: // '\0'
				f = f.getNext();
				break;
			}
		} while (true);
		append(event);
	}

	public synchronized void setErrorHandler(ErrorHandler eh)
	{
		if (eh == null)
			LogLog.warn("You have tried to set a null error-handler.");
		else
			errorHandler = eh;
	}

	public void setLayout(Layout layout)
	{
		this.layout = layout;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setThreshold(Priority threshold)
	{
		this.threshold = threshold;
	}
}
