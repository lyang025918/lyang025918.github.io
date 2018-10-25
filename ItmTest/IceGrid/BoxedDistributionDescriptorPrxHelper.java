// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoxedDistributionDescriptorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			BoxedDistributionDescriptorPrx, _BoxedDistributionDescriptorDelM, _BoxedDistributionDescriptorDelD

public final class BoxedDistributionDescriptorPrxHelper extends ObjectPrxHelperBase
	implements BoxedDistributionDescriptorPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::BoxedDistributionDescriptor"
	};

	public BoxedDistributionDescriptorPrxHelper()
	{
	}

	public static BoxedDistributionDescriptorPrx checkedCast(ObjectPrx __obj)
	{
		BoxedDistributionDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (BoxedDistributionDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					BoxedDistributionDescriptorPrxHelper __h = new BoxedDistributionDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static BoxedDistributionDescriptorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		BoxedDistributionDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (BoxedDistributionDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					BoxedDistributionDescriptorPrxHelper __h = new BoxedDistributionDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static BoxedDistributionDescriptorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		BoxedDistributionDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					BoxedDistributionDescriptorPrxHelper __h = new BoxedDistributionDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static BoxedDistributionDescriptorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		BoxedDistributionDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					BoxedDistributionDescriptorPrxHelper __h = new BoxedDistributionDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static BoxedDistributionDescriptorPrx uncheckedCast(ObjectPrx __obj)
	{
		BoxedDistributionDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (BoxedDistributionDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				BoxedDistributionDescriptorPrxHelper __h = new BoxedDistributionDescriptorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static BoxedDistributionDescriptorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		BoxedDistributionDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			BoxedDistributionDescriptorPrxHelper __h = new BoxedDistributionDescriptorPrxHelper();
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
		return new _BoxedDistributionDescriptorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _BoxedDistributionDescriptorDelD();
	}

	public static void __write(BasicStream __os, BoxedDistributionDescriptorPrx v)
	{
		__os.writeProxy(v);
	}

	public static BoxedDistributionDescriptorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			BoxedDistributionDescriptorPrxHelper result = new BoxedDistributionDescriptorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
