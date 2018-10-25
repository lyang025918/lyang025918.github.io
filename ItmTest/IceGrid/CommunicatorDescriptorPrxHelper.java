// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommunicatorDescriptorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			CommunicatorDescriptorPrx, _CommunicatorDescriptorDelM, _CommunicatorDescriptorDelD

public final class CommunicatorDescriptorPrxHelper extends ObjectPrxHelperBase
	implements CommunicatorDescriptorPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor"
	};

	public CommunicatorDescriptorPrxHelper()
	{
	}

	public static CommunicatorDescriptorPrx checkedCast(ObjectPrx __obj)
	{
		CommunicatorDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (CommunicatorDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					CommunicatorDescriptorPrxHelper __h = new CommunicatorDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static CommunicatorDescriptorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		CommunicatorDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (CommunicatorDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					CommunicatorDescriptorPrxHelper __h = new CommunicatorDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static CommunicatorDescriptorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		CommunicatorDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					CommunicatorDescriptorPrxHelper __h = new CommunicatorDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static CommunicatorDescriptorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		CommunicatorDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					CommunicatorDescriptorPrxHelper __h = new CommunicatorDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static CommunicatorDescriptorPrx uncheckedCast(ObjectPrx __obj)
	{
		CommunicatorDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (CommunicatorDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				CommunicatorDescriptorPrxHelper __h = new CommunicatorDescriptorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static CommunicatorDescriptorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		CommunicatorDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			CommunicatorDescriptorPrxHelper __h = new CommunicatorDescriptorPrxHelper();
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
		return new _CommunicatorDescriptorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _CommunicatorDescriptorDelD();
	}

	public static void __write(BasicStream __os, CommunicatorDescriptorPrx v)
	{
		__os.writeProxy(v);
	}

	public static CommunicatorDescriptorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			CommunicatorDescriptorPrxHelper result = new CommunicatorDescriptorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
