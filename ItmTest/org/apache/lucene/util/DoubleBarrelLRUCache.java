// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DoubleBarrelLRUCache.java

package org.apache.lucene.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class DoubleBarrelLRUCache
{
	public static abstract class CloneableKey
	{

		public abstract CloneableKey clone();

		public volatile Object clone()
			throws CloneNotSupportedException
		{
			return clone();
		}

		public CloneableKey()
		{
		}
	}


	private final Map cache1 = new ConcurrentHashMap();
	private final Map cache2 = new ConcurrentHashMap();
	private final AtomicInteger countdown;
	private volatile boolean swapped;
	private final int maxSize;

	public DoubleBarrelLRUCache(int maxSize)
	{
		this.maxSize = maxSize;
		countdown = new AtomicInteger(maxSize);
	}

	public Object get(CloneableKey key)
	{
		Map primary;
		Map secondary;
		if (swapped)
		{
			primary = cache2;
			secondary = cache1;
		} else
		{
			primary = cache1;
			secondary = cache2;
		}
		Object result = primary.get(key);
		if (result == null)
		{
			result = secondary.get(key);
			if (result != null)
				put(key.clone(), result);
		}
		return result;
	}

	public void put(CloneableKey key, Object value)
	{
		Map primary;
		Map secondary;
		if (swapped)
		{
			primary = cache2;
			secondary = cache1;
		} else
		{
			primary = cache1;
			secondary = cache2;
		}
		primary.put(key, value);
		if (countdown.decrementAndGet() == 0)
		{
			secondary.clear();
			swapped = !swapped;
			countdown.set(maxSize);
		}
	}
}
