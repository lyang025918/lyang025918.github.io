// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryObserverPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_RegistryObserverDel, RegistryObserverPrx, _RegistryObserverDelM, _RegistryObserverDelD, 
//			RegistryInfoSeqHelper, RegistryInfo, Callback_RegistryObserver_registryDown, Callback_RegistryObserver_registryInit, 
//			AMI_RegistryObserver_registryInit, Callback_RegistryObserver_registryUp

public final class RegistryObserverPrxHelper extends ObjectPrxHelperBase
	implements RegistryObserverPrx
{

	private static final String __registryDown_name = "registryDown";
	private static final String __registryInit_name = "registryInit";
	private static final String __registryUp_name = "registryUp";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::RegistryObserver"
	};

	public RegistryObserverPrxHelper()
	{
	}

	public void registryDown(String name)
	{
		registryDown(name, null, false);
	}

	public void registryDown(String name, Map __ctx)
	{
		registryDown(name, __ctx, true);
	}

	private void registryDown(String name, Map __ctx, boolean __explicitCtx)
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
				_RegistryObserverDel __del = (_RegistryObserverDel)__delBase;
				__del.registryDown(name, __ctx);
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

	public AsyncResult begin_registryDown(String name)
	{
		return begin_registryDown(name, null, false, null);
	}

	public AsyncResult begin_registryDown(String name, Map __ctx)
	{
		return begin_registryDown(name, __ctx, true, null);
	}

	public AsyncResult begin_registryDown(String name, Callback __cb)
	{
		return begin_registryDown(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryDown(String name, Map __ctx, Callback __cb)
	{
		return begin_registryDown(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryDown(String name, Callback_RegistryObserver_registryDown __cb)
	{
		return begin_registryDown(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryDown(String name, Map __ctx, Callback_RegistryObserver_registryDown __cb)
	{
		return begin_registryDown(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_registryDown(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "registryDown", __cb);
		try
		{
			__result.__prepare("registryDown", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
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

	public void end_registryDown(AsyncResult __result)
	{
		__end(__result, "registryDown");
	}

	public void registryInit(RegistryInfo registries[])
	{
		registryInit(registries, null, false);
	}

	public void registryInit(RegistryInfo registries[], Map __ctx)
	{
		registryInit(registries, __ctx, true);
	}

	private void registryInit(RegistryInfo registries[], Map __ctx, boolean __explicitCtx)
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
				_RegistryObserverDel __del = (_RegistryObserverDel)__delBase;
				__del.registryInit(registries, __ctx);
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

	public AsyncResult begin_registryInit(RegistryInfo registries[])
	{
		return begin_registryInit(registries, null, false, null);
	}

	public AsyncResult begin_registryInit(RegistryInfo registries[], Map __ctx)
	{
		return begin_registryInit(registries, __ctx, true, null);
	}

	public AsyncResult begin_registryInit(RegistryInfo registries[], Callback __cb)
	{
		return begin_registryInit(registries, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryInit(RegistryInfo registries[], Map __ctx, Callback __cb)
	{
		return begin_registryInit(registries, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryInit(RegistryInfo registries[], Callback_RegistryObserver_registryInit __cb)
	{
		return begin_registryInit(registries, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryInit(RegistryInfo registries[], Map __ctx, Callback_RegistryObserver_registryInit __cb)
	{
		return begin_registryInit(registries, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_registryInit(RegistryInfo registries[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "registryInit", __cb);
		try
		{
			__result.__prepare("registryInit", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			RegistryInfoSeqHelper.write(__os, registries);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_registryInit(AsyncResult __result)
	{
		__end(__result, "registryInit");
	}

	public boolean registryInit_async(AMI_RegistryObserver_registryInit __cb, RegistryInfo registries[])
	{
		AsyncResult __r = begin_registryInit(registries, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean registryInit_async(AMI_RegistryObserver_registryInit __cb, RegistryInfo registries[], Map __ctx)
	{
		AsyncResult __r = begin_registryInit(registries, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void registryUp(RegistryInfo node)
	{
		registryUp(node, null, false);
	}

	public void registryUp(RegistryInfo node, Map __ctx)
	{
		registryUp(node, __ctx, true);
	}

	private void registryUp(RegistryInfo node, Map __ctx, boolean __explicitCtx)
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
				_RegistryObserverDel __del = (_RegistryObserverDel)__delBase;
				__del.registryUp(node, __ctx);
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

	public AsyncResult begin_registryUp(RegistryInfo node)
	{
		return begin_registryUp(node, null, false, null);
	}

	public AsyncResult begin_registryUp(RegistryInfo node, Map __ctx)
	{
		return begin_registryUp(node, __ctx, true, null);
	}

	public AsyncResult begin_registryUp(RegistryInfo node, Callback __cb)
	{
		return begin_registryUp(node, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryUp(RegistryInfo node, Map __ctx, Callback __cb)
	{
		return begin_registryUp(node, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryUp(RegistryInfo node, Callback_RegistryObserver_registryUp __cb)
	{
		return begin_registryUp(node, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_registryUp(RegistryInfo node, Map __ctx, Callback_RegistryObserver_registryUp __cb)
	{
		return begin_registryUp(node, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_registryUp(RegistryInfo node, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "registryUp", __cb);
		try
		{
			__result.__prepare("registryUp", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			node.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_registryUp(AsyncResult __result)
	{
		__end(__result, "registryUp");
	}

	public static RegistryObserverPrx checkedCast(ObjectPrx __obj)
	{
		RegistryObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RegistryObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					RegistryObserverPrxHelper __h = new RegistryObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RegistryObserverPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		RegistryObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RegistryObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					RegistryObserverPrxHelper __h = new RegistryObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static RegistryObserverPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		RegistryObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					RegistryObserverPrxHelper __h = new RegistryObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RegistryObserverPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		RegistryObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					RegistryObserverPrxHelper __h = new RegistryObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static RegistryObserverPrx uncheckedCast(ObjectPrx __obj)
	{
		RegistryObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (RegistryObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				RegistryObserverPrxHelper __h = new RegistryObserverPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static RegistryObserverPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		RegistryObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			RegistryObserverPrxHelper __h = new RegistryObserverPrxHelper();
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
		return new _RegistryObserverDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _RegistryObserverDelD();
	}

	public static void __write(BasicStream __os, RegistryObserverPrx v)
	{
		__os.writeProxy(v);
	}

	public static RegistryObserverPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			RegistryObserverPrxHelper result = new RegistryObserverPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
