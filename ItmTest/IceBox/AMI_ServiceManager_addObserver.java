// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_ServiceManager_addObserver.java

package IceBox;

import Ice.AMISentCallback;
import Ice.LocalException;

// Referenced classes of package IceBox:
//			Callback_ServiceManager_addObserver

public abstract class AMI_ServiceManager_addObserver extends Callback_ServiceManager_addObserver
{

	public AMI_ServiceManager_addObserver()
	{
	}

	public abstract void ice_response();

	public abstract void ice_exception(LocalException localexception);

	public final void response()
	{
		ice_response();
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
