// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdaptiveLoadBalancingPolicyPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AdaptiveLoadBalancingPolicyPrx, _AdaptiveLoadBalancingPolicyDelM, _AdaptiveLoadBalancingPolicyDelD

public final class AdaptiveLoadBalancingPolicyPrxHelper extends ObjectPrxHelperBase
	implements AdaptiveLoadBalancingPolicyPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::AdaptiveLoadBalancingPolicy", "::IceGrid::LoadBalancingPolicy"
	};

	public AdaptiveLoadBalancingPolicyPrxHelper()
	{
	}

	public static AdaptiveLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj)
	{
		AdaptiveLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdaptiveLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					AdaptiveLoadBalancingPolicyPrxHelper __h = new AdaptiveLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdaptiveLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		AdaptiveLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdaptiveLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					AdaptiveLoadBalancingPolicyPrxHelper __h = new AdaptiveLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdaptiveLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		AdaptiveLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					AdaptiveLoadBalancingPolicyPrxHelper __h = new AdaptiveLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdaptiveLoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		AdaptiveLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					AdaptiveLoadBalancingPolicyPrxHelper __h = new AdaptiveLoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdaptiveLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj)
	{
		AdaptiveLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdaptiveLoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				AdaptiveLoadBalancingPolicyPrxHelper __h = new AdaptiveLoadBalancingPolicyPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static AdaptiveLoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		AdaptiveLoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			AdaptiveLoadBalancingPolicyPrxHelper __h = new AdaptiveLoadBalancingPolicyPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _AdaptiveLoadBalancingPolicyDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _AdaptiveLoadBalancingPolicyDelD();
	}

	public static void __write(BasicStream __os, AdaptiveLoadBalancingPolicyPrx v)
	{
		__os.writeProxy(v);
	}

	public static AdaptiveLoadBalancingPolicyPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			AdaptiveLoadBalancingPolicyPrxHelper result = new AdaptiveLoadBalancingPolicyPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
