// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Router_getSessionTimeout.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			RouterPrx

public abstract class Callback_Router_getSessionTimeout extends TwowayCallback
{

	public Callback_Router_getSessionTimeout()
	{
	}

	public abstract void response(long l);

	public final void __completed(AsyncResult __result)
	{
		RouterPrx __proxy = (RouterPrx)__result.getProxy();
		long __ret = 0L;
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
