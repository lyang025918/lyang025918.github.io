// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PermissionsVerifierPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_PermissionsVerifierDel, PermissionsVerifierPrx, _PermissionsVerifierDelM, _PermissionsVerifierDelD, 
//			Callback_PermissionsVerifier_checkPermissions, AMI_PermissionsVerifier_checkPermissions

public final class PermissionsVerifierPrxHelper extends ObjectPrxHelperBase
	implements PermissionsVerifierPrx
{

	private static final String __checkPermissions_name = "checkPermissions";
	public static final String __ids[] = {
		"::Glacier2::PermissionsVerifier", "::Ice::Object"
	};

	public PermissionsVerifierPrxHelper()
	{
	}

	public boolean checkPermissions(String userId, String password, StringHolder reason)
	{
		return checkPermissions(userId, password, reason, null, false);
	}

	public boolean checkPermissions(String userId, String password, StringHolder reason, Map __ctx)
	{
		return checkPermissions(userId, password, reason, __ctx, true);
	}

	private boolean checkPermissions(String userId, String password, StringHolder reason, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_PermissionsVerifierDel __del;
		__checkTwowayOnly("checkPermissions");
		__delBase = __getDelegate(false);
		__del = (_PermissionsVerifierDel)__delBase;
		return __del.checkPermissions(userId, password, reason, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_checkPermissions(String userId, String password)
	{
		return begin_checkPermissions(userId, password, null, false, null);
	}

	public AsyncResult begin_checkPermissions(String userId, String password, Map __ctx)
	{
		return begin_checkPermissions(userId, password, __ctx, true, null);
	}

	public AsyncResult begin_checkPermissions(String userId, String password, Callback __cb)
	{
		return begin_checkPermissions(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_checkPermissions(String userId, String password, Map __ctx, Callback __cb)
	{
		return begin_checkPermissions(userId, password, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_checkPermissions(String userId, String password, Callback_PermissionsVerifier_checkPermissions __cb)
	{
		return begin_checkPermissions(userId, password, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_checkPermissions(String userId, String password, Map __ctx, Callback_PermissionsVerifier_checkPermissions __cb)
	{
		return begin_checkPermissions(userId, password, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_checkPermissions(String userId, String password, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("checkPermissions");
		OutgoingAsync __result = new OutgoingAsync(this, "checkPermissions", __cb);
		try
		{
			__result.__prepare("checkPermissions", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(userId);
			__os.writeString(password);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public boolean end_checkPermissions(StringHolder reason, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "checkPermissions");
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
		reason.value = __is.readString();
		boolean __ret = __is.readBool();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean checkPermissions_async(AMI_PermissionsVerifier_checkPermissions __cb, String userId, String password)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("checkPermissions");
			__r = begin_checkPermissions(userId, password, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "checkPermissions", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean checkPermissions_async(AMI_PermissionsVerifier_checkPermissions __cb, String userId, String password, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("checkPermissions");
			__r = begin_checkPermissions(userId, password, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "checkPermissions", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static PermissionsVerifierPrx checkedCast(ObjectPrx __obj)
	{
		PermissionsVerifierPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (PermissionsVerifierPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					PermissionsVerifierPrxHelper __h = new PermissionsVerifierPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static PermissionsVerifierPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		PermissionsVerifierPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (PermissionsVerifierPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					PermissionsVerifierPrxHelper __h = new PermissionsVerifierPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static PermissionsVerifierPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		PermissionsVerifierPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					PermissionsVerifierPrxHelper __h = new PermissionsVerifierPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static PermissionsVerifierPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		PermissionsVerifierPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					PermissionsVerifierPrxHelper __h = new PermissionsVerifierPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static PermissionsVerifierPrx uncheckedCast(ObjectPrx __obj)
	{
		PermissionsVerifierPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (PermissionsVerifierPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				PermissionsVerifierPrxHelper __h = new PermissionsVerifierPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static PermissionsVerifierPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		PermissionsVerifierPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			PermissionsVerifierPrxHelper __h = new PermissionsVerifierPrxHelper();
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
		return new _PermissionsVerifierDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _PermissionsVerifierDelD();
	}

	public static void __write(BasicStream __os, PermissionsVerifierPrx v)
	{
		__os.writeProxy(v);
	}

	public static PermissionsVerifierPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			PermissionsVerifierPrxHelper result = new PermissionsVerifierPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
