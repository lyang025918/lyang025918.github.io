// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Registry_getSessionTimeout.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			RegistryPrx

public abstract class Callback_Registry_getSessionTimeout extends TwowayCallback
{

	public Callback_Registry_getSessionTimeout()
	{
	}

	public abstract void response(int i);

	public final void __completed(AsyncResult __result)
	{
		RegistryPrx __proxy = (RegistryPrx)__result.getProxy();
		int __ret = 0;
		try
		{
			__ret = __proxy.end_getSessionTimeout(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
