// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_AdminSession_getAdminCallbackTemplate.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminSessionPrx

public abstract class Callback_AdminSession_getAdminCallbackTemplate extends TwowayCallback
{

	public Callback_AdminSession_getAdminCallbackTemplate()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public final void __completed(AsyncResult __result)
	{
		AdminSessionPrx __proxy = (AdminSessionPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_getAdminCallbackTemplate(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
