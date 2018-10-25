// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoundRobinLoadBalancingPolicyPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			RoundRobinLoadBalancingPolicyPrx, _RoundRobinLoadBalancingPolicyDelM, _RoundRobinLoadBalancingPolicyDelD

public final class RoundRobinLoadBalancingPolicyPrxHelper extends ObjectPrxHelperBase
	implements RoundRobinLoadBalancingPolicyPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::LoadBalancingPolicy", "::IceGrid::RoundRobinLoadBalancingPolicy"
	};

	public RoundRobinLoadBalancingPolicyPrxHelper()
	{
	}

	public static RoundRobinLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj)
	{
		RoundRobinLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RoundRobinLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					RoundRobinLoadBalancingPolicyPrxHelper __h = new RoundRobinLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RoundRobinLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		RoundRobinLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RoundRobinLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					RoundRobinLoadBalancingPolicyPrxHelper __h = new RoundRobinLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RoundRobinLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		RoundRobinLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					RoundRobinLoadBalancingPolicyPrxHelper __h = new RoundRobinLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RoundRobinLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		RoundRobinLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					RoundRobinLoadBalancingPolicyPrxHelper __h = new RoundRobinLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RoundRobinLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj)
	{
		RoundRobinLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RoundRobinLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				RoundRobinLoadBalancingPolicyPrxHelper __h = new RoundRobinLoadBalancingPolicyPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static RoundRobinLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		RoundRobinLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			RoundRobinLoadBalancingPolicyPrxHelper __h = new RoundRobinLoadBalancingPolicyPrxHelper();
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
		return new _RoundRobinLoadBalancingPolicyDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _RoundRobinLoadBalancingPolicyDelD();
	}

	public static void __write(BasicStream __os, RoundRobinLoadBalancingPolicyPrx v)
	{
		__os.writeProxy(v);
	}

	public static RoundRobinLoadBalancingPolicyPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			RoundRobinLoadBalancingPolicyPrxHelper result = new RoundRobinLoadBalancingPolicyPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
