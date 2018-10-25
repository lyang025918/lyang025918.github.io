// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSLPermissionsVerifierPrxHelper.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_SSLPermissionsVerifierDel, SSLPermissionsVerifierPrx, _SSLPermissionsVerifierDelM, _SSLPermissionsVerifierDelD, 
//			SSLInfo, Callback_SSLPermissionsVerifier_authorize, AMI_SSLPermissionsVerifier_authorize

public final class SSLPermissionsVerifierPrxHelper extends ObjectPrxHelperBase
	implements SSLPermissionsVerifierPrx
{

	private static final String __authorize_name = "authorize";
	public static final String __ids[] = {
		"::Glacier2::SSLPermissionsVerifier", "::Ice::Object"
	};

	public SSLPermissionsVerifierPrxHelper()
	{
	}

	public boolean authorize(SSLInfo info, StringHolder reason)
	{
		return authorize(info, reason, null, false);
	}

	public boolean authorize(SSLInfo info, StringHolder reason, Map __ctx)
	{
		return authorize(info, reason, __ctx, true);
	}

	private boolean authorize(SSLInfo info, StringHolder reason, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SSLPermissionsVerifierDel __del;
		__checkTwowayOnly("authorize");
		__delBase = __getDelegate(false);
		__del = (_SSLPermissionsVerifierDel)__delBase;
		return __del.authorize(info, reason, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_authorize(SSLInfo info)
	{
		return begin_authorize(info, null, false, null);
	}

	public AsyncResult begin_authorize(SSLInfo info, Map __ctx)
	{
		return begin_authorize(info, __ctx, true, null);
	}

	public AsyncResult begin_authorize(SSLInfo info, Callback __cb)
	{
		return begin_authorize(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_authorize(SSLInfo info, Map __ctx, Callback __cb)
	{
		return begin_authorize(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_authorize(SSLInfo info, Callback_SSLPermissionsVerifier_authorize __cb)
	{
		return begin_authorize(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_authorize(SSLInfo info, Map __ctx, Callback_SSLPermissionsVerifier_authorize __cb)
	{
		return begin_authorize(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_authorize(SSLInfo info, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("authorize");
		OutgoingAsync __result = new OutgoingAsync(this, "authorize", __cb);
		try
		{
			__result.__prepare("authorize", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			info.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public boolean end_authorize(StringHolder reason, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "authorize");
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

	public boolean authorize_async(AMI_SSLPermissionsVerifier_authorize __cb, SSLInfo info)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("authorize");
			__r = begin_authorize(info, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "authorize", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean authorize_async(AMI_SSLPermissionsVerifier_authorize __cb, SSLInfo info, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("authorize");
			__r = begin_authorize(info, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "authorize", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static SSLPermissionsVerifierPrx checkedCast(ObjectPrx __obj)
	{
		SSLPermissionsVerifierPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SSLPermissionsVerifierPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SSLPermissionsVerifierPrxHelper __h = new SSLPermissionsVerifierPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SSLPermissionsVerifierPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SSLPermissionsVerifierPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SSLPermissionsVerifierPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SSLPermissionsVerifierPrxHelper __h = new SSLPermissionsVerifierPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SSLPermissionsVerifierPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SSLPermissionsVerifierPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SSLPermissionsVerifierPrxHelper __h = new SSLPermissionsVerifierPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SSLPermissionsVerifierPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SSLPermissionsVerifierPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SSLPermissionsVerifierPrxHelper __h = new SSLPermissionsVerifierPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SSLPermissionsVerifierPrx uncheckedCast(ObjectPrx __obj)
	{
		SSLPermissionsVerifierPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SSLPermissionsVerifierPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SSLPermissionsVerifierPrxHelper __h = new SSLPermissionsVerifierPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SSLPermissionsVerifierPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SSLPermissionsVerifierPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SSLPermissionsVerifierPrxHelper __h = new SSLPermissionsVerifierPrxHelper();
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
		return new _SSLPermissionsVerifierDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SSLPermissionsVerifierDelD();
	}

	public static void __write(BasicStream __os, SSLPermissionsVerifierPrx v)
	{
		__os.writeProxy(v);
	}

	public static SSLPermissionsVerifierPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SSLPermissionsVerifierPrxHelper result = new SSLPermissionsVerifierPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
