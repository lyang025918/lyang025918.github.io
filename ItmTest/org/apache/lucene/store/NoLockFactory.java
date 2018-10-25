// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoLockFactory.java

package org.apache.lucene.store;


// Referenced classes of package org.apache.lucene.store:
//			LockFactory, NoLock, Lock

public class NoLockFactory extends LockFactory
{

	private static NoLock singletonLock = new NoLock();
	private static NoLockFactory singleton = new NoLockFactory();

	private NoLockFactory()
	{
	}

	public static NoLockFactory getNoLockFactory()
	{
		return singleton;
	}

	public Lock makeLock(String lockName)
	{
		return singletonLock;
	}

	public void clearLock(String s)
	{
	}

}
