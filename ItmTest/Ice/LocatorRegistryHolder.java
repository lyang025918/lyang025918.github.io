// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorRegistryHolder.java

package Ice;

import IceInternal.Ex;

// Referenced classes of package Ice:
//			ObjectHolderBase, LocatorRegistry, Object, _LocatorRegistryDisp

public final class LocatorRegistryHolder extends ObjectHolderBase
{

	public LocatorRegistryHolder()
	{
	}

	public LocatorRegistryHolder(LocatorRegistry value)
	{
		this.value = value;
	}

	public void patch(Object v)
	{
		try
		{
			value = (LocatorRegistry)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _LocatorRegistryDisp.ice_staticId();
	}
}
