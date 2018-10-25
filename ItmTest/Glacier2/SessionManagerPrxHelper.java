// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionManagerPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_SessionManagerDel, CannotCreateSessionException, SessionManagerPrx, _SessionManagerDelM, 
//			_SessionManagerDelD, SessionControlPrxHelper, SessionPrxHelper, SessionControlPrx, 
//			SessionPrx, Callback_SessionManager_create, AMI_SessionManager_create

public final class SessionManagerPrxHelper extends ObjectPrxHelperBase
	implements SessionManagerPrx
{

	private static final String __create_name = "create";
	public static final String __ids[] = {
		"::Glacier2::SessionManager", "::Ice::Object"
	};

	public SessionManagerPrxHelper()
	{
	}

	public SessionPrx create(String userId, SessionControlPrx control)
		throws CannotCreateSessionException
	{
		return create(userId, control, null, false);
	}

	public SessionPrx create(String userId, SessionControlPrx control, Map __ctx)
		throws CannotCreateSessionException
	{
		return create(userId, control, __ctx, true);
	}

	private SessionPrx create(String userId, SessionControlPrx control, Map __ctx, boolean __explicitCtx)
		throws CannotCreateSessionException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionManagerDel __del;
		__checkTwowayOnly("create");
		__delBase = __getDelegate(false);
		__del = (_SessionManagerDel)__delBase;
		return __del.create(userId, control, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_create(String userId, SessionControlPrx control)
	{
		return begin_create(userId, control, null, false, null);
	}

	public AsyncResult begin_create(String userId, SessionControlPrx control, Map __ctx)
	{
		return begin_create(userId, control, __ctx, true, null);
	}

	public AsyncResult begin_create(String userId, SessionControlPrx control, Callback __cb)
	{
		return begin_create(userId, control, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String userId, SessionControlPrx control, Map __ctx, Callback __cb)
	{
		return begin_create(userId, control, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String userId, SessionControlPrx control, Callback_SessionManager_create __cb)
	{
		return begin_create(userId, control, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String userId, SessionControlPrx control, Map __ctx, Callback_SessionManager_create __cb)
	{
		return begin_create(userId, control, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_create(String userId, SessionControlPrx control, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("create");
		OutgoingAsync __result = new OutgoingAsync(this, "create", __cb);
		try
		{
			__result.__prepare("create", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(userId);
			SessionControlPrxHelper.__write(__os, control);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public SessionPrx end_create(AsyncResult __result)
		throws CannotCreateSessionException
	{
		AsyncResult.__check(__result, this, "create");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (CannotCreateSessionException __ex)
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

	public boolean create_async(AMI_SessionManager_create __cb, String userId, SessionControlPrx control)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("create");
			__r = begin_create(userId, control, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "create", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean create_async(AMI_SessionManager_create __cb, String userId, SessionControlPrx control, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("create");
			__r = begin_create(userId, control, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "create", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static SessionManagerPrx checkedCast(ObjectPrx __obj)
	{
		SessionManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SessionManagerPrxHelper __h = new SessionManagerPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionManagerPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SessionManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SessionManagerPrxHelper __h = new SessionManagerPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionManagerPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SessionManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SessionManagerPrxHelper __h = new SessionManagerPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionManagerPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SessionManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SessionManagerPrxHelper __h = new SessionManagerPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionManagerPrx uncheckedCast(ObjectPrx __obj)
	{
		SessionManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SessionManagerPrxHelper __h = new SessionManagerPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SessionManagerPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SessionManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SessionManagerPrxHelper __h = new SessionManagerPrxHelper();
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
		return new _SessionManagerDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SessionManagerDelD();
	}

	public static void __write(BasicStream __os, SessionManagerPrx v)
	{
		__os.writeProxy(v);
	}

	public static SessionManagerPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SessionManagerPrxHelper result = new SessionManagerPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
