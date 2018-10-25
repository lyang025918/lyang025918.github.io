// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserAccountMapperHolder.java

package IceGrid;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceGrid:
//			UserAccountMapper, _UserAccountMapperDisp

public final class UserAccountMapperHolder extends ObjectHolderBase
{

	public UserAccountMapperHolder()
	{
	}

	public UserAccountMapperHolder(UserAccountMapper value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (UserAccountMapper)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _UserAccountMapperDisp.ice_staticId();
	}
}
