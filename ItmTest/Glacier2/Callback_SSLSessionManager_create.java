// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SSLSessionManager_create.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			SSLSessionManagerPrx, SessionPrx

public abstract class Callback_SSLSessionManager_create extends TwowayCallback
{

	public Callback_SSLSessionManager_create()
	{
	}

	public abstract void response(SessionPrx sessionprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		SSLSessionManagerPrx __proxy = (SSLSessionManagerPrx)__result.getProxy();
		SessionPrx __ret = null;
		try
		{
			__ret = __proxy.end_create(__result);
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
