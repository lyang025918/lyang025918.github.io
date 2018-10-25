// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_ServiceManager_startService.java

package IceBox;

import Ice.*;

// Referenced classes of package IceBox:
//			ServiceManagerPrx

public abstract class Callback_ServiceManager_startService extends TwowayCallback
{

	public Callback_ServiceManager_startService()
	{
	}

	public abstract void response();

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		ServiceManagerPrx __proxy = (ServiceManagerPrx)__result.getProxy();
		try
		{
			__proxy.end_startService(__result);
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
