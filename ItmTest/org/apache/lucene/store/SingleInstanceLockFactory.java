// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleInstanceLockFactory.java

package org.apache.lucene.store;

import java.io.IOException;
import java.util.HashSet;

// Referenced classes of package org.apache.lucene.store:
//			LockFactory, SingleInstanceLock, Lock

public class SingleInstanceLockFactory extends LockFactory
{

	private HashSet locks;

	public SingleInstanceLockFactory()
	{
		locks = new HashSet();
	}

	public Lock makeLock(String lockName)
	{
		return new SingleInstanceLock(locks, lockName);
	}

	public void clearLock(String lockName)
		throws IOException
	{
		synchronized (locks)
		{
			if (locks.contains(lockName))
				locks.remove(lockName);
		}
	}
}
