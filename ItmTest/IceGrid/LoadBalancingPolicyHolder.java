// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoadBalancingPolicyHolder.java

package IceGrid;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceGrid:
//			LoadBalancingPolicy

public final class LoadBalancingPolicyHolder extends ObjectHolderBase
{

	public LoadBalancingPolicyHolder()
	{
	}

	public LoadBalancingPolicyHolder(LoadBalancingPolicy value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (LoadBalancingPolicy)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return LoadBalancingPolicy.ice_staticId();
	}
}
