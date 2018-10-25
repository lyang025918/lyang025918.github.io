// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleFSLockFactory.java

package org.apache.lucene.store;

import java.io.File;
import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			Lock, LockReleaseFailedException

class SimpleFSLock extends Lock
{

	File lockFile;
	File lockDir;

	public SimpleFSLock(File lockDir, String lockFileName)
	{
		this.lockDir = lockDir;
		lockFile = new File(lockDir, lockFileName);
	}

	public boolean obtain()
		throws IOException
	{
		if (!lockDir.exists())
		{
			if (!lockDir.mkdirs())
				throw new IOException((new StringBuilder()).append("Cannot create directory: ").append(lockDir.getAbsolutePath()).toString());
		} else
		if (!lockDir.isDirectory())
			throw new IOException((new StringBuilder()).append("Found regular file where directory expected: ").append(lockDir.getAbsolutePath()).toString());
		return lockFile.createNewFile();
	}

	public void release()
		throws LockReleaseFailedException
	{
		if (lockFile.exists() && !lockFile.delete())
			throw new LockReleaseFailedException((new StringBuilder()).append("failed to delete ").append(lockFile).toString());
		else
			return;
	}

	public boolean isLocked()
	{
		return lockFile.exists();
	}

	public String toString()
	{
		return (new StringBuilder()).append("SimpleFSLock@").append(lockFile).toString();
	}
}
