// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SessionControl_identities.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			SessionControlPrx, IdentitySetPrx

public abstract class Callback_SessionControl_identities extends TwowayCallback
{

	public Callback_SessionControl_identities()
	{
	}

	public abstract void response(IdentitySetPrx identitysetprx);

	public final void __completed(AsyncResult __result)
	{
		SessionControlPrx __proxy = (SessionControlPrx)__result.getProxy();
		IdentitySetPrx __ret = null;
		try
		{
			__ret = __proxy.end_identities(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
