// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItmSvcFactoryPrxHelper.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_ItmSvcFactoryDel, ItmSvcFactoryPrx, _ItmSvcFactoryDelM, _ItmSvcFactoryDelD, 
//			ItmSvcPrxHelper, ItmSvcPrx, Callback_ItmSvcFactory_create

public final class ItmSvcFactoryPrxHelper extends ObjectPrxHelperBase
	implements ItmSvcFactoryPrx
{

	private static final String __create_name = "create";
	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::ItmSvcFactory"
	};

	public ItmSvcFactoryPrxHelper()
	{
	}

	public ItmSvcPrx create(String params)
	{
		return create(params, null, false);
	}

	public ItmSvcPrx create(String params, Map __ctx)
	{
		return create(params, __ctx, true);
	}

	private ItmSvcPrx create(String params, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_ItmSvcFactoryDel __del;
		__checkTwowayOnly("create");
		__delBase = __getDelegate(false);
		__del = (_ItmSvcFactoryDel)__delBase;
		return __del.create(params, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_create(String params)
	{
		return begin_create(params, null, false, null);
	}

	public AsyncResult begin_create(String params, Map __ctx)
	{
		return begin_create(params, __ctx, true, null);
	}

	public AsyncResult begin_create(String params, Callback __cb)
	{
		return begin_create(params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String params, Map __ctx, Callback __cb)
	{
		return begin_create(params, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String params, Callback_ItmSvcFactory_create __cb)
	{
		return begin_create(params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String params, Map __ctx, Callback_ItmSvcFactory_create __cb)
	{
		return begin_create(params, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_create(String params, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("create");
		OutgoingAsync __result = new OutgoingAsync(this, "create", __cb);
		try
		{
			__result.__prepare("create", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(params);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ItmSvcPrx end_create(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "create");
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
		ItmSvcPrx __ret = ItmSvcPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public static ItmSvcFactoryPrx checkedCast(ObjectPrx __obj)
	{
		ItmSvcFactoryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ItmSvcFactoryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ItmSvcFactoryPrxHelper __h = new ItmSvcFactoryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ItmSvcFactoryPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ItmSvcFactoryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ItmSvcFactoryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ItmSvcFactoryPrxHelper __h = new ItmSvcFactoryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ItmSvcFactoryPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ItmSvcFactoryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ItmSvcFactoryPrxHelper __h = new ItmSvcFactoryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ItmSvcFactoryPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ItmSvcFactoryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ItmSvcFactoryPrxHelper __h = new ItmSvcFactoryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ItmSvcFactoryPrx uncheckedCast(ObjectPrx __obj)
	{
		ItmSvcFactoryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ItmSvcFactoryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ItmSvcFactoryPrxHelper __h = new ItmSvcFactoryPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ItmSvcFactoryPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ItmSvcFactoryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ItmSvcFactoryPrxHelper __h = new ItmSvcFactoryPrxHelper();
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
		return new _ItmSvcFactoryDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ItmSvcFactoryDelD();
	}

	public static void __write(BasicStream __os, ItmSvcFactoryPrx v)
	{
		__os.writeProxy(v);
	}

	public static ItmSvcFactoryPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ItmSvcFactoryPrxHelper result = new ItmSvcFactoryPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
