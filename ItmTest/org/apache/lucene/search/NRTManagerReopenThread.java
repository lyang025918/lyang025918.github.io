// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NRTManagerReopenThread.java

package org.apache.lucene.search;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.search:
//			NRTManager

public class NRTManagerReopenThread extends Thread
	implements NRTManager.WaitingListener, Closeable
{

	private final NRTManager manager;
	private final long targetMaxStaleNS;
	private final long targetMinStaleNS;
	private boolean finish;
	private long waitingGen;

	public NRTManagerReopenThread(NRTManager manager, double targetMaxStaleSec, double targetMinStaleSec)
	{
		if (targetMaxStaleSec < targetMinStaleSec)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("targetMaxScaleSec (= ").append(targetMaxStaleSec).append(") < targetMinStaleSec (=").append(targetMinStaleSec).append(")").toString());
		} else
		{
			this.manager = manager;
			targetMaxStaleNS = (long)(1000000000D * targetMaxStaleSec);
			targetMinStaleNS = (long)(1000000000D * targetMinStaleSec);
			manager.addWaitingListener(this);
			return;
		}
	}

	public synchronized void close()
	{
		manager.removeWaitingListener(this);
		finish = true;
		notify();
		try
		{
			join();
		}
		catch (InterruptedException ie)
		{
			throw new ThreadInterruptedException(ie);
		}
	}

	public synchronized void waiting(long targetGen)
	{
		waitingGen = Math.max(waitingGen, targetGen);
		notify();
	}

	public void run()
	{
		long lastReopenStartNS = System.nanoTime();
_L3:
		boolean hasWaiting = false;
		NRTManagerReopenThread nrtmanagerreopenthread = this;
		JVM INSTR monitorenter ;
_L1:
		long sleepNS;
		if (finish)
			break MISSING_BLOCK_LABEL_112;
		hasWaiting = waitingGen > manager.getCurrentSearchingGen();
		long nextReopenStartNS = lastReopenStartNS + (hasWaiting ? targetMinStaleNS : targetMaxStaleNS);
		sleepNS = nextReopenStartNS - System.nanoTime();
		if (sleepNS <= 0L)
			break MISSING_BLOCK_LABEL_112;
		wait(sleepNS / 0xf4240L, (int)(sleepNS % 0xf4240L));
		  goto _L1
		InterruptedException ie;
		ie;
		Thread.currentThread().interrupt();
		finish = true;
		if (!finish)
			break MISSING_BLOCK_LABEL_123;
		return;
		Exception exception;
		exception;
		throw exception;
		lastReopenStartNS = System.nanoTime();
		try
		{
			manager.maybeRefresh();
		}
		catch (IOException ioe)
		{
			throw new RuntimeException(ioe);
		}
		if (true) goto _L3; else goto _L2
_L2:
		Throwable t;
		t;
		throw new RuntimeException(t);
	}
}
