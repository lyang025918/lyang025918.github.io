// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_SessionDel, SessionPrx, _SessionDelM, _SessionDelD, 
//			Callback_Session_destroy, AMI_Session_destroy

public final class SessionPrxHelper extends ObjectPrxHelperBase
	implements SessionPrx
{

	private static final String __destroy_name = "destroy";
	public static final String __ids[] = {
		"::Glacier2::Session", "::Ice::Object"
	};

	public SessionPrxHelper()
	{
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
				_SessionDel __del = (_SessionDel)__delBase;
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

	public AsyncResult begin_destroy(Callback_Session_destroy __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback_Session_destroy __cb)
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

	public boolean destroy_async(AMI_Session_destroy __cb)
	{
		AsyncResult __r = begin_destroy(null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean destroy_async(AMI_Session_destroy __cb, Map __ctx)
	{
		AsyncResult __r = begin_destroy(__ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public static SessionPrx checkedCast(ObjectPrx __obj)
	{
		SessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionPrx uncheckedCast(ObjectPrx __obj)
	{
		SessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SessionPrxHelper __h = new SessionPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SessionPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SessionPrxHelper __h = new SessionPrxHelper();
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
		return new _SessionDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SessionDelD();
	}

	public static void __write(BasicStream __os, SessionPrx v)
	{
		__os.writeProxy(v);
	}

	public static SessionPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SessionPrxHelper result = new SessionPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
