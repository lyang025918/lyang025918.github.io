// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItmSvcPrxHelper.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_ItmSvcDel, ItmSvcPrx, _ItmSvcDelM, _ItmSvcDelD, 
//			SvcBuildCallbackPrx, SvcBuildCallbackPrxHelper, SvcSearchResultPrxHelper, Callback_ItmSvc_build, 
//			Callback_ItmSvc_maintain, Callback_ItmSvc_mining, SvcSearchResultPrx, Callback_ItmSvc_search

public final class ItmSvcPrxHelper extends ObjectPrxHelperBase
	implements ItmSvcPrx
{

	private static final String __build_name = "build";
	private static final String __maintain_name = "maintain";
	private static final String __mining_name = "mining";
	private static final String __search_name = "search";
	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::ItmSvc"
	};

	public ItmSvcPrxHelper()
	{
	}

	public int build(String indexPath, String params, SvcBuildCallbackPrx builder)
	{
		return build(indexPath, params, builder, null, false);
	}

	public int build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx)
	{
		return build(indexPath, params, builder, __ctx, true);
	}

	private int build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_ItmSvcDel __del;
		__checkTwowayOnly("build");
		__delBase = __getDelegate(false);
		__del = (_ItmSvcDel)__delBase;
		return __del.build(indexPath, params, builder, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder)
	{
		return begin_build(indexPath, params, builder, null, false, null);
	}

	public AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx)
	{
		return begin_build(indexPath, params, builder, __ctx, true, null);
	}

	public AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder, Callback __cb)
	{
		return begin_build(indexPath, params, builder, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx, Callback __cb)
	{
		return begin_build(indexPath, params, builder, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder, Callback_ItmSvc_build __cb)
	{
		return begin_build(indexPath, params, builder, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx, Callback_ItmSvc_build __cb)
	{
		return begin_build(indexPath, params, builder, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("build");
		OutgoingAsync __result = new OutgoingAsync(this, "build", __cb);
		try
		{
			__result.__prepare("build", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(indexPath);
			__os.writeString(params);
			SvcBuildCallbackPrxHelper.__write(__os, builder);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public int end_build(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "build");
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

	public int maintain(String indexPath, String action, String params)
	{
		return maintain(indexPath, action, params, null, false);
	}

	public int maintain(String indexPath, String action, String params, Map __ctx)
	{
		return maintain(indexPath, action, params, __ctx, true);
	}

	private int maintain(String indexPath, String action, String params, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_ItmSvcDel __del;
		__checkTwowayOnly("maintain");
		__delBase = __getDelegate(false);
		__del = (_ItmSvcDel)__delBase;
		return __del.maintain(indexPath, action, params, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_maintain(String indexPath, String action, String params)
	{
		return begin_maintain(indexPath, action, params, null, false, null);
	}

	public AsyncResult begin_maintain(String indexPath, String action, String params, Map __ctx)
	{
		return begin_maintain(indexPath, action, params, __ctx, true, null);
	}

	public AsyncResult begin_maintain(String indexPath, String action, String params, Callback __cb)
	{
		return begin_maintain(indexPath, action, params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_maintain(String indexPath, String action, String params, Map __ctx, Callback __cb)
	{
		return begin_maintain(indexPath, action, params, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_maintain(String indexPath, String action, String params, Callback_ItmSvc_maintain __cb)
	{
		return begin_maintain(indexPath, action, params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_maintain(String indexPath, String action, String params, Map __ctx, Callback_ItmSvc_maintain __cb)
	{
		return begin_maintain(indexPath, action, params, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_maintain(String indexPath, String action, String params, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("maintain");
		OutgoingAsync __result = new OutgoingAsync(this, "maintain", __cb);
		try
		{
			__result.__prepare("maintain", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(indexPath);
			__os.writeString(action);
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

	public int end_maintain(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "maintain");
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

	public int mining(String indexPath, String type, String params, StringHolder buffer)
	{
		return mining(indexPath, type, params, buffer, null, false);
	}

	public int mining(String indexPath, String type, String params, StringHolder buffer, Map __ctx)
	{
		return mining(indexPath, type, params, buffer, __ctx, true);
	}

	private int mining(String indexPath, String type, String params, StringHolder buffer, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_ItmSvcDel __del;
		__checkTwowayOnly("mining");
		__delBase = __getDelegate(false);
		__del = (_ItmSvcDel)__delBase;
		return __del.mining(indexPath, type, params, buffer, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_mining(String indexPath, String type, String params)
	{
		return begin_mining(indexPath, type, params, null, false, null);
	}

	public AsyncResult begin_mining(String indexPath, String type, String params, Map __ctx)
	{
		return begin_mining(indexPath, type, params, __ctx, true, null);
	}

	public AsyncResult begin_mining(String indexPath, String type, String params, Callback __cb)
	{
		return begin_mining(indexPath, type, params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_mining(String indexPath, String type, String params, Map __ctx, Callback __cb)
	{
		return begin_mining(indexPath, type, params, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_mining(String indexPath, String type, String params, Callback_ItmSvc_mining __cb)
	{
		return begin_mining(indexPath, type, params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_mining(String indexPath, String type, String params, Map __ctx, Callback_ItmSvc_mining __cb)
	{
		return begin_mining(indexPath, type, params, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_mining(String indexPath, String type, String params, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("mining");
		OutgoingAsync __result = new OutgoingAsync(this, "mining", __cb);
		try
		{
			__result.__prepare("mining", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(indexPath);
			__os.writeString(type);
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

	public int end_mining(StringHolder buffer, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "mining");
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
		buffer.value = __is.readString();
		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public SvcSearchResultPrx search(String indexPath, String querySyntax, String params, IntHolder errcode)
	{
		return search(indexPath, querySyntax, params, errcode, null, false);
	}

	public SvcSearchResultPrx search(String indexPath, String querySyntax, String params, IntHolder errcode, Map __ctx)
	{
		return search(indexPath, querySyntax, params, errcode, __ctx, true);
	}

	private SvcSearchResultPrx search(String indexPath, String querySyntax, String params, IntHolder errcode, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_ItmSvcDel __del;
		__checkTwowayOnly("search");
		__delBase = __getDelegate(false);
		__del = (_ItmSvcDel)__delBase;
		return __del.search(indexPath, querySyntax, params, errcode, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_search(String indexPath, String querySyntax, String params)
	{
		return begin_search(indexPath, querySyntax, params, null, false, null);
	}

	public AsyncResult begin_search(String indexPath, String querySyntax, String params, Map __ctx)
	{
		return begin_search(indexPath, querySyntax, params, __ctx, true, null);
	}

	public AsyncResult begin_search(String indexPath, String querySyntax, String params, Callback __cb)
	{
		return begin_search(indexPath, querySyntax, params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_search(String indexPath, String querySyntax, String params, Map __ctx, Callback __cb)
	{
		return begin_search(indexPath, querySyntax, params, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_search(String indexPath, String querySyntax, String params, Callback_ItmSvc_search __cb)
	{
		return begin_search(indexPath, querySyntax, params, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_search(String indexPath, String querySyntax, String params, Map __ctx, Callback_ItmSvc_search __cb)
	{
		return begin_search(indexPath, querySyntax, params, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_search(String indexPath, String querySyntax, String params, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("search");
		OutgoingAsync __result = new OutgoingAsync(this, "search", __cb);
		try
		{
			__result.__prepare("search", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(indexPath);
			__os.writeString(querySyntax);
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

	public SvcSearchResultPrx end_search(IntHolder errcode, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "search");
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
		errcode.value = __is.readInt();
		SvcSearchResultPrx __ret = SvcSearchResultPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public static ItmSvcPrx checkedCast(ObjectPrx __obj)
	{
		ItmSvcPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ItmSvcPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ItmSvcPrxHelper __h = new ItmSvcPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ItmSvcPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ItmSvcPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ItmSvcPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ItmSvcPrxHelper __h = new ItmSvcPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ItmSvcPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ItmSvcPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ItmSvcPrxHelper __h = new ItmSvcPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ItmSvcPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ItmSvcPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ItmSvcPrxHelper __h = new ItmSvcPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ItmSvcPrx uncheckedCast(ObjectPrx __obj)
	{
		ItmSvcPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ItmSvcPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ItmSvcPrxHelper __h = new ItmSvcPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ItmSvcPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ItmSvcPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ItmSvcPrxHelper __h = new ItmSvcPrxHelper();
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
		return new _ItmSvcDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ItmSvcDelD();
	}

	public static void __write(BasicStream __os, ItmSvcPrx v)
	{
		__os.writeProxy(v);
	}

	public static ItmSvcPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ItmSvcPrxHelper result = new ItmSvcPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
