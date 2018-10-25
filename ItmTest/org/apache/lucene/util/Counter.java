// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Counter.java

package org.apache.lucene.util;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Counter
{
	private static final class AtomicCounter extends Counter
	{

		private final AtomicLong count;

		public long addAndGet(long delta)
		{
			return count.addAndGet(delta);
		}

		public long get()
		{
			return count.get();
		}

		private AtomicCounter()
		{
			count = new AtomicLong();
		}

	}

	private static final class SerialCounter extends Counter
	{

		private long count;

		public long addAndGet(long delta)
		{
			return count += delta;
		}

		public long get()
		{
			return count;
		}

		private SerialCounter()
		{
			count = 0L;
		}

	}


	public Counter()
	{
	}

	public abstract long addAndGet(long l);

	public abstract long get();

	public static Counter newCounter()
	{
		return newCounter(false);
	}

	public static Counter newCounter(boolean threadSafe)
	{
		return ((Counter) (threadSafe ? new AtomicCounter() : new SerialCounter()));
	}
}
