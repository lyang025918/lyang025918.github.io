// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrderedLoadBalancingPolicyHolder.java

package IceGrid;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceGrid:
//			OrderedLoadBalancingPolicy

public final class OrderedLoadBalancingPolicyHolder extends ObjectHolderBase
{

	public OrderedLoadBalancingPolicyHolder()
	{
	}

	public OrderedLoadBalancingPolicyHolder(OrderedLoadBalancingPolicy value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (OrderedLoadBalancingPolicy)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return OrderedLoadBalancingPolicy.ice_staticId();
	}
}
