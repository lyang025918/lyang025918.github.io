// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionMonitor.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.util.*;

// Referenced classes of package IceInternal:
//			TimerTask, Instance, Timer, Time, 
//			Ex

public final class ConnectionMonitor
	implements TimerTask
{

	private Instance _instance;
	private final int _interval;
	private int _scheduledInterval;
	private Set _connections;
	static final boolean $assertionsDisabled = !IceInternal/ConnectionMonitor.desiredAssertionStatus();

	public synchronized void destroy()
	{
		if (!$assertionsDisabled && _instance == null)
		{
			throw new AssertionError();
		} else
		{
			_instance = null;
			_connections = null;
			return;
		}
	}

	public void checkIntervalForACM(int acmTimeout)
	{
		if (acmTimeout <= 0)
			return;
		int interval;
		if (_interval == 0)
		{
			interval = Math.min(300, Math.max(5, acmTimeout / 10));
		} else
		{
			if (_scheduledInterval == _interval)
				return;
			interval = _interval;
		}
		synchronized (this)
		{
			if (_scheduledInterval == 0 || _scheduledInterval > interval)
			{
				_scheduledInterval = interval;
				_instance.timer().cancel(this);
				_instance.timer().scheduleRepeated(this, interval * 1000);
			}
		}
	}

	public synchronized void add(ConnectionI connection)
	{
		if (!$assertionsDisabled && _instance == null)
		{
			throw new AssertionError();
		} else
		{
			_connections.add(connection);
			return;
		}
	}

	public synchronized void remove(ConnectionI connection)
	{
		if (!$assertionsDisabled && _instance == null)
		{
			throw new AssertionError();
		} else
		{
			_connections.remove(connection);
			return;
		}
	}

	ConnectionMonitor(Instance instance, int interval)
	{
		_connections = new HashSet();
		_instance = instance;
		_interval = interval;
		_scheduledInterval = 0;
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_instance == null);
		Assert.FinalizerAssert(_connections == null);
		super.finalize();
	}

	public void runTimerTask()
	{
		Set connections;
label0:
		{
			connections = new HashSet();
			synchronized (this)
			{
				if (_instance != null)
					break label0;
			}
			return;
		}
		connections.clear();
		connections.addAll(_connections);
		connectionmonitor;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		long now;
		Iterator i$;
		now = Time.currentMonotonicTimeMillis();
		i$ = connections.iterator();
_L2:
		ConnectionI conn;
		if (!i$.hasNext())
			break MISSING_BLOCK_LABEL_169;
		conn = (ConnectionI)i$.next();
		conn.monitor(now);
		  goto _L2
		Exception ex;
		ex;
label1:
		{
			synchronized (this)
			{
				if (_instance != null)
					break label1;
			}
			return;
		}
		String s = (new StringBuilder()).append("exception in connection monitor:\n").append(Ex.toString(ex)).toString();
		_instance.initializationData().logger.error(s);
		connectionmonitor1;
		JVM INSTR monitorexit ;
		  goto _L2
		exception1;
		throw exception1;
	}

}
