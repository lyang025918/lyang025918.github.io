// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NativeFSLockFactory.java

package org.apache.lucene.store;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashSet;

// Referenced classes of package org.apache.lucene.store:
//			Lock, LockReleaseFailedException

class NativeFSLock extends Lock
{

	private RandomAccessFile f;
	private FileChannel channel;
	private FileLock lock;
	private File path;
	private File lockDir;
	private static HashSet LOCK_HELD = new HashSet();

	public NativeFSLock(File lockDir, String lockFileName)
	{
		this.lockDir = lockDir;
		path = new File(lockDir, lockFileName);
	}

	private synchronized boolean lockExists()
	{
		return lock != null;
	}

	public synchronized boolean obtain()
		throws IOException
	{
		String canonicalPath;
		boolean markedHeld;
		if (lockExists())
			return false;
		if (!lockDir.exists())
		{
			if (!lockDir.mkdirs())
				throw new IOException((new StringBuilder()).append("Cannot create directory: ").append(lockDir.getAbsolutePath()).toString());
		} else
		if (!lockDir.isDirectory())
			throw new IOException((new StringBuilder()).append("Found regular file where directory expected: ").append(lockDir.getAbsolutePath()).toString());
		canonicalPath = path.getCanonicalPath();
		markedHeld = false;
label0:
		{
			boolean flag;
			synchronized (LOCK_HELD)
			{
				if (!LOCK_HELD.contains(canonicalPath))
					break label0;
				flag = false;
			}
			if (markedHeld && !lockExists())
				synchronized (LOCK_HELD)
				{
					if (LOCK_HELD.contains(canonicalPath))
						LOCK_HELD.remove(canonicalPath);
				}
			return flag;
		}
		LOCK_HELD.add(canonicalPath);
		markedHeld = true;
		hashset;
		JVM INSTR monitorexit ;
		IOException e;
		try
		{
			f = new RandomAccessFile(path, "rw");
		}
		// Misplaced declaration of an exception variable
		catch (IOException e)
		{
			failureReason = e;
			f = null;
		}
		if (f == null)
			break MISSING_BLOCK_LABEL_456;
		channel = f.getChannel();
		lock = channel.tryLock();
		if (lock != null)
			break MISSING_BLOCK_LABEL_384;
		channel.close();
		channel = null;
		break MISSING_BLOCK_LABEL_384;
		Exception exception2;
		exception2;
		channel = null;
		throw exception2;
		e;
		failureReason = e;
		if (lock != null)
			break MISSING_BLOCK_LABEL_384;
		channel.close();
		channel = null;
		break MISSING_BLOCK_LABEL_384;
		Exception exception3;
		exception3;
		channel = null;
		throw exception3;
		Exception exception4;
		exception4;
		if (lock != null)
			break MISSING_BLOCK_LABEL_381;
		channel.close();
		channel = null;
		break MISSING_BLOCK_LABEL_381;
		Exception exception5;
		exception5;
		channel = null;
		throw exception5;
		throw exception4;
		if (channel != null)
			break MISSING_BLOCK_LABEL_456;
		f.close();
		f = null;
		break MISSING_BLOCK_LABEL_456;
		Exception exception6;
		exception6;
		f = null;
		throw exception6;
		Exception exception7;
		exception7;
		if (channel != null)
			break MISSING_BLOCK_LABEL_453;
		f.close();
		f = null;
		break MISSING_BLOCK_LABEL_453;
		Exception exception8;
		exception8;
		f = null;
		throw exception8;
		throw exception7;
		if (markedHeld && !lockExists())
			synchronized (LOCK_HELD)
			{
				if (LOCK_HELD.contains(canonicalPath))
					LOCK_HELD.remove(canonicalPath);
			}
		break MISSING_BLOCK_LABEL_561;
		Exception exception10;
		exception10;
		if (markedHeld && !lockExists())
			synchronized (LOCK_HELD)
			{
				if (LOCK_HELD.contains(canonicalPath))
					LOCK_HELD.remove(canonicalPath);
			}
		throw exception10;
		return lockExists();
	}

	public synchronized void release()
		throws IOException
	{
		if (!lockExists())
			break MISSING_BLOCK_LABEL_466;
		lock.release();
		lock = null;
		channel.close();
		channel = null;
		f.close();
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		break MISSING_BLOCK_LABEL_455;
		Exception exception1;
		exception1;
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		throw exception1;
		Exception exception3;
		exception3;
		channel = null;
		f.close();
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		break MISSING_BLOCK_LABEL_224;
		Exception exception5;
		exception5;
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		throw exception5;
		throw exception3;
		Exception exception7;
		exception7;
		lock = null;
		channel.close();
		channel = null;
		f.close();
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		break MISSING_BLOCK_LABEL_452;
		Exception exception9;
		exception9;
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		throw exception9;
		Exception exception11;
		exception11;
		channel = null;
		f.close();
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		break MISSING_BLOCK_LABEL_449;
		Exception exception13;
		exception13;
		f = null;
		synchronized (LOCK_HELD)
		{
			LOCK_HELD.remove(path.getCanonicalPath());
		}
		throw exception13;
		throw exception11;
		throw exception7;
		path.delete();
		break MISSING_BLOCK_LABEL_531;
		boolean obtained = false;
		if (!(obtained = obtain()))
			throw new LockReleaseFailedException((new StringBuilder()).append("Cannot forcefully unlock a NativeFSLock which is held by another indexer component: ").append(path).toString());
		if (obtained)
			release();
		break MISSING_BLOCK_LABEL_531;
		Exception exception15;
		exception15;
		if (obtained)
			release();
		throw exception15;
	}

	public synchronized boolean isLocked()
	{
		if (lockExists())
			return true;
		if (!path.exists())
			return false;
		boolean obtained;
		obtained = obtain();
		if (obtained)
			release();
		return !obtained;
		IOException ioe;
		ioe;
		return false;
	}

	public String toString()
	{
		return (new StringBuilder()).append("NativeFSLock@").append(path).toString();
	}

}
