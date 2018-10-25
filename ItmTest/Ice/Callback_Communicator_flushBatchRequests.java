// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Communicator_flushBatchRequests.java

package Ice;

import IceInternal.CallbackBase;

// Referenced classes of package Ice:
//			LocalException, AsyncResult, Communicator

public abstract class Callback_Communicator_flushBatchRequests extends CallbackBase
{

	public Callback_Communicator_flushBatchRequests()
	{
	}

	public abstract void exception(LocalException localexception);

	public void sent(boolean flag)
	{
	}

	public final void __completed(AsyncResult __result)
	{
		try
		{
			__result.getCommunicator().end_flushBatchRequests(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
		}
	}

	public final void __sent(AsyncResult __result)
	{
		sent(__result.sentSynchronously());
	}
}
