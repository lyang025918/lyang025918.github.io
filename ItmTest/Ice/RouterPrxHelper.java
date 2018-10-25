// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterPrxHelper.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.LocalExceptionWrapper;
import IceInternal.OutgoingAsync;
import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrxHelperBase, _RouterDel, LocalException, UserException, 
//			UnknownUserException, TwowayOnlyException, RouterPrx, FacetNotExistException, 
//			_RouterDelM, _RouterDelD, OperationMode, ObjectProxySeqHelper, 
//			AsyncResult, ObjectPrx, Callback, Callback_Router_addProxies, 
//			AMI_Router_addProxies, Callback_Router_addProxy, Callback_Router_getClientProxy, AMI_Router_getClientProxy, 
//			Callback_Router_getServerProxy, _ObjectDelM, _ObjectDelD

public final class RouterPrxHelper extends ObjectPrxHelperBase
	implements RouterPrx
{

	private static final String __addProxies_name = "addProxies";
	private static final String __addProxy_name = "addProxy";
	private static final String __getClientProxy_name = "getClientProxy";
	private static final String __getServerProxy_name = "getServerProxy";
	public static final String __ids[] = {
		"::Ice::Object", "::Ice::Router"
	};

	public RouterPrxHelper()
	{
	}

	public ObjectPrx[] addProxies(ObjectPrx proxies[])
	{
		return addProxies(proxies, null, false);
	}

	public ObjectPrx[] addProxies(ObjectPrx proxies[], Map __ctx)
	{
		return addProxies(proxies, __ctx, true);
	}

	private ObjectPrx[] addProxies(ObjectPrx proxies[], Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("addProxies");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
		return __del.addProxies(proxies, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_addProxies(ObjectPrx proxies[])
	{
		return begin_addProxies(proxies, null, false, null);
	}

	public AsyncResult begin_addProxies(ObjectPrx proxies[], Map __ctx)
	{
		return begin_addProxies(proxies, __ctx, true, null);
	}

	public AsyncResult begin_addProxies(ObjectPrx proxies[], Callback __cb)
	{
		return begin_addProxies(proxies, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addProxies(ObjectPrx proxies[], Map __ctx, Callback __cb)
	{
		return begin_addProxies(proxies, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addProxies(ObjectPrx proxies[], Callback_Router_addProxies __cb)
	{
		return begin_addProxies(proxies, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addProxies(ObjectPrx proxies[], Map __ctx, Callback_Router_addProxies __cb)
	{
		return begin_addProxies(proxies, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_addProxies(ObjectPrx proxies[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("addProxies");
		OutgoingAsync __result = new OutgoingAsync(this, "addProxies", __cb);
		try
		{
			__result.__prepare("addProxies", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			ObjectProxySeqHelper.write(__os, proxies);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx[] end_addProxies(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "addProxies");
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
		ObjectPrx __ret[] = ObjectProxySeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public boolean addProxies_async(AMI_Router_addProxies __cb, ObjectPrx proxies[])
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addProxies");
			__r = begin_addProxies(proxies, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addProxies", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean addProxies_async(AMI_Router_addProxies __cb, ObjectPrx proxies[], Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addProxies");
			__r = begin_addProxies(proxies, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addProxies", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public void addProxy(ObjectPrx proxy)
	{
		addProxy(proxy, null, false);
	}

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public void addProxy(ObjectPrx proxy, Map __ctx)
	{
		addProxy(proxy, __ctx, true);
	}

	private void addProxy(ObjectPrx proxy, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_RouterDel __del = (_RouterDel)__delBase;
				__del.addProxy(proxy, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public AsyncResult begin_addProxy(ObjectPrx proxy)
	{
		return begin_addProxy(proxy, null, false, null);
	}

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public AsyncResult begin_addProxy(ObjectPrx proxy, Map __ctx)
	{
		return begin_addProxy(proxy, __ctx, true, null);
	}

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public AsyncResult begin_addProxy(ObjectPrx proxy, Callback __cb)
	{
		return begin_addProxy(proxy, null, false, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public AsyncResult begin_addProxy(ObjectPrx proxy, Map __ctx, Callback __cb)
	{
		return begin_addProxy(proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public AsyncResult begin_addProxy(ObjectPrx proxy, Callback_Router_addProxy __cb)
	{
		return begin_addProxy(proxy, null, false, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public AsyncResult begin_addProxy(ObjectPrx proxy, Map __ctx, Callback_Router_addProxy __cb)
	{
		return begin_addProxy(proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_addProxy(ObjectPrx proxy, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "addProxy", __cb);
		try
		{
			__result.__prepare("addProxy", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeProxy(proxy);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_addProxy(AsyncResult __result)
	{
		__end(__result, "addProxy");
	}

	public ObjectPrx getClientProxy()
	{
		return getClientProxy(null, false);
	}

	public ObjectPrx getClientProxy(Map __ctx)
	{
		return getClientProxy(__ctx, true);
	}

	private ObjectPrx getClientProxy(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("getClientProxy");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
		return __del.getClientProxy(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getClientProxy()
	{
		return begin_getClientProxy(null, false, null);
	}

	public AsyncResult begin_getClientProxy(Map __ctx)
	{
		return begin_getClientProxy(__ctx, true, null);
	}

	public AsyncResult begin_getClientProxy(Callback __cb)
	{
		return begin_getClientProxy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getClientProxy(Map __ctx, Callback __cb)
	{
		return begin_getClientProxy(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getClientProxy(Callback_Router_getClientProxy __cb)
	{
		return begin_getClientProxy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getClientProxy(Map __ctx, Callback_Router_getClientProxy __cb)
	{
		return begin_getClientProxy(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getClientProxy(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getClientProxy");
		OutgoingAsync __result = new OutgoingAsync(this, "getClientProxy", __cb);
		try
		{
			__result.__prepare("getClientProxy", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_getClientProxy(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getClientProxy");
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
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean getClientProxy_async(AMI_Router_getClientProxy __cb)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getClientProxy");
			__r = begin_getClientProxy(null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getClientProxy", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean getClientProxy_async(AMI_Router_getClientProxy __cb, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getClientProxy");
			__r = begin_getClientProxy(__ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getClientProxy", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public ObjectPrx getServerProxy()
	{
		return getServerProxy(null, false);
	}

	public ObjectPrx getServerProxy(Map __ctx)
	{
		return getServerProxy(__ctx, true);
	}

	private ObjectPrx getServerProxy(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("getServerProxy");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
		return __del.getServerProxy(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getServerProxy()
	{
		return begin_getServerProxy(null, false, null);
	}

	public AsyncResult begin_getServerProxy(Map __ctx)
	{
		return begin_getServerProxy(__ctx, true, null);
	}

	public AsyncResult begin_getServerProxy(Callback __cb)
	{
		return begin_getServerProxy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerProxy(Map __ctx, Callback __cb)
	{
		return begin_getServerProxy(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerProxy(Callback_Router_getServerProxy __cb)
	{
		return begin_getServerProxy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerProxy(Map __ctx, Callback_Router_getServerProxy __cb)
	{
		return begin_getServerProxy(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getServerProxy(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getServerProxy");
		OutgoingAsync __result = new OutgoingAsync(this, "getServerProxy", __cb);
		try
		{
			__result.__prepare("getServerProxy", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_getServerProxy(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getServerProxy");
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
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public static RouterPrx checkedCast(ObjectPrx __obj)
	{
		RouterPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RouterPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					RouterPrxHelper __h = new RouterPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RouterPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		RouterPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RouterPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					RouterPrxHelper __h = new RouterPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RouterPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		RouterPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					RouterPrxHelper __h = new RouterPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RouterPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		RouterPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					RouterPrxHelper __h = new RouterPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RouterPrx uncheckedCast(ObjectPrx __obj)
	{
		RouterPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RouterPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				RouterPrxHelper __h = new RouterPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static RouterPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		RouterPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			RouterPrxHelper __h = new RouterPrxHelper();
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
		return new _RouterDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _RouterDelD();
	}

	public static void __write(BasicStream __os, RouterPrx v)
	{
		__os.writeProxy(v);
	}

	public static RouterPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			RouterPrxHelper result = new RouterPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
