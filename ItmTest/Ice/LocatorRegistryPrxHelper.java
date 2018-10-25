// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorRegistryPrxHelper.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.LocalExceptionWrapper;
import IceInternal.OutgoingAsync;
import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrxHelperBase, _LocatorRegistryDel, LocalException, AdapterAlreadyActiveException, 
//			AdapterNotFoundException, UserException, UnknownUserException, TwowayOnlyException, 
//			InvalidReplicaGroupIdException, ServerNotFoundException, LocatorRegistryPrx, FacetNotExistException, 
//			_LocatorRegistryDelM, _LocatorRegistryDelD, OperationMode, AsyncResult, 
//			ProcessPrxHelper, ObjectPrx, Callback, Callback_LocatorRegistry_setAdapterDirectProxy, 
//			AMI_LocatorRegistry_setAdapterDirectProxy, Callback_LocatorRegistry_setReplicatedAdapterDirectProxy, AMI_LocatorRegistry_setReplicatedAdapterDirectProxy, ProcessPrx, 
//			Callback_LocatorRegistry_setServerProcessProxy, _ObjectDelM, _ObjectDelD

public final class LocatorRegistryPrxHelper extends ObjectPrxHelperBase
	implements LocatorRegistryPrx
{

	private static final String __setAdapterDirectProxy_name = "setAdapterDirectProxy";
	private static final String __setReplicatedAdapterDirectProxy_name = "setReplicatedAdapterDirectProxy";
	private static final String __setServerProcessProxy_name = "setServerProcessProxy";
	public static final String __ids[] = {
		"::Ice::LocatorRegistry", "::Ice::Object"
	};

	public LocatorRegistryPrxHelper()
	{
	}

	public void setAdapterDirectProxy(String id, ObjectPrx proxy)
		throws AdapterAlreadyActiveException, AdapterNotFoundException
	{
		setAdapterDirectProxy(id, proxy, null, false);
	}

	public void setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx)
		throws AdapterAlreadyActiveException, AdapterNotFoundException
	{
		setAdapterDirectProxy(id, proxy, __ctx, true);
	}

	private void setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx, boolean __explicitCtx)
		throws AdapterAlreadyActiveException, AdapterNotFoundException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("setAdapterDirectProxy");
				__delBase = __getDelegate(false);
				_LocatorRegistryDel __del = (_LocatorRegistryDel)__delBase;
				__del.setAdapterDirectProxy(id, proxy, __ctx);
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

	public AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy)
	{
		return begin_setAdapterDirectProxy(id, proxy, null, false, null);
	}

	public AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx)
	{
		return begin_setAdapterDirectProxy(id, proxy, __ctx, true, null);
	}

	public AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy, Callback __cb)
	{
		return begin_setAdapterDirectProxy(id, proxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx, Callback __cb)
	{
		return begin_setAdapterDirectProxy(id, proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy, Callback_LocatorRegistry_setAdapterDirectProxy __cb)
	{
		return begin_setAdapterDirectProxy(id, proxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx, Callback_LocatorRegistry_setAdapterDirectProxy __cb)
	{
		return begin_setAdapterDirectProxy(id, proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("setAdapterDirectProxy");
		OutgoingAsync __result = new OutgoingAsync(this, "setAdapterDirectProxy", __cb);
		try
		{
			__result.__prepare("setAdapterDirectProxy", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeProxy(proxy);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_setAdapterDirectProxy(AsyncResult __result)
		throws AdapterAlreadyActiveException, AdapterNotFoundException
	{
		AsyncResult.__check(__result, this, "setAdapterDirectProxy");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AdapterAlreadyActiveException __ex)
			{
				throw __ex;
			}
			catch (AdapterNotFoundException __ex)
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

	public boolean setAdapterDirectProxy_async(AMI_LocatorRegistry_setAdapterDirectProxy __cb, String id, ObjectPrx proxy)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("setAdapterDirectProxy");
			__r = begin_setAdapterDirectProxy(id, proxy, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "setAdapterDirectProxy", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean setAdapterDirectProxy_async(AMI_LocatorRegistry_setAdapterDirectProxy __cb, String id, ObjectPrx proxy, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("setAdapterDirectProxy");
			__r = begin_setAdapterDirectProxy(id, proxy, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "setAdapterDirectProxy", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, null, false);
	}

	public void setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, __ctx, true);
	}

	private void setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx, boolean __explicitCtx)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("setReplicatedAdapterDirectProxy");
				__delBase = __getDelegate(false);
				_LocatorRegistryDel __del = (_LocatorRegistryDel)__delBase;
				__del.setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, __ctx);
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

	public AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p)
	{
		return begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, null, false, null);
	}

	public AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx)
	{
		return begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, __ctx, true, null);
	}

	public AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Callback __cb)
	{
		return begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx, Callback __cb)
	{
		return begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Callback_LocatorRegistry_setReplicatedAdapterDirectProxy __cb)
	{
		return begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx, Callback_LocatorRegistry_setReplicatedAdapterDirectProxy __cb)
	{
		return begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("setReplicatedAdapterDirectProxy");
		OutgoingAsync __result = new OutgoingAsync(this, "setReplicatedAdapterDirectProxy", __cb);
		try
		{
			__result.__prepare("setReplicatedAdapterDirectProxy", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(adapterId);
			__os.writeString(replicaGroupId);
			__os.writeProxy(p);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_setReplicatedAdapterDirectProxy(AsyncResult __result)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		AsyncResult.__check(__result, this, "setReplicatedAdapterDirectProxy");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AdapterAlreadyActiveException __ex)
			{
				throw __ex;
			}
			catch (AdapterNotFoundException __ex)
			{
				throw __ex;
			}
			catch (InvalidReplicaGroupIdException __ex)
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

	public boolean setReplicatedAdapterDirectProxy_async(AMI_LocatorRegistry_setReplicatedAdapterDirectProxy __cb, String adapterId, String replicaGroupId, ObjectPrx p)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("setReplicatedAdapterDirectProxy");
			__r = begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "setReplicatedAdapterDirectProxy", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean setReplicatedAdapterDirectProxy_async(AMI_LocatorRegistry_setReplicatedAdapterDirectProxy __cb, String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("setReplicatedAdapterDirectProxy");
			__r = begin_setReplicatedAdapterDirectProxy(adapterId, replicaGroupId, p, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "setReplicatedAdapterDirectProxy", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void setServerProcessProxy(String id, ProcessPrx proxy)
		throws ServerNotFoundException
	{
		setServerProcessProxy(id, proxy, null, false);
	}

	public void setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx)
		throws ServerNotFoundException
	{
		setServerProcessProxy(id, proxy, __ctx, true);
	}

	private void setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx, boolean __explicitCtx)
		throws ServerNotFoundException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("setServerProcessProxy");
				__delBase = __getDelegate(false);
				_LocatorRegistryDel __del = (_LocatorRegistryDel)__delBase;
				__del.setServerProcessProxy(id, proxy, __ctx);
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

	public AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy)
	{
		return begin_setServerProcessProxy(id, proxy, null, false, null);
	}

	public AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx)
	{
		return begin_setServerProcessProxy(id, proxy, __ctx, true, null);
	}

	public AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy, Callback __cb)
	{
		return begin_setServerProcessProxy(id, proxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx, Callback __cb)
	{
		return begin_setServerProcessProxy(id, proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy, Callback_LocatorRegistry_setServerProcessProxy __cb)
	{
		return begin_setServerProcessProxy(id, proxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx, Callback_LocatorRegistry_setServerProcessProxy __cb)
	{
		return begin_setServerProcessProxy(id, proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("setServerProcessProxy");
		OutgoingAsync __result = new OutgoingAsync(this, "setServerProcessProxy", __cb);
		try
		{
			__result.__prepare("setServerProcessProxy", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			ProcessPrxHelper.__write(__os, proxy);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_setServerProcessProxy(AsyncResult __result)
		throws ServerNotFoundException
	{
		AsyncResult.__check(__result, this, "setServerProcessProxy");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ServerNotFoundException __ex)
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

	public static LocatorRegistryPrx checkedCast(ObjectPrx __obj)
	{
		LocatorRegistryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LocatorRegistryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					LocatorRegistryPrxHelper __h = new LocatorRegistryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static LocatorRegistryPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		LocatorRegistryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LocatorRegistryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					LocatorRegistryPrxHelper __h = new LocatorRegistryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static LocatorRegistryPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		LocatorRegistryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					LocatorRegistryPrxHelper __h = new LocatorRegistryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static LocatorRegistryPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		LocatorRegistryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					LocatorRegistryPrxHelper __h = new LocatorRegistryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static LocatorRegistryPrx uncheckedCast(ObjectPrx __obj)
	{
		LocatorRegistryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LocatorRegistryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				LocatorRegistryPrxHelper __h = new LocatorRegistryPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static LocatorRegistryPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		LocatorRegistryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			LocatorRegistryPrxHelper __h = new LocatorRegistryPrxHelper();
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
		return new _LocatorRegistryDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _LocatorRegistryDelD();
	}

	public static void __write(BasicStream __os, LocatorRegistryPrx v)
	{
		__os.writeProxy(v);
	}

	public static LocatorRegistryPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			LocatorRegistryPrxHelper result = new LocatorRegistryPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
