// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getServerState.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx, ServerState

public abstract class Callback_Admin_getServerState extends TwowayCallback
{

	public Callback_Admin_getServerState()
	{
	}

	public abstract void response(ServerState serverstate);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		ServerState __ret = null;
		try
		{
			__ret = __proxy.end_getServerState(__result);
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
