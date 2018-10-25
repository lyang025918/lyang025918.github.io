// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrderedLoadBalancingPolicyPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			OrderedLoadBalancingPolicyPrx, _OrderedLoadBalancingPolicyDelM, _OrderedLoadBalancingPolicyDelD

public final class OrderedLoadBalancingPolicyPrxHelper extends ObjectPrxHelperBase
	implements OrderedLoadBalancingPolicyPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::LoadBalancingPolicy", "::IceGrid::OrderedLoadBalancingPolicy"
	};

	public OrderedLoadBalancingPolicyPrxHelper()
	{
	}

	public static OrderedLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj)
	{
		OrderedLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (OrderedLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					OrderedLoadBalancingPolicyPrxHelper __h = new OrderedLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static OrderedLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		OrderedLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (OrderedLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					OrderedLoadBalancingPolicyPrxHelper __h = new OrderedLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static OrderedLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		OrderedLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					OrderedLoadBalancingPolicyPrxHelper __h = new OrderedLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static OrderedLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		OrderedLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					OrderedLoadBalancingPolicyPrxHelper __h = new OrderedLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static OrderedLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj)
	{
		OrderedLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (OrderedLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				OrderedLoadBalancingPolicyPrxHelper __h = new OrderedLoadBalancingPolicyPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static OrderedLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		OrderedLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			OrderedLoadBalancingPolicyPrxHelper __h = new OrderedLoadBalancingPolicyPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[2];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _OrderedLoadBalancingPolicyDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _OrderedLoadBalancingPolicyDelD();
	}

	public static void __write(BasicStream __os, OrderedLoadBalancingPolicyPrx v)
	{
		__os.writeProxy(v);
	}

	public static OrderedLoadBalancingPolicyPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			OrderedLoadBalancingPolicyPrxHelper result = new OrderedLoadBalancingPolicyPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
