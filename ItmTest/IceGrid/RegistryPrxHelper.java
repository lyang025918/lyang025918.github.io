// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_RegistryDel, PermissionDeniedException, RegistryPrx, _RegistryDelM, 
//			_RegistryDelD, AdminSessionPrxHelper, SessionPrxHelper, AdminSessionPrx, 
//			Callback_Registry_createAdminSession, Callback_Registry_createAdminSessionFromSecureConnection, SessionPrx, Callback_Registry_createSession, 
//			Callback_Registry_createSessionFromSecureConnection, Callback_Registry_getSessionTimeout

public final class RegistryPrxHelper extends ObjectPrxHelperBase
	implements RegistryPrx
{

	private static final String __createAdminSession_name = "createAdminSession";
	private static final String __createAdminSessionFromSecureConnection_name = "createAdminSessionFromSecureConnection";
	private static final String __createSession_name = "createSession";
	private static final String __createSessionFromSecureConnection_name = "createSessionFromSecureConnection";
	private static final String __getSessionTimeout_name = "getSessionTimeout";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::Registry"
	};

	public RegistryPrxHelper()
	{
	}

	public AdminSessionPrx createAdminSession(String userId, String password)
		throws PermissionDeniedException
	{
		return createAdminSession(userId, password, null, false);
	}

	public AdminSessionPrx createAdminSession(String userId, String password, Map __ctx)
		throws PermissionDeniedException
	{
		return createAdminSession(userId, password, __ctx, true);
	}

	private AdminSessionPrx createAdminSession(String userId, String password, Map __ctx, boolean __explicitCtx)
		throws PermissionDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RegistryDel __del;
		__checkTwowayOnly("createAdminSession");
		__delBase = __getDelegate(false);
		__del = (_RegistryDel)__delBase;
		return __del.createAdminSession(userId, password, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_createAdminSession(String userId, String password)
	{
		return begin_createAdminSession(userId, password, null, false, null);
	}

	public AsyncResult begin_createAdminSession(String userId, String password, Map __ctx)
	{
		return begin_createAdminSession(userId, password, __ctx, true, null);
	}

	public AsyncResult begin_createAdminSession(String userId, String password, Callback __cb)
	{
		return begin_createAdminSession(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createAdminSession(String userId, String password, Map __ctx, Callback __cb)
	{
		return begin_createAdminSession(userId, password, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createAdminSession(String userId, String password, Callback_Registry_createAdminSession __cb)
	{
		return begin_createAdminSession(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createAdminSession(String userId, String password, Map __ctx, Callback_Registry_createAdminSession __cb)
	{
		return begin_createAdminSession(userId, password, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_createAdminSession(String userId, String password, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("createAdminSession");
		OutgoingAsync __result = new OutgoingAsync(this, "createAdminSession", __cb);
		try
		{
			__result.__prepare("createAdminSession", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(userId);
			__os.writeString(password);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public AdminSessionPrx end_createAdminSession(AsyncResult __result)
		throws PermissionDeniedException
	{
		AsyncResult.__check(__result, this, "createAdminSession");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (PermissionDeniedException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		AdminSessionPrx __ret = AdminSessionPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public AdminSessionPrx createAdminSessionFromSecureConnection()
		throws PermissionDeniedException
	{
		return createAdminSessionFromSecureConnection(null, false);
	}

	public AdminSessionPrx createAdminSessionFromSecureConnection(Map __ctx)
		throws PermissionDeniedException
	{
		return createAdminSessionFromSecureConnection(__ctx, true);
	}

	private AdminSessionPrx createAdminSessionFromSecureConnection(Map __ctx, boolean __explicitCtx)
		throws PermissionDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RegistryDel __del;
		__checkTwowayOnly("createAdminSessionFromSecureConnection");
		__delBase = __getDelegate(false);
		__del = (_RegistryDel)__delBase;
		return __del.createAdminSessionFromSecureConnection(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_createAdminSessionFromSecureConnection()
	{
		return begin_createAdminSessionFromSecureConnection(null, false, null);
	}

	public AsyncResult begin_createAdminSessionFromSecureConnection(Map __ctx)
	{
		return begin_createAdminSessionFromSecureConnection(__ctx, true, null);
	}

	public AsyncResult begin_createAdminSessionFromSecureConnection(Callback __cb)
	{
		return begin_createAdminSessionFromSecureConnection(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createAdminSessionFromSecureConnection(Map __ctx, Callback __cb)
	{
		return begin_createAdminSessionFromSecureConnection(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createAdminSessionFromSecureConnection(Callback_Registry_createAdminSessionFromSecureConnection __cb)
	{
		return begin_createAdminSessionFromSecureConnection(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createAdminSessionFromSecureConnection(Map __ctx, Callback_Registry_createAdminSessionFromSecureConnection __cb)
	{
		return begin_createAdminSessionFromSecureConnection(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_createAdminSessionFromSecureConnection(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("createAdminSessionFromSecureConnection");
		OutgoingAsync __result = new OutgoingAsync(this, "createAdminSessionFromSecureConnection", __cb);
		try
		{
			__result.__prepare("createAdminSessionFromSecureConnection", OperationMode.Normal, __ctx, __explicitCtx);
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

	public AdminSessionPrx end_createAdminSessionFromSecureConnection(AsyncResult __result)
		throws PermissionDeniedException
	{
		AsyncResult.__check(__result, this, "createAdminSessionFromSecureConnection");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (PermissionDeniedException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		AdminSessionPrx __ret = AdminSessionPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public SessionPrx createSession(String userId, String password)
		throws PermissionDeniedException
	{
		return createSession(userId, password, null, false);
	}

	public SessionPrx createSession(String userId, String password, Map __ctx)
		throws PermissionDeniedException
	{
		return createSession(userId, password, __ctx, true);
	}

	private SessionPrx createSession(String userId, String password, Map __ctx, boolean __explicitCtx)
		throws PermissionDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RegistryDel __del;
		__checkTwowayOnly("createSession");
		__delBase = __getDelegate(false);
		__del = (_RegistryDel)__delBase;
		return __del.createSession(userId, password, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_createSession(String userId, String password)
	{
		return begin_createSession(userId, password, null, false, null);
	}

	public AsyncResult begin_createSession(String userId, String password, Map __ctx)
	{
		return begin_createSession(userId, password, __ctx, true, null);
	}

	public AsyncResult begin_createSession(String userId, String password, Callback __cb)
	{
		return begin_createSession(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSession(String userId, String password, Map __ctx, Callback __cb)
	{
		return begin_createSession(userId, password, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSession(String userId, String password, Callback_Registry_createSession __cb)
	{
		return begin_createSession(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSession(String userId, String password, Map __ctx, Callback_Registry_createSession __cb)
	{
		return begin_createSession(userId, password, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_createSession(String userId, String password, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("createSession");
		OutgoingAsync __result = new OutgoingAsync(this, "createSession", __cb);
		try
		{
			__result.__prepare("createSession", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(userId);
			__os.writeString(password);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public SessionPrx end_createSession(AsyncResult __result)
		throws PermissionDeniedException
	{
		AsyncResult.__check(__result, this, "createSession");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (PermissionDeniedException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		SessionPrx __ret = SessionPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public SessionPrx createSessionFromSecureConnection()
		throws PermissionDeniedException
	{
		return createSessionFromSecureConnection(null, false);
	}

	public SessionPrx createSessionFromSecureConnection(Map __ctx)
		throws PermissionDeniedException
	{
		return createSessionFromSecureConnection(__ctx, true);
	}

	private SessionPrx createSessionFromSecureConnection(Map __ctx, boolean __explicitCtx)
		throws PermissionDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RegistryDel __del;
		__checkTwowayOnly("createSessionFromSecureConnection");
		__delBase = __getDelegate(false);
		__del = (_RegistryDel)__delBase;
		return __del.createSessionFromSecureConnection(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_createSessionFromSecureConnection()
	{
		return begin_createSessionFromSecureConnection(null, false, null);
	}

	public AsyncResult begin_createSessionFromSecureConnection(Map __ctx)
	{
		return begin_createSessionFromSecureConnection(__ctx, true, null);
	}

	public AsyncResult begin_createSessionFromSecureConnection(Callback __cb)
	{
		return begin_createSessionFromSecureConnection(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSessionFromSecureConnection(Map __ctx, Callback __cb)
	{
		return begin_createSessionFromSecureConnection(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSessionFromSecureConnection(Callback_Registry_createSessionFromSecureConnection __cb)
	{
		return begin_createSessionFromSecureConnection(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSessionFromSecureConnection(Map __ctx, Callback_Registry_createSessionFromSecureConnection __cb)
	{
		return begin_createSessionFromSecureConnection(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_createSessionFromSecureConnection(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("createSessionFromSecureConnection");
		OutgoingAsync __result = new OutgoingAsync(this, "createSessionFromSecureConnection", __cb);
		try
		{
			__result.__prepare("createSessionFromSecureConnection", OperationMode.Normal, __ctx, __explicitCtx);
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

	public SessionPrx end_createSessionFromSecureConnection(AsyncResult __result)
		throws PermissionDeniedException
	{
		AsyncResult.__check(__result, this, "createSessionFromSecureConnection");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (PermissionDeniedException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		SessionPrx __ret = SessionPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
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
		_RegistryDel __del;
		__checkTwowayOnly("getSessionTimeout");
		__delBase = __getDelegate(false);
		__del = (_RegistryDel)__delBase;
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

	public AsyncResult begin_getSessionTimeout(Callback_Registry_getSessionTimeout __cb)
	{
		return begin_getSessionTimeout(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSessionTimeout(Map __ctx, Callback_Registry_getSessionTimeout __cb)
	{
		return begin_getSessionTimeout(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getSessionTimeout(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getSessionTimeout");
		OutgoingAsync __result = new OutgoingAsync(this, "getSessionTimeout", __cb);
		try
		{
			__result.__prepare("getSessionTimeout", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public static RegistryPrx checkedCast(ObjectPrx __obj)
	{
		RegistryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RegistryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					RegistryPrxHelper __h = new RegistryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RegistryPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		RegistryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RegistryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					RegistryPrxHelper __h = new RegistryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RegistryPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		RegistryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					RegistryPrxHelper __h = new RegistryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RegistryPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		RegistryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					RegistryPrxHelper __h = new RegistryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RegistryPrx uncheckedCast(ObjectPrx __obj)
	{
		RegistryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RegistryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				RegistryPrxHelper __h = new RegistryPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static RegistryPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		RegistryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			RegistryPrxHelper __h = new RegistryPrxHelper();
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
		return new _RegistryDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _RegistryDelD();
	}

	public static void __write(BasicStream __os, RegistryPrx v)
	{
		__os.writeProxy(v);
	}

	public static RegistryPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			RegistryPrxHelper result = new RegistryPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
