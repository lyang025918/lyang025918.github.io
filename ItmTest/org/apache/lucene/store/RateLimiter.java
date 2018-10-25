// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RateLimiter.java

package org.apache.lucene.store;

import org.apache.lucene.util.ThreadInterruptedException;

public class RateLimiter
{

	private volatile double mbPerSec;
	private volatile double nsPerByte;
	private volatile long lastNS;

	public RateLimiter(double mbPerSec)
	{
		setMbPerSec(mbPerSec);
	}

	public void setMbPerSec(double mbPerSec)
	{
		this.mbPerSec = mbPerSec;
		nsPerByte = 1000000000D / (1048576D * mbPerSec);
	}

	public double getMbPerSec()
	{
		return mbPerSec;
	}

	public void pause(long bytes)
	{
		if (bytes == 1L)
			return;
		long targetNS = lastNS = lastNS + (long)((double)bytes * nsPerByte);
		long curNS = System.nanoTime();
		if (lastNS < curNS)
			lastNS = curNS;
		do
		{
			long pauseNS = targetNS - curNS;
			if (pauseNS > 0L)
			{
				try
				{
					Thread.sleep((int)(pauseNS / 0xf4240L), (int)(pauseNS % 0xf4240L));
				}
				catch (InterruptedException ie)
				{
					throw new ThreadInterruptedException(ie);
				}
				curNS = System.nanoTime();
			} else
			{
				return;
			}
		} while (true);
	}
}
