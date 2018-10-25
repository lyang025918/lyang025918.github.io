// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_PropertiesAdmin_getPropertiesForPrefix.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			TwowayCallback, PropertiesAdminPrx, LocalException, AsyncResult

public abstract class Callback_PropertiesAdmin_getPropertiesForPrefix extends TwowayCallback
{

	public Callback_PropertiesAdmin_getPropertiesForPrefix()
	{
	}

	public abstract void response(Map map);

	public final void __completed(AsyncResult __result)
	{
		PropertiesAdminPrx __proxy = (PropertiesAdminPrx)__result.getProxy();
		Map __ret = null;
		try
		{
			__ret = __proxy.end_getPropertiesForPrefix(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
