// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProxyBatchOutgoingAsync.java

package IceInternal;

import Ice.*;

// Referenced classes of package IceInternal:
//			BatchOutgoingAsync, Reference, RequestHandler, CallbackBase

public class ProxyBatchOutgoingAsync extends BatchOutgoingAsync
{

	private ObjectPrx _proxy;

	public ProxyBatchOutgoingAsync(ObjectPrx prx, String operation, CallbackBase callback)
	{
		super(((ObjectPrxHelperBase)prx).__reference().getInstance(), operation, callback);
		_proxy = prx;
	}

	public void __send()
	{
		_ObjectDel delegate = null;
		int cnt = -1;
		try
		{
			delegate = ((ObjectPrxHelperBase)_proxy).__getDelegate(false);
			int status = delegate.__getRequestHandler().flushAsyncBatchRequests(this);
			if ((status & 1) > 0)
			{
				_sentSynchronously = true;
				if ((status & 2) > 0)
					__sent();
			}
		}
		catch (LocalException __ex)
		{
			cnt = ((ObjectPrxHelperBase)_proxy).__handleException(delegate, __ex, null, cnt);
		}
	}

	public ObjectPrx getProxy()
	{
		return _proxy;
	}
}
