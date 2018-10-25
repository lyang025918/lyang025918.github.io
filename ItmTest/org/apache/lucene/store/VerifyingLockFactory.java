// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VerifyingLockFactory.java

package org.apache.lucene.store;

import java.io.*;
import java.net.Socket;

// Referenced classes of package org.apache.lucene.store:
//			LockFactory, Lock

public class VerifyingLockFactory extends LockFactory
{
	private class CheckedLock extends Lock
	{

		private Lock lock;
		final VerifyingLockFactory this$0;

		private void verify(byte message)
		{
			try
			{
				Socket s = new Socket(host, port);
				OutputStream out = s.getOutputStream();
				out.write(id);
				out.write(message);
				InputStream in = s.getInputStream();
				int result = in.read();
				in.close();
				out.close();
				s.close();
				if (result != 0)
					throw new RuntimeException("lock was double acquired");
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		public synchronized boolean obtain(long lockWaitTimeout)
			throws IOException
		{
			boolean obtained = lock.obtain(lockWaitTimeout);
			if (obtained)
				verify((byte)1);
			return obtained;
		}

		public synchronized boolean obtain()
			throws IOException
		{
			return lock.obtain();
		}

		public synchronized boolean isLocked()
			throws IOException
		{
			return lock.isLocked();
		}

		public synchronized void release()
			throws IOException
		{
			if (isLocked())
			{
				verify((byte)0);
				lock.release();
			}
		}

		public CheckedLock(Lock lock)
		{
			this$0 = VerifyingLockFactory.this;
			super();
			this.lock = lock;
		}
	}


	LockFactory lf;
	byte id;
	String host;
	int port;

	public VerifyingLockFactory(byte id, LockFactory lf, String host, int port)
	{
		this.id = id;
		this.lf = lf;
		this.host = host;
		this.port = port;
	}

	public synchronized Lock makeLock(String lockName)
	{
		return new CheckedLock(lf.makeLock(lockName));
	}

	public synchronized void clearLock(String lockName)
		throws IOException
	{
		lf.clearLock(lockName);
	}
}
