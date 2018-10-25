// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NativeFSLockFactory.java

package org.apache.lucene.store;

import java.io.File;
import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			FSLockFactory, NativeFSLock, Lock

public class NativeFSLockFactory extends FSLockFactory
{

	public NativeFSLockFactory()
	{
		this((File)null);
	}

	public NativeFSLockFactory(String lockDirName)
	{
		this(new File(lockDirName));
	}

	public NativeFSLockFactory(File lockDir)
	{
		setLockDir(lockDir);
	}

	public synchronized Lock makeLock(String lockName)
	{
		if (lockPrefix != null)
			lockName = (new StringBuilder()).append(lockPrefix).append("-").append(lockName).toString();
		return new NativeFSLock(lockDir, lockName);
	}

	public void clearLock(String lockName)
		throws IOException
	{
		if (lockDir.exists())
		{
			makeLock(lockName).release();
			if (lockPrefix != null)
				lockName = (new StringBuilder()).append(lockPrefix).append("-").append(lockName).toString();
			(new File(lockDir, lockName)).delete();
		}
	}
}
