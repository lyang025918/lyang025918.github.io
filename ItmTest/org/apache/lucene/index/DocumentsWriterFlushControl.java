// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriterFlushControl.java

package org.apache.lucene.index;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriterStallControl, DocumentsWriterPerThread, DocumentsWriterDeleteQueue, DocumentsWriter, 
//			LiveIndexWriterConfig, FlushPolicy, DocumentsWriterPerThreadPool, IndexWriter, 
//			BufferedDeletesStream

final class DocumentsWriterFlushControl
{
	private static class BlockedFlush
	{

		final DocumentsWriterPerThread dwpt;
		final long bytes;

		BlockedFlush(DocumentsWriterPerThread dwpt, long bytes)
		{
			this.dwpt = dwpt;
			this.bytes = bytes;
		}
	}


	private final long hardMaxBytesPerDWPT;
	private long activeBytes;
	private long flushBytes;
	private volatile int numPending;
	final AtomicBoolean flushDeletes = new AtomicBoolean(false);
	private boolean fullFlush;
	private final Queue flushQueue = new LinkedList();
	private final Queue blockedFlushes = new LinkedList();
	private final IdentityHashMap flushingWriters = new IdentityHashMap();
	double maxConfiguredRamBuffer;
	long peakActiveBytes;
	long peakFlushBytes;
	long peakNetBytes;
	long peakDelta;
	final DocumentsWriterStallControl stallControl = new DocumentsWriterStallControl();
	private final DocumentsWriterPerThreadPool perThreadPool;
	private final FlushPolicy flushPolicy;
	private boolean closed;
	private final DocumentsWriter documentsWriter;
	private final LiveIndexWriterConfig config;
	private final List fullFlushBuffer = new ArrayList();
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterFlushControl.desiredAssertionStatus();

	DocumentsWriterFlushControl(DocumentsWriter documentsWriter, LiveIndexWriterConfig config)
	{
		activeBytes = 0L;
		flushBytes = 0L;
		numPending = 0;
		fullFlush = false;
		maxConfiguredRamBuffer = 0.0D;
		peakActiveBytes = 0L;
		peakFlushBytes = 0L;
		peakNetBytes = 0L;
		peakDelta = 0L;
		closed = false;
		perThreadPool = documentsWriter.perThreadPool;
		flushPolicy = documentsWriter.flushPolicy;
		hardMaxBytesPerDWPT = config.getRAMPerThreadHardLimitMB() * 1024 * 1024;
		this.config = config;
		this.documentsWriter = documentsWriter;
	}

	public synchronized long activeBytes()
	{
		return activeBytes;
	}

	public synchronized long flushBytes()
	{
		return flushBytes;
	}

	public synchronized long netBytes()
	{
		return flushBytes + activeBytes;
	}

	private long stallLimitBytes()
	{
		double maxRamMB = config.getRAMBufferSizeMB();
		return maxRamMB == -1D ? 0x7fffffffffffffffL : (long)(2D * (maxRamMB * 1024D * 1024D));
	}

	private boolean assertMemory()
	{
		double maxRamMB = config.getRAMBufferSizeMB();
		if (maxRamMB != -1D)
		{
			maxConfiguredRamBuffer = Math.max(maxRamMB, maxConfiguredRamBuffer);
			long ram = flushBytes + activeBytes;
			long ramBufferBytes = (long)(maxConfiguredRamBuffer * 1024D * 1024D);
			long expected = 2L * ramBufferBytes + (long)(numPending + numFlushingDWPT() + numBlockedFlushes()) * peakDelta;
			if (peakDelta < ramBufferBytes >> 1 && !$assertionsDisabled && ram > expected)
				throw new AssertionError((new StringBuilder()).append("ram was ").append(ram).append(" expected: ").append(expected).append(" flush mem: ").append(flushBytes).append(" activeMem: ").append(activeBytes).append(" pendingMem: ").append(numPending).append(" flushingMem: ").append(numFlushingDWPT()).append(" blockedMem: ").append(numBlockedFlushes()).append(" peakDeltaMem: ").append(peakDelta).toString());
		}
		return true;
	}

