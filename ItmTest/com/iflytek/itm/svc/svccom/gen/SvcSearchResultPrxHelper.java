// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcSearchResultPrxHelper.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_SvcSearchResultDel, SvcDocument, SvcGroup, SvcSearchResultPrx, 
//			_SvcSearchResultDelM, _SvcSearchResultDelD, Callback_SvcSearchResult_close, Callback_SvcSearchResult_docs, 
//			Callback_SvcSearchResult_getGroupTotalHits, Callback_SvcSearchResult_getTotalHits, Callback_SvcSearchResult_groups

public final class SvcSearchResultPrxHelper extends ObjectPrxHelperBase
	implements SvcSearchResultPrx
{

	private static final String __close_name = "close";
	private static final String __docs_name = "docs";
	private static final String __getGroupTotalHits_name = "getGroupTotalHits";
	private static final String __getTotalHits_name = "getTotalHits";
	private static final String __groups_name = "groups";
	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::SvcSearchResult"
	};

	public SvcSearchResultPrxHelper()
	{
	}

	public int close()
	{
		return close(null, false);
	}

	public int close(Map __ctx)
	{
		return close(__ctx, true);
	}

	private int close(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_SvcSearchResultDel __del;
		__checkTwowayOnly("close");
		__delBase = __getDelegate(false);
		__del = (_SvcSearchResultDel)__delBase;
		return __del.close(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_close()
	{
		return begin_close(null, false, null);
	}

	public AsyncResult begin_close(Map __ctx)
	{
		return begin_close(__ctx, true, null);
	}

	public AsyncResult begin_close(Callback __cb)
	{
		return begin_close(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_close(Map __ctx, Callback __cb)
	{
		return begin_close(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_close(Callback_SvcSearchResult_close __cb)
	{
		return begin_close(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_close(Map __ctx, Callback_SvcSearchResult_close __cb)
	{
		return begin_close(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_close(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("close");
		OutgoingAsync __result = new OutgoingAsync(this, "close", __cb);
		try
		{
			__result.__prepare("close", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_close(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "close");
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

	public int docs(Holder svcdocs)
	{
		return docs(svcdocs, null, false);
	}

	public int docs(Holder svcdocs, Map __ctx)
	{
		return docs(svcdocs, __ctx, true);
	}

	private int docs(Holder svcdocs, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_SvcSearchResultDel __del;
		__checkTwowayOnly("docs");
		__delBase = __getDelegate(false);
		__del = (_SvcSearchResultDel)__delBase;
		return __del.docs(svcdocs, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_docs()
	{
		return begin_docs(null, false, null);
	}

	public AsyncResult begin_docs(Map __ctx)
	{
		return begin_docs(__ctx, true, null);
	}

	public AsyncResult begin_docs(Callback __cb)
	{
		return begin_docs(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_docs(Map __ctx, Callback __cb)
	{
		return begin_docs(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_docs(Callback_SvcSearchResult_docs __cb)
	{
		return begin_docs(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_docs(Map __ctx, Callback_SvcSearchResult_docs __cb)
	{
		return begin_docs(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_docs(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("docs");
		OutgoingAsync __result = new OutgoingAsync(this, "docs", __cb);
		try
		{
			__result.__prepare("docs", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_docs(Holder svcdocs, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "docs");
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
		svcdocs.value = new ArrayList();
		int __len0 = __is.readAndCheckSeqSize(5);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			SvcDocument __elem = new SvcDocument();
			__elem.__read(__is);
			((List)svcdocs.value).add(__elem);
		}

		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public int getGroupTotalHits()
	{
		return getGroupTotalHits(null, false);
	}

	public int getGroupTotalHits(Map __ctx)
	{
		return getGroupTotalHits(__ctx, true);
	}

	private int getGroupTotalHits(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_SvcSearchResultDel __del;
		__checkTwowayOnly("getGroupTotalHits");
		__delBase = __getDelegate(false);
		__del = (_SvcSearchResultDel)__delBase;
		return __del.getGroupTotalHits(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getGroupTotalHits()
	{
		return begin_getGroupTotalHits(null, false, null);
	}

	public AsyncResult begin_getGroupTotalHits(Map __ctx)
	{
		return begin_getGroupTotalHits(__ctx, true, null);
	}

	public AsyncResult begin_getGroupTotalHits(Callback __cb)
	{
		return begin_getGroupTotalHits(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getGroupTotalHits(Map __ctx, Callback __cb)
	{
		return begin_getGroupTotalHits(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getGroupTotalHits(Callback_SvcSearchResult_getGroupTotalHits __cb)
	{
		return begin_getGroupTotalHits(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getGroupTotalHits(Map __ctx, Callback_SvcSearchResult_getGroupTotalHits __cb)
	{
		return begin_getGroupTotalHits(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getGroupTotalHits(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getGroupTotalHits");
		OutgoingAsync __result = new OutgoingAsync(this, "getGroupTotalHits", __cb);
		try
		{
			__result.__prepare("getGroupTotalHits", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_getGroupTotalHits(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getGroupTotalHits");
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

	public int getTotalHits()
	{
		return getTotalHits(null, false);
	}

	public int getTotalHits(Map __ctx)
	{
		return getTotalHits(__ctx, true);
	}

	private int getTotalHits(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_SvcSearchResultDel __del;
		__checkTwowayOnly("getTotalHits");
		__delBase = __getDelegate(false);
		__del = (_SvcSearchResultDel)__delBase;
		return __del.getTotalHits(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getTotalHits()
	{
		return begin_getTotalHits(null, false, null);
	}

	public AsyncResult begin_getTotalHits(Map __ctx)
	{
		return begin_getTotalHits(__ctx, true, null);
	}

	public AsyncResult begin_getTotalHits(Callback __cb)
	{
		return begin_getTotalHits(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getTotalHits(Map __ctx, Callback __cb)
	{
		return begin_getTotalHits(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getTotalHits(Callback_SvcSearchResult_getTotalHits __cb)
	{
		return begin_getTotalHits(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getTotalHits(Map __ctx, Callback_SvcSearchResult_getTotalHits __cb)
	{
		return begin_getTotalHits(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getTotalHits(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getTotalHits");
		OutgoingAsync __result = new OutgoingAsync(this, "getTotalHits", __cb);
		try
		{
			__result.__prepare("getTotalHits", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_getTotalHits(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getTotalHits");
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

	public int groups(Holder svcgroups)
	{
		return groups(svcgroups, null, false);
	}

	public int groups(Holder svcgroups, Map __ctx)
	{
		return groups(svcgroups, __ctx, true);
	}

	private int groups(Holder svcgroups, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_SvcSearchResultDel __del;
		__checkTwowayOnly("groups");
		__delBase = __getDelegate(false);
		__del = (_SvcSearchResultDel)__delBase;
		return __del.groups(svcgroups, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_groups()
	{
		return begin_groups(null, false, null);
	}

	public AsyncResult begin_groups(Map __ctx)
	{
		return begin_groups(__ctx, true, null);
	}

	public AsyncResult begin_groups(Callback __cb)
	{
		return begin_groups(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_groups(Map __ctx, Callback __cb)
	{
		return begin_groups(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_groups(Callback_SvcSearchResult_groups __cb)
	{
		return begin_groups(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_groups(Map __ctx, Callback_SvcSearchResult_groups __cb)
	{
		return begin_groups(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_groups(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("groups");
		OutgoingAsync __result = new OutgoingAsync(this, "groups", __cb);
		try
		{
			__result.__prepare("groups", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_groups(Holder svcgroups, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "groups");
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
		svcgroups.value = new ArrayList();
		int __len0 = __is.readAndCheckSeqSize(5);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			SvcGroup __elem = new SvcGroup();
			__elem.__read(__is);
			((List)svcgroups.value).add(__elem);
		}

		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public static SvcSearchResultPrx checkedCast(ObjectPrx __obj)
	{
		SvcSearchResultPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SvcSearchResultPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SvcSearchResultPrxHelper __h = new SvcSearchResultPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SvcSearchResultPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SvcSearchResultPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SvcSearchResultPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SvcSearchResultPrxHelper __h = new SvcSearchResultPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SvcSearchResultPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SvcSearchResultPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SvcSearchResultPrxHelper __h = new SvcSearchResultPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SvcSearchResultPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SvcSearchResultPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SvcSearchResultPrxHelper __h = new SvcSearchResultPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SvcSearchResultPrx uncheckedCast(ObjectPrx __obj)
	{
		SvcSearchResultPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SvcSearchResultPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SvcSearchResultPrxHelper __h = new SvcSearchResultPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SvcSearchResultPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SvcSearchResultPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SvcSearchResultPrxHelper __h = new SvcSearchResultPrxHelper();
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
		return new _SvcSearchResultDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SvcSearchResultDelD();
	}

	public static void __write(BasicStream __os, SvcSearchResultPrx v)
	{
		__os.writeProxy(v);
	}

	public static SvcSearchResultPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SvcSearchResultPrxHelper result = new SvcSearchResultPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
