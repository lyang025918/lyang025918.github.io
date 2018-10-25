// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BatchOutgoingAsync.java

package IceInternal;

import Ice.*;

// Referenced classes of package IceInternal:
//			OutgoingAsyncMessageCallback, Instance, CallbackBase

public class BatchOutgoingAsync extends AsyncResult
	implements OutgoingAsyncMessageCallback
{

	public BatchOutgoingAsync(Instance instance, String operation, CallbackBase callback)
	{
		super(instance, operation, callback);
	}

	public boolean __sent(ConnectionI connection)
	{
		Object obj = _monitor;
		JVM INSTR monitorenter ;
		_state |= 7;
		_monitor.notifyAll();
		return true;
		Exception exception;
		exception;
		throw exception;
	}

	public void __sent()
	{
		__sentInternal();
	}

	public void __finished(LocalException exc, boolean sent)
	{
		__exception(exc);
	}
}
