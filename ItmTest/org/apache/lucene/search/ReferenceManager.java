// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReferenceManager.java

package org.apache.lucene.search;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.lucene.store.AlreadyClosedException;

public abstract class ReferenceManager
	implements Closeable
{

	private static final String REFERENCE_MANAGER_IS_CLOSED_MSG = "this ReferenceManager is closed";
	protected volatile Object current;
	private final Lock refreshLock = new ReentrantLock();
	static final boolean $assertionsDisabled = !org/apache/lucene/search/ReferenceManager.desiredAssertionStatus();

	public ReferenceManager()
	{
	}

	private void ensureOpen()
	{
		if (current == null)
			throw new AlreadyClosedException("this ReferenceManager is closed");
		else
			return;
	}

	private synchronized void swapReference(Object newReference)
		throws IOException
	{
		ensureOpen();
		Object oldReference = current;
		current = newReference;
		release(oldReference);
	}

	protected abstract void decRef(Object obj)
		throws IOException;

	protected abstract Object refreshIfNeeded(Object obj)
		throws IOException;

	protected abstract boolean tryIncRef(Object obj);

	public final Object acquire()
	{
		Object ref;
		do
			if ((ref = current) == null)
				throw new AlreadyClosedException("this ReferenceManager is closed");
		while (!tryIncRef(ref));
		return ref;
	}

	public final synchronized void close()
		throws IOException
	{
		if (current != null)
		{
			swapReference(null);
			afterClose();
		}
	}

	protected void afterClose()
		throws IOException
	{
	}

	private void doMaybeRefresh()
		throws IOException
	{
		refreshLock.lock();
		Object reference = acquire();
		Object newReference;
		boolean success;
		newReference = refreshIfNeeded(reference);
		if (newReference == null)
			break MISSING_BLOCK_LABEL_80;
		if (!$assertionsDisabled && newReference == reference)
			throw new AssertionError("refreshIfNeeded should return null if refresh wasn't needed");
		success = false;
		swapReference(newReference);
		success = true;
		if (!success)
			release(newReference);
		break MISSING_BLOCK_LABEL_80;
		Exception exception;
		exception;
		if (!success)
			release(newReference);
		throw exception;
		release(reference);
		break MISSING_BLOCK_LABEL_98;
		Exception exception1;
		exception1;
		release(reference);
		throw exception1;
		afterRefresh();
		refreshLock.unlock();
		break MISSING_BLOCK_LABEL_128;
		Exception exception2;
		exception2;
		refreshLock.unlock();
		throw exception2;
	}

	public final boolean maybeRefresh()
		throws IOException
	{
		boolean doTryRefresh;
		ensureOpen();
		doTryRefresh = refreshLock.tryLock();
		if (!doTryRefresh)
			break MISSING_BLOCK_LABEL_46;
		doMaybeRefresh();
		refreshLock.unlock();
		break MISSING_BLOCK_LABEL_46;
		Exception exception;
		exception;
		refreshLock.unlock();
		throw exception;
		return doTryRefresh;
	}

	public final void maybeRefreshBlocking()
		throws IOException
	{
		ensureOpen();
		refreshLock.lock();
		doMaybeRefresh();
		refreshLock.unlock();
		break MISSING_BLOCK_LABEL_41;
		Exception exception;
		exception;
		refreshLock.unlock();
		throw exception;
	}

	protected void afterRefresh()
		throws IOException
	{
	}

	public final void release(Object reference)
		throws IOException
	{
		if (!$assertionsDisabled && reference == null)
		{
			throw new AssertionError();
		} else
		{
			decRef(reference);
			return;
		}
	}

}
