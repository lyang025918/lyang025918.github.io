// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriterStallControl.java

package org.apache.lucene.index;

import java.util.IdentityHashMap;
import java.util.Map;
import org.apache.lucene.util.ThreadInterruptedException;

final class DocumentsWriterStallControl
{

	private volatile boolean stalled;
	private int numWaiting;
	private boolean wasStalled;
	private final Map waiting = new IdentityHashMap();
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterStallControl.desiredAssertionStatus();

	DocumentsWriterStallControl()
	{
	}

	synchronized void updateStalled(boolean stalled)
	{
		this.stalled = stalled;
		if (stalled)
			wasStalled = true;
		notifyAll();
	}

	void waitIfStalled()
	{
		if (stalled)
			synchronized (this)
			{
				if (stalled)
					try
					{
						if (!$assertionsDisabled && !incWaiters())
							throw new AssertionError();
						wait();
						if (!$assertionsDisabled && !decrWaiters())
							throw new AssertionError();
					}
					catch (InterruptedException e)
					{
						throw new ThreadInterruptedException(e);
					}
			}
	}

	boolean anyStalledThreads()
	{
		return stalled;
	}

	private boolean incWaiters()
	{
		numWaiting++;
		if (!$assertionsDisabled && waiting.put(Thread.currentThread(), Boolean.TRUE) != null)
			throw new AssertionError();
		else
			return numWaiting > 0;
	}

	private boolean decrWaiters()
	{
		numWaiting--;
		if (!$assertionsDisabled && waiting.remove(Thread.currentThread()) == null)
			throw new AssertionError();
		else
			return numWaiting >= 0;
	}

	synchronized boolean hasBlocked()
	{
		return numWaiting > 0;
	}

	boolean isHealthy()
	{
		return !stalled;
	}

	synchronized boolean isThreadQueued(Thread t)
	{
		return waiting.containsKey(t);
	}

	synchronized boolean wasStalled()
	{
		return wasStalled;
	}

}
