// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceDescriptorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ServiceDescriptorPrx, _ServiceDescriptorDelM, _ServiceDescriptorDelD

public final class ServiceDescriptorPrxHelper extends ObjectPrxHelperBase
	implements ServiceDescriptorPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor", "::IceGrid::ServiceDescriptor"
	};

	public ServiceDescriptorPrxHelper()
	{
	}

	public static ServiceDescriptorPrx checkedCast(ObjectPrx __obj)
	{
		ServiceDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ServiceDescriptorPrxHelper __h = new ServiceDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServiceDescriptorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ServiceDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ServiceDescriptorPrxHelper __h = new ServiceDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServiceDescriptorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ServiceDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ServiceDescriptorPrxHelper __h = new ServiceDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServiceDescriptorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ServiceDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ServiceDescriptorPrxHelper __h = new ServiceDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServiceDescriptorPrx uncheckedCast(ObjectPrx __obj)
	{
		ServiceDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ServiceDescriptorPrxHelper __h = new ServiceDescriptorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ServiceDescriptorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ServiceDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ServiceDescriptorPrxHelper __h = new ServiceDescriptorPrxHelper();
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
		return new _ServiceDescriptorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ServiceDescriptorDelD();
	}

	public static void __write(BasicStream __os, ServiceDescriptorPrx v)
	{
		__os.writeProxy(v);
	}

	public static ServiceDescriptorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ServiceDescriptorPrxHelper result = new ServiceDescriptorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
