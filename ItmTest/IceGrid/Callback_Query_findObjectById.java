// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Query_findObjectById.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			QueryPrx

public abstract class Callback_Query_findObjectById extends TwowayCallback
{

	public Callback_Query_findObjectById()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public final void __completed(AsyncResult __result)
	{
		QueryPrx __proxy = (QueryPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_findObjectById(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
