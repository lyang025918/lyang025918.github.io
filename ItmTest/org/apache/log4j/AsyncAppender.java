// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AsyncAppender.java

package org.apache.log4j;

import java.text.MessageFormat;
import java.util.*;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			AppenderSkeleton, Appender, Level, Logger

public class AsyncAppender extends AppenderSkeleton
	implements AppenderAttachable
{
	private static class Dispatcher
		implements Runnable
	{

		private final AsyncAppender parent;
		private final List buffer;
		private final Map discardMap;
		private final AppenderAttachableImpl appenders;

		public void run()
		{
			boolean isActive = true;
			try
			{
				do
				{
					if (!isActive)
						break;
					LoggingEvent events[] = null;
					synchronized (buffer)
					{
						int bufferSize = buffer.size();
						for (isActive = !parent.closed; bufferSize == 0 && isActive; isActive = !parent.closed)
						{
							buffer.wait();
							bufferSize = buffer.size();
						}

						if (bufferSize > 0)
						{
							events = new LoggingEvent[bufferSize + discardMap.size()];
							buffer.toArray(events);
							int index = bufferSize;
							for (Iterator iter = discardMap.values().iterator(); iter.hasNext();)
								events[index++] = ((DiscardSummary)iter.next()).createEvent();

							buffer.clear();
							discardMap.clear();
							buffer.notifyAll();
						}
					}
					if (events != null)
					{
						int i = 0;
						while (i < events.length) 
						{
							synchronized (appenders)
							{
								appenders.appendLoopOnAppenders(events[i]);
							}
							i++;
						}
					}
				} while (true);
			}
			catch (InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
		}

		public Dispatcher(AsyncAppender parent, List buffer, Map discardMap, AppenderAttachableImpl appenders)
		{
			this.parent = parent;
			this.buffer = buffer;
			this.appenders = appenders;
			this.discardMap = discardMap;
		}
	}

	private static final class DiscardSummary
	{

		private LoggingEvent maxEvent;
		private int count;

		public void add(LoggingEvent event)
		{
			if (event.getLevel().toInt() > maxEvent.getLevel().toInt())
				maxEvent = event;
			count++;
		}

		public LoggingEvent createEvent()
		{
			String msg = MessageFormat.format("Discarded {0} messages due to full event buffer including: {1}", new Object[] {
				new Integer(count), maxEvent.getMessage()
			});
			return new LoggingEvent("org.apache.log4j.AsyncAppender.DONT_REPORT_LOCATION", Logger.getLogger(maxEvent.getLoggerName()), maxEvent.getLevel(), msg, null);
		}

		public DiscardSummary(LoggingEvent event)
		{
			maxEvent = event;
			count = 1;
		}
	}


	public static final int DEFAULT_BUFFER_SIZE = 128;
	private final List buffer = new ArrayList();
	private final Map discardMap = new HashMap();
	private int bufferSize;
	AppenderAttachableImpl aai;
	private final AppenderAttachableImpl appenders = new AppenderAttachableImpl();
	private final Thread dispatcher;
	private boolean locationInfo;
	private boolean blocking;

	public AsyncAppender()
	{
		bufferSize = 128;
		locationInfo = false;
		blocking = true;
		aai = appenders;
		dispatcher = new Thread(new Dispatcher(this, buffer, discardMap, appenders));
		dispatcher.setDaemon(true);
		dispatcher.setName("AsyncAppender-Dispatcher-" + dispatcher.getName());
		dispatcher.start();
	}

	public void addAppender(Appender newAppender)
	{
		synchronized (appenders)
		{
			appenders.addAppender(newAppender);
		}
	}

	public void append(LoggingEvent event)
	{
		if (dispatcher == null || !dispatcher.isAlive() || bufferSize <= 0)
		{
			synchronized (appenders)
			{
				appenders.appendLoopOnAppenders(event);
			}
			return;
		}
		event.getNDC();
		event.getThreadName();
		event.getMDCCopy();
		if (locationInfo)
			event.getLocationInformation();
		event.getRenderedMessage();
		event.getThrowableStrRep();
		synchronized (buffer)
		{
			do
			{
				int previousSize = buffer.size();
				if (previousSize < bufferSize)
				{
					buffer.add(event);
					if (previousSize == 0)
						buffer.notifyAll();
					break;
				}
				boolean discard = true;
				if (blocking && !Thread.interrupted() && Thread.currentThread() != dispatcher)
					try
					{
						buffer.wait();
						discard = false;
					}
					catch (InterruptedException e)
					{
						Thread.currentThread().interrupt();
					}
				if (!discard)
					continue;
				String loggerName = event.getLoggerName();
				DiscardSummary summary = (DiscardSummary)discardMap.get(loggerName);
				if (summary == null)
				{
					summary = new DiscardSummary(event);
					discardMap.put(loggerName, summary);
				} else
				{
					summary.add(event);
				}
				break;
			} while (true);
		}
	}

	public void close()
	{
		synchronized (buffer)
		{
			closed = true;
			buffer.notifyAll();
		}
		try
		{
			dispatcher.join();
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
			LogLog.error("Got an InterruptedException while waiting for the dispatcher to finish.", e);
		}
		synchronized (appenders)
		{
			Enumeration iter = appenders.getAllAppenders();
			if (iter != null)
				do
				{
					if (!iter.hasMoreElements())
						break;
					Object next = iter.nextElement();
					if (next instanceof Appender)
						((Appender)next).close();
				} while (true);
		}
	}

	public Enumeration getAllAppenders()
	{
		AppenderAttachableImpl appenderattachableimpl = appenders;
		JVM INSTR monitorenter ;
		return appenders.getAllAppenders();
		Exception exception;
		exception;
		throw exception;
	}

	public Appender getAppender(String name)
	{
		AppenderAttachableImpl appenderattachableimpl = appenders;
		JVM INSTR monitorenter ;
		return appenders.getAppender(name);
		Exception exception;
		exception;
		throw exception;
	}

	public boolean getLocationInfo()
	{
		return locationInfo;
	}

	public boolean isAttached(Appender appender)
	{
		AppenderAttachableImpl appenderattachableimpl = appenders;
		JVM INSTR monitorenter ;
		return appenders.isAttached(appender);
		Exception exception;
		exception;
		throw exception;
	}

	public boolean requiresLayout()
	{
		return false;
	}

	public void removeAllAppenders()
	{
		synchronized (appenders)
		{
			appenders.removeAllAppenders();
		}
	}

	public void removeAppender(Appender appender)
	{
		synchronized (appenders)
		{
			appenders.removeAppender(appender);
		}
	}

	public void removeAppender(String name)
	{
		synchronized (appenders)
		{
			appenders.removeAppender(name);
		}
	}

	public void setLocationInfo(boolean flag)
	{
		locationInfo = flag;
	}

	public void setBufferSize(int size)
	{
		if (size < 0)
			throw new NegativeArraySizeException("size");
		synchronized (buffer)
		{
			bufferSize = size >= 1 ? size : 1;
			buffer.notifyAll();
		}
	}

	public int getBufferSize()
	{
		return bufferSize;
	}

	public void setBlocking(boolean value)
	{
		synchronized (buffer)
		{
			blocking = value;
			buffer.notifyAll();
		}
	}

	public boolean getBlocking()
	{
		return blocking;
	}
}
