// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConcurrentMergeScheduler.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			MergeScheduler, IndexWriter, MergePolicy

public class ConcurrentMergeScheduler extends MergeScheduler
{
	protected class MergeThread extends Thread
	{

		IndexWriter tWriter;
		MergePolicy.OneMerge startMerge;
		MergePolicy.OneMerge runningMerge;
		private volatile boolean done;
		final ConcurrentMergeScheduler this$0;

		public synchronized void setRunningMerge(MergePolicy.OneMerge merge)
		{
			runningMerge = merge;
		}

		public synchronized MergePolicy.OneMerge getRunningMerge()
		{
			return runningMerge;
		}

		public synchronized MergePolicy.OneMerge getCurrentMerge()
		{
			if (done)
				return null;
			if (runningMerge != null)
				return runningMerge;
			else
				return startMerge;
		}

		public void setThreadPriority(int pri)
		{
			try
			{
				setPriority(pri);
			}
			catch (NullPointerException npe) { }
			catch (SecurityException se) { }
		}

		public void run()
		{
			MergePolicy.OneMerge merge = startMerge;
			if (verbose())
				message("  merge thread: start");
			do
			{
				setRunningMerge(merge);
				doMerge(merge);
				merge = tWriter.getNextMerge();
				if (merge == null)
					break;
				tWriter.mergeInit(merge);
				updateMergeThreads();
				if (verbose())
					message((new StringBuilder()).append("  merge thread: do another merge ").append(tWriter.segString(merge.segments)).toString());
			} while (true);
			if (verbose())
				message("  merge thread: done");
			done = true;
			synchronized (ConcurrentMergeScheduler.this)
			{
				updateMergeThreads();
				notifyAll();
			}
			break MISSING_BLOCK_LABEL_284;
			Throwable exc;
			exc;
			if (!(exc instanceof MergePolicy.MergeAbortedException) && !suppressExceptions)
				handleMergeException(exc);
			done = true;
			synchronized (ConcurrentMergeScheduler.this)
			{
				updateMergeThreads();
				notifyAll();
			}
			break MISSING_BLOCK_LABEL_284;
			Exception exception2;
			exception2;
			done = true;
			synchronized (ConcurrentMergeScheduler.this)
			{
				updateMergeThreads();
				notifyAll();
			}
			throw exception2;
		}

		public MergeThread(IndexWriter writer, MergePolicy.OneMerge startMerge)
		{
			this$0 = ConcurrentMergeScheduler.this;
			super();
			tWriter = writer;
			this.startMerge = startMerge;
		}
	}


	private int mergeThreadPriority;
	protected List mergeThreads;
	private int maxThreadCount;
	private int maxMergeCount;
	protected Directory dir;
	protected IndexWriter writer;
	protected int mergeThreadCount;
	protected static final Comparator compareByMergeDocCount = new Comparator() {

		public int compare(MergeThread t1, MergeThread t2)
		{
			MergePolicy.OneMerge m1 = t1.getCurrentMerge();
			MergePolicy.OneMerge m2 = t2.getCurrentMerge();
			int c1 = m1 != null ? m1.totalDocCount : 0x7fffffff;
			int c2 = m2 != null ? m2.totalDocCount : 0x7fffffff;
			return c2 - c1;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((MergeThread)x0, (MergeThread)x1);
		}

	};
	private boolean suppressExceptions;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/ConcurrentMergeScheduler.desiredAssertionStatus();

	public ConcurrentMergeScheduler()
	{
		mergeThreadPriority = -1;
		mergeThreads = new ArrayList();
		maxThreadCount = Math.max(1, Math.min(3, Runtime.getRuntime().availableProcessors() / 2));
		maxMergeCount = maxThreadCount + 2;
	}

