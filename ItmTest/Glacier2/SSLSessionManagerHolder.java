// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSLSessionManagerHolder.java

package Glacier2;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package Glacier2:
//			SSLSessionManager, _SSLSessionManagerDisp

public final class SSLSessionManagerHolder extends ObjectHolderBase
{

	public SSLSessionManagerHolder()
	{
	}

	public SSLSessionManagerHolder(SSLSessionManager value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (SSLSessionManager)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _SSLSessionManagerDisp.ice_staticId();
	}
}