	private void commitPerThreadBytes(DocumentsWriterPerThreadPool.ThreadState perThread)
	{
		long delta = perThread.dwpt.bytesUsed() - perThread.bytesUsed;
		perThread.bytesUsed += delta;
		if (perThread.flushPending)
			flushBytes += delta;
		else
			activeBytes += delta;
		if (!$assertionsDisabled && !updatePeaks(delta))
			throw new AssertionError();
		else
			return;
	}

	private boolean updatePeaks(long delta)
	{
		peakActiveBytes = Math.max(peakActiveBytes, activeBytes);
		peakFlushBytes = Math.max(peakFlushBytes, flushBytes);
		peakNetBytes = Math.max(peakNetBytes, netBytes());
		peakDelta = Math.max(peakDelta, delta);
		return true;
	}

	synchronized DocumentsWriterPerThread doAfterDocument(DocumentsWriterPerThreadPool.ThreadState perThread, boolean isUpdate)
	{
		DocumentsWriterPerThread documentswriterperthread;
		commitPerThreadBytes(perThread);
		if (!perThread.flushPending)
		{
			if (isUpdate)
				flushPolicy.onUpdate(this, perThread);
			else
				flushPolicy.onInsert(this, perThread);
			if (!perThread.flushPending && perThread.bytesUsed > hardMaxBytesPerDWPT)
				setFlushPending(perThread);
		}
		DocumentsWriterPerThread flushingDWPT;
		if (fullFlush)
		{
			if (perThread.flushPending)
			{
				checkoutAndBlock(perThread);
				flushingDWPT = nextPendingFlush();
			} else
			{
				flushingDWPT = null;
			}
		} else
		{
			flushingDWPT = tryCheckoutForFlush(perThread);
		}
		documentswriterperthread = flushingDWPT;
		updateStallState();
		if (!$assertionsDisabled && !assertMemory())
			throw new AssertionError();
		else
			return documentswriterperthread;
		Exception exception;
		exception;
		updateStallState();
		if (!$assertionsDisabled && !assertMemory())
			throw new AssertionError();
		else
			throw exception;
	}

	synchronized void doAfterFlush(DocumentsWriterPerThread dwpt)
	{
		if (!$assertionsDisabled && !flushingWriters.containsKey(dwpt))
			throw new AssertionError();
		Long bytes = (Long)flushingWriters.remove(dwpt);
		flushBytes -= bytes.longValue();
		perThreadPool.recycle(dwpt);
		if (!$assertionsDisabled && !assertMemory())
			throw new AssertionError();
		updateStallState();
		notifyAll();
		break MISSING_BLOCK_LABEL_125;
		Exception exception;
		exception;
		notifyAll();
		throw exception;
		Exception exception1;
		exception1;
		updateStallState();
		notifyAll();
		break MISSING_BLOCK_LABEL_122;
		Exception exception2;
		exception2;
		notifyAll();
		throw exception2;
		throw exception1;
	}

	private final void updateStallState()
	{
		if (!$assertionsDisabled && !Thread.holdsLock(this))
		{
			throw new AssertionError();
		} else
		{
			long limit = stallLimitBytes();
			boolean stall = activeBytes + flushBytes > limit && activeBytes < limit && !closed;
			stallControl.updateStalled(stall);
			return;
		}
	}

