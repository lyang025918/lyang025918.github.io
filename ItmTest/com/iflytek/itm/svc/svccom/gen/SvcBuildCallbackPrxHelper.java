// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcBuildCallbackPrxHelper.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_SvcBuildCallbackDel, SvcDocument, SvcBuildCallbackPrx, _SvcBuildCallbackDelM, 
//			_SvcBuildCallbackDelD, SvcDocumentHolder, Callback_SvcBuildCallback_event, Callback_SvcBuildCallback_read

public final class SvcBuildCallbackPrxHelper extends ObjectPrxHelperBase
	implements SvcBuildCallbackPrx
{

	private static final String __event_name = "event";
	private static final String __read_name = "read";
	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::SvcBuildCallback"
	};

	public SvcBuildCallbackPrxHelper()
	{
	}

	public void event(String id, int evt, String msg)
	{
		event(id, evt, msg, null, false);
	}

	public void event(String id, int evt, String msg, Map __ctx)
	{
		event(id, evt, msg, __ctx, true);
	}

	private void event(String id, int evt, String msg, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_SvcBuildCallbackDel __del = (_SvcBuildCallbackDel)__delBase;
				__del.event(id, evt, msg, __ctx);
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

	public AsyncResult begin_event(String id, int evt, String msg)
	{
		return begin_event(id, evt, msg, null, false, null);
	}

	public AsyncResult begin_event(String id, int evt, String msg, Map __ctx)
	{
		return begin_event(id, evt, msg, __ctx, true, null);
	}

	public AsyncResult begin_event(String id, int evt, String msg, Callback __cb)
	{
		return begin_event(id, evt, msg, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_event(String id, int evt, String msg, Map __ctx, Callback __cb)
	{
		return begin_event(id, evt, msg, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_event(String id, int evt, String msg, Callback_SvcBuildCallback_event __cb)
	{
		return begin_event(id, evt, msg, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_event(String id, int evt, String msg, Map __ctx, Callback_SvcBuildCallback_event __cb)
	{
		return begin_event(id, evt, msg, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_event(String id, int evt, String msg, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "event", __cb);
		try
		{
			__result.__prepare("event", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeInt(evt);
			__os.writeString(msg);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_event(AsyncResult __result)
	{
		__end(__result, "event");
	}

	public int read(SvcDocumentHolder doc)
	{
		return read(doc, null, false);
	}

	public int read(SvcDocumentHolder doc, Map __ctx)
	{
		return read(doc, __ctx, true);
	}

	private int read(SvcDocumentHolder doc, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __delBase = null;
		_SvcBuildCallbackDel __del;
		__checkTwowayOnly("read");
		__delBase = __getDelegate(false);
		__del = (_SvcBuildCallbackDel)__delBase;
		return __del.read(doc, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_read()
	{
		return begin_read(null, false, null);
	}

	public AsyncResult begin_read(Map __ctx)
	{
		return begin_read(__ctx, true, null);
	}

	public AsyncResult begin_read(Callback __cb)
	{
		return begin_read(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_read(Map __ctx, Callback __cb)
	{
		return begin_read(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_read(Callback_SvcBuildCallback_read __cb)
	{
		return begin_read(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_read(Map __ctx, Callback_SvcBuildCallback_read __cb)
	{
		return begin_read(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_read(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("read");
		OutgoingAsync __result = new OutgoingAsync(this, "read", __cb);
		try
		{
			__result.__prepare("read", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_read(SvcDocumentHolder doc, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "read");
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
		doc.value = new SvcDocument();
		doc.value.__read(__is);
		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public static SvcBuildCallbackPrx checkedCast(ObjectPrx __obj)
	{
		SvcBuildCallbackPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SvcBuildCallbackPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SvcBuildCallbackPrxHelper __h = new SvcBuildCallbackPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SvcBuildCallbackPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SvcBuildCallbackPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SvcBuildCallbackPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SvcBuildCallbackPrxHelper __h = new SvcBuildCallbackPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SvcBuildCallbackPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SvcBuildCallbackPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SvcBuildCallbackPrxHelper __h = new SvcBuildCallbackPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SvcBuildCallbackPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SvcBuildCallbackPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SvcBuildCallbackPrxHelper __h = new SvcBuildCallbackPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SvcBuildCallbackPrx uncheckedCast(ObjectPrx __obj)
	{
		SvcBuildCallbackPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SvcBuildCallbackPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SvcBuildCallbackPrxHelper __h = new SvcBuildCallbackPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SvcBuildCallbackPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SvcBuildCallbackPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SvcBuildCallbackPrxHelper __h = new SvcBuildCallbackPrxHelper();
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
		return new _SvcBuildCallbackDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SvcBuildCallbackDelD();
	}

	public static void __write(BasicStream __os, SvcBuildCallbackPrx v)
	{
		__os.writeProxy(v);
	}

	public static SvcBuildCallbackPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SvcBuildCallbackPrxHelper result = new SvcBuildCallbackPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
