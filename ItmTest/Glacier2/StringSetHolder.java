// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringSetHolder.java

package Glacier2;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package Glacier2:
//			StringSet, _StringSetDisp

public final class StringSetHolder extends ObjectHolderBase
{

	public StringSetHolder()
	{
	}

	public StringSetHolder(StringSet value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (StringSet)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _StringSetDisp.ice_staticId();
	}
}
