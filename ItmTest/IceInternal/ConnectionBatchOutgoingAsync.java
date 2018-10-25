// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionBatchOutgoingAsync.java

package IceInternal;

import Ice.Connection;
import Ice.ConnectionI;

// Referenced classes of package IceInternal:
//			BatchOutgoingAsync, Instance, CallbackBase

public class ConnectionBatchOutgoingAsync extends BatchOutgoingAsync
{

	private ConnectionI _connection;

	public ConnectionBatchOutgoingAsync(ConnectionI con, Instance instance, String operation, CallbackBase callback)
	{
		super(instance, operation, callback);
		_connection = con;
	}

	public void __send()
	{
		int status = _connection.flushAsyncBatchRequests(this);
		if ((status & 1) > 0)
		{
			_sentSynchronously = true;
			if ((status & 2) > 0)
				__sent();
		}
	}

	public Connection getConnection()
	{
		return _connection;
	}
}
