// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_SSLPermissionsVerifier_authorize.java

package Glacier2;

import Ice.AMISentCallback;
import Ice.LocalException;

// Referenced classes of package Glacier2:
//			Callback_SSLPermissionsVerifier_authorize

public abstract class AMI_SSLPermissionsVerifier_authorize extends Callback_SSLPermissionsVerifier_authorize
{

	public AMI_SSLPermissionsVerifier_authorize()
	{
	}

	public abstract void ice_response(boolean flag, String s);

	public abstract void ice_exception(LocalException localexception);

	public final void response(boolean __ret, String reason)
	{
		ice_response(__ret, reason);
	}

	public final void exception(LocalException __ex)
	{
		ice_exception(__ex);
	}

	public final void sent(boolean sentSynchronously)
	{
		if (!sentSynchronously && (this instanceof AMISentCallback))
			((AMISentCallback)this).ice_sent();
	}
}
