// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BatchOutgoing.java

package IceInternal;

import Ice.ConnectionI;
import Ice.LocalException;

// Referenced classes of package IceInternal:
//			BasicStream, OutgoingMessageCallback, RequestHandler, Reference, 
//			Instance

public final class BatchOutgoing
	implements OutgoingMessageCallback
{

	private RequestHandler _handler;
	private ConnectionI _connection;
	private BasicStream _os;
	private boolean _sent;
	private LocalException _exception;
	static final boolean $assertionsDisabled = !IceInternal/BatchOutgoing.desiredAssertionStatus();

	public BatchOutgoing(ConnectionI connection, Instance instance)
	{
		_connection = connection;
		_sent = false;
		_os = new BasicStream(instance);
	}

	public BatchOutgoing(RequestHandler handler)
	{
		_handler = handler;
		_sent = false;
		_os = new BasicStream(handler.getReference().getInstance());
	}

	public void invoke()
	{
		if (!$assertionsDisabled && _handler == null && _connection == null)
			throw new AssertionError();
		if (_handler != null && !_handler.flushBatchRequests(this) || _connection != null && !_connection.flushBatchRequests(this))
			synchronized (this)
			{
				while (_exception == null && !_sent) 
					try
					{
						wait();
					}
					catch (InterruptedException ex) { }
				if (_exception != null)
					throw _exception;
			}
	}

	public void sent(boolean async)
	{
		if (async)
			synchronized (this)
			{
				_sent = true;
				notify();
			}
		else
			_sent = true;
	}

	public synchronized void finished(LocalException ex, boolean sent)
	{
		_exception = ex;
		notify();
	}

	public BasicStream os()
	{
		return _os;
	}

}
