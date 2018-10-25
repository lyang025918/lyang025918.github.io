// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearcherLifetimeManager.java

package org.apache.lucene.search;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.search:
//			IndexSearcher

public class SearcherLifetimeManager
	implements Closeable
{
	public static final class PruneByAge
		implements Pruner
	{

		private final double maxAgeSec;

		public boolean doPrune(double ageSec, IndexSearcher searcher)
		{
			return ageSec > maxAgeSec;
		}

		public PruneByAge(double maxAgeSec)
		{
			if (maxAgeSec < 0.0D)
			{
				throw new IllegalArgumentException((new StringBuilder()).append("maxAgeSec must be > 0 (got ").append(maxAgeSec).append(")").toString());
			} else
			{
				this.maxAgeSec = maxAgeSec;
				return;
			}
		}
	}

	public static interface Pruner
	{

		public abstract boolean doPrune(double d, IndexSearcher indexsearcher);
	}

	private static class SearcherTracker
		implements Comparable, Closeable
	{

		public final IndexSearcher searcher;
		public final double recordTimeSec = (double)System.nanoTime() / 1000000000D;
		public final long version;

		public int compareTo(SearcherTracker other)
		{
			if (recordTimeSec < other.recordTimeSec)
				return 1;
			return other.recordTimeSec >= recordTimeSec ? 0 : -1;
		}

		public synchronized void close()
			throws IOException
		{
			searcher.getIndexReader().decRef();
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((SearcherTracker)x0);
		}

		public SearcherTracker(IndexSearcher searcher)
		{
			this.searcher = searcher;
			version = ((DirectoryReader)searcher.getIndexReader()).getVersion();
			searcher.getIndexReader().incRef();
		}
	}


	static final double NANOS_PER_SEC = 1000000000D;
	private volatile boolean closed;
	private final ConcurrentHashMap searchers = new ConcurrentHashMap();

	public SearcherLifetimeManager()
	{
	}

	private void ensureOpen()
	{
		if (closed)
			throw new AlreadyClosedException("this SearcherLifetimeManager instance is closed");
		else
			return;
	}

	public long record(IndexSearcher searcher)
		throws IOException
	{
		ensureOpen();
		long version = ((DirectoryReader)searcher.getIndexReader()).getVersion();
		SearcherTracker tracker = (SearcherTracker)searchers.get(Long.valueOf(version));
		if (tracker == null)
		{
			tracker = new SearcherTracker(searcher);
			if (searchers.putIfAbsent(Long.valueOf(version), tracker) != null)
				tracker.close();
		} else
		if (tracker.searcher != searcher)
			throw new IllegalArgumentException((new StringBuilder()).append("the provided searcher has the same underlying reader version yet the searcher instance differs from before (new=").append(searcher).append(" vs old=").append(tracker.searcher).toString());
		return version;
	}

	public IndexSearcher acquire(long version)
	{
		ensureOpen();
		SearcherTracker tracker = (SearcherTracker)searchers.get(Long.valueOf(version));
		if (tracker != null && tracker.searcher.getIndexReader().tryIncRef())
			return tracker.searcher;
		else
			return null;
	}

	public void release(IndexSearcher s)
		throws IOException
	{
		s.getIndexReader().decRef();
	}

	public synchronized void prune(Pruner pruner)
		throws IOException
	{
		List trackers = new ArrayList();
		SearcherTracker tracker;
		for (Iterator i$ = searchers.values().iterator(); i$.hasNext(); trackers.add(tracker))
			tracker = (SearcherTracker)i$.next();

		Collections.sort(trackers);
		double lastRecordTimeSec = 0.0D;
		double now = (double)System.nanoTime() / 1000000000D;
		for (Iterator i$ = trackers.iterator(); i$.hasNext();)
		{
			SearcherTracker tracker = (SearcherTracker)i$.next();
			double ageSec;
			if (lastRecordTimeSec == 0.0D)
				ageSec = 0.0D;
			else
				ageSec = now - lastRecordTimeSec;
			if (pruner.doPrune(ageSec, tracker.searcher))
			{
				searchers.remove(Long.valueOf(tracker.version));
				tracker.close();
			}
			lastRecordTimeSec = tracker.recordTimeSec;
		}

	}

	public synchronized void close()
		throws IOException
	{
		closed = true;
		List toClose = new ArrayList(searchers.values());
		SearcherTracker tracker;
		for (Iterator i$ = toClose.iterator(); i$.hasNext(); searchers.remove(Long.valueOf(tracker.version)))
			tracker = (SearcherTracker)i$.next();

		IOUtils.close(toClose);
		if (searchers.size() != 0)
			throw new IllegalStateException("another thread called record while this SearcherLifetimeManager instance was being closed; not all searchers were closed");
		else
			return;
	}
}
