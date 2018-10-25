// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FSLockFactory.java

package org.apache.lucene.store;

import java.io.File;

// Referenced classes of package org.apache.lucene.store:
//			LockFactory

public abstract class FSLockFactory extends LockFactory
{

	protected File lockDir;

	public FSLockFactory()
	{
		lockDir = null;
	}

	protected final void setLockDir(File lockDir)
	{
		if (this.lockDir != null)
		{
			throw new IllegalStateException("You can set the lock directory for this factory only once.");
		} else
		{
			this.lockDir = lockDir;
			return;
		}
	}

	public File getLockDir()
	{
		return lockDir;
	}
}
