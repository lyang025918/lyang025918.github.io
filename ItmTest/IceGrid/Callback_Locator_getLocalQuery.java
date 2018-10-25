// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Locator_getLocalQuery.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			LocatorPrx, QueryPrx

public abstract class Callback_Locator_getLocalQuery extends TwowayCallback
{

	public Callback_Locator_getLocalQuery()
	{
	}

	public abstract void response(QueryPrx queryprx);

	public final void __completed(AsyncResult __result)
	{
		LocatorPrx __proxy = (LocatorPrx)__result.getProxy();
		QueryPrx __ret = null;
		try
		{
			__ret = __proxy.end_getLocalQuery(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