	public synchronized void waitForFlush()
	{
		while (flushingWriters.size() != 0) 
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				throw new ThreadInterruptedException(e);
			}
	}

	public synchronized void setFlushPending(DocumentsWriterPerThreadPool.ThreadState perThread)
	{
		if (!$assertionsDisabled && perThread.flushPending)
			throw new AssertionError();
		if (perThread.dwpt.getNumDocsInRAM() > 0)
		{
			perThread.flushPending = true;
			long bytes = perThread.bytesUsed;
			flushBytes += bytes;
			activeBytes -= bytes;
			numPending++;
			if (!$assertionsDisabled && !assertMemory())
				throw new AssertionError();
		}
	}

	synchronized void doOnAbort(DocumentsWriterPerThreadPool.ThreadState state)
	{
		if (state.flushPending)
			flushBytes -= state.bytesUsed;
		else
			activeBytes -= state.bytesUsed;
		if (!$assertionsDisabled && !assertMemory())
			throw new AssertionError();
		perThreadPool.replaceForFlush(state, closed);
		updateStallState();
		break MISSING_BLOCK_LABEL_84;
		Exception exception;
		exception;
		updateStallState();
		throw exception;
	}

	synchronized DocumentsWriterPerThread tryCheckoutForFlush(DocumentsWriterPerThreadPool.ThreadState perThread)
	{
		return perThread.flushPending ? internalTryCheckOutForFlush(perThread) : null;
	}

	private void checkoutAndBlock(DocumentsWriterPerThreadPool.ThreadState perThread)
	{
		perThread.lock();
		if (!$assertionsDisabled && !perThread.flushPending)
			throw new AssertionError("can not block non-pending threadstate");
		if (!$assertionsDisabled && !fullFlush)
			throw new AssertionError("can not block if fullFlush == false");
		long bytes = perThread.bytesUsed;
		DocumentsWriterPerThread dwpt = perThreadPool.replaceForFlush(perThread, closed);
		numPending--;
		blockedFlushes.add(new BlockedFlush(dwpt, bytes));
		perThread.unlock();
		break MISSING_BLOCK_LABEL_113;
		Exception exception;
		exception;
		perThread.unlock();
		throw exception;
	}

	private DocumentsWriterPerThread internalTryCheckOutForFlush(DocumentsWriterPerThreadPool.ThreadState perThread)
	{
		if (!$assertionsDisabled && !Thread.holdsLock(this))
			throw new AssertionError();
		if (!$assertionsDisabled && !perThread.flushPending)
			throw new AssertionError();
		if (!perThread.tryLock())
			break MISSING_BLOCK_LABEL_175;
		DocumentsWriterPerThread documentswriterperthread1;
		if (!perThread.isActive())
			break MISSING_BLOCK_LABEL_159;
		if (!$assertionsDisabled && !perThread.isHeldByCurrentThread())
			throw new AssertionError();
		long bytes = perThread.bytesUsed;
		DocumentsWriterPerThread dwpt = perThreadPool.replaceForFlush(perThread, closed);
		if (!$assertionsDisabled && flushingWriters.containsKey(dwpt))
			throw new AssertionError("DWPT is already flushing");
		flushingWriters.put(dwpt, Long.valueOf(bytes));
		numPending--;
		documentswriterperthread1 = dwpt;
		perThread.unlock();
		updateStallState();
		return documentswriterperthread1;
		perThread.unlock();
		break MISSING_BLOCK_LABEL_175;
		Exception exception;
		exception;
		perThread.unlock();
		throw exception;
		DocumentsWriterPerThread documentswriterperthread = null;
		updateStallState();
		return documentswriterperthread;
		Exception exception1;
		exception1;
		updateStallState();
		throw exception1;
	}

	public String toString()
	{
		return (new StringBuilder()).append("DocumentsWriterFlushControl [activeBytes=").append(activeBytes).append(", flushBytes=").append(flushBytes).append("]").toString();
	}

	DocumentsWriterPerThread nextPendingFlush()
	{
		DocumentsWriterFlushControl documentswriterflushcontrol = this;
		JVM INSTR monitorenter ;
		DocumentsWriterPerThread poll;
		if ((poll = (DocumentsWriterPerThread)flushQueue.poll()) == null)
			break MISSING_BLOCK_LABEL_31;
		updateStallState();
		return poll;
		int numPending;
		boolean fullFlush;
		fullFlush = this.fullFlush;
		numPending = this.numPending;
		documentswriterflushcontrol;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		if (numPending > 0 && !fullFlush)
		{
			int limit = perThreadPool.getActiveThreadState();
			for (int i = 0; i < limit && numPending > 0; i++)
			{
				DocumentsWriterPerThreadPool.ThreadState next = perThreadPool.getThreadState(i);
				if (!next.flushPending)
					continue;
				DocumentsWriterPerThread dwpt = tryCheckoutForFlush(next);
				if (dwpt != null)
					return dwpt;
			}

		}
		return null;
	}

	synchronized void setClosed()
	{
		if (!closed)
		{
			closed = true;
			perThreadPool.deactivateUnreleasedStates();
		}
	}

	public Iterator allActiveThreadStates()
	{
		return getPerThreadsIterator(perThreadPool.getActiveThreadState());
	}

	private Iterator getPerThreadsIterator(final int upto)
	{
		return new Iterator() {

			int i;
			final int val$upto;
			final DocumentsWriterFlushControl this$0;

			public boolean hasNext()
			{
				return i < upto;
			}

			public DocumentsWriterPerThreadPool.ThreadState next()
			{
				return perThreadPool.getThreadState(i++);
			}

			public void remove()
			{
				throw new UnsupportedOperationException("remove() not supported.");
			}

			public volatile Object next()
			{
				return next();
			}

			
			{
				this$0 = DocumentsWriterFlushControl.this;
				upto = j;
				super();
				i = 0;
			}
		};
	}

	synchronized void doOnDelete()
	{
		flushPolicy.onDelete(this, null);
	}

	public int getNumGlobalTermDeletes()
	{
		return documentsWriter.deleteQueue.numGlobalTermDeletes() + documentsWriter.indexWriter.bufferedDeletesStream.numTerms();
	}

	public long getDeleteBytesUsed()
	{
		return documentsWriter.deleteQueue.bytesUsed() + documentsWriter.indexWriter.bufferedDeletesStream.bytesUsed();
	}

	synchronized int numFlushingDWPT()
	{
		return flushingWriters.size();
	}

	public boolean doApplyAllDeletes()
	{
		return flushDeletes.getAndSet(false);
	}

	public void setApplyAllDeletes()
	{
		flushDeletes.set(true);
	}

	int numActiveDWPT()
	{
		return perThreadPool.getActiveThreadState();
	}

	DocumentsWriterPerThreadPool.ThreadState obtainAndLock()
	{
		DocumentsWriterPerThreadPool.ThreadState perThread;
		boolean success;
		perThread = perThreadPool.getAndLock(Thread.currentThread(), documentsWriter);
		success = false;
		DocumentsWriterPerThreadPool.ThreadState threadstate;
		if (perThread.isActive() && perThread.dwpt.deleteQueue != documentsWriter.deleteQueue)
			addFlushableState(perThread);
		success = true;
		threadstate = perThread;
		if (!success)
			perThread.unlock();
		return threadstate;
		Exception exception;
		exception;
		if (!success)
			perThread.unlock();
		throw exception;
	}

	void markForFullFlush()
	{
		DocumentsWriterDeleteQueue flushingQueue;
		int limit;
		int i;
		synchronized (this)
		{
			if (!$assertionsDisabled && fullFlush)
				throw new AssertionError("called DWFC#markForFullFlush() while full flush is still running");
			if (!$assertionsDisabled && !fullFlushBuffer.isEmpty())
				throw new AssertionError((new StringBuilder()).append("full flush buffer should be empty: ").append(fullFlushBuffer).toString());
			fullFlush = true;
			flushingQueue = documentsWriter.deleteQueue;
			DocumentsWriterDeleteQueue newQueue = new DocumentsWriterDeleteQueue(flushingQueue.generation + 1L);
			documentsWriter.deleteQueue = newQueue;
		}
		limit = perThreadPool.getActiveThreadState();
		i = 0;
_L3:
		if (i >= limit) goto _L2; else goto _L1
_L1:
		DocumentsWriterPerThreadPool.ThreadState next;
		next = perThreadPool.getThreadState(i);
		next.lock();
		if (!next.isActive())
		{
			next.unlock();
			continue; /* Loop/switch isn't completed */
		}
		if (!$assertionsDisabled && next.dwpt.deleteQueue != flushingQueue && next.dwpt.deleteQueue != documentsWriter.deleteQueue)
			throw new AssertionError((new StringBuilder()).append(" flushingQueue: ").append(flushingQueue).append(" currentqueue: ").append(documentsWriter.deleteQueue).append(" perThread queue: ").append(next.dwpt.deleteQueue).append(" numDocsInRam: ").append(next.dwpt.getNumDocsInRAM()).toString());
		if (next.dwpt.deleteQueue != flushingQueue)
		{
			next.unlock();
			continue; /* Loop/switch isn't completed */
		}
		addFlushableState(next);
		next.unlock();
		continue; /* Loop/switch isn't completed */
		Exception exception1;
		exception1;
		next.unlock();
		throw exception1;
		i++;
		  goto _L3
_L2:
		synchronized (this)
		{
			pruneBlockedQueue(flushingQueue);
			if (!$assertionsDisabled && !assertBlockedFlushes(documentsWriter.deleteQueue))
				throw new AssertionError();
			flushQueue.addAll(fullFlushBuffer);
			fullFlushBuffer.clear();
			updateStallState();
		}
		if (!$assertionsDisabled && !assertActiveDeleteQueue(documentsWriter.deleteQueue))
			throw new AssertionError();
		else
			return;
	}

	private boolean assertActiveDeleteQueue(DocumentsWriterDeleteQueue queue)
	{
		int limit;
		int i;
		limit = perThreadPool.getActiveThreadState();
		i = 0;
_L2:
		DocumentsWriterPerThreadPool.ThreadState next;
		if (i >= limit)
			break; /* Loop/switch isn't completed */
		next = perThreadPool.getThreadState(i);
		next.lock();
		if (!$assertionsDisabled && next.isActive() && next.dwpt.deleteQueue != queue)
			throw new AssertionError();
		next.unlock();
		break MISSING_BLOCK_LABEL_82;
		Exception exception;
		exception;
		next.unlock();
		throw exception;
		i++;
		if (true) goto _L2; else goto _L1
_L1:
		return true;
	}

	void addFlushableState(DocumentsWriterPerThreadPool.ThreadState perThread)
	{
		if (documentsWriter.infoStream.isEnabled("DWFC"))
			documentsWriter.infoStream.message("DWFC", (new StringBuilder()).append("addFlushableState ").append(perThread.dwpt).toString());
		DocumentsWriterPerThread dwpt = perThread.dwpt;
		if (!$assertionsDisabled && !perThread.isHeldByCurrentThread())
			throw new AssertionError();
		if (!$assertionsDisabled && !perThread.isActive())
			throw new AssertionError();
		if (!$assertionsDisabled && !fullFlush)
			throw new AssertionError();
		if (!$assertionsDisabled && dwpt.deleteQueue == documentsWriter.deleteQueue)
			throw new AssertionError();
		if (dwpt.getNumDocsInRAM() > 0)
			synchronized (this)
			{
				if (!perThread.flushPending)
					setFlushPending(perThread);
				DocumentsWriterPerThread flushingDWPT = internalTryCheckOutForFlush(perThread);
				if (!$assertionsDisabled && flushingDWPT == null)
					throw new AssertionError("DWPT must never be null here since we hold the lock and it holds documents");
				if (!$assertionsDisabled && dwpt != flushingDWPT)
					throw new AssertionError("flushControl returned different DWPT");
				fullFlushBuffer.add(flushingDWPT);
			}
		else
		if (closed)
			perThreadPool.deactivateThreadState(perThread);
		else
			perThreadPool.reinitThreadState(perThread);
	}

	private void pruneBlockedQueue(DocumentsWriterDeleteQueue flushingQueue)
	{
		Iterator iterator = blockedFlushes.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			BlockedFlush blockedFlush = (BlockedFlush)iterator.next();
			if (blockedFlush.dwpt.deleteQueue == flushingQueue)
			{
				iterator.remove();
				if (!$assertionsDisabled && flushingWriters.containsKey(blockedFlush.dwpt))
					throw new AssertionError("DWPT is already flushing");
				flushingWriters.put(blockedFlush.dwpt, Long.valueOf(blockedFlush.bytes));
				flushQueue.add(blockedFlush.dwpt);
			}
		} while (true);
	}

	synchronized void finishFullFlush()
	{
		if (!$assertionsDisabled && !fullFlush)
			throw new AssertionError();
		if (!$assertionsDisabled && !flushQueue.isEmpty())
			throw new AssertionError();
		if (!$assertionsDisabled && !flushingWriters.isEmpty())
			throw new AssertionError();
		if (!blockedFlushes.isEmpty())
		{
			if (!$assertionsDisabled && !assertBlockedFlushes(documentsWriter.deleteQueue))
				throw new AssertionError();
			pruneBlockedQueue(documentsWriter.deleteQueue);
			if (!$assertionsDisabled && !blockedFlushes.isEmpty())
				throw new AssertionError();
		}
		fullFlush = false;
		updateStallState();
		break MISSING_BLOCK_LABEL_172;
		Exception exception;
		exception;
		fullFlush = false;
		updateStallState();
		throw exception;
	}

	boolean assertBlockedFlushes(DocumentsWriterDeleteQueue flushingQueue)
	{
		for (Iterator i$ = blockedFlushes.iterator(); i$.hasNext();)
		{
			BlockedFlush blockedFlush = (BlockedFlush)i$.next();
			if (!$assertionsDisabled && blockedFlush.dwpt.deleteQueue != flushingQueue)
				throw new AssertionError();
		}

		return true;
	}

	synchronized void abortFullFlushes()
	{
		abortPendingFlushes();
		fullFlush = false;
		break MISSING_BLOCK_LABEL_20;
		Exception exception;
		exception;
		fullFlush = false;
		throw exception;
	}

	synchronized void abortPendingFlushes()
	{
		for (Iterator i$ = flushQueue.iterator(); i$.hasNext();)
		{
			DocumentsWriterPerThread dwpt = (DocumentsWriterPerThread)i$.next();
			try
			{
				dwpt.abort();
				doAfterFlush(dwpt);
			}
			catch (Throwable ex) { }
		}

		for (Iterator i$ = blockedFlushes.iterator(); i$.hasNext();)
		{
			BlockedFlush blockedFlush = (BlockedFlush)i$.next();
			try
			{
				flushingWriters.put(blockedFlush.dwpt, Long.valueOf(blockedFlush.bytes));
				blockedFlush.dwpt.abort();
				doAfterFlush(blockedFlush.dwpt);
			}
			catch (Throwable ex) { }
		}

		flushQueue.clear();
		blockedFlushes.clear();
		updateStallState();
		break MISSING_BLOCK_LABEL_167;
		Exception exception;
		exception;
		flushQueue.clear();
		blockedFlushes.clear();
		updateStallState();
		throw exception;
	}

	synchronized boolean isFullFlush()
	{
		return fullFlush;
	}

	synchronized int numQueuedFlushes()
	{
		return flushQueue.size();
	}

	synchronized int numBlockedFlushes()
	{
		return blockedFlushes.size();
	}

	void waitIfStalled()
	{
		if (documentsWriter.infoStream.isEnabled("DWFC"))
			documentsWriter.infoStream.message("DWFC", (new StringBuilder()).append("waitIfStalled: numFlushesPending: ").append(flushQueue.size()).append(" netBytes: ").append(netBytes()).append(" flushBytes: ").append(flushBytes()).append(" fullFlush: ").append(fullFlush).toString());
		stallControl.waitIfStalled();
	}

	boolean anyStalledThreads()
	{
		return stallControl.anyStalledThreads();
	}


}
