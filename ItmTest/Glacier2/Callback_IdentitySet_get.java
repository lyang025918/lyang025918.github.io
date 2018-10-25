// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_IdentitySet_get.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			IdentitySetPrx

public abstract class Callback_IdentitySet_get extends TwowayCallback
{

	public Callback_IdentitySet_get()
	{
	}

	public abstract void response(Identity aidentity[]);

	public final void __completed(AsyncResult __result)
	{
		IdentitySetPrx __proxy = (IdentitySetPrx)__result.getProxy();
		Identity __ret[] = null;
		try
		{
			__ret = __proxy.end_get(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
