// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IceBoxDescriptorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			IceBoxDescriptorPrx, _IceBoxDescriptorDelM, _IceBoxDescriptorDelD

public final class IceBoxDescriptorPrxHelper extends ObjectPrxHelperBase
	implements IceBoxDescriptorPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor", "::IceGrid::IceBoxDescriptor", "::IceGrid::ServerDescriptor"
	};

	public IceBoxDescriptorPrxHelper()
	{
	}

	public static IceBoxDescriptorPrx checkedCast(ObjectPrx __obj)
	{
		IceBoxDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (IceBoxDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					IceBoxDescriptorPrxHelper __h = new IceBoxDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static IceBoxDescriptorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		IceBoxDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (IceBoxDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					IceBoxDescriptorPrxHelper __h = new IceBoxDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static IceBoxDescriptorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		IceBoxDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					IceBoxDescriptorPrxHelper __h = new IceBoxDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static IceBoxDescriptorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		IceBoxDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					IceBoxDescriptorPrxHelper __h = new IceBoxDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static IceBoxDescriptorPrx uncheckedCast(ObjectPrx __obj)
	{
		IceBoxDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (IceBoxDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				IceBoxDescriptorPrxHelper __h = new IceBoxDescriptorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static IceBoxDescriptorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		IceBoxDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			IceBoxDescriptorPrxHelper __h = new IceBoxDescriptorPrxHelper();
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
		return new _IceBoxDescriptorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _IceBoxDescriptorDelD();
	}

	public static void __write(BasicStream __os, IceBoxDescriptorPrx v)
	{
		__os.writeProxy(v);
	}

	public static IceBoxDescriptorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			IceBoxDescriptorPrxHelper result = new IceBoxDescriptorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
