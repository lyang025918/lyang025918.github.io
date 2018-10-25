// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DispatchInterceptor.java

package Ice;

import IceInternal.Direct;
import IceInternal.Incoming;

// Referenced classes of package Ice:
//			ObjectImpl, ResponseSentException, DispatchStatus, Request, 
//			Current

public abstract class DispatchInterceptor extends ObjectImpl
{

	public DispatchInterceptor()
	{
	}

	public abstract DispatchStatus dispatch(Request request);

	public DispatchStatus __dispatch(Incoming in, Current current)
	{
		DispatchStatus status;
		status = dispatch(in);
		if (status != DispatchStatus.DispatchAsync)
			in.killAsync();
		return status;
		ResponseSentException e;
		e;
		return DispatchStatus.DispatchAsync;
		e;
		try
		{
			in.killAsync();
			throw e;
		}
		catch (ResponseSentException rse)
		{
			return DispatchStatus.DispatchAsync;
		}
	}

	public DispatchStatus __collocDispatch(Direct request)
	{
		return dispatch(request);
	}
}
