// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesAdminPrxHelper.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.LocalExceptionWrapper;
import IceInternal.OutgoingAsync;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package Ice:
//			ObjectPrxHelperBase, _PropertiesAdminDel, LocalException, UserException, 
//			UnknownUserException, TwowayOnlyException, PropertiesAdminPrx, FacetNotExistException, 
//			_PropertiesAdminDelM, _PropertiesAdminDelD, OperationMode, AsyncResult, 
//			ObjectPrx, Callback, Callback_PropertiesAdmin_getPropertiesForPrefix, AMI_PropertiesAdmin_getPropertiesForPrefix, 
//			Callback_PropertiesAdmin_getProperty, AMI_PropertiesAdmin_getProperty, _ObjectDelM, _ObjectDelD

public final class PropertiesAdminPrxHelper extends ObjectPrxHelperBase
	implements PropertiesAdminPrx
{

	private static final String __getPropertiesForPrefix_name = "getPropertiesForPrefix";
	private static final String __getProperty_name = "getProperty";
	public static final String __ids[] = {
		"::Ice::Object", "::Ice::PropertiesAdmin"
	};

	public PropertiesAdminPrxHelper()
	{
	}

	public Map getPropertiesForPrefix(String prefix)
	{
		return getPropertiesForPrefix(prefix, null, false);
	}

	public Map getPropertiesForPrefix(String prefix, Map __ctx)
	{
		return getPropertiesForPrefix(prefix, __ctx, true);
	}

	private Map getPropertiesForPrefix(String prefix, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_PropertiesAdminDel __del;
		__checkTwowayOnly("getPropertiesForPrefix");
		__delBase = __getDelegate(false);
		__del = (_PropertiesAdminDel)__delBase;
		return __del.getPropertiesForPrefix(prefix, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getPropertiesForPrefix(String prefix)
	{
		return begin_getPropertiesForPrefix(prefix, null, false, null);
	}

	public AsyncResult begin_getPropertiesForPrefix(String prefix, Map __ctx)
	{
		return begin_getPropertiesForPrefix(prefix, __ctx, true, null);
	}

	public AsyncResult begin_getPropertiesForPrefix(String prefix, Callback __cb)
	{
		return begin_getPropertiesForPrefix(prefix, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getPropertiesForPrefix(String prefix, Map __ctx, Callback __cb)
	{
		return begin_getPropertiesForPrefix(prefix, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getPropertiesForPrefix(String prefix, Callback_PropertiesAdmin_getPropertiesForPrefix __cb)
	{
		return begin_getPropertiesForPrefix(prefix, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getPropertiesForPrefix(String prefix, Map __ctx, Callback_PropertiesAdmin_getPropertiesForPrefix __cb)
	{
		return begin_getPropertiesForPrefix(prefix, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getPropertiesForPrefix(String prefix, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getPropertiesForPrefix");
		OutgoingAsync __result = new OutgoingAsync(this, "getPropertiesForPrefix", __cb);
		try
		{
			__result.__prepare("getPropertiesForPrefix", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(prefix);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public Map end_getPropertiesForPrefix(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getPropertiesForPrefix");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		Map __ret = new TreeMap();
		int __sz0 = __is.readSize();
		for (int __i0 = 0; __i0 < __sz0; __i0++)
		{
			String __key = __is.readString();
			String __value = __is.readString();
			__ret.put(__key, __value);
		}

		__is.endReadEncaps();
		return __ret;
	}

	public boolean getPropertiesForPrefix_async(AMI_PropertiesAdmin_getPropertiesForPrefix __cb, String prefix)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getPropertiesForPrefix");
			__r = begin_getPropertiesForPrefix(prefix, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getPropertiesForPrefix", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean getPropertiesForPrefix_async(AMI_PropertiesAdmin_getPropertiesForPrefix __cb, String prefix, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getPropertiesForPrefix");
			__r = begin_getPropertiesForPrefix(prefix, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getPropertiesForPrefix", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public String getProperty(String key)
	{
		return getProperty(key, null, false);
	}

	public String getProperty(String key, Map __ctx)
	{
		return getProperty(key, __ctx, true);
	}

	private String getProperty(String key, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_PropertiesAdminDel __del;
		__checkTwowayOnly("getProperty");
		__delBase = __getDelegate(false);
		__del = (_PropertiesAdminDel)__delBase;
		return __del.getProperty(key, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getProperty(String key)
	{
		return begin_getProperty(key, null, false, null);
	}

	public AsyncResult begin_getProperty(String key, Map __ctx)
	{
		return begin_getProperty(key, __ctx, true, null);
	}

	public AsyncResult begin_getProperty(String key, Callback __cb)
	{
		return begin_getProperty(key, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getProperty(String key, Map __ctx, Callback __cb)
	{
		return begin_getProperty(key, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getProperty(String key, Callback_PropertiesAdmin_getProperty __cb)
	{
		return begin_getProperty(key, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getProperty(String key, Map __ctx, Callback_PropertiesAdmin_getProperty __cb)
	{
		return begin_getProperty(key, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getProperty(String key, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getProperty");
		OutgoingAsync __result = new OutgoingAsync(this, "getProperty", __cb);
		try
		{
			__result.__prepare("getProperty", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(key);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public String end_getProperty(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getProperty");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		String __ret = __is.readString();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean getProperty_async(AMI_PropertiesAdmin_getProperty __cb, String key)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getProperty");
			__r = begin_getProperty(key, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getProperty", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean getProperty_async(AMI_PropertiesAdmin_getProperty __cb, String key, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getProperty");
			__r = begin_getProperty(key, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getProperty", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static PropertiesAdminPrx checkedCast(ObjectPrx __obj)
	{
		PropertiesAdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (PropertiesAdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					PropertiesAdminPrxHelper __h = new PropertiesAdminPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static PropertiesAdminPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		PropertiesAdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (PropertiesAdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					PropertiesAdminPrxHelper __h = new PropertiesAdminPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static PropertiesAdminPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		PropertiesAdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					PropertiesAdminPrxHelper __h = new PropertiesAdminPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static PropertiesAdminPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		PropertiesAdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					PropertiesAdminPrxHelper __h = new PropertiesAdminPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static PropertiesAdminPrx uncheckedCast(ObjectPrx __obj)
	{
		PropertiesAdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (PropertiesAdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				PropertiesAdminPrxHelper __h = new PropertiesAdminPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static PropertiesAdminPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		PropertiesAdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			PropertiesAdminPrxHelper __h = new PropertiesAdminPrxHelper();
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
		return new _PropertiesAdminDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _PropertiesAdminDelD();
	}

	public static void __write(BasicStream __os, PropertiesAdminPrx v)
	{
		__os.writeProxy(v);
	}

	public static PropertiesAdminPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			PropertiesAdminPrxHelper result = new PropertiesAdminPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
