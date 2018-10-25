// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdminPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_AdminDel, AdminPrx, _AdminDelM, _AdminDelD, 
//			Callback_Admin_shutdown

public final class AdminPrxHelper extends ObjectPrxHelperBase
	implements AdminPrx
{

	private static final String __shutdown_name = "shutdown";
	public static final String __ids[] = {
		"::Glacier2::Admin", "::Ice::Object"
	};

	public AdminPrxHelper()
	{
	}

	public void shutdown()
	{
		shutdown(null, false);
	}

	public void shutdown(Map __ctx)
	{
		shutdown(__ctx, true);
	}

	private void shutdown(Map __ctx, boolean __explicitCtx)
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
				_AdminDel __del = (_AdminDel)__delBase;
				__del.shutdown(__ctx);
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

	public AsyncResult begin_shutdown()
	{
		return begin_shutdown(null, false, null);
	}

	public AsyncResult begin_shutdown(Map __ctx)
	{
		return begin_shutdown(__ctx, true, null);
	}

	public AsyncResult begin_shutdown(Callback __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback __cb)
	{
		return begin_shutdown(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Callback_Admin_shutdown __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback_Admin_shutdown __cb)
	{
		return begin_shutdown(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_shutdown(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "shutdown", __cb);
		try
		{
			__result.__prepare("shutdown", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_shutdown(AsyncResult __result)
	{
		__end(__result, "shutdown");
	}

	public static AdminPrx checkedCast(ObjectPrx __obj)
	{
		AdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdminPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		AdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdminPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		AdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdminPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		AdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdminPrx uncheckedCast(ObjectPrx __obj)
	{
		AdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				AdminPrxHelper __h = new AdminPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static AdminPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		AdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			AdminPrxHelper __h = new AdminPrxHelper();
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
		return new _AdminDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _AdminDelD();
	}

	public static void __write(BasicStream __os, AdminPrx v)
	{
		__os.writeProxy(v);
	}

	public static AdminPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			AdminPrxHelper result = new AdminPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
