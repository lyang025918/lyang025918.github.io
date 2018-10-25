// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RetryQueue.java

package IceInternal;

import java.util.HashSet;
import java.util.Iterator;

// Referenced classes of package IceInternal:
//			RetryTask, Instance, Timer, OutgoingAsync

public class RetryQueue
{

	private final Instance _instance;
	private final HashSet _requests = new HashSet();

	RetryQueue(Instance instance)
	{
		_instance = instance;
	}

	public synchronized void add(OutgoingAsync outAsync, int interval)
	{
		RetryTask task = new RetryTask(this, outAsync);
		_instance.timer().schedule(task, interval);
		_requests.add(task);
	}

	public synchronized void destroy()
	{
		RetryTask task;
		for (Iterator i$ = _requests.iterator(); i$.hasNext(); task.destroy())
		{
			task = (RetryTask)i$.next();
			_instance.timer().cancel(task);
		}

		_requests.clear();
	}

	synchronized boolean remove(RetryTask task)
	{
		return _requests.remove(task);
	}
}
