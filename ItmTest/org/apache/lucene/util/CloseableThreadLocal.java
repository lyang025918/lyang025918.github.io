// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CloseableThreadLocal.java

package org.apache.lucene.util;

import java.io.Closeable;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CloseableThreadLocal
	implements Closeable
{

	private ThreadLocal t;
	private Map hardRefs;
	private static int PURGE_MULTIPLIER = 20;
	private final AtomicInteger countUntilPurge;

	public CloseableThreadLocal()
	{
		t = new ThreadLocal();
		hardRefs = new WeakHashMap();
		countUntilPurge = new AtomicInteger(PURGE_MULTIPLIER);
	}

	protected Object initialValue()
	{
		return null;
	}

	public Object get()
	{
		WeakReference weakRef = (WeakReference)t.get();
		if (weakRef == null)
		{
			Object iv = initialValue();
			if (iv != null)
			{
				set(iv);
				return iv;
			} else
			{
				return null;
			}
		} else
		{
			maybePurge();
			return weakRef.get();
		}
	}

	public void set(Object object)
	{
		t.set(new WeakReference(object));
		synchronized (hardRefs)
		{
			hardRefs.put(Thread.currentThread(), object);
			maybePurge();
		}
	}

	private void maybePurge()
	{
		if (countUntilPurge.getAndDecrement() == 0)
			purge();
	}

	private void purge()
	{
		synchronized (hardRefs)
		{
			int stillAliveCount = 0;
			for (Iterator it = hardRefs.keySet().iterator(); it.hasNext();)
			{
				Thread t = (Thread)it.next();
				if (!t.isAlive())
					it.remove();
				else
					stillAliveCount++;
			}

			int nextCount = (1 + stillAliveCount) * PURGE_MULTIPLIER;
			if (nextCount <= 0)
				nextCount = 0xf4240;
			countUntilPurge.set(nextCount);
		}
	}

	public void close()
	{
		hardRefs = null;
		if (t != null)
			t.remove();
		t = null;
	}

}
