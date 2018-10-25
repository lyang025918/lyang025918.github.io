// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionControlHolder.java

package Glacier2;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package Glacier2:
//			SessionControl, _SessionControlDisp

public final class SessionControlHolder extends ObjectHolderBase
{

	public SessionControlHolder()
	{
	}

	public SessionControlHolder(SessionControl value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (SessionControl)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _SessionControlDisp.ice_staticId();
	}
}
