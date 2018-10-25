// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NRTManager.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.search:
//			ReferenceManager, SearcherFactory, IndexSearcher, SearcherManager, 
//			Query

public class NRTManager extends ReferenceManager
{
	public static class TrackingIndexWriter
	{

		private final IndexWriter writer;
		private final AtomicLong indexingGen = new AtomicLong(1L);

		public long updateDocument(Term t, Iterable d, Analyzer a)
			throws IOException
		{
			writer.updateDocument(t, d, a);
			return indexingGen.get();
		}

		public long updateDocument(Term t, Iterable d)
			throws IOException
		{
			writer.updateDocument(t, d);
			return indexingGen.get();
		}

		public long updateDocuments(Term t, Iterable docs, Analyzer a)
			throws IOException
		{
			writer.updateDocuments(t, docs, a);
			return indexingGen.get();
		}

		public long updateDocuments(Term t, Iterable docs)
			throws IOException
		{
			writer.updateDocuments(t, docs);
			return indexingGen.get();
		}

		public long deleteDocuments(Term t)
			throws IOException
		{
			writer.deleteDocuments(t);
			return indexingGen.get();
		}

		public transient long deleteDocuments(Term terms[])
			throws IOException
		{
			writer.deleteDocuments(terms);
			return indexingGen.get();
		}

		public long deleteDocuments(Query q)
			throws IOException
		{
			writer.deleteDocuments(q);
			return indexingGen.get();
		}

		public transient long deleteDocuments(Query queries[])
			throws IOException
		{
			writer.deleteDocuments(queries);
			return indexingGen.get();
		}

		public long deleteAll()
			throws IOException
		{
			writer.deleteAll();
			return indexingGen.get();
		}

		public long addDocument(Iterable d, Analyzer a)
			throws IOException
		{
			writer.addDocument(d, a);
			return indexingGen.get();
		}

		public long addDocuments(Iterable docs, Analyzer a)
			throws IOException
		{
			writer.addDocuments(docs, a);
			return indexingGen.get();
		}

		public long addDocument(Iterable d)
			throws IOException
		{
			writer.addDocument(d);
			return indexingGen.get();
		}

		public long addDocuments(Iterable docs)
			throws IOException
		{
			writer.addDocuments(docs);
			return indexingGen.get();
		}

		public transient long addIndexes(Directory dirs[])
			throws IOException
		{
			writer.addIndexes(dirs);
			return indexingGen.get();
		}

		public transient long addIndexes(IndexReader readers[])
			throws IOException
		{
			writer.addIndexes(readers);
			return indexingGen.get();
		}

		public long getGeneration()
		{
			return indexingGen.get();
		}

		public IndexWriter getIndexWriter()
		{
			return writer;
		}

		long getAndIncrementGeneration()
		{
			return indexingGen.getAndIncrement();
		}

		public long tryDeleteDocument(IndexReader reader, int docID)
			throws IOException
		{
			if (writer.tryDeleteDocument(reader, docID))
				return indexingGen.get();
			else
				return -1L;
		}

		public TrackingIndexWriter(IndexWriter writer)
		{
			this.writer = writer;
		}
	}

	public static interface WaitingListener
	{

		public abstract void waiting(long l);
	}


	private static final long MAX_SEARCHER_GEN = 0x7fffffffffffffffL;
	private final TrackingIndexWriter writer;
	private final List waitingListeners;
	private final ReentrantLock genLock;
	private final Condition newGeneration;
	private final SearcherFactory searcherFactory;
	private volatile long searchingGen;
	private long lastRefreshGen;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/NRTManager.desiredAssertionStatus();

	public NRTManager(TrackingIndexWriter writer, SearcherFactory searcherFactory)
		throws IOException
	{
		this(writer, searcherFactory, true);
	}

	public NRTManager(TrackingIndexWriter writer, SearcherFactory searcherFactory, boolean applyAllDeletes)
		throws IOException
	{
		waitingListeners = new CopyOnWriteArrayList();
		genLock = new ReentrantLock();
		newGeneration = genLock.newCondition();
		this.writer = writer;
		if (searcherFactory == null)
			searcherFactory = new SearcherFactory();
		this.searcherFactory = searcherFactory;
		current = SearcherManager.getSearcher(searcherFactory, DirectoryReader.open(writer.getIndexWriter(), applyAllDeletes));
	}

	protected void decRef(IndexSearcher reference)
		throws IOException
	{
		reference.getIndexReader().decRef();
	}

	protected boolean tryIncRef(IndexSearcher reference)
	{
		return reference.getIndexReader().tryIncRef();
	}

