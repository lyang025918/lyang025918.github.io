// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterHolder.java

package Ice;

import IceInternal.Ex;

// Referenced classes of package Ice:
//			ObjectHolderBase, Router, Object, _RouterDisp

public final class RouterHolder extends ObjectHolderBase
{

	public RouterHolder()
	{
	}

	public RouterHolder(Router value)
	{
		this.value = value;
	}

	public void patch(Object v)
	{
		try
		{
			value = (Router)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _RouterDisp.ice_staticId();
	}
}
