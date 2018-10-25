// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_StringSet_get.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			StringSetPrx

public abstract class Callback_StringSet_get extends TwowayCallback
{

	public Callback_StringSet_get()
	{
	}

	public abstract void response(String as[]);

	public final void __completed(AsyncResult __result)
	{
		StringSetPrx __proxy = (StringSetPrx)__result.getProxy();
		String __ret[] = null;
		try
		{
			__ret = __proxy.end_get(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
