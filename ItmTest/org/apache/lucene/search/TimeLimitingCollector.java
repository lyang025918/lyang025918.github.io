// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TimeLimitingCollector.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Counter;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.search:
//			Collector, Scorer

public class TimeLimitingCollector extends Collector
{
	public static final class TimerThread extends Thread
	{

		public static final String THREAD_NAME = "TimeLimitedCollector timer thread";
		public static final int DEFAULT_RESOLUTION = 20;
		private volatile long time;
		private volatile boolean stop;
		private volatile long resolution;
		final Counter counter;

		public void run()
		{
			while (!stop) 
			{
				counter.addAndGet(resolution);
				try
				{
					Thread.sleep(resolution);
				}
				catch (InterruptedException ie)
				{
					throw new ThreadInterruptedException(ie);
				}
			}
		}

		public long getMilliseconds()
		{
			return time;
		}

		public void stopTimer()
		{
			stop = true;
		}

		public long getResolution()
		{
			return resolution;
		}

		public void setResolution(long resolution)
		{
			this.resolution = Math.max(resolution, 5L);
		}

		public TimerThread(long resolution, Counter counter)
		{
			super("TimeLimitedCollector timer thread");
			time = 0L;
			stop = false;
			this.resolution = resolution;
			this.counter = counter;
			setDaemon(true);
		}

		public TimerThread(Counter counter)
		{
			this(20L, counter);
		}
	}

	private static final class TimerThreadHolder
	{

		static final TimerThread THREAD;

		static 
		{
			THREAD = new TimerThread(Counter.newCounter(true));
			THREAD.start();
		}

		private TimerThreadHolder()
		{
		}
	}

	public static class TimeExceededException extends RuntimeException
	{

		private long timeAllowed;
		private long timeElapsed;
		private int lastDocCollected;

		public long getTimeAllowed()
		{
			return timeAllowed;
		}

		public long getTimeElapsed()
		{
			return timeElapsed;
		}

		public int getLastDocCollected()
		{
			return lastDocCollected;
		}

		private TimeExceededException(long timeAllowed, long timeElapsed, int lastDocCollected)
		{
			super((new StringBuilder()).append("Elapsed time: ").append(timeElapsed).append("Exceeded allowed search time: ").append(timeAllowed).append(" ms.").toString());
			this.timeAllowed = timeAllowed;
			this.timeElapsed = timeElapsed;
			this.lastDocCollected = lastDocCollected;
		}

		TimeExceededException(long x0, long x1, int x2, 1 x3)
		{
			this(x0, x1, x2);
		}
	}


	private long t0;
	private long timeout;
	private Collector collector;
	private final Counter clock;
	private final long ticksAllowed;
	private boolean greedy;
	private int docBase;

	public TimeLimitingCollector(Collector collector, Counter clock, long ticksAllowed)
	{
		t0 = 0x8000000000000000L;
		timeout = 0x8000000000000000L;
		greedy = false;
		this.collector = collector;
		this.clock = clock;
		this.ticksAllowed = ticksAllowed;
	}

	public void setBaseline(long clockTime)
	{
		t0 = clockTime;
		timeout = t0 + ticksAllowed;
	}

	public void setBaseline()
	{
		setBaseline(clock.get());
	}

	public boolean isGreedy()
	{
		return greedy;
	}

	public void setGreedy(boolean greedy)
	{
		this.greedy = greedy;
	}

	public void collect(int doc)
		throws IOException
	{
		long time = clock.get();
		if (timeout < time)
		{
			if (greedy)
				collector.collect(doc);
			throw new TimeExceededException(timeout - t0, time - t0, docBase + doc);
		} else
		{
			collector.collect(doc);
			return;
		}
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		collector.setNextReader(context);
		docBase = context.docBase;
		if (0x8000000000000000L == t0)
			setBaseline();
	}

	public void setScorer(Scorer scorer)
		throws IOException
	{
		collector.setScorer(scorer);
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return collector.acceptsDocsOutOfOrder();
	}

	public void setCollector(Collector collector)
	{
		this.collector = collector;
	}

	public static Counter getGlobalCounter()
	{
		return TimerThreadHolder.THREAD.counter;
	}

	public static TimerThread getGlobalTimerThread()
	{
		return TimerThreadHolder.THREAD;
	}
}
