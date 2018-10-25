// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationObserverPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.List;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_ApplicationObserverDel, ApplicationObserverPrx, _ApplicationObserverDelM, _ApplicationObserverDelD, 
//			ApplicationInfo, ApplicationInfoSeqHelper, ApplicationUpdateInfo, Callback_ApplicationObserver_applicationAdded, 
//			Callback_ApplicationObserver_applicationInit, AMI_ApplicationObserver_applicationInit, Callback_ApplicationObserver_applicationRemoved, Callback_ApplicationObserver_applicationUpdated

public final class ApplicationObserverPrxHelper extends ObjectPrxHelperBase
	implements ApplicationObserverPrx
{

	private static final String __applicationAdded_name = "applicationAdded";
	private static final String __applicationInit_name = "applicationInit";
	private static final String __applicationRemoved_name = "applicationRemoved";
	private static final String __applicationUpdated_name = "applicationUpdated";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::ApplicationObserver"
	};

	public ApplicationObserverPrxHelper()
	{
	}

	public void applicationAdded(int serial, ApplicationInfo desc)
	{
		applicationAdded(serial, desc, null, false);
	}

	public void applicationAdded(int serial, ApplicationInfo desc, Map __ctx)
	{
		applicationAdded(serial, desc, __ctx, true);
	}

	private void applicationAdded(int serial, ApplicationInfo desc, Map __ctx, boolean __explicitCtx)
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
				_ApplicationObserverDel __del = (_ApplicationObserverDel)__delBase;
				__del.applicationAdded(serial, desc, __ctx);
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

	public AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc)
	{
		return begin_applicationAdded(serial, desc, null, false, null);
	}

	public AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc, Map __ctx)
	{
		return begin_applicationAdded(serial, desc, __ctx, true, null);
	}

	public AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc, Callback __cb)
	{
		return begin_applicationAdded(serial, desc, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc, Map __ctx, Callback __cb)
	{
		return begin_applicationAdded(serial, desc, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc, Callback_ApplicationObserver_applicationAdded __cb)
	{
		return begin_applicationAdded(serial, desc, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc, Map __ctx, Callback_ApplicationObserver_applicationAdded __cb)
	{
		return begin_applicationAdded(serial, desc, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_applicationAdded(int serial, ApplicationInfo desc, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "applicationAdded", __cb);
		try
		{
			__result.__prepare("applicationAdded", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeInt(serial);
			desc.__write(__os);
			__os.writePendingObjects();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_applicationAdded(AsyncResult __result)
	{
		__end(__result, "applicationAdded");
	}

	public void applicationInit(int serial, List applications)
	{
		applicationInit(serial, applications, null, false);
	}

	public void applicationInit(int serial, List applications, Map __ctx)
	{
		applicationInit(serial, applications, __ctx, true);
	}

	private void applicationInit(int serial, List applications, Map __ctx, boolean __explicitCtx)
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
				_ApplicationObserverDel __del = (_ApplicationObserverDel)__delBase;
				__del.applicationInit(serial, applications, __ctx);
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

	public AsyncResult begin_applicationInit(int serial, List applications)
	{
		return begin_applicationInit(serial, applications, null, false, null);
	}

	public AsyncResult begin_applicationInit(int serial, List applications, Map __ctx)
	{
		return begin_applicationInit(serial, applications, __ctx, true, null);
	}

	public AsyncResult begin_applicationInit(int serial, List applications, Callback __cb)
	{
		return begin_applicationInit(serial, applications, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationInit(int serial, List applications, Map __ctx, Callback __cb)
	{
		return begin_applicationInit(serial, applications, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationInit(int serial, List applications, Callback_ApplicationObserver_applicationInit __cb)
	{
		return begin_applicationInit(serial, applications, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationInit(int serial, List applications, Map __ctx, Callback_ApplicationObserver_applicationInit __cb)
	{
		return begin_applicationInit(serial, applications, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_applicationInit(int serial, List applications, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "applicationInit", __cb);
		try
		{
			__result.__prepare("applicationInit", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeInt(serial);
			ApplicationInfoSeqHelper.write(__os, applications);
			__os.writePendingObjects();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_applicationInit(AsyncResult __result)
	{
		__end(__result, "applicationInit");
	}

	public boolean applicationInit_async(AMI_ApplicationObserver_applicationInit __cb, int serial, List applications)
	{
		AsyncResult __r = begin_applicationInit(serial, applications, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean applicationInit_async(AMI_ApplicationObserver_applicationInit __cb, int serial, List applications, Map __ctx)
	{
		AsyncResult __r = begin_applicationInit(serial, applications, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void applicationRemoved(int serial, String name)
	{
		applicationRemoved(serial, name, null, false);
	}

	public void applicationRemoved(int serial, String name, Map __ctx)
	{
		applicationRemoved(serial, name, __ctx, true);
	}

	private void applicationRemoved(int serial, String name, Map __ctx, boolean __explicitCtx)
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
				_ApplicationObserverDel __del = (_ApplicationObserverDel)__delBase;
				__del.applicationRemoved(serial, name, __ctx);
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

	public AsyncResult begin_applicationRemoved(int serial, String name)
	{
		return begin_applicationRemoved(serial, name, null, false, null);
	}

	public AsyncResult begin_applicationRemoved(int serial, String name, Map __ctx)
	{
		return begin_applicationRemoved(serial, name, __ctx, true, null);
	}

	public AsyncResult begin_applicationRemoved(int serial, String name, Callback __cb)
	{
		return begin_applicationRemoved(serial, name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationRemoved(int serial, String name, Map __ctx, Callback __cb)
	{
		return begin_applicationRemoved(serial, name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationRemoved(int serial, String name, Callback_ApplicationObserver_applicationRemoved __cb)
	{
		return begin_applicationRemoved(serial, name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationRemoved(int serial, String name, Map __ctx, Callback_ApplicationObserver_applicationRemoved __cb)
	{
		return begin_applicationRemoved(serial, name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_applicationRemoved(int serial, String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "applicationRemoved", __cb);
		try
		{
			__result.__prepare("applicationRemoved", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeInt(serial);
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

	public void end_applicationRemoved(AsyncResult __result)
	{
		__end(__result, "applicationRemoved");
	}

	public void applicationUpdated(int serial, ApplicationUpdateInfo desc)
	{
		applicationUpdated(serial, desc, null, false);
	}

	public void applicationUpdated(int serial, ApplicationUpdateInfo desc, Map __ctx)
	{
		applicationUpdated(serial, desc, __ctx, true);
	}

	private void applicationUpdated(int serial, ApplicationUpdateInfo desc, Map __ctx, boolean __explicitCtx)
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
				_ApplicationObserverDel __del = (_ApplicationObserverDel)__delBase;
				__del.applicationUpdated(serial, desc, __ctx);
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

	public AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc)
	{
		return begin_applicationUpdated(serial, desc, null, false, null);
	}

	public AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc, Map __ctx)
	{
		return begin_applicationUpdated(serial, desc, __ctx, true, null);
	}

	public AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc, Callback __cb)
	{
		return begin_applicationUpdated(serial, desc, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc, Map __ctx, Callback __cb)
	{
		return begin_applicationUpdated(serial, desc, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc, Callback_ApplicationObserver_applicationUpdated __cb)
	{
		return begin_applicationUpdated(serial, desc, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc, Map __ctx, Callback_ApplicationObserver_applicationUpdated __cb)
	{
		return begin_applicationUpdated(serial, desc, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_applicationUpdated(int serial, ApplicationUpdateInfo desc, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "applicationUpdated", __cb);
		try
		{
			__result.__prepare("applicationUpdated", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeInt(serial);
			desc.__write(__os);
			__os.writePendingObjects();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_applicationUpdated(AsyncResult __result)
	{
		__end(__result, "applicationUpdated");
	}

	public static ApplicationObserverPrx checkedCast(ObjectPrx __obj)
	{
		ApplicationObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ApplicationObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ApplicationObserverPrxHelper __h = new ApplicationObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ApplicationObserverPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ApplicationObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ApplicationObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ApplicationObserverPrxHelper __h = new ApplicationObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ApplicationObserverPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ApplicationObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ApplicationObserverPrxHelper __h = new ApplicationObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ApplicationObserverPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ApplicationObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ApplicationObserverPrxHelper __h = new ApplicationObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ApplicationObserverPrx uncheckedCast(ObjectPrx __obj)
	{
		ApplicationObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ApplicationObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ApplicationObserverPrxHelper __h = new ApplicationObserverPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ApplicationObserverPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ApplicationObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ApplicationObserverPrxHelper __h = new ApplicationObserverPrxHelper();
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
		return new _ApplicationObserverDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ApplicationObserverDelD();
	}

	public static void __write(BasicStream __os, ApplicationObserverPrx v)
	{
		__os.writeProxy(v);
	}

	public static ApplicationObserverPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ApplicationObserverPrxHelper result = new ApplicationObserverPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
