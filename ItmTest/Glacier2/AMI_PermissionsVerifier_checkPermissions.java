// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_PermissionsVerifier_checkPermissions.java

package Glacier2;

import Ice.AMISentCallback;
import Ice.LocalException;

// Referenced classes of package Glacier2:
//			Callback_PermissionsVerifier_checkPermissions

public abstract class AMI_PermissionsVerifier_checkPermissions extends Callback_PermissionsVerifier_checkPermissions
{

	public AMI_PermissionsVerifier_checkPermissions()
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
