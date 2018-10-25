// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoxedStringPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			BoxedStringPrx, _BoxedStringDelM, _BoxedStringDelD

public final class BoxedStringPrxHelper extends ObjectPrxHelperBase
	implements BoxedStringPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::BoxedString"
	};

	public BoxedStringPrxHelper()
	{
	}

	public static BoxedStringPrx checkedCast(ObjectPrx __obj)
	{
		BoxedStringPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (BoxedStringPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					BoxedStringPrxHelper __h = new BoxedStringPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static BoxedStringPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		BoxedStringPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (BoxedStringPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					BoxedStringPrxHelper __h = new BoxedStringPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static BoxedStringPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		BoxedStringPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					BoxedStringPrxHelper __h = new BoxedStringPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static BoxedStringPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		BoxedStringPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					BoxedStringPrxHelper __h = new BoxedStringPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static BoxedStringPrx uncheckedCast(ObjectPrx __obj)
	{
		BoxedStringPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (BoxedStringPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				BoxedStringPrxHelper __h = new BoxedStringPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static BoxedStringPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		BoxedStringPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			BoxedStringPrxHelper __h = new BoxedStringPrxHelper();
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
		return new _BoxedStringDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _BoxedStringDelD();
	}

	public static void __write(BasicStream __os, BoxedStringPrx v)
	{
		__os.writeProxy(v);
	}

	public static BoxedStringPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			BoxedStringPrxHelper result = new BoxedStringPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
