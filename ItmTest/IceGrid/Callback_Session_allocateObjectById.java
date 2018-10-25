// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Session_allocateObjectById.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			SessionPrx

public abstract class Callback_Session_allocateObjectById extends TwowayCallback
{

	public Callback_Session_allocateObjectById()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		SessionPrx __proxy = (SessionPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_allocateObjectById(__result);
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
