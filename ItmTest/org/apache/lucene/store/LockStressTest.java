// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LockStressTest.java

package org.apache.lucene.store;

import java.io.*;

// Referenced classes of package org.apache.lucene.store:
//			LockFactory, FSLockFactory, VerifyingLockFactory, LockObtainFailedException, 
//			Lock

public class LockStressTest
{

	public LockStressTest()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		if (args.length != 6)
		{
			System.out.println("\nUsage: java org.apache.lucene.store.LockStressTest myID verifierHostOrIP verifierPort lockFactoryClassName lockDirName sleepTime\n\n  myID = int from 0 .. 255 (should be unique for test process)\n  verifierHostOrIP = host name or IP address where LockVerifyServer is running\n  verifierPort = port that LockVerifyServer is listening on\n  lockFactoryClassName = primary LockFactory class that we will use\n  lockDirName = path to the lock directory (only set for Simple/NativeFSLockFactory\n  sleepTimeMS = milliseconds to pause betweeen each lock obtain/release\n\nYou should run multiple instances of this process, each with its own\nunique ID, and each pointing to the same lock directory, to verify\nthat locking is working correctly.\n\nMake sure you are first running LockVerifyServer.\n\n");
			System.exit(1);
		}
		int myID = Integer.parseInt(args[0]);
		if (myID < 0 || myID > 255)
		{
			System.out.println("myID must be a unique int 0..255");
			System.exit(1);
		}
		String verifierHost = args[1];
		int verifierPort = Integer.parseInt(args[2]);
		String lockFactoryClassName = args[3];
		String lockDirName = args[4];
		int sleepTimeMS = Integer.parseInt(args[5]);
		LockFactory lockFactory;
		try
		{
			lockFactory = (LockFactory)Class.forName(lockFactoryClassName).asSubclass(org/apache/lucene/store/LockFactory).newInstance();
		}
		catch (IllegalAccessException e)
		{
			throw new IOException((new StringBuilder()).append("IllegalAccessException when instantiating LockClass ").append(lockFactoryClassName).toString());
		}
		catch (InstantiationException e)
		{
			throw new IOException((new StringBuilder()).append("InstantiationException when instantiating LockClass ").append(lockFactoryClassName).toString());
		}
		catch (ClassCastException e)
		{
			throw new IOException((new StringBuilder()).append("unable to cast LockClass ").append(lockFactoryClassName).append(" instance to a LockFactory").toString());
		}
		catch (ClassNotFoundException e)
		{
			throw new IOException((new StringBuilder()).append("unable to find LockClass ").append(lockFactoryClassName).toString());
		}
		File lockDir = new File(lockDirName);
		if (lockFactory instanceof FSLockFactory)
			((FSLockFactory)lockFactory).setLockDir(lockDir);
		lockFactory.setLockPrefix("test");
		LockFactory verifyLF = new VerifyingLockFactory((byte)myID, lockFactory, verifierHost, verifierPort);
		Lock l = verifyLF.makeLock("test.lock");
		do
		{
			boolean obtained = false;
			try
			{
				obtained = l.obtain(10L);
			}
			catch (LockObtainFailedException e)
			{
				System.out.print("x");
			}
			if (obtained)
			{
				System.out.print("l");
				l.release();
			}
			Thread.sleep(sleepTimeMS);
		} while (true);
	}
}
