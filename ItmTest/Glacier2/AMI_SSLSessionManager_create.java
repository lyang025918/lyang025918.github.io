// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_SSLSessionManager_create.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			Callback_SSLSessionManager_create, SessionPrx

public abstract class AMI_SSLSessionManager_create extends Callback_SSLSessionManager_create
{

	public AMI_SSLSessionManager_create()
	{
	}

	public abstract void ice_response(SessionPrx sessionprx);

	public abstract void ice_exception(LocalException localexception);

	public abstract void ice_exception(UserException userexception);

	public final void response(SessionPrx __ret)
	{
		ice_response(__ret);
	}

	public final void exception(UserException __ex)
	{
		ice_exception(__ex);
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
