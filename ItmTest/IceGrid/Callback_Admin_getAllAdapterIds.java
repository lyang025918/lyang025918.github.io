// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getAllAdapterIds.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx

public abstract class Callback_Admin_getAllAdapterIds extends TwowayCallback
{

	public Callback_Admin_getAllAdapterIds()
	{
	}

	public abstract void response(String as[]);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		String __ret[] = null;
		try
		{
			__ret = __proxy.end_getAllAdapterIds(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
