// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadAffinityDocumentsWriterThreadPool.java

package org.apache.lucene.index;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriterPerThreadPool, DocumentsWriter

class ThreadAffinityDocumentsWriterThreadPool extends DocumentsWriterPerThreadPool
{

	private Map threadBindings;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/ThreadAffinityDocumentsWriterThreadPool.desiredAssertionStatus();

	public ThreadAffinityDocumentsWriterThreadPool(int maxNumPerThreads)
	{
		super(maxNumPerThreads);
		threadBindings = new ConcurrentHashMap();
		if (!$assertionsDisabled && getMaxThreadStates() < 1)
			throw new AssertionError();
		else
			return;
	}

	public DocumentsWriterPerThreadPool.ThreadState getAndLock(Thread requestingThread, DocumentsWriter documentsWriter)
	{
		DocumentsWriterPerThreadPool.ThreadState threadState = (DocumentsWriterPerThreadPool.ThreadState)threadBindings.get(requestingThread);
		if (threadState != null && threadState.tryLock())
			return threadState;
		DocumentsWriterPerThreadPool.ThreadState minThreadState = null;
		minThreadState = minContendedThreadState();
		if (minThreadState == null || minThreadState.hasQueuedThreads())
		{
			DocumentsWriterPerThreadPool.ThreadState newState = newThreadState();
			if (newState != null)
				if (!$assertionsDisabled && !newState.isHeldByCurrentThread())
				{
					throw new AssertionError();
				} else
				{
					threadBindings.put(requestingThread, newState);
					return newState;
				}
			if (minThreadState == null)
				minThreadState = minContendedThreadState();
		}
		if (!$assertionsDisabled && minThreadState == null)
		{
			throw new AssertionError("ThreadState is null");
		} else
		{
			minThreadState.lock();
			return minThreadState;
		}
	}

	public ThreadAffinityDocumentsWriterThreadPool clone()
	{
		ThreadAffinityDocumentsWriterThreadPool clone = (ThreadAffinityDocumentsWriterThreadPool)super.clone();
		clone.threadBindings = new ConcurrentHashMap();
		return clone;
	}

	public volatile DocumentsWriterPerThreadPool clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
