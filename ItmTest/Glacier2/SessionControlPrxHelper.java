// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionControlPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_SessionControlDel, SessionControlPrx, _SessionControlDelM, _SessionControlDelD, 
//			StringSetPrxHelper, IdentitySetPrxHelper, StringSetPrx, Callback_SessionControl_adapterIds, 
//			Callback_SessionControl_categories, Callback_SessionControl_destroy, AMI_SessionControl_destroy, Callback_SessionControl_getSessionTimeout, 
//			IdentitySetPrx, Callback_SessionControl_identities

public final class SessionControlPrxHelper extends ObjectPrxHelperBase
	implements SessionControlPrx
{

	private static final String __adapterIds_name = "adapterIds";
	private static final String __categories_name = "categories";
	private static final String __destroy_name = "destroy";
	private static final String __getSessionTimeout_name = "getSessionTimeout";
	private static final String __identities_name = "identities";
	public static final String __ids[] = {
		"::Glacier2::SessionControl", "::Ice::Object"
	};

	public SessionControlPrxHelper()
	{
	}

	public StringSetPrx adapterIds()
	{
		return adapterIds(null, false);
	}

	public StringSetPrx adapterIds(Map __ctx)
	{
		return adapterIds(__ctx, true);
	}

	private StringSetPrx adapterIds(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionControlDel __del;
		__checkTwowayOnly("adapterIds");
		__delBase = __getDelegate(false);
		__del = (_SessionControlDel)__delBase;
		return __del.adapterIds(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_adapterIds()
	{
		return begin_adapterIds(null, false, null);
	}

	public AsyncResult begin_adapterIds(Map __ctx)
	{
		return begin_adapterIds(__ctx, true, null);
	}

	public AsyncResult begin_adapterIds(Callback __cb)
	{
		return begin_adapterIds(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterIds(Map __ctx, Callback __cb)
	{
		return begin_adapterIds(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterIds(Callback_SessionControl_adapterIds __cb)
	{
		return begin_adapterIds(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterIds(Map __ctx, Callback_SessionControl_adapterIds __cb)
	{
		return begin_adapterIds(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_adapterIds(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("adapterIds");
		OutgoingAsync __result = new OutgoingAsync(this, "adapterIds", __cb);
		try
		{
			__result.__prepare("adapterIds", OperationMode.Normal, __ctx, __explicitCtx);
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

	public StringSetPrx end_adapterIds(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "adapterIds");
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
		StringSetPrx __ret = StringSetPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public StringSetPrx categories()
	{
		return categories(null, false);
	}

	public StringSetPrx categories(Map __ctx)
	{
		return categories(__ctx, true);
	}

	private StringSetPrx categories(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionControlDel __del;
		__checkTwowayOnly("categories");
		__delBase = __getDelegate(false);
		__del = (_SessionControlDel)__delBase;
		return __del.categories(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_categories()
	{
		return begin_categories(null, false, null);
	}

	public AsyncResult begin_categories(Map __ctx)
	{
		return begin_categories(__ctx, true, null);
	}

	public AsyncResult begin_categories(Callback __cb)
	{
		return begin_categories(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_categories(Map __ctx, Callback __cb)
	{
		return begin_categories(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_categories(Callback_SessionControl_categories __cb)
	{
		return begin_categories(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_categories(Map __ctx, Callback_SessionControl_categories __cb)
	{
		return begin_categories(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_categories(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("categories");
		OutgoingAsync __result = new OutgoingAsync(this, "categories", __cb);
		try
		{
			__result.__prepare("categories", OperationMode.Normal, __ctx, __explicitCtx);
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

	public StringSetPrx end_categories(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "categories");
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
		StringSetPrx __ret = StringSetPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public void destroy()
	{
		destroy(null, false);
	}

	public void destroy(Map __ctx)
	{
		destroy(__ctx, true);
	}

	private void destroy(Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_SessionControlDel __del = (_SessionControlDel)__delBase;
				__del.destroy(__ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_destroy()
	{
		return begin_destroy(null, false, null);
	}

	public AsyncResult begin_destroy(Map __ctx)
	{
		return begin_destroy(__ctx, true, null);
	}

	public AsyncResult begin_destroy(Callback __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback __cb)
	{
		return begin_destroy(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Callback_SessionControl_destroy __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback_SessionControl_destroy __cb)
	{
		return begin_destroy(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_destroy(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "destroy", __cb);
		try
		{
			__result.__prepare("destroy", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_destroy(AsyncResult __result)
	{
		__end(__result, "destroy");
	}

	public boolean destroy_async(AMI_SessionControl_destroy __cb)
	{
		AsyncResult __r = begin_destroy(null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean destroy_async(AMI_SessionControl_destroy __cb, Map __ctx)
	{
		AsyncResult __r = begin_destroy(__ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public int getSessionTimeout()
	{
		return getSessionTimeout(null, false);
	}

	public int getSessionTimeout(Map __ctx)
	{
		return getSessionTimeout(__ctx, true);
	}

	private int getSessionTimeout(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionControlDel __del;
		__checkTwowayOnly("getSessionTimeout");
		__delBase = __getDelegate(false);
		__del = (_SessionControlDel)__delBase;
		return __del.getSessionTimeout(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getSessionTimeout()
	{
		return begin_getSessionTimeout(null, false, null);
	}

	public AsyncResult begin_getSessionTimeout(Map __ctx)
	{
		return begin_getSessionTimeout(__ctx, true, null);
	}

	public AsyncResult begin_getSessionTimeout(Callback __cb)
	{
		return begin_getSessionTimeout(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSessionTimeout(Map __ctx, Callback __cb)
	{
		return begin_getSessionTimeout(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSessionTimeout(Callback_SessionControl_getSessionTimeout __cb)
	{
		return begin_getSessionTimeout(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSessionTimeout(Map __ctx, Callback_SessionControl_getSessionTimeout __cb)
	{
		return begin_getSessionTimeout(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getSessionTimeout(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getSessionTimeout");
		OutgoingAsync __result = new OutgoingAsync(this, "getSessionTimeout", __cb);
		try
		{
			__result.__prepare("getSessionTimeout", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public int end_getSessionTimeout(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getSessionTimeout");
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
		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public IdentitySetPrx identities()
	{
		return identities(null, false);
	}

	public IdentitySetPrx identities(Map __ctx)
	{
		return identities(__ctx, true);
	}

	private IdentitySetPrx identities(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionControlDel __del;
		__checkTwowayOnly("identities");
		__delBase = __getDelegate(false);
		__del = (_SessionControlDel)__delBase;
		return __del.identities(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_identities()
	{
		return begin_identities(null, false, null);
	}

	public AsyncResult begin_identities(Map __ctx)
	{
		return begin_identities(__ctx, true, null);
	}

	public AsyncResult begin_identities(Callback __cb)
	{
		return begin_identities(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_identities(Map __ctx, Callback __cb)
	{
		return begin_identities(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_identities(Callback_SessionControl_identities __cb)
	{
		return begin_identities(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_identities(Map __ctx, Callback_SessionControl_identities __cb)
	{
		return begin_identities(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_identities(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("identities");
		OutgoingAsync __result = new OutgoingAsync(this, "identities", __cb);
		try
		{
			__result.__prepare("identities", OperationMode.Normal, __ctx, __explicitCtx);
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

	public IdentitySetPrx end_identities(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "identities");
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
		IdentitySetPrx __ret = IdentitySetPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public static SessionControlPrx checkedCast(ObjectPrx __obj)
	{
		SessionControlPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionControlPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SessionControlPrxHelper __h = new SessionControlPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionControlPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SessionControlPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionControlPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SessionControlPrxHelper __h = new SessionControlPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionControlPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SessionControlPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SessionControlPrxHelper __h = new SessionControlPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionControlPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SessionControlPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SessionControlPrxHelper __h = new SessionControlPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionControlPrx uncheckedCast(ObjectPrx __obj)
	{
		SessionControlPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionControlPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SessionControlPrxHelper __h = new SessionControlPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SessionControlPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SessionControlPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SessionControlPrxHelper __h = new SessionControlPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[0];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _SessionControlDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SessionControlDelD();
	}

	public static void __write(BasicStream __os, SessionControlPrx v)
	{
		__os.writeProxy(v);
	}

	public static SessionControlPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SessionControlPrxHelper result = new SessionControlPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
