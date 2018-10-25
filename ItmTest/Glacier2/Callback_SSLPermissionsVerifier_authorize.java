// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SSLPermissionsVerifier_authorize.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			SSLPermissionsVerifierPrx

public abstract class Callback_SSLPermissionsVerifier_authorize extends TwowayCallback
{

	public Callback_SSLPermissionsVerifier_authorize()
	{
	}

	public abstract void response(boolean flag, String s);

	public final void __completed(AsyncResult __result)
	{
		SSLPermissionsVerifierPrx __proxy = (SSLPermissionsVerifierPrx)__result.getProxy();
		boolean __ret = false;
		StringHolder reason = new StringHolder();
		try
		{
			__ret = __proxy.end_authorize(reason, __result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret, reason.value);
	}
}
