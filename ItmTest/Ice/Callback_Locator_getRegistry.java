// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Locator_getRegistry.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, LocatorPrx, LocalException, AsyncResult, 
//			LocatorRegistryPrx

public abstract class Callback_Locator_getRegistry extends TwowayCallback
{

	public Callback_Locator_getRegistry()
	{
	}

	public abstract void response(LocatorRegistryPrx locatorregistryprx);

	public final void __completed(AsyncResult __result)
	{
		LocatorPrx __proxy = (LocatorPrx)__result.getProxy();
		LocatorRegistryPrx __ret = null;
		try
		{
			__ret = __proxy.end_getRegistry(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
