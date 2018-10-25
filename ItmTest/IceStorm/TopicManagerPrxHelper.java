// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopicManagerPrxHelper.java

package IceStorm;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceStorm:
//			_TopicManagerDel, TopicExists, NoSuchTopic, TopicManagerPrx, 
//			_TopicManagerDelM, _TopicManagerDelD, TopicPrxHelper, TopicDictHelper, 
//			TopicPrx, Callback_TopicManager_create, Callback_TopicManager_getSliceChecksums, Callback_TopicManager_retrieve, 
//			Callback_TopicManager_retrieveAll

public final class TopicManagerPrxHelper extends ObjectPrxHelperBase
	implements TopicManagerPrx
{

	private static final String __create_name = "create";
	private static final String __getSliceChecksums_name = "getSliceChecksums";
	private static final String __retrieve_name = "retrieve";
	private static final String __retrieveAll_name = "retrieveAll";
	public static final String __ids[] = {
		"::Ice::Object", "::IceStorm::TopicManager"
	};

	public TopicManagerPrxHelper()
	{
	}

	public TopicPrx create(String name)
		throws TopicExists
	{
		return create(name, null, false);
	}

	public TopicPrx create(String name, Map __ctx)
		throws TopicExists
	{
		return create(name, __ctx, true);
	}

	private TopicPrx create(String name, Map __ctx, boolean __explicitCtx)
		throws TopicExists
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicManagerDel __del;
		__checkTwowayOnly("create");
		__delBase = __getDelegate(false);
		__del = (_TopicManagerDel)__delBase;
		return __del.create(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_create(String name)
	{
		return begin_create(name, null, false, null);
	}

	public AsyncResult begin_create(String name, Map __ctx)
	{
		return begin_create(name, __ctx, true, null);
	}

	public AsyncResult begin_create(String name, Callback __cb)
	{
		return begin_create(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String name, Map __ctx, Callback __cb)
	{
		return begin_create(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String name, Callback_TopicManager_create __cb)
	{
		return begin_create(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_create(String name, Map __ctx, Callback_TopicManager_create __cb)
	{
		return begin_create(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_create(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("create");
		OutgoingAsync __result = new OutgoingAsync(this, "create", __cb);
		try
		{
			__result.__prepare("create", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public TopicPrx end_create(AsyncResult __result)
		throws TopicExists
	{
		AsyncResult.__check(__result, this, "create");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (TopicExists __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		TopicPrx __ret = TopicPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public Map getSliceChecksums()
	{
		return getSliceChecksums(null, false);
	}

	public Map getSliceChecksums(Map __ctx)
	{
		return getSliceChecksums(__ctx, true);
	}

	private Map getSliceChecksums(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicManagerDel __del;
		__checkTwowayOnly("getSliceChecksums");
		__delBase = __getDelegate(false);
		__del = (_TopicManagerDel)__delBase;
		return __del.getSliceChecksums(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getSliceChecksums()
	{
		return begin_getSliceChecksums(null, false, null);
	}

	public AsyncResult begin_getSliceChecksums(Map __ctx)
	{
		return begin_getSliceChecksums(__ctx, true, null);
	}

	public AsyncResult begin_getSliceChecksums(Callback __cb)
	{
		return begin_getSliceChecksums(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSliceChecksums(Map __ctx, Callback __cb)
	{
		return begin_getSliceChecksums(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSliceChecksums(Callback_TopicManager_getSliceChecksums __cb)
	{
		return begin_getSliceChecksums(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSliceChecksums(Map __ctx, Callback_TopicManager_getSliceChecksums __cb)
	{
		return begin_getSliceChecksums(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getSliceChecksums(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getSliceChecksums");
		OutgoingAsync __result = new OutgoingAsync(this, "getSliceChecksums", __cb);
		try
		{
			__result.__prepare("getSliceChecksums", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public Map end_getSliceChecksums(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getSliceChecksums");
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
		Map __ret = SliceChecksumDictHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public TopicPrx retrieve(String name)
		throws NoSuchTopic
	{
		return retrieve(name, null, false);
	}

	public TopicPrx retrieve(String name, Map __ctx)
		throws NoSuchTopic
	{
		return retrieve(name, __ctx, true);
	}

	private TopicPrx retrieve(String name, Map __ctx, boolean __explicitCtx)
		throws NoSuchTopic
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicManagerDel __del;
		__checkTwowayOnly("retrieve");
		__delBase = __getDelegate(false);
		__del = (_TopicManagerDel)__delBase;
		return __del.retrieve(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_retrieve(String name)
	{
		return begin_retrieve(name, null, false, null);
	}

	public AsyncResult begin_retrieve(String name, Map __ctx)
	{
		return begin_retrieve(name, __ctx, true, null);
	}

	public AsyncResult begin_retrieve(String name, Callback __cb)
	{
		return begin_retrieve(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_retrieve(String name, Map __ctx, Callback __cb)
	{
		return begin_retrieve(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_retrieve(String name, Callback_TopicManager_retrieve __cb)
	{
		return begin_retrieve(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_retrieve(String name, Map __ctx, Callback_TopicManager_retrieve __cb)
	{
		return begin_retrieve(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_retrieve(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("retrieve");
		OutgoingAsync __result = new OutgoingAsync(this, "retrieve", __cb);
		try
		{
			__result.__prepare("retrieve", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public TopicPrx end_retrieve(AsyncResult __result)
		throws NoSuchTopic
	{
		AsyncResult.__check(__result, this, "retrieve");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NoSuchTopic __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		TopicPrx __ret = TopicPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public Map retrieveAll()
	{
		return retrieveAll(null, false);
	}

	public Map retrieveAll(Map __ctx)
	{
		return retrieveAll(__ctx, true);
	}

	private Map retrieveAll(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicManagerDel __del;
		__checkTwowayOnly("retrieveAll");
		__delBase = __getDelegate(false);
		__del = (_TopicManagerDel)__delBase;
		return __del.retrieveAll(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_retrieveAll()
	{
		return begin_retrieveAll(null, false, null);
	}

	public AsyncResult begin_retrieveAll(Map __ctx)
	{
		return begin_retrieveAll(__ctx, true, null);
	}

	public AsyncResult begin_retrieveAll(Callback __cb)
	{
		return begin_retrieveAll(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_retrieveAll(Map __ctx, Callback __cb)
	{
		return begin_retrieveAll(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_retrieveAll(Callback_TopicManager_retrieveAll __cb)
	{
		return begin_retrieveAll(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_retrieveAll(Map __ctx, Callback_TopicManager_retrieveAll __cb)
	{
		return begin_retrieveAll(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_retrieveAll(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("retrieveAll");
		OutgoingAsync __result = new OutgoingAsync(this, "retrieveAll", __cb);
		try
		{
			__result.__prepare("retrieveAll", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public Map end_retrieveAll(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "retrieveAll");
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
		Map __ret = TopicDictHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public static TopicManagerPrx checkedCast(ObjectPrx __obj)
	{
		TopicManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (TopicManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					TopicManagerPrxHelper __h = new TopicManagerPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static TopicManagerPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		TopicManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (TopicManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					TopicManagerPrxHelper __h = new TopicManagerPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static TopicManagerPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		TopicManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					TopicManagerPrxHelper __h = new TopicManagerPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static TopicManagerPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		TopicManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					TopicManagerPrxHelper __h = new TopicManagerPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static TopicManagerPrx uncheckedCast(ObjectPrx __obj)
	{
		TopicManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (TopicManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				TopicManagerPrxHelper __h = new TopicManagerPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static TopicManagerPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		TopicManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			TopicManagerPrxHelper __h = new TopicManagerPrxHelper();
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
		return new _TopicManagerDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _TopicManagerDelD();
	}

	public static void __write(BasicStream __os, TopicManagerPrx v)
	{
		__os.writeProxy(v);
	}

	public static TopicManagerPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			TopicManagerPrxHelper result = new TopicManagerPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
