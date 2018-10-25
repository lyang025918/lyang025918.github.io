// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSLPermissionsVerifierHolder.java

package Glacier2;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package Glacier2:
//			SSLPermissionsVerifier, _SSLPermissionsVerifierDisp

public final class SSLPermissionsVerifierHolder extends ObjectHolderBase
{

	public SSLPermissionsVerifierHolder()
	{
	}

	public SSLPermissionsVerifierHolder(SSLPermissionsVerifier value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (SSLPermissionsVerifier)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _SSLPermissionsVerifierDisp.ice_staticId();
	}
}
