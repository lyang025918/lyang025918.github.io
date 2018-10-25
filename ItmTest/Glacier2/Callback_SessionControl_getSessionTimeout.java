// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SessionControl_getSessionTimeout.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			SessionControlPrx

public abstract class Callback_SessionControl_getSessionTimeout extends TwowayCallback
{

	public Callback_SessionControl_getSessionTimeout()
	{
	}

	public abstract void response(int i);

	public final void __completed(AsyncResult __result)
	{
		SessionControlPrx __proxy = (SessionControlPrx)__result.getProxy();
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
