// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LockFactory.java

package org.apache.lucene.store;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			Lock

public abstract class LockFactory
{

	protected String lockPrefix;

	public LockFactory()
	{
		lockPrefix = null;
	}

	public void setLockPrefix(String lockPrefix)
	{
		this.lockPrefix = lockPrefix;
	}

	public String getLockPrefix()
	{
		return lockPrefix;
	}

	public abstract Lock makeLock(String s);

	public abstract void clearLock(String s)
		throws IOException;
}
