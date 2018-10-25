// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoadBalancingPolicyPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			LoadBalancingPolicyPrx, _LoadBalancingPolicyDelM, _LoadBalancingPolicyDelD

public final class LoadBalancingPolicyPrxHelper extends ObjectPrxHelperBase
	implements LoadBalancingPolicyPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::LoadBalancingPolicy"
	};

	public LoadBalancingPolicyPrxHelper()
	{
	}

	public static LoadBalancingPolicyPrx checkedCast(ObjectPrx __obj)
	{
		LoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					LoadBalancingPolicyPrxHelper __h = new LoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static LoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		LoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					LoadBalancingPolicyPrxHelper __h = new LoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static LoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		LoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					LoadBalancingPolicyPrxHelper __h = new LoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static LoadBalancingPolicyPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		LoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					LoadBalancingPolicyPrxHelper __h = new LoadBalancingPolicyPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static LoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj)
	{
		LoadBalancingPolicyPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LoadBalancingPolicyPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				LoadBalancingPolicyPrxHelper __h = new LoadBalancingPolicyPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static LoadBalancingPolicyPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		LoadBalancingPolicyPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			LoadBalancingPolicyPrxHelper __h = new LoadBalancingPolicyPrxHelper();
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
		return new _LoadBalancingPolicyDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _LoadBalancingPolicyDelD();
	}

	public static void __write(BasicStream __os, LoadBalancingPolicyPrx v)
	{
		__os.writeProxy(v);
	}

	public static LoadBalancingPolicyPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			LoadBalancingPolicyPrxHelper result = new LoadBalancingPolicyPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
