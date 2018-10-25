// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceObserverHolder.java

package IceBox;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceBox:
//			ServiceObserver, _ServiceObserverDisp

public final class ServiceObserverHolder extends ObjectHolderBase
{

	public ServiceObserverHolder()
	{
	}

	public ServiceObserverHolder(ServiceObserver value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (ServiceObserver)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _ServiceObserverDisp.ice_staticId();
	}
}
