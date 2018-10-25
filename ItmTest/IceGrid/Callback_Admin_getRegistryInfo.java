// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getRegistryInfo.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx, RegistryInfo

public abstract class Callback_Admin_getRegistryInfo extends TwowayCallback
{

	public Callback_Admin_getRegistryInfo()
	{
	}

	public abstract void response(RegistryInfo registryinfo);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		RegistryInfo __ret = null;
		try
		{
			__ret = __proxy.end_getRegistryInfo(__result);
		}
		catch (UserException __ex)
		{
			exception(__ex);
			return;
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
