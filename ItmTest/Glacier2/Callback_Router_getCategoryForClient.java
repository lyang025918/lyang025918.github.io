// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Router_getCategoryForClient.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			RouterPrx

public abstract class Callback_Router_getCategoryForClient extends TwowayCallback
{

	public Callback_Router_getCategoryForClient()
	{
	}

	public abstract void response(String s);

	public final void __completed(AsyncResult __result)
	{
		RouterPrx __proxy = (RouterPrx)__result.getProxy();
		String __ret = null;
		try
		{
			__ret = __proxy.end_getCategoryForClient(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
