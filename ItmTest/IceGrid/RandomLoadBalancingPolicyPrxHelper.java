// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RandomLoadBalancingPolicyPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			RandomLoadBalancingPolicyPrx, _RandomLoadBalancingPolicyDelM, _RandomLoadBalancingPolicyDelD

public final class RandomLoadBalancingPolicyPrxHelper extends ObjectPrxHelperBase
	implements RandomLoadBalancingPolicyPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::LoadBalancingPolicy", "::IceGrid::RandomLoadBalancingPolicy"
	};

	public RandomLoadBalancingPolicyPrxHelper()
	{
	}

	public static RandomLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj)
	{
		RandomLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RandomLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					RandomLoadBalancingPolicyPrxHelper __h = new RandomLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RandomLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		RandomLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RandomLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					RandomLoadBalancingPolicyPrxHelper __h = new RandomLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RandomLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		RandomLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					RandomLoadBalancingPolicyPrxHelper __h = new RandomLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RandomLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		RandomLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					RandomLoadBalancingPolicyPrxHelper __h = new RandomLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RandomLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj)
	{
		RandomLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RandomLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				RandomLoadBalancingPolicyPrxHelper __h = new RandomLoadBalancingPolicyPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static RandomLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		RandomLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			RandomLoadBalancingPolicyPrxHelper __h = new RandomLoadBalancingPolicyPrxHelper();
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
		return new _RandomLoadBalancingPolicyDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _RandomLoadBalancingPolicyDelD();
	}

	public static void __write(BasicStream __os, RandomLoadBalancingPolicyPrx v)
	{
		__os.writeProxy(v);
	}

	public static RandomLoadBalancingPolicyPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			RandomLoadBalancingPolicyPrxHelper result = new RandomLoadBalancingPolicyPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
