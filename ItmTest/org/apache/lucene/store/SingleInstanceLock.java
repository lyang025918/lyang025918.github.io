// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleInstanceLockFactory.java

package org.apache.lucene.store;

import java.io.IOException;
import java.util.HashSet;

// Referenced classes of package org.apache.lucene.store:
//			Lock

class SingleInstanceLock extends Lock
{

	String lockName;
	private HashSet locks;

	public SingleInstanceLock(HashSet locks, String lockName)
	{
		this.locks = locks;
		this.lockName = lockName;
	}

	public boolean obtain()
		throws IOException
	{
		HashSet hashset = locks;
		JVM INSTR monitorenter ;
		return locks.add(lockName);
		Exception exception;
		exception;
		throw exception;
	}

	public void release()
	{
		synchronized (locks)
		{
			locks.remove(lockName);
		}
	}

	public boolean isLocked()
	{
		HashSet hashset = locks;
		JVM INSTR monitorenter ;
		return locks.contains(lockName);
		Exception exception;
		exception;
		throw exception;
	}

	public String toString()
	{
		return (new StringBuilder()).append(super.toString()).append(": ").append(lockName).toString();
	}
}
