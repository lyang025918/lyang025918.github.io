// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerDescriptorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ServerDescriptorPrx, _ServerDescriptorDelM, _ServerDescriptorDelD

public final class ServerDescriptorPrxHelper extends ObjectPrxHelperBase
	implements ServerDescriptorPrx
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor", "::IceGrid::ServerDescriptor"
	};

	public ServerDescriptorPrxHelper()
	{
	}

	public static ServerDescriptorPrx checkedCast(ObjectPrx __obj)
	{
		ServerDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServerDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ServerDescriptorPrxHelper __h = new ServerDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServerDescriptorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ServerDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServerDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ServerDescriptorPrxHelper __h = new ServerDescriptorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServerDescriptorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ServerDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ServerDescriptorPrxHelper __h = new ServerDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServerDescriptorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ServerDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ServerDescriptorPrxHelper __h = new ServerDescriptorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServerDescriptorPrx uncheckedCast(ObjectPrx __obj)
	{
		ServerDescriptorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServerDescriptorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ServerDescriptorPrxHelper __h = new ServerDescriptorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ServerDescriptorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ServerDescriptorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ServerDescriptorPrxHelper __h = new ServerDescriptorPrxHelper();
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
		return new _ServerDescriptorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ServerDescriptorDelD();
	}

	public static void __write(BasicStream __os, ServerDescriptorPrx v)
	{
		__os.writeProxy(v);
	}

	public static ServerDescriptorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ServerDescriptorPrxHelper result = new ServerDescriptorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
