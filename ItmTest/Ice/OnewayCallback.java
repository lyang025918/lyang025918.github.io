// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OnewayCallback.java

package Ice;

import IceInternal.CallbackBase;

// Referenced classes of package Ice:
//			ObjectPrxHelperBase, LocalException, AsyncResult

public abstract class OnewayCallback extends CallbackBase
{

	public OnewayCallback()
	{
	}

	public abstract void response();

	public abstract void exception(LocalException localexception);

	public void sent(boolean flag)
	{
	}

	public final void __sent(AsyncResult __result)
	{
		sent(__result.sentSynchronously());
	}

	public final void __completed(AsyncResult __result)
	{
		try
		{
			((ObjectPrxHelperBase)__result.getProxy()).__end(__result, __result.getOperation());
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response();
	}
}
