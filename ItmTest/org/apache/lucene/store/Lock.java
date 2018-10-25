// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lock.java

package org.apache.lucene.store;

import java.io.IOException;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.store:
//			LockObtainFailedException

public abstract class Lock
{
	public static abstract class With
	{

		private Lock lock;
		private long lockWaitTimeout;

		protected abstract Object doBody()
			throws IOException;

		public Object run()
			throws IOException
		{
			boolean locked = false;
			Object obj;
			locked = lock.obtain(lockWaitTimeout);
			obj = doBody();
			if (locked)
				lock.release();
			return obj;
			Exception exception;
			exception;
			if (locked)
				lock.release();
			throw exception;
		}

		public With(Lock lock, long lockWaitTimeout)
		{
			this.lock = lock;
			this.lockWaitTimeout = lockWaitTimeout;
		}
	}


	public static long LOCK_POLL_INTERVAL = 1000L;
	public static final long LOCK_OBTAIN_WAIT_FOREVER = -1L;
	protected Throwable failureReason;

	public Lock()
	{
	}

	public abstract boolean obtain()
		throws IOException;

	public boolean obtain(long lockWaitTimeout)
		throws IOException
	{
		failureReason = null;
		boolean locked = obtain();
		if (lockWaitTimeout < 0L && lockWaitTimeout != -1L)
			throw new IllegalArgumentException((new StringBuilder()).append("lockWaitTimeout should be LOCK_OBTAIN_WAIT_FOREVER or a non-negative number (got ").append(lockWaitTimeout).append(")").toString());
		long maxSleepCount = lockWaitTimeout / LOCK_POLL_INTERVAL;
		long sleepCount = 0L;
		for (; !locked; locked = obtain())
		{
			if (lockWaitTimeout != -1L && sleepCount++ >= maxSleepCount)
			{
				String reason = (new StringBuilder()).append("Lock obtain timed out: ").append(toString()).toString();
				if (failureReason != null)
					reason = (new StringBuilder()).append(reason).append(": ").append(failureReason).toString();
				LockObtainFailedException e = new LockObtainFailedException(reason);
				if (failureReason != null)
					e.initCause(failureReason);
				throw e;
			}
			try
			{
				Thread.sleep(LOCK_POLL_INTERVAL);
			}
			catch (InterruptedException ie)
			{
				throw new ThreadInterruptedException(ie);
			}
		}

		return locked;
	}

	public abstract void release()
		throws IOException;

	public abstract boolean isLocked()
		throws IOException;

}
