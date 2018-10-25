// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Locator_getLocalRegistry.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			LocatorPrx, RegistryPrx

public abstract class Callback_Locator_getLocalRegistry extends TwowayCallback
{

	public Callback_Locator_getLocalRegistry()
	{
	}

	public abstract void response(RegistryPrx registryprx);

	public final void __completed(AsyncResult __result)
	{
		LocatorPrx __proxy = (LocatorPrx)__result.getProxy();
		RegistryPrx __ret = null;
		try
		{
			__ret = __proxy.end_getLocalRegistry(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
