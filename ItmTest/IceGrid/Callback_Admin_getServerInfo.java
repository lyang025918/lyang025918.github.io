// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getServerInfo.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx, ServerInfo

public abstract class Callback_Admin_getServerInfo extends TwowayCallback
{

	public Callback_Admin_getServerInfo()
	{
	}

	public abstract void response(ServerInfo serverinfo);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		ServerInfo __ret = null;
		try
		{
			__ret = __proxy.end_getServerInfo(__result);
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
