// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Router_destroySession.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			RouterPrx

public abstract class Callback_Router_destroySession extends TwowayCallback
{

	public Callback_Router_destroySession()
	{
	}

	public abstract void response();

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		RouterPrx __proxy = (RouterPrx)__result.getProxy();
		try
		{
			__proxy.end_destroySession(__result);
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
		response();
	}
}
