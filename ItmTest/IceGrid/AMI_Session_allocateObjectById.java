// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_Session_allocateObjectById.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			Callback_Session_allocateObjectById

public abstract class AMI_Session_allocateObjectById extends Callback_Session_allocateObjectById
{

	public AMI_Session_allocateObjectById()
	{
	}

	public abstract void ice_response(ObjectPrx objectprx);

	public abstract void ice_exception(LocalException localexception);

	public abstract void ice_exception(UserException userexception);

	public final void response(ObjectPrx __ret)
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
