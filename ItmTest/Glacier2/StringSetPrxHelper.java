// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringSetPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_StringSetDel, StringSetPrx, _StringSetDelM, _StringSetDelD, 
//			Callback_StringSet_add, Callback_StringSet_get, Callback_StringSet_remove

public final class StringSetPrxHelper extends ObjectPrxHelperBase
	implements StringSetPrx
{

	private static final String __add_name = "add";
	private static final String __get_name = "get";
	private static final String __remove_name = "remove";
	public static final String __ids[] = {
		"::Glacier2::StringSet", "::Ice::Object"
	};

	public StringSetPrxHelper()
	{
	}

	public void add(String additions[])
	{
		add(additions, null, false);
	}

	public void add(String additions[], Map __ctx)
	{
		add(additions, __ctx, true);
	}

	private void add(String additions[], Map __ctx, boolean __explicitCtx)
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
				_StringSetDel __del = (_StringSetDel)__delBase;
				__del.add(additions, __ctx);
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

	public AsyncResult begin_add(String additions[])
	{
		return begin_add(additions, null, false, null);
	}

	public AsyncResult begin_add(String additions[], Map __ctx)
	{
		return begin_add(additions, __ctx, true, null);
	}

	public AsyncResult begin_add(String additions[], Callback __cb)
	{
		return begin_add(additions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_add(String additions[], Map __ctx, Callback __cb)
	{
		return begin_add(additions, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_add(String additions[], Callback_StringSet_add __cb)
	{
		return begin_add(additions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_add(String additions[], Map __ctx, Callback_StringSet_add __cb)
	{
		return begin_add(additions, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_add(String additions[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "add", __cb);
		try
		{
			__result.__prepare("add", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			StringSeqHelper.write(__os, additions);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_add(AsyncResult __result)
	{
		__end(__result, "add");
	}

	public String[] get()
	{
		return get(null, false);
	}

	public String[] get(Map __ctx)
	{
		return get(__ctx, true);
	}

	private String[] get(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_StringSetDel __del;
		__checkTwowayOnly("get");
		__delBase = __getDelegate(false);
		__del = (_StringSetDel)__delBase;
		return __del.get(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_get()
	{
		return begin_get(null, false, null);
	}

	public AsyncResult begin_get(Map __ctx)
	{
		return begin_get(__ctx, true, null);
	}

	public AsyncResult begin_get(Callback __cb)
	{
		return begin_get(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_get(Map __ctx, Callback __cb)
	{
		return begin_get(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_get(Callback_StringSet_get __cb)
	{
		return begin_get(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_get(Map __ctx, Callback_StringSet_get __cb)
	{
		return begin_get(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_get(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("get");
		OutgoingAsync __result = new OutgoingAsync(this, "get", __cb);
		try
		{
			__result.__prepare("get", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public String[] end_get(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "get");
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
		String __ret[] = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public void remove(String deletions[])
	{
		remove(deletions, null, false);
	}

	public void remove(String deletions[], Map __ctx)
	{
		remove(deletions, __ctx, true);
	}

	private void remove(String deletions[], Map __ctx, boolean __explicitCtx)
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
				_StringSetDel __del = (_StringSetDel)__delBase;
				__del.remove(deletions, __ctx);
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

	public AsyncResult begin_remove(String deletions[])
	{
		return begin_remove(deletions, null, false, null);
	}

	public AsyncResult begin_remove(String deletions[], Map __ctx)
	{
		return begin_remove(deletions, __ctx, true, null);
	}

	public AsyncResult begin_remove(String deletions[], Callback __cb)
	{
		return begin_remove(deletions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_remove(String deletions[], Map __ctx, Callback __cb)
	{
		return begin_remove(deletions, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_remove(String deletions[], Callback_StringSet_remove __cb)
	{
		return begin_remove(deletions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_remove(String deletions[], Map __ctx, Callback_StringSet_remove __cb)
	{
		return begin_remove(deletions, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_remove(String deletions[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "remove", __cb);
		try
		{
			__result.__prepare("remove", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			StringSeqHelper.write(__os, deletions);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_remove(AsyncResult __result)
	{
		__end(__result, "remove");
	}

	public static StringSetPrx checkedCast(ObjectPrx __obj)
	{
		StringSetPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (StringSetPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					StringSetPrxHelper __h = new StringSetPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static StringSetPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		StringSetPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (StringSetPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					StringSetPrxHelper __h = new StringSetPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static StringSetPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		StringSetPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					StringSetPrxHelper __h = new StringSetPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static StringSetPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		StringSetPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					StringSetPrxHelper __h = new StringSetPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static StringSetPrx uncheckedCast(ObjectPrx __obj)
	{
		StringSetPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (StringSetPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				StringSetPrxHelper __h = new StringSetPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static StringSetPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		StringSetPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			StringSetPrxHelper __h = new StringSetPrxHelper();
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
		return new _StringSetDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _StringSetDelD();
	}

	public static void __write(BasicStream __os, StringSetPrx v)
	{
		__os.writeProxy(v);
	}

	public static StringSetPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			StringSetPrxHelper result = new StringSetPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