	public void addWaitingListener(WaitingListener l)
	{
		waitingListeners.add(l);
	}

	public void removeWaitingListener(WaitingListener l)
	{
		waitingListeners.remove(l);
	}

	public void waitForGeneration(long targetGen)
	{
		waitForGeneration(targetGen, -1L, TimeUnit.NANOSECONDS);
	}

	public void waitForGeneration(long targetGen, long time, TimeUnit unit)
	{
		long curGen = writer.getGeneration();
		if (targetGen > curGen)
			throw new IllegalArgumentException((new StringBuilder()).append("targetGen=").append(targetGen).append(" was never returned by this NRTManager instance (current gen=").append(curGen).append(")").toString());
		genLock.lockInterruptibly();
		if (targetGen <= searchingGen)
			break MISSING_BLOCK_LABEL_145;
		WaitingListener listener;
		for (Iterator i$ = waitingListeners.iterator(); i$.hasNext(); listener.waiting(targetGen))
			listener = (WaitingListener)i$.next();

		do
			if (targetGen <= searchingGen)
				break MISSING_BLOCK_LABEL_145;
		while (waitOnGenCondition(time, unit));
		genLock.unlock();
		return;
		genLock.unlock();
		break MISSING_BLOCK_LABEL_182;
		Exception exception;
		exception;
		genLock.unlock();
		throw exception;
		InterruptedException ie;
		ie;
		throw new ThreadInterruptedException(ie);
	}

	private boolean waitOnGenCondition(long time, TimeUnit unit)
		throws InterruptedException
	{
		if (!$assertionsDisabled && !genLock.isHeldByCurrentThread())
			throw new AssertionError();
		if (time < 0L)
		{
			newGeneration.await();
			return true;
		} else
		{
			return newGeneration.await(time, unit);
		}
	}

	public long getCurrentSearchingGen()
	{
		return searchingGen;
	}

	protected IndexSearcher refreshIfNeeded(IndexSearcher referenceToRefresh)
		throws IOException
	{
		lastRefreshGen = writer.getAndIncrementGeneration();
		IndexReader r = referenceToRefresh.getIndexReader();
		if (!$assertionsDisabled && !(r instanceof DirectoryReader))
			throw new AssertionError((new StringBuilder()).append("searcher's IndexReader should be a DirectoryReader, but got ").append(r).toString());
		DirectoryReader dirReader = (DirectoryReader)r;
		IndexSearcher newSearcher = null;
		if (!dirReader.isCurrent())
		{
			IndexReader newReader = DirectoryReader.openIfChanged(dirReader);
			if (newReader != null)
				newSearcher = SearcherManager.getSearcher(searcherFactory, newReader);
		}
		return newSearcher;
	}

	protected void afterRefresh()
	{
		genLock.lock();
		if (searchingGen != 0x7fffffffffffffffL)
		{
			if (!$assertionsDisabled && lastRefreshGen < searchingGen)
				throw new AssertionError();
			searchingGen = lastRefreshGen;
		}
		newGeneration.signalAll();
		genLock.unlock();
		break MISSING_BLOCK_LABEL_81;
		Exception exception;
		exception;
		genLock.unlock();
		throw exception;
	}

	protected synchronized void afterClose()
		throws IOException
	{
		genLock.lock();
		searchingGen = 0x7fffffffffffffffL;
		newGeneration.signalAll();
		genLock.unlock();
		break MISSING_BLOCK_LABEL_43;
		Exception exception;
		exception;
		genLock.unlock();
		throw exception;
	}

	public boolean isSearcherCurrent()
		throws IOException
	{
		IndexSearcher searcher = (IndexSearcher)acquire();
		boolean flag;
		IndexReader r = searcher.getIndexReader();
		if (!$assertionsDisabled && !(r instanceof DirectoryReader))
			throw new AssertionError((new StringBuilder()).append("searcher's IndexReader should be a DirectoryReader, but got ").append(r).toString());
		flag = ((DirectoryReader)r).isCurrent();
		release(searcher);
		return flag;
		Exception exception;
		exception;
		release(searcher);
		throw exception;
	}

	protected volatile boolean tryIncRef(Object x0)
	{
		return tryIncRef((IndexSearcher)x0);
	}

	protected volatile Object refreshIfNeeded(Object x0)
		throws IOException
	{
		return refreshIfNeeded((IndexSearcher)x0);
	}

	protected volatile void decRef(Object x0)
		throws IOException
	{
		decRef((IndexSearcher)x0);
	}

}
