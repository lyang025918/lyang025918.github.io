// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getSliceChecksums.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AdminPrx

public abstract class Callback_Admin_getSliceChecksums extends TwowayCallback
{

	public Callback_Admin_getSliceChecksums()
	{
	}

	public abstract void response(Map map);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		Map __ret = null;
		try
		{
			__ret = __proxy.end_getSliceChecksums(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
