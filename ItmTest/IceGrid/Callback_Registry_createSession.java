// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Registry_createSession.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			RegistryPrx, SessionPrx

public abstract class Callback_Registry_createSession extends TwowayCallback
{

	public Callback_Registry_createSession()
	{
	}

	public abstract void response(SessionPrx sessionprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		RegistryPrx __proxy = (RegistryPrx)__result.getProxy();
		SessionPrx __ret = null;
		try
		{
			__ret = __proxy.end_createSession(__result);
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
