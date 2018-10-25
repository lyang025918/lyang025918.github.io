// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleFSLockFactory.java

package org.apache.lucene.store;

import java.io.File;
import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			FSLockFactory, SimpleFSLock, Lock

public class SimpleFSLockFactory extends FSLockFactory
{

	public SimpleFSLockFactory()
	{
		this((File)null);
	}

	public SimpleFSLockFactory(File lockDir)
	{
		setLockDir(lockDir);
	}

	public SimpleFSLockFactory(String lockDirName)
	{
		setLockDir(new File(lockDirName));
	}

	public Lock makeLock(String lockName)
	{
		if (lockPrefix != null)
			lockName = (new StringBuilder()).append(lockPrefix).append("-").append(lockName).toString();
		return new SimpleFSLock(lockDir, lockName);
	}

	public void clearLock(String lockName)
		throws IOException
	{
		if (lockDir.exists())
		{
			if (lockPrefix != null)
				lockName = (new StringBuilder()).append(lockPrefix).append("-").append(lockName).toString();
			File lockFile = new File(lockDir, lockName);
			if (lockFile.exists() && !lockFile.delete())
				throw new IOException((new StringBuilder()).append("Cannot delete ").append(lockFile).toString());
		}
	}
}
