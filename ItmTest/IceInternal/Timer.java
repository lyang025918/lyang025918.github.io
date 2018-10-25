// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Timer.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.util.*;

// Referenced classes of package IceInternal:
//			Time, Instance, TimerTask, Ex

public final class Timer extends Thread
{
	private static class Token
		implements Comparable
	{

		long scheduledTime;
		int id;
		long delay;
		TimerTask task;

		public int compareTo(Token r)
		{
			if (scheduledTime < r.scheduledTime)
				return -1;
			if (scheduledTime > r.scheduledTime)
				return 1;
			if (id < r.id)
				return -1;
			return id <= r.id ? 0 : 1;
		}

		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj instanceof Token)
				return compareTo((Token)obj) == 0;
			else
				return false;
		}

		public int hashCode()
		{
			return id ^ (int)scheduledTime;
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((Token)x0);
		}

		public Token(long scheduledTime, int id, long delay, TimerTask task)
		{
			this.scheduledTime = scheduledTime;
			this.id = id;
			this.delay = delay;
			this.task = task;
		}
	}


	private final SortedSet _tokens = new TreeSet();
	private final Map _tasks = new HashMap();
	private Instance _instance;
	private long _wakeUpTime;
	private int _tokenId;
	static final boolean $assertionsDisabled = !IceInternal/Timer.desiredAssertionStatus();

	public void _destroy()
	{
label0:
		{
			synchronized (this)
			{
				if (_instance != null)
					break label0;
			}
			return;
		}
		_instance = null;
		notify();
		_tokens.clear();
		_tasks.clear();
		timer;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		do
			try
			{
				join();
				break;
			}
			catch (InterruptedException ex) { }
		while (true);
		return;
	}

	public synchronized void schedule(TimerTask task, long delay)
	{
		if (_instance == null)
			throw new CommunicatorDestroyedException();
		Token token = new Token(Time.currentMonotonicTimeMillis() + delay, ++_tokenId, 0L, task);
		Token previous = (Token)_tasks.put(task, token);
		if (!$assertionsDisabled && previous != null)
			throw new AssertionError();
		_tokens.add(token);
		if (token.scheduledTime < _wakeUpTime)
			notify();
	}

	public synchronized void scheduleRepeated(TimerTask task, long period)
	{
		if (_instance == null)
			throw new CommunicatorDestroyedException();
		Token token = new Token(Time.currentMonotonicTimeMillis() + period, ++_tokenId, period, task);
		Token previous = (Token)_tasks.put(task, token);
		if (!$assertionsDisabled && previous != null)
			throw new AssertionError();
		_tokens.add(token);
		if (token.scheduledTime < _wakeUpTime)
			notify();
	}

	public synchronized boolean cancel(TimerTask task)
	{
		if (_instance == null)
			return false;
		Token token = (Token)_tasks.remove(task);
		if (token == null)
		{
			return false;
		} else
		{
			_tokens.remove(token);
			return true;
		}
	}

	Timer(Instance instance)
	{
		_wakeUpTime = 0x7fffffffffffffffL;
		_tokenId = 0;
		_instance = instance;
		String threadName = _instance.initializationData().properties.getProperty("Ice.ProgramName");
		if (threadName.length() > 0)
			threadName = (new StringBuilder()).append(threadName).append("-").toString();
		setName((new StringBuilder()).append(threadName).append("Ice.Timer").toString());
		start();
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_instance == null);
		super.finalize();
	}

	public void run()
	{
		Token token = null;
_L6:
label0:
		{
			synchronized (this)
			{
				if (_instance != null && token != null && token.delay > 0L && _tasks.containsKey(token.task))
				{
					token.scheduledTime = Time.currentMonotonicTimeMillis() + token.delay;
					_tokens.add(token);
				}
				token = null;
				if (_instance != null)
					break label0;
			}
			break; /* Loop/switch isn't completed */
		}
		if (_tokens.isEmpty())
		{
			_wakeUpTime = 0x7fffffffffffffffL;
			do
				try
				{
					wait();
					break;
				}
				catch (InterruptedException ex) { }
			while (true);
		}
		if (_instance != null)
			break MISSING_BLOCK_LABEL_121;
		timer;
		JVM INSTR monitorexit ;
		break; /* Loop/switch isn't completed */
_L2:
		long now;
		Token first;
		if (_tokens.isEmpty() || _instance == null)
			break MISSING_BLOCK_LABEL_240;
		now = Time.currentMonotonicTimeMillis();
		first = (Token)_tokens.first();
		if (first.scheduledTime <= now)
		{
			_tokens.remove(first);
			token = first;
			if (token.delay == 0L)
				_tasks.remove(token.task);
			break MISSING_BLOCK_LABEL_240;
		}
		_wakeUpTime = first.scheduledTime;
_L3:
		wait(first.scheduledTime - now);
		if (true) goto _L2; else goto _L1
_L1:
		InterruptedException ex;
		ex;
		  goto _L3
		if (_instance != null)
			break MISSING_BLOCK_LABEL_252;
		timer;
		JVM INSTR monitorexit ;
		break; /* Loop/switch isn't completed */
		timer;
		JVM INSTR monitorexit ;
		  goto _L4
		exception;
		throw exception;
_L4:
		if (token != null)
			try
			{
				token.task.runTimerTask();
			}
			catch (Exception ex)
			{
				synchronized (this)
				{
					if (_instance != null)
					{
						String s = (new StringBuilder()).append("unexpected exception from task run method in timer thread:\n").append(Ex.toString(ex)).toString();
						_instance.initializationData().logger.error(s);
					}
				}
			}
		if (true) goto _L6; else goto _L5
_L5:
	}

}
