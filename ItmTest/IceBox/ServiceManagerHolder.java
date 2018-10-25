// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceManagerHolder.java

package IceBox;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceBox:
//			ServiceManager, _ServiceManagerDisp

public final class ServiceManagerHolder extends ObjectHolderBase
{

	public ServiceManagerHolder()
	{
	}

	public ServiceManagerHolder(ServiceManager value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (ServiceManager)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _ServiceManagerDisp.ice_staticId();
	}
}
