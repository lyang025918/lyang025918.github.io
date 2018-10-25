// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommunicatorBatchOutgoingAsync.java

package IceInternal;

import Ice.*;

// Referenced classes of package IceInternal:
//			BatchOutgoingAsync, Instance, CallbackBase

public class CommunicatorBatchOutgoingAsync extends BatchOutgoingAsync
{

	private Communicator _communicator;
	private int _useCount;
	private Callback _cb;
	static final boolean $assertionsDisabled = !IceInternal/CommunicatorBatchOutgoingAsync.desiredAssertionStatus();

	public CommunicatorBatchOutgoingAsync(Communicator communicator, Instance instance, String operation, CallbackBase callback)
	{
		super(instance, operation, callback);
		_cb = new Callback() {

			final CommunicatorBatchOutgoingAsync this$0;

			public void completed(AsyncResult r)
			{
				CommunicatorBatchOutgoingAsync.this.completed(r);
			}

			public void sent(AsyncResult r)
			{
				CommunicatorBatchOutgoingAsync.this.sent(r);
			}

			
			{
				this$0 = CommunicatorBatchOutgoingAsync.this;
				super();
			}
		};
		_communicator = communicator;
		_useCount = 1;
		_sentSynchronously = true;
	}

	public Communicator getCommunicator()
	{
		return _communicator;
	}

	public void flushConnection(Connection con)
	{
		synchronized (_monitor)
		{
			_useCount++;
		}
		con.begin_flushBatchRequests(_cb);
	}

	public void ready()
	{
		check(null, null, true);
	}

	private void completed(AsyncResult r)
	{
		Connection con = r.getConnection();
		if (!$assertionsDisabled && con == null)
			throw new AssertionError();
		try
		{
			con.end_flushBatchRequests(r);
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
		catch (LocalException ex)
		{
			check(r, ex, false);
		}
	}

	private void sent(AsyncResult r)
	{
		check(r, null, r.sentSynchronously());
	}

	private void check(AsyncResult r, LocalException ex, boolean userThread)
	{
		boolean done = false;
		synchronized (_monitor)
		{
			if (!$assertionsDisabled && _useCount <= 0)
				throw new AssertionError();
			_useCount--;
			if (r != null && !r.sentSynchronously() || ex != null)
				_sentSynchronously = false;
			if (_useCount == 0)
			{
				done = true;
				_state |= 7;
				_monitor.notifyAll();
			}
		}
		if (done)
			if (!_sentSynchronously && userThread)
			{
				__sentAsync();
			} else
			{
				if (!$assertionsDisabled && _sentSynchronously != userThread)
					throw new AssertionError();
				__sent();
			}
	}



}
