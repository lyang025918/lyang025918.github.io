// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IdentitySetPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_IdentitySetDel, IdentitySetPrx, _IdentitySetDelM, _IdentitySetDelD, 
//			Callback_IdentitySet_add, Callback_IdentitySet_get, Callback_IdentitySet_remove

public final class IdentitySetPrxHelper extends ObjectPrxHelperBase
	implements IdentitySetPrx
{

	private static final String __add_name = "add";
	private static final String __get_name = "get";
	private static final String __remove_name = "remove";
	public static final String __ids[] = {
		"::Glacier2::IdentitySet", "::Ice::Object"
	};

	public IdentitySetPrxHelper()
	{
	}

	public void add(Identity additions[])
	{
		add(additions, null, false);
	}

	public void add(Identity additions[], Map __ctx)
	{
		add(additions, __ctx, true);
	}

	private void add(Identity additions[], Map __ctx, boolean __explicitCtx)
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
				_IdentitySetDel __del = (_IdentitySetDel)__delBase;
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

	public AsyncResult begin_add(Identity additions[])
	{
		return begin_add(additions, null, false, null);
	}

	public AsyncResult begin_add(Identity additions[], Map __ctx)
	{
		return begin_add(additions, __ctx, true, null);
	}

	public AsyncResult begin_add(Identity additions[], Callback __cb)
	{
		return begin_add(additions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_add(Identity additions[], Map __ctx, Callback __cb)
	{
		return begin_add(additions, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_add(Identity additions[], Callback_IdentitySet_add __cb)
	{
		return begin_add(additions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_add(Identity additions[], Map __ctx, Callback_IdentitySet_add __cb)
	{
		return begin_add(additions, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_add(Identity additions[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "add", __cb);
		try
		{
			__result.__prepare("add", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			IdentitySeqHelper.write(__os, additions);
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

	public Identity[] get()
	{
		return get(null, false);
	}

	public Identity[] get(Map __ctx)
	{
		return get(__ctx, true);
	}

	private Identity[] get(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_IdentitySetDel __del;
		__checkTwowayOnly("get");
		__delBase = __getDelegate(false);
		__del = (_IdentitySetDel)__delBase;
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

	public AsyncResult begin_get(Callback_IdentitySet_get __cb)
	{
		return begin_get(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_get(Map __ctx, Callback_IdentitySet_get __cb)
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

	public Identity[] end_get(AsyncResult __result)
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
		Identity __ret[] = IdentitySeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public void remove(Identity deletions[])
	{
		remove(deletions, null, false);
	}

	public void remove(Identity deletions[], Map __ctx)
	{
		remove(deletions, __ctx, true);
	}

	private void remove(Identity deletions[], Map __ctx, boolean __explicitCtx)
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
				_IdentitySetDel __del = (_IdentitySetDel)__delBase;
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

	public AsyncResult begin_remove(Identity deletions[])
	{
		return begin_remove(deletions, null, false, null);
	}

	public AsyncResult begin_remove(Identity deletions[], Map __ctx)
	{
		return begin_remove(deletions, __ctx, true, null);
	}

	public AsyncResult begin_remove(Identity deletions[], Callback __cb)
	{
		return begin_remove(deletions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_remove(Identity deletions[], Map __ctx, Callback __cb)
	{
		return begin_remove(deletions, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_remove(Identity deletions[], Callback_IdentitySet_remove __cb)
	{
		return begin_remove(deletions, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_remove(Identity deletions[], Map __ctx, Callback_IdentitySet_remove __cb)
	{
		return begin_remove(deletions, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_remove(Identity deletions[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "remove", __cb);
		try
		{
			__result.__prepare("remove", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			IdentitySeqHelper.write(__os, deletions);
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

	public static IdentitySetPrx checkedCast(ObjectPrx __obj)
	{
		IdentitySetPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (IdentitySetPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					IdentitySetPrxHelper __h = new IdentitySetPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static IdentitySetPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		IdentitySetPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (IdentitySetPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					IdentitySetPrxHelper __h = new IdentitySetPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static IdentitySetPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		IdentitySetPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					IdentitySetPrxHelper __h = new IdentitySetPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static IdentitySetPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		IdentitySetPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					IdentitySetPrxHelper __h = new IdentitySetPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static IdentitySetPrx uncheckedCast(ObjectPrx __obj)
	{
		IdentitySetPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (IdentitySetPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				IdentitySetPrxHelper __h = new IdentitySetPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static IdentitySetPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		IdentitySetPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			IdentitySetPrxHelper __h = new IdentitySetPrxHelper();
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
		return new _IdentitySetDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _IdentitySetDelD();
	}

	public static void __write(BasicStream __os, IdentitySetPrx v)
	{
		__os.writeProxy(v);
	}

	public static IdentitySetPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			IdentitySetPrxHelper result = new IdentitySetPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
