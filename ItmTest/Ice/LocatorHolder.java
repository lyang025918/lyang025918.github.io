// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorHolder.java

package Ice;

import IceInternal.Ex;

// Referenced classes of package Ice:
//			ObjectHolderBase, Locator, Object, _LocatorDisp

public final class LocatorHolder extends ObjectHolderBase
{

	public LocatorHolder()
	{
	}

	public LocatorHolder(Locator value)
	{
		this.value = value;
	}

	public void patch(Object v)
	{
		try
		{
			value = (Locator)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _LocatorDisp.ice_staticId();
	}
}
