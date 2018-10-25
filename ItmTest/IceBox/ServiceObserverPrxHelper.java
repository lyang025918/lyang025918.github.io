// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceObserverPrxHelper.java

package IceBox;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceBox:
//			_ServiceObserverDel, ServiceObserverPrx, _ServiceObserverDelM, _ServiceObserverDelD, 
//			Callback_ServiceObserver_servicesStarted, AMI_ServiceObserver_servicesStarted, Callback_ServiceObserver_servicesStopped, AMI_ServiceObserver_servicesStopped

public final class ServiceObserverPrxHelper extends ObjectPrxHelperBase
	implements ServiceObserverPrx
{

	private static final String __servicesStarted_name = "servicesStarted";
	private static final String __servicesStopped_name = "servicesStopped";
	public static final String __ids[] = {
		"::Ice::Object", "::IceBox::ServiceObserver"
	};

	public ServiceObserverPrxHelper()
	{
	}

	public void servicesStarted(String services[])
	{
		servicesStarted(services, null, false);
	}

	public void servicesStarted(String services[], Map __ctx)
	{
		servicesStarted(services, __ctx, true);
	}

	private void servicesStarted(String services[], Map __ctx, boolean __explicitCtx)
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
				_ServiceObserverDel __del = (_ServiceObserverDel)__delBase;
				__del.servicesStarted(services, __ctx);
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

	public AsyncResult begin_servicesStarted(String services[])
	{
		return begin_servicesStarted(services, null, false, null);
	}

	public AsyncResult begin_servicesStarted(String services[], Map __ctx)
	{
		return begin_servicesStarted(services, __ctx, true, null);
	}

	public AsyncResult begin_servicesStarted(String services[], Callback __cb)
	{
		return begin_servicesStarted(services, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_servicesStarted(String services[], Map __ctx, Callback __cb)
	{
		return begin_servicesStarted(services, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_servicesStarted(String services[], Callback_ServiceObserver_servicesStarted __cb)
	{
		return begin_servicesStarted(services, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_servicesStarted(String services[], Map __ctx, Callback_ServiceObserver_servicesStarted __cb)
	{
		return begin_servicesStarted(services, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_servicesStarted(String services[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "servicesStarted", __cb);
		try
		{
			__result.__prepare("servicesStarted", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			StringSeqHelper.write(__os, services);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_servicesStarted(AsyncResult __result)
	{
		__end(__result, "servicesStarted");
	}

	public boolean servicesStarted_async(AMI_ServiceObserver_servicesStarted __cb, String services[])
	{
		AsyncResult __r = begin_servicesStarted(services, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean servicesStarted_async(AMI_ServiceObserver_servicesStarted __cb, String services[], Map __ctx)
	{
		AsyncResult __r = begin_servicesStarted(services, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void servicesStopped(String services[])
	{
		servicesStopped(services, null, false);
	}

	public void servicesStopped(String services[], Map __ctx)
	{
		servicesStopped(services, __ctx, true);
	}

	private void servicesStopped(String services[], Map __ctx, boolean __explicitCtx)
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
				_ServiceObserverDel __del = (_ServiceObserverDel)__delBase;
				__del.servicesStopped(services, __ctx);
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

	public AsyncResult begin_servicesStopped(String services[])
	{
		return begin_servicesStopped(services, null, false, null);
	}

	public AsyncResult begin_servicesStopped(String services[], Map __ctx)
	{
		return begin_servicesStopped(services, __ctx, true, null);
	}

	public AsyncResult begin_servicesStopped(String services[], Callback __cb)
	{
		return begin_servicesStopped(services, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_servicesStopped(String services[], Map __ctx, Callback __cb)
	{
		return begin_servicesStopped(services, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_servicesStopped(String services[], Callback_ServiceObserver_servicesStopped __cb)
	{
		return begin_servicesStopped(services, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_servicesStopped(String services[], Map __ctx, Callback_ServiceObserver_servicesStopped __cb)
	{
		return begin_servicesStopped(services, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_servicesStopped(String services[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "servicesStopped", __cb);
		try
		{
			__result.__prepare("servicesStopped", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			StringSeqHelper.write(__os, services);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_servicesStopped(AsyncResult __result)
	{
		__end(__result, "servicesStopped");
	}

	public boolean servicesStopped_async(AMI_ServiceObserver_servicesStopped __cb, String services[])
	{
		AsyncResult __r = begin_servicesStopped(services, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean servicesStopped_async(AMI_ServiceObserver_servicesStopped __cb, String services[], Map __ctx)
	{
		AsyncResult __r = begin_servicesStopped(services, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public static ServiceObserverPrx checkedCast(ObjectPrx __obj)
	{
		ServiceObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ServiceObserverPrxHelper __h = new ServiceObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServiceObserverPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ServiceObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ServiceObserverPrxHelper __h = new ServiceObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ServiceObserverPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ServiceObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ServiceObserverPrxHelper __h = new ServiceObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServiceObserverPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ServiceObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ServiceObserverPrxHelper __h = new ServiceObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ServiceObserverPrx uncheckedCast(ObjectPrx __obj)
	{
		ServiceObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ServiceObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ServiceObserverPrxHelper __h = new ServiceObserverPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ServiceObserverPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ServiceObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ServiceObserverPrxHelper __h = new ServiceObserverPrxHelper();
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
		return new _ServiceObserverDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ServiceObserverDelD();
	}

	public static void __write(BasicStream __os, ServiceObserverPrx v)
	{
		__os.writeProxy(v);
	}

	public static ServiceObserverPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ServiceObserverPrxHelper result = new ServiceObserverPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
