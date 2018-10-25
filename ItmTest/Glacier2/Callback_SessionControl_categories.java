// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SessionControl_categories.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			SessionControlPrx, StringSetPrx

public abstract class Callback_SessionControl_categories extends TwowayCallback
{

	public Callback_SessionControl_categories()
	{
	}

	public abstract void response(StringSetPrx stringsetprx);

	public final void __completed(AsyncResult __result)
	{
		SessionControlPrx __proxy = (SessionControlPrx)__result.getProxy();
		StringSetPrx __ret = null;
		try
		{
			__ret = __proxy.end_categories(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
