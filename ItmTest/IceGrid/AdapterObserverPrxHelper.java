// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterObserverPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_AdapterObserverDel, AdapterObserverPrx, _AdapterObserverDelM, _AdapterObserverDelD, 
//			AdapterInfo, AdapterInfoSeqHelper, Callback_AdapterObserver_adapterAdded, Callback_AdapterObserver_adapterInit, 
//			AMI_AdapterObserver_adapterInit, Callback_AdapterObserver_adapterRemoved, Callback_AdapterObserver_adapterUpdated

public final class AdapterObserverPrxHelper extends ObjectPrxHelperBase
	implements AdapterObserverPrx
{

	private static final String __adapterAdded_name = "adapterAdded";
	private static final String __adapterInit_name = "adapterInit";
	private static final String __adapterRemoved_name = "adapterRemoved";
	private static final String __adapterUpdated_name = "adapterUpdated";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::AdapterObserver"
	};

	public AdapterObserverPrxHelper()
	{
	}

	public void adapterAdded(AdapterInfo info)
	{
		adapterAdded(info, null, false);
	}

	public void adapterAdded(AdapterInfo info, Map __ctx)
	{
		adapterAdded(info, __ctx, true);
	}

	private void adapterAdded(AdapterInfo info, Map __ctx, boolean __explicitCtx)
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
				_AdapterObserverDel __del = (_AdapterObserverDel)__delBase;
				__del.adapterAdded(info, __ctx);
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

	public AsyncResult begin_adapterAdded(AdapterInfo info)
	{
		return begin_adapterAdded(info, null, false, null);
	}

	public AsyncResult begin_adapterAdded(AdapterInfo info, Map __ctx)
	{
		return begin_adapterAdded(info, __ctx, true, null);
	}

	public AsyncResult begin_adapterAdded(AdapterInfo info, Callback __cb)
	{
		return begin_adapterAdded(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterAdded(AdapterInfo info, Map __ctx, Callback __cb)
	{
		return begin_adapterAdded(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterAdded(AdapterInfo info, Callback_AdapterObserver_adapterAdded __cb)
	{
		return begin_adapterAdded(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterAdded(AdapterInfo info, Map __ctx, Callback_AdapterObserver_adapterAdded __cb)
	{
		return begin_adapterAdded(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_adapterAdded(AdapterInfo info, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "adapterAdded", __cb);
		try
		{
			__result.__prepare("adapterAdded", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_adapterAdded(AsyncResult __result)
	{
		__end(__result, "adapterAdded");
	}

	public void adapterInit(AdapterInfo adpts[])
	{
		adapterInit(adpts, null, false);
	}

	public void adapterInit(AdapterInfo adpts[], Map __ctx)
	{
		adapterInit(adpts, __ctx, true);
	}

	private void adapterInit(AdapterInfo adpts[], Map __ctx, boolean __explicitCtx)
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
				_AdapterObserverDel __del = (_AdapterObserverDel)__delBase;
				__del.adapterInit(adpts, __ctx);
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

	public AsyncResult begin_adapterInit(AdapterInfo adpts[])
	{
		return begin_adapterInit(adpts, null, false, null);
	}

	public AsyncResult begin_adapterInit(AdapterInfo adpts[], Map __ctx)
	{
		return begin_adapterInit(adpts, __ctx, true, null);
	}

	public AsyncResult begin_adapterInit(AdapterInfo adpts[], Callback __cb)
	{
		return begin_adapterInit(adpts, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterInit(AdapterInfo adpts[], Map __ctx, Callback __cb)
	{
		return begin_adapterInit(adpts, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterInit(AdapterInfo adpts[], Callback_AdapterObserver_adapterInit __cb)
	{
		return begin_adapterInit(adpts, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterInit(AdapterInfo adpts[], Map __ctx, Callback_AdapterObserver_adapterInit __cb)
	{
		return begin_adapterInit(adpts, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_adapterInit(AdapterInfo adpts[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "adapterInit", __cb);
		try
		{
			__result.__prepare("adapterInit", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			AdapterInfoSeqHelper.write(__os, adpts);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_adapterInit(AsyncResult __result)
	{
		__end(__result, "adapterInit");
	}

	public boolean adapterInit_async(AMI_AdapterObserver_adapterInit __cb, AdapterInfo adpts[])
	{
		AsyncResult __r = begin_adapterInit(adpts, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean adapterInit_async(AMI_AdapterObserver_adapterInit __cb, AdapterInfo adpts[], Map __ctx)
	{
		AsyncResult __r = begin_adapterInit(adpts, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void adapterRemoved(String id)
	{
		adapterRemoved(id, null, false);
	}

	public void adapterRemoved(String id, Map __ctx)
	{
		adapterRemoved(id, __ctx, true);
	}

	private void adapterRemoved(String id, Map __ctx, boolean __explicitCtx)
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
				_AdapterObserverDel __del = (_AdapterObserverDel)__delBase;
				__del.adapterRemoved(id, __ctx);
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

	public AsyncResult begin_adapterRemoved(String id)
	{
		return begin_adapterRemoved(id, null, false, null);
	}

	public AsyncResult begin_adapterRemoved(String id, Map __ctx)
	{
		return begin_adapterRemoved(id, __ctx, true, null);
	}

	public AsyncResult begin_adapterRemoved(String id, Callback __cb)
	{
		return begin_adapterRemoved(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterRemoved(String id, Map __ctx, Callback __cb)
	{
		return begin_adapterRemoved(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterRemoved(String id, Callback_AdapterObserver_adapterRemoved __cb)
	{
		return begin_adapterRemoved(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterRemoved(String id, Map __ctx, Callback_AdapterObserver_adapterRemoved __cb)
	{
		return begin_adapterRemoved(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_adapterRemoved(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "adapterRemoved", __cb);
		try
		{
			__result.__prepare("adapterRemoved", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_adapterRemoved(AsyncResult __result)
	{
		__end(__result, "adapterRemoved");
	}

	public void adapterUpdated(AdapterInfo info)
	{
		adapterUpdated(info, null, false);
	}

	public void adapterUpdated(AdapterInfo info, Map __ctx)
	{
		adapterUpdated(info, __ctx, true);
	}

	private void adapterUpdated(AdapterInfo info, Map __ctx, boolean __explicitCtx)
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
				_AdapterObserverDel __del = (_AdapterObserverDel)__delBase;
				__del.adapterUpdated(info, __ctx);
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

	public AsyncResult begin_adapterUpdated(AdapterInfo info)
	{
		return begin_adapterUpdated(info, null, false, null);
	}

	public AsyncResult begin_adapterUpdated(AdapterInfo info, Map __ctx)
	{
		return begin_adapterUpdated(info, __ctx, true, null);
	}

	public AsyncResult begin_adapterUpdated(AdapterInfo info, Callback __cb)
	{
		return begin_adapterUpdated(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterUpdated(AdapterInfo info, Map __ctx, Callback __cb)
	{
		return begin_adapterUpdated(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterUpdated(AdapterInfo info, Callback_AdapterObserver_adapterUpdated __cb)
	{
		return begin_adapterUpdated(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_adapterUpdated(AdapterInfo info, Map __ctx, Callback_AdapterObserver_adapterUpdated __cb)
	{
		return begin_adapterUpdated(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_adapterUpdated(AdapterInfo info, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "adapterUpdated", __cb);
		try
		{
			__result.__prepare("adapterUpdated", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_adapterUpdated(AsyncResult __result)
	{
		__end(__result, "adapterUpdated");
	}

	public static AdapterObserverPrx checkedCast(ObjectPrx __obj)
	{
		AdapterObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdapterObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					AdapterObserverPrxHelper __h = new AdapterObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdapterObserverPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		AdapterObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdapterObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					AdapterObserverPrxHelper __h = new AdapterObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdapterObserverPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		AdapterObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					AdapterObserverPrxHelper __h = new AdapterObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdapterObserverPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		AdapterObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					AdapterObserverPrxHelper __h = new AdapterObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdapterObserverPrx uncheckedCast(ObjectPrx __obj)
	{
		AdapterObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdapterObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				AdapterObserverPrxHelper __h = new AdapterObserverPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static AdapterObserverPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		AdapterObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			AdapterObserverPrxHelper __h = new AdapterObserverPrxHelper();
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
		return new _AdapterObserverDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _AdapterObserverDelD();
	}

	public static void __write(BasicStream __os, AdapterObserverPrx v)
	{
		__os.writeProxy(v);
	}

	public static AdapterObserverPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			AdapterObserverPrxHelper result = new AdapterObserverPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
