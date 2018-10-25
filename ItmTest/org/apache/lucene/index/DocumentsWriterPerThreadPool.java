// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriterPerThreadPool.java

package org.apache.lucene.index;

import java.util.concurrent.locks.ReentrantLock;
import org.apache.lucene.util.SetOnce;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriterPerThread, FieldInfos, DocumentsWriter, LiveIndexWriterConfig

abstract class DocumentsWriterPerThreadPool
	implements Cloneable
{
	static final class ThreadState extends ReentrantLock
	{

		DocumentsWriterPerThread dwpt;
		volatile boolean flushPending;
		long bytesUsed;
		private boolean isActive;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterPerThreadPool.desiredAssertionStatus();

		private void resetWriter(DocumentsWriterPerThread dwpt)
		{
			if (!$assertionsDisabled && !isHeldByCurrentThread())
				throw new AssertionError();
			if (dwpt == null)
				isActive = false;
			this.dwpt = dwpt;
			bytesUsed = 0L;
			flushPending = false;
		}

		boolean isActive()
		{
			if (!$assertionsDisabled && !isHeldByCurrentThread())
				throw new AssertionError();
			else
				return isActive;
		}

		public long getBytesUsedPerThread()
		{
			if (!$assertionsDisabled && !isHeldByCurrentThread())
				throw new AssertionError();
			else
				return bytesUsed;
		}

		public DocumentsWriterPerThread getDocumentsWriterPerThread()
		{
			if (!$assertionsDisabled && !isHeldByCurrentThread())
				throw new AssertionError();
			else
				return dwpt;
		}

		public boolean isFlushPending()
		{
			return flushPending;
		}




		ThreadState(DocumentsWriterPerThread dpwt)
		{
			flushPending = false;
			bytesUsed = 0L;
			isActive = true;
			dwpt = dpwt;
		}
	}


	private ThreadState threadStates[];
	private volatile int numThreadStatesActive;
	private SetOnce globalFieldMap;
	private SetOnce documentsWriter;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterPerThreadPool.desiredAssertionStatus();

	DocumentsWriterPerThreadPool(int maxNumThreadStates)
	{
		globalFieldMap = new SetOnce();
		documentsWriter = new SetOnce();
		if (maxNumThreadStates < 1)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("maxNumThreadStates must be >= 1 but was: ").append(maxNumThreadStates).toString());
		} else
		{
			threadStates = new ThreadState[maxNumThreadStates];
			numThreadStatesActive = 0;
			return;
		}
	}

	void initialize(DocumentsWriter documentsWriter, FieldInfos.FieldNumbers globalFieldMap, LiveIndexWriterConfig config)
	{
		this.documentsWriter.set(documentsWriter);
		this.globalFieldMap.set(globalFieldMap);
		for (int i = 0; i < threadStates.length; i++)
		{
			FieldInfos.Builder infos = new FieldInfos.Builder(globalFieldMap);
			threadStates[i] = new ThreadState(new DocumentsWriterPerThread(documentsWriter.directory, documentsWriter, infos, documentsWriter.chain));
		}

	}

	public DocumentsWriterPerThreadPool clone()
	{
		if (!$assertionsDisabled && numThreadStatesActive != 0)
			throw new AssertionError();
		DocumentsWriterPerThreadPool clone;
		try
		{
			clone = (DocumentsWriterPerThreadPool)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
		clone.documentsWriter = new SetOnce();
		clone.globalFieldMap = new SetOnce();
		clone.threadStates = new ThreadState[threadStates.length];
		return clone;
	}

	int getMaxThreadStates()
	{
		return threadStates.length;
	}

	int getActiveThreadState()
	{
		return numThreadStatesActive;
	}

	synchronized ThreadState newThreadState()
	{
		ThreadState threadState;
		boolean unlock;
		if (numThreadStatesActive >= threadStates.length)
			break MISSING_BLOCK_LABEL_133;
		threadState = threadStates[numThreadStatesActive];
		threadState.lock();
		unlock = true;
		ThreadState threadstate;
		if (!threadState.isActive())
			break MISSING_BLOCK_LABEL_87;
		numThreadStatesActive++;
		if (!$assertionsDisabled && threadState.dwpt == null)
			throw new AssertionError();
		threadState.dwpt.initialize();
		unlock = false;
		threadstate = threadState;
		if (unlock)
			threadState.unlock();
		return threadstate;
		if (!$assertionsDisabled && !assertUnreleasedThreadStatesInactive())
			throw new AssertionError();
		threadstate = null;
		if (unlock)
			threadState.unlock();
		return threadstate;
		Exception exception;
		exception;
		if (unlock)
			threadState.unlock();
		throw exception;
		return null;
	}

	private synchronized boolean assertUnreleasedThreadStatesInactive()
	{
		int i = numThreadStatesActive;
_L2:
		if (i >= threadStates.length)
			break; /* Loop/switch isn't completed */
		if (!$assertionsDisabled && !threadStates[i].tryLock())
			throw new AssertionError("unreleased threadstate should not be locked");
		if (!$assertionsDisabled && threadStates[i].isActive())
			throw new AssertionError("expected unreleased thread state to be inactive");
		threadStates[i].unlock();
		break MISSING_BLOCK_LABEL_94;
		Exception exception;
		exception;
		threadStates[i].unlock();
		throw exception;
		i++;
		if (true) goto _L2; else goto _L1
_L1:
		return true;
	}

	synchronized void deactivateUnreleasedStates()
	{
		int i = numThreadStatesActive;
_L2:
		ThreadState threadState;
		if (i >= threadStates.length)
			break; /* Loop/switch isn't completed */
		threadState = threadStates[i];
		threadState.lock();
		threadState.resetWriter(null);
		threadState.unlock();
		break MISSING_BLOCK_LABEL_44;
		Exception exception;
		exception;
		threadState.unlock();
		throw exception;
		i++;
		if (true) goto _L2; else goto _L1
_L1:
	}

	DocumentsWriterPerThread replaceForFlush(ThreadState threadState, boolean closed)
	{
		if (!$assertionsDisabled && !threadState.isHeldByCurrentThread())
			throw new AssertionError();
		if (!$assertionsDisabled && globalFieldMap.get() == null)
			throw new AssertionError();
		DocumentsWriterPerThread dwpt = threadState.dwpt;
		if (!closed)
		{
			FieldInfos.Builder infos = new FieldInfos.Builder((FieldInfos.FieldNumbers)globalFieldMap.get());
			DocumentsWriterPerThread newDwpt = new DocumentsWriterPerThread(dwpt, infos);
			newDwpt.initialize();
			threadState.resetWriter(newDwpt);
		} else
		{
			threadState.resetWriter(null);
		}
		return dwpt;
	}

	void recycle(DocumentsWriterPerThread documentswriterperthread)
	{
	}

	abstract ThreadState getAndLock(Thread thread, DocumentsWriter documentswriter);

	ThreadState getThreadState(int ord)
	{
		if (!$assertionsDisabled && ord >= numThreadStatesActive)
			throw new AssertionError();
		else
			return threadStates[ord];
	}

	ThreadState minContendedThreadState()
	{
		ThreadState minThreadState = null;
		int limit = numThreadStatesActive;
		for (int i = 0; i < limit; i++)
		{
			ThreadState state = threadStates[i];
			if (minThreadState == null || state.getQueueLength() < minThreadState.getQueueLength())
				minThreadState = state;
		}

		return minThreadState;
	}

	int numDeactivatedThreadStates()
	{
		int count;
		int i;
		count = 0;
		i = 0;
_L2:
		ThreadState threadState;
		if (i >= threadStates.length)
			break; /* Loop/switch isn't completed */
		threadState = threadStates[i];
		threadState.lock();
		if (!threadState.isActive)
			count++;
		threadState.unlock();
		break MISSING_BLOCK_LABEL_50;
		Exception exception;
		exception;
		threadState.unlock();
		throw exception;
		i++;
		if (true) goto _L2; else goto _L1
_L1:
		return count;
	}

	void deactivateThreadState(ThreadState threadState)
	{
		if (!$assertionsDisabled && !threadState.isActive())
		{
			throw new AssertionError();
		} else
		{
			threadState.resetWriter(null);
			return;
		}
	}

	void reinitThreadState(ThreadState threadState)
	{
		if (!$assertionsDisabled && !threadState.isActive)
			throw new AssertionError();
		if (!$assertionsDisabled && threadState.dwpt.getNumDocsInRAM() != 0)
		{
			throw new AssertionError();
		} else
		{
			threadState.dwpt.initialize();
			return;
		}
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
