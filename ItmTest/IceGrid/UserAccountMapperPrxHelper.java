// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserAccountMapperPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_UserAccountMapperDel, UserAccountNotFoundException, UserAccountMapperPrx, _UserAccountMapperDelM, 
//			_UserAccountMapperDelD, Callback_UserAccountMapper_getUserAccount

public final class UserAccountMapperPrxHelper extends ObjectPrxHelperBase
	implements UserAccountMapperPrx
{

	private static final String __getUserAccount_name = "getUserAccount";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::UserAccountMapper"
	};

	public UserAccountMapperPrxHelper()
	{
	}

	public String getUserAccount(String user)
		throws UserAccountNotFoundException
	{
		return getUserAccount(user, null, false);
	}

	public String getUserAccount(String user, Map __ctx)
		throws UserAccountNotFoundException
	{
		return getUserAccount(user, __ctx, true);
	}

	private String getUserAccount(String user, Map __ctx, boolean __explicitCtx)
		throws UserAccountNotFoundException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_UserAccountMapperDel __del;
		__checkTwowayOnly("getUserAccount");
		__delBase = __getDelegate(false);
		__del = (_UserAccountMapperDel)__delBase;
		return __del.getUserAccount(user, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getUserAccount(String user)
	{
		return begin_getUserAccount(user, null, false, null);
	}

	public AsyncResult begin_getUserAccount(String user, Map __ctx)
	{
		return begin_getUserAccount(user, __ctx, true, null);
	}

	public AsyncResult begin_getUserAccount(String user, Callback __cb)
	{
		return begin_getUserAccount(user, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getUserAccount(String user, Map __ctx, Callback __cb)
	{
		return begin_getUserAccount(user, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getUserAccount(String user, Callback_UserAccountMapper_getUserAccount __cb)
	{
		return begin_getUserAccount(user, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getUserAccount(String user, Map __ctx, Callback_UserAccountMapper_getUserAccount __cb)
	{
		return begin_getUserAccount(user, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getUserAccount(String user, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getUserAccount");
		OutgoingAsync __result = new OutgoingAsync(this, "getUserAccount", __cb);
		try
		{
			__result.__prepare("getUserAccount", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(user);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public String end_getUserAccount(AsyncResult __result)
		throws UserAccountNotFoundException
	{
		AsyncResult.__check(__result, this, "getUserAccount");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserAccountNotFoundException __ex)
			{
				throw __ex;
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

	public static UserAccountMapperPrx checkedCast(ObjectPrx __obj)
	{
		UserAccountMapperPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (UserAccountMapperPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					UserAccountMapperPrxHelper __h = new UserAccountMapperPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static UserAccountMapperPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		UserAccountMapperPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (UserAccountMapperPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					UserAccountMapperPrxHelper __h = new UserAccountMapperPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static UserAccountMapperPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		UserAccountMapperPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					UserAccountMapperPrxHelper __h = new UserAccountMapperPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static UserAccountMapperPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		UserAccountMapperPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					UserAccountMapperPrxHelper __h = new UserAccountMapperPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static UserAccountMapperPrx uncheckedCast(ObjectPrx __obj)
	{
		UserAccountMapperPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (UserAccountMapperPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				UserAccountMapperPrxHelper __h = new UserAccountMapperPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static UserAccountMapperPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		UserAccountMapperPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			UserAccountMapperPrxHelper __h = new UserAccountMapperPrxHelper();
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
		return new _UserAccountMapperDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _UserAccountMapperDelD();
	}

	public static void __write(BasicStream __os, UserAccountMapperPrx v)
	{
		__os.writeProxy(v);
	}

	public static UserAccountMapperPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			UserAccountMapperPrxHelper result = new UserAccountMapperPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
