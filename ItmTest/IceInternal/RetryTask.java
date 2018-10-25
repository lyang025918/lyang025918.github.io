// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RetryTask.java

package IceInternal;

import Ice.CommunicatorDestroyedException;
import Ice.LocalException;

// Referenced classes of package IceInternal:
//			TimerTask, RetryQueue, OutgoingAsync

class RetryTask
	implements TimerTask
{

	private final RetryQueue _queue;
	private final OutgoingAsync _outAsync;

	RetryTask(RetryQueue queue, OutgoingAsync outAsync)
	{
		_queue = queue;
		_outAsync = outAsync;
	}

	public void runTimerTask()
	{
		if (_queue.remove(this))
			try
			{
				_outAsync.__send(false);
			}
			catch (LocalException ex)
			{
				_outAsync.__exceptionAsync(ex);
			}
	}

	public void destroy()
	{
		_outAsync.__exceptionAsync(new CommunicatorDestroyedException());
	}
}