	public void setMaxThreadCount(int count)
	{
		if (count < 1)
			throw new IllegalArgumentException("count should be at least 1");
		if (count > maxMergeCount)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("count should be <= maxMergeCount (= ").append(maxMergeCount).append(")").toString());
		} else
		{
			maxThreadCount = count;
			return;
		}
	}

	public int getMaxThreadCount()
	{
		return maxThreadCount;
	}

	public void setMaxMergeCount(int count)
	{
		if (count < 1)
			throw new IllegalArgumentException("count should be at least 1");
		if (count < maxThreadCount)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("count should be >= maxThreadCount (= ").append(maxThreadCount).append(")").toString());
		} else
		{
			maxMergeCount = count;
			return;
		}
	}

	public int getMaxMergeCount()
	{
		return maxMergeCount;
	}

	public synchronized int getMergeThreadPriority()
	{
		initMergeThreadPriority();
		return mergeThreadPriority;
	}

	public synchronized void setMergeThreadPriority(int pri)
	{
		if (pri > 10 || pri < 1)
		{
			throw new IllegalArgumentException("priority must be in range 1 .. 10 inclusive");
		} else
		{
			mergeThreadPriority = pri;
			updateMergeThreads();
			return;
		}
	}

	protected synchronized void updateMergeThreads()
	{
		List activeMerges = new ArrayList();
		for (int threadIdx = 0; threadIdx < mergeThreads.size();)
		{
			MergeThread mergeThread = (MergeThread)mergeThreads.get(threadIdx);
			if (!mergeThread.isAlive())
			{
				mergeThreads.remove(threadIdx);
			} else
			{
				if (mergeThread.getCurrentMerge() != null)
					activeMerges.add(mergeThread);
				threadIdx++;
			}
		}

		CollectionUtil.mergeSort(activeMerges, compareByMergeDocCount);
		int pri = mergeThreadPriority;
		int activeMergeCount = activeMerges.size();
		for (int threadIdx = 0; threadIdx < activeMergeCount; threadIdx++)
		{
			MergeThread mergeThread = (MergeThread)activeMerges.get(threadIdx);
			MergePolicy.OneMerge merge = mergeThread.getCurrentMerge();
			if (merge == null)
				continue;
			boolean doPause = threadIdx < activeMergeCount - maxThreadCount;
			if (verbose() && doPause != merge.getPause())
				if (doPause)
					message((new StringBuilder()).append("pause thread ").append(mergeThread.getName()).toString());
				else
					message((new StringBuilder()).append("unpause thread ").append(mergeThread.getName()).toString());
			if (doPause != merge.getPause())
				merge.setPause(doPause);
			if (doPause)
				continue;
			if (verbose())
				message((new StringBuilder()).append("set priority of merge thread ").append(mergeThread.getName()).append(" to ").append(pri).toString());
			mergeThread.setThreadPriority(pri);
			pri = Math.min(10, 1 + pri);
		}

	}

	protected boolean verbose()
	{
		return writer != null && writer.infoStream.isEnabled("CMS");
	}

	protected void message(String message)
	{
		writer.infoStream.message("CMS", message);
	}

	private synchronized void initMergeThreadPriority()
	{
		if (mergeThreadPriority == -1)
		{
			mergeThreadPriority = 1 + Thread.currentThread().getPriority();
			if (mergeThreadPriority > 10)
				mergeThreadPriority = 10;
		}
	}

	public void close()
	{
		sync();
	}

	public void sync()
	{
		boolean interrupted = false;
		do
		{
			MergeThread toSync = null;
			synchronized (this)
			{
				Iterator i$ = mergeThreads.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					MergeThread t = (MergeThread)i$.next();
					if (!t.isAlive())
						continue;
					toSync = t;
					break;
				} while (true);
			}
			if (toSync == null)
				break;
			try
			{
				toSync.join();
			}
			catch (InterruptedException ie)
			{
				interrupted = true;
			}
		} while (true);
		if (interrupted)
			Thread.currentThread().interrupt();
		break MISSING_BLOCK_LABEL_115;
		Exception exception1;
		exception1;
		if (interrupted)
			Thread.currentThread().interrupt();
		throw exception1;
	}

	protected synchronized int mergeThreadCount()
	{
		int count = 0;
		Iterator i$ = mergeThreads.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			MergeThread mt = (MergeThread)i$.next();
			if (mt.isAlive() && mt.getCurrentMerge() != null)
				count++;
		} while (true);
		return count;
	}

	public void merge(IndexWriter writer)
		throws IOException
	{
		if (!$assertionsDisabled && Thread.holdsLock(writer))
			throw new AssertionError();
		this.writer = writer;
		initMergeThreadPriority();
		dir = writer.getDirectory();
		if (verbose())
		{
			message("now merge");
			message((new StringBuilder()).append("  index: ").append(writer.segString()).toString());
		}
_L2:
		MergePolicy.OneMerge merge;
		boolean success;
		synchronized (this)
		{
			long startStallTime = 0L;
			while (mergeThreadCount() >= 1 + maxMergeCount) 
			{
				startStallTime = System.currentTimeMillis();
				if (verbose())
					message("    too many merges; stalling...");
				try
				{
					wait();
				}
				catch (InterruptedException ie)
				{
					throw new ThreadInterruptedException(ie);
				}
			}
			if (verbose() && startStallTime != 0L)
				message((new StringBuilder()).append("  stalled for ").append(System.currentTimeMillis() - startStallTime).append(" msec").toString());
		}
		merge = writer.getNextMerge();
		if (merge == null)
		{
			if (verbose())
				message("  no more merges pending; now return");
			return;
		}
		writer.mergeInit(merge);
		success = false;
		synchronized (this)
		{
			if (verbose())
				message((new StringBuilder()).append("  consider merge ").append(writer.segString(merge.segments)).toString());
			MergeThread merger = getMergeThread(writer, merge);
			mergeThreads.add(merger);
			if (verbose())
				message((new StringBuilder()).append("    launch new thread [").append(merger.getName()).append("]").toString());
			merger.start();
			updateMergeThreads();
			success = true;
		}
		if (!success)
			writer.mergeFinish(merge);
		if (true) goto _L2; else goto _L1
_L1:
		Exception exception2;
		exception2;
		if (!success)
			writer.mergeFinish(merge);
		throw exception2;
	}

	protected void doMerge(MergePolicy.OneMerge merge)
		throws IOException
	{
		writer.merge(merge);
	}

	protected synchronized MergeThread getMergeThread(IndexWriter writer, MergePolicy.OneMerge merge)
		throws IOException
	{
		MergeThread thread = new MergeThread(writer, merge);
		thread.setThreadPriority(mergeThreadPriority);
		thread.setDaemon(true);
		thread.setName((new StringBuilder()).append("Lucene Merge Thread #").append(mergeThreadCount++).toString());
		return thread;
	}

	protected void handleMergeException(Throwable exc)
	{
		try
		{
			Thread.sleep(250L);
		}
		catch (InterruptedException ie)
		{
			throw new ThreadInterruptedException(ie);
		}
		throw new MergePolicy.MergeException(exc, dir);
	}

	void setSuppressExceptions()
	{
		suppressExceptions = true;
	}

	void clearSuppressExceptions()
	{
		suppressExceptions = false;
	}


}
