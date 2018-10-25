// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_Router_destroySession.java

package Glacier2;

import Ice.*;

// Referenced classes of package Glacier2:
//			Callback_Router_destroySession

public abstract class AMI_Router_destroySession extends Callback_Router_destroySession
{

	public AMI_Router_destroySession()
	{
	}

	public abstract void ice_response();

	public abstract void ice_exception(LocalException localexception);

	public abstract void ice_exception(UserException userexception);

	public final void response()
	{
		ice_response();
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
