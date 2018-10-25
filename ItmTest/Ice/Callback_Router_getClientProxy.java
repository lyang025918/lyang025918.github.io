// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Router_getClientProxy.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, RouterPrx, LocalException, AsyncResult, 
//			ObjectPrx

public abstract class Callback_Router_getClientProxy extends TwowayCallback
{

	public Callback_Router_getClientProxy()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public final void __completed(AsyncResult __result)
	{
		RouterPrx __proxy = (RouterPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_getClientProxy(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
