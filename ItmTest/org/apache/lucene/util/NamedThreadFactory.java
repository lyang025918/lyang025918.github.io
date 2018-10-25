// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NamedThreadFactory.java

package org.apache.lucene.util;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory
	implements ThreadFactory
{

	private static final AtomicInteger threadPoolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private static final String NAME_PATTERN = "%s-%d-thread";
	private final String threadNamePrefix;

	public NamedThreadFactory(String threadNamePrefix)
	{
		SecurityManager s = System.getSecurityManager();
		group = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
		this.threadNamePrefix = String.format(Locale.ROOT, "%s-%d-thread", new Object[] {
			checkPrefix(threadNamePrefix), Integer.valueOf(threadPoolNumber.getAndIncrement())
		});
	}

	private static String checkPrefix(String prefix)
	{
		return prefix != null && prefix.length() != 0 ? prefix : "Lucene";
	}

	public Thread newThread(Runnable r)
	{
		Thread t = new Thread(group, r, String.format(Locale.ROOT, "%s-%d", new Object[] {
			threadNamePrefix, Integer.valueOf(threadNumber.getAndIncrement())
		}), 0L);
		t.setDaemon(false);
		t.setPriority(5);
		return t;
	}

}
