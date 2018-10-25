// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_RouterDel, CannotCreateSessionException, PermissionDeniedException, SessionNotExistException, 
//			RouterPrx, _RouterDelM, _RouterDelD, SessionPrxHelper, 
//			SessionPrx, Callback_Router_createSession, Callback_Router_createSessionFromSecureConnection, Callback_Router_destroySession, 
//			AMI_Router_destroySession, Callback_Router_getCategoryForClient, Callback_Router_getSessionTimeout, Callback_Router_refreshSession, 
//			AMI_Router_refreshSession

public final class RouterPrxHelper extends ObjectPrxHelperBase
	implements RouterPrx
{

	private static final String __createSession_name = "createSession";
	private static final String __createSessionFromSecureConnection_name = "createSessionFromSecureConnection";
	private static final String __destroySession_name = "destroySession";
	private static final String __getCategoryForClient_name = "getCategoryForClient";
	private static final String __getSessionTimeout_name = "getSessionTimeout";
	private static final String __refreshSession_name = "refreshSession";
	private static final String __addProxies_name = "addProxies";
	private static final String __addProxy_name = "addProxy";
	private static final String __getClientProxy_name = "getClientProxy";
	private static final String __getServerProxy_name = "getServerProxy";
	public static final String __ids[] = {
		"::Glacier2::Router", "::Ice::Object", "::Ice::Router"
	};

	public RouterPrxHelper()
	{
	}

	public SessionPrx createSession(String userId, String password)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		return createSession(userId, password, null, false);
	}

	public SessionPrx createSession(String userId, String password, Map __ctx)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		return createSession(userId, password, __ctx, true);
	}

	private SessionPrx createSession(String userId, String password, Map __ctx, boolean __explicitCtx)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("createSession");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
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

	public AsyncResult begin_createSession(String userId, String password, Callback_Router_createSession __cb)
	{
		return begin_createSession(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSession(String userId, String password, Map __ctx, Callback_Router_createSession __cb)
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
		throws CannotCreateSessionException, PermissionDeniedException
	{
		AsyncResult.__check(__result, this, "createSession");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (CannotCreateSessionException __ex)
			{
				throw __ex;
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
		throws CannotCreateSessionException, PermissionDeniedException
	{
		return createSessionFromSecureConnection(null, false);
	}

	public SessionPrx createSessionFromSecureConnection(Map __ctx)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		return createSessionFromSecureConnection(__ctx, true);
	}

	private SessionPrx createSessionFromSecureConnection(Map __ctx, boolean __explicitCtx)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("createSessionFromSecureConnection");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
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

	public AsyncResult begin_createSessionFromSecureConnection(Callback_Router_createSessionFromSecureConnection __cb)
	{
		return begin_createSessionFromSecureConnection(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_createSessionFromSecureConnection(Map __ctx, Callback_Router_createSessionFromSecureConnection __cb)
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
		throws CannotCreateSessionException, PermissionDeniedException
	{
		AsyncResult.__check(__result, this, "createSessionFromSecureConnection");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (CannotCreateSessionException __ex)
			{
				throw __ex;
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

	public void destroySession()
		throws SessionNotExistException
	{
		destroySession(null, false);
	}

	public void destroySession(Map __ctx)
		throws SessionNotExistException
	{
		destroySession(__ctx, true);
	}

	private void destroySession(Map __ctx, boolean __explicitCtx)
		throws SessionNotExistException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("destroySession");
				__delBase = __getDelegate(false);
				_RouterDel __del = (_RouterDel)__delBase;
				__del.destroySession(__ctx);
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

	public AsyncResult begin_destroySession()
	{
		return begin_destroySession(null, false, null);
	}

	public AsyncResult begin_destroySession(Map __ctx)
	{
		return begin_destroySession(__ctx, true, null);
	}

	public AsyncResult begin_destroySession(Callback __cb)
	{
		return begin_destroySession(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroySession(Map __ctx, Callback __cb)
	{
		return begin_destroySession(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroySession(Callback_Router_destroySession __cb)
	{
		return begin_destroySession(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroySession(Map __ctx, Callback_Router_destroySession __cb)
	{
		return begin_destroySession(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_destroySession(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("destroySession");
		OutgoingAsync __result = new OutgoingAsync(this, "destroySession", __cb);
		try
		{
			__result.__prepare("destroySession", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_destroySession(AsyncResult __result)
		throws SessionNotExistException
	{
		AsyncResult.__check(__result, this, "destroySession");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (SessionNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	public boolean destroySession_async(AMI_Router_destroySession __cb)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("destroySession");
			__r = begin_destroySession(null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "destroySession", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean destroySession_async(AMI_Router_destroySession __cb, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("destroySession");
			__r = begin_destroySession(__ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "destroySession", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public String getCategoryForClient()
	{
		return getCategoryForClient(null, false);
	}

	public String getCategoryForClient(Map __ctx)
	{
		return getCategoryForClient(__ctx, true);
	}

	private String getCategoryForClient(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("getCategoryForClient");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
		return __del.getCategoryForClient(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getCategoryForClient()
	{
		return begin_getCategoryForClient(null, false, null);
	}

	public AsyncResult begin_getCategoryForClient(Map __ctx)
	{
		return begin_getCategoryForClient(__ctx, true, null);
	}

	public AsyncResult begin_getCategoryForClient(Callback __cb)
	{
		return begin_getCategoryForClient(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getCategoryForClient(Map __ctx, Callback __cb)
	{
		return begin_getCategoryForClient(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getCategoryForClient(Callback_Router_getCategoryForClient __cb)
	{
		return begin_getCategoryForClient(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getCategoryForClient(Map __ctx, Callback_Router_getCategoryForClient __cb)
	{
		return begin_getCategoryForClient(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getCategoryForClient(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getCategoryForClient");
		OutgoingAsync __result = new OutgoingAsync(this, "getCategoryForClient", __cb);
		try
		{
			__result.__prepare("getCategoryForClient", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public String end_getCategoryForClient(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getCategoryForClient");
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

	public long getSessionTimeout()
	{
		return getSessionTimeout(null, false);
	}

	public long getSessionTimeout(Map __ctx)
	{
		return getSessionTimeout(__ctx, true);
	}

	private long getSessionTimeout(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_RouterDel __del;
		__checkTwowayOnly("getSessionTimeout");
		__delBase = __getDelegate(false);
		__del = (_RouterDel)__delBase;
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

	public AsyncResult begin_getSessionTimeout(Callback_Router_getSessionTimeout __cb)
	{
		return begin_getSessionTimeout(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSessionTimeout(Map __ctx, Callback_Router_getSessionTimeout __cb)
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

	public long end_getSessionTimeout(AsyncResult __result)
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
		long __ret = __is.readLong();
		__is.endReadEncaps();
		return __ret;
	}

	public void refreshSession()
		throws SessionNotExistException
	{
		refreshSession(null, false);
	}

	public void refreshSession(Map __ctx)
		throws SessionNotExistException
	{
		refreshSession(__ctx, true);
	}

	private void refreshSession(Map __ctx, boolean __explicitCtx)
		throws SessionNotExistException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("refreshSession");
				__delBase = __getDelegate(false);
				_RouterDel __del = (_RouterDel)__delBase;
				__del.refreshSession(__ctx);
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

	public AsyncResult begin_refreshSession()
	{
		return begin_refreshSession(null, false, null);
	}

	public AsyncResult begin_refreshSession(Map __ctx)
	{
		return begin_refreshSession(__ctx, true, null);
	}

	public AsyncResult begin_refreshSession(Callback __cb)
	{
		return begin_refreshSession(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_refreshSession(Map __ctx, Callback __cb)
	{
		return begin_refreshSession(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_refreshSession(Callback_Router_refreshSession __cb)
	{
		return begin_refreshSession(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_refreshSession(Map __ctx, Callback_Router_refreshSession __cb)
	{
		return begin_refreshSession(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_refreshSession(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("refreshSession");
		OutgoingAsync __result = new OutgoingAsync(this, "refreshSession", __cb);
		try
		{
			__result.__prepare("refreshSession", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_refreshSession(AsyncResult __result)
		throws SessionNotExistException
	{
		AsyncResult.__check(__result, this, "refreshSession");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (SessionNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	public boolean refreshSession_async(AMI_Router_refreshSession __cb)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("refreshSession");
			__r = begin_refreshSession(null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "refreshSession", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean refreshSession_async(AMI_Router_refreshSession __cb, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("refreshSession");
			__r = begin_refreshSession(__ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "refreshSession", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
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
		Ice._ObjectDel __delBase = null;
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
			Ice._ObjectDel __delBase = null;
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
		Ice._ObjectDel __delBase = null;
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
		Ice._ObjectDel __delBase = null;
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
		return __ids[0];
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
