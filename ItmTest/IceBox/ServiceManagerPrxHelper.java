// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceManagerPrxHelper.java

package IceBox;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceBox:
//			_ServiceManagerDel, AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, 
//			ServiceManagerPrx, _ServiceManagerDelM, _ServiceManagerDelD, ServiceObserverPrxHelper, 
//			ServiceObserverPrx, Callback_ServiceManager_addObserver, AMI_ServiceManager_addObserver, Callback_ServiceManager_getSliceChecksums, 
//			Callback_ServiceManager_shutdown, Callback_ServiceManager_startService, AMI_ServiceManager_startService, Callback_ServiceManager_stopService, 
//			AMI_ServiceManager_stopService

public final class ServiceManagerPrxHelper extends ObjectPrxHelperBase
	implements ServiceManagerPrx
{

	private static final String __addObserver_name = "addObserver";
	private static final String __getSliceChecksums_name = "getSliceChecksums";
	private static final String __shutdown_name = "shutdown";
	private static final String __startService_name = "startService";
	private static final String __stopService_name = "stopService";
	public static final String __ids[] = {
		"::Ice::Object", "::IceBox::ServiceManager"
	};

	public ServiceManagerPrxHelper()
	{
	}

	public void addObserver(ServiceObserverPrx observer)
	{
		addObserver(observer, null, false);
	}

	public void addObserver(ServiceObserverPrx observer, Map __ctx)
	{
		addObserver(observer, __ctx, true);
	}

	private void addObserver(ServiceObserverPrx observer, Map __ctx, boolean __explicitCtx)
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
				_ServiceManagerDel __del = (_ServiceManagerDel)__delBase;
				__del.addObserver(observer, __ctx);
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

	public AsyncResult begin_addObserver(ServiceObserverPrx observer)
	{
		return begin_addObserver(observer, null, false, null);
	}

	public AsyncResult begin_addObserver(ServiceObserverPrx observer, Map __ctx)
	{
		return begin_addObserver(observer, __ctx, true, null);
	}

	public AsyncResult begin_addObserver(ServiceObserverPrx observer, Callback __cb)
	{
		return begin_addObserver(observer, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObserver(ServiceObserverPrx observer, Map __ctx, Callback __cb)
	{
		return begin_addObserver(observer, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObserver(ServiceObserverPrx observer, Callback_ServiceManager_addObserver __cb)
	{
		return begin_addObserver(observer, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObserver(ServiceObserverPrx observer, Map __ctx, Callback_ServiceManager_addObserver __cb)
	{
		return begin_addObserver(observer, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_addObserver(ServiceObserverPrx observer, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "addObserver", __cb);
		try
		{
			__result.__prepare("addObserver", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			ServiceObserverPrxHelper.__write(__os, observer);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_addObserver(AsyncResult __result)
	{
		__end(__result, "addObserver");
	}

	public boolean addObserver_async(AMI_ServiceManager_addObserver __cb, ServiceObserverPrx observer)
	{
		AsyncResult __r = begin_addObserver(observer, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean addObserver_async(AMI_ServiceManager_addObserver __cb, ServiceObserverPrx observer, Map __ctx)
	{
		AsyncResult __r = begin_addObserver(observer, __ctx, true, __cb);
		return __r.sentSynchronously();
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
		_ServiceManagerDel __del;
		__checkTwowayOnly("getSliceChecksums");
		__delBase = __getDelegate(false);
		__del = (_ServiceManagerDel)__delBase;
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

	public AsyncResult begin_getSliceChecksums(Callback_ServiceManager_getSliceChecksums __cb)
	{
		return begin_getSliceChecksums(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSliceChecksums(Map __ctx, Callback_ServiceManager_getSliceChecksums __cb)
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

	public void shutdown()
	{
		shutdown(null, false);
	}

	public void shutdown(Map __ctx)
	{
		shutdown(__ctx, true);
	}

	private void shutdown(Map __ctx, boolean __explicitCtx)
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
				_ServiceManagerDel __del = (_ServiceManagerDel)__delBase;
				__del.shutdown(__ctx);
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

	public AsyncResult begin_shutdown()
	{
		return begin_shutdown(null, false, null);
	}

	public AsyncResult begin_shutdown(Map __ctx)
	{
		return begin_shutdown(__ctx, true, null);
	}

	public AsyncResult begin_shutdown(Callback __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback __cb)
	{
		return begin_shutdown(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Callback_ServiceManager_shutdown __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback_ServiceManager_shutdown __cb)
	{
		return begin_shutdown(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_shutdown(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "shutdown", __cb);
		try
		{
			__result.__prepare("shutdown", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_shutdown(AsyncResult __result)
	{
		__end(__result, "shutdown");
	}

	public void startService(String service)
		throws AlreadyStartedException, NoSuchServiceException
	{
		startService(service, null, false);
	}

	public void startService(String service, Map __ctx)
		throws AlreadyStartedException, NoSuchServiceException
	{
		startService(service, __ctx, true);
	}

	private void startService(String service, Map __ctx, boolean __explicitCtx)
		throws AlreadyStartedException, NoSuchServiceException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("startService");
				__delBase = __getDelegate(false);
				_ServiceManagerDel __del = (_ServiceManagerDel)__delBase;
				__del.startService(service, __ctx);
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

	public AsyncResult begin_startService(String service)
	{
		return begin_startService(service, null, false, null);
	}

	public AsyncResult begin_startService(String service, Map __ctx)
	{
		return begin_startService(service, __ctx, true, null);
	}

	public AsyncResult begin_startService(String service, Callback __cb)
	{
		return begin_startService(service, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startService(String service, Map __ctx, Callback __cb)
	{
		return begin_startService(service, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startService(String service, Callback_ServiceManager_startService __cb)
	{
		return begin_startService(service, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startService(String service, Map __ctx, Callback_ServiceManager_startService __cb)
	{
		return begin_startService(service, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_startService(String service, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("startService");
		OutgoingAsync __result = new OutgoingAsync(this, "startService", __cb);
		try
		{
			__result.__prepare("startService", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(service);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_startService(AsyncResult __result)
		throws AlreadyStartedException, NoSuchServiceException
	{
		AsyncResult.__check(__result, this, "startService");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AlreadyStartedException __ex)
			{
				throw __ex;
			}
			catch (NoSuchServiceException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	public boolean startService_async(AMI_ServiceManager_startService __cb, String service)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("startService");
			__r = begin_startService(service, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "startService", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean startService_async(AMI_ServiceManager_startService __cb, String service, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("startService");
			__r = begin_startService(service, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "startService", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void stopService(String service)
		throws AlreadyStoppedException, NoSuchServiceException
	{
		stopService(service, null, false);
	}

	public void stopService(String service, Map __ctx)
		throws AlreadyStoppedException, NoSuchServiceException
	{
		stopService(service, __ctx, true);
	}

	private void stopService(String service, Map __ctx, boolean __explicitCtx)
		throws AlreadyStoppedException, NoSuchServiceException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("stopService");
				__delBase = __getDelegate(false);
				_ServiceManagerDel __del = (_ServiceManagerDel)__delBase;
				__del.stopService(service, __ctx);
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

	public AsyncResult begin_stopService(String service)
	{
		return begin_stopService(service, null, false, null);
	}

	public AsyncResult begin_stopService(String service, Map __ctx)
	{
		return begin_stopService(service, __ctx, true, null);
	}

	public AsyncResult begin_stopService(String service, Callback __cb)
	{
		return begin_stopService(service, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_stopService(String service, Map __ctx, Callback __cb)
	{
		return begin_stopService(service, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_stopService(String service, Callback_ServiceManager_stopService __cb)
	{
		return begin_stopService(service, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_stopService(String service, Map __ctx, Callback_ServiceManager_stopService __cb)
	{
		return begin_stopService(service, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_stopService(String service, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("stopService");
		OutgoingAsync __result = new OutgoingAsync(this, "stopService", __cb);
		try
		{
			__result.__prepare("stopService", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(service);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_stopService(AsyncResult __result)
		throws AlreadyStoppedException, NoSuchServiceException
	{
		AsyncResult.__check(__result, this, "stopService");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AlreadyStoppedException __ex)
			{
				throw __ex;
			}
			catch (NoSuchServiceException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	public boolean stopService_async(AMI_ServiceManager_stopService __cb, String service)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("stopService");
			__r = begin_stopService(service, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "stopService", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean stopService_async(AMI_ServiceManager_stopService __cb, String service, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("stopService");
			__r = begin_stopService(service, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "stopService", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static ServiceManagerPrx checkedCast(ObjectPrx __obj)
	{
		ServiceManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ServiceManagerPrxHelper __h = new ServiceManagerPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServiceManagerPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ServiceManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ServiceManagerPrxHelper __h = new ServiceManagerPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServiceManagerPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ServiceManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ServiceManagerPrxHelper __h = new ServiceManagerPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServiceManagerPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ServiceManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ServiceManagerPrxHelper __h = new ServiceManagerPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServiceManagerPrx uncheckedCast(ObjectPrx __obj)
	{
		ServiceManagerPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceManagerPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ServiceManagerPrxHelper __h = new ServiceManagerPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ServiceManagerPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ServiceManagerPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ServiceManagerPrxHelper __h = new ServiceManagerPrxHelper();
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
		return new _ServiceManagerDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ServiceManagerDelD();
	}

	public static void __write(BasicStream __os, ServiceManagerPrx v)
	{
		__os.writeProxy(v);
	}

	public static ServiceManagerPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ServiceManagerPrxHelper result = new ServiceManagerPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
