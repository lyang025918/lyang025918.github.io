// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_LocatorDel, LocatorPrx, _LocatorDelM, _LocatorDelD, 
//			QueryPrxHelper, RegistryPrxHelper, QueryPrx, Callback_Locator_getLocalQuery, 
//			RegistryPrx, Callback_Locator_getLocalRegistry

public final class LocatorPrxHelper extends ObjectPrxHelperBase
	implements LocatorPrx
{

	private static final String __findAdapterById_name = "findAdapterById";
	private static final String __findObjectById_name = "findObjectById";
	private static final String __getRegistry_name = "getRegistry";
	private static final String __getLocalQuery_name = "getLocalQuery";
	private static final String __getLocalRegistry_name = "getLocalRegistry";
	public static final String __ids[] = {
		"::Ice::Locator", "::Ice::Object", "::IceGrid::Locator"
	};

	public LocatorPrxHelper()
	{
	}

	public ObjectPrx findAdapterById(String id)
		throws AdapterNotFoundException
	{
		return findAdapterById(id, null, false);
	}

	public ObjectPrx findAdapterById(String id, Map __ctx)
		throws AdapterNotFoundException
	{
		return findAdapterById(id, __ctx, true);
	}

	private ObjectPrx findAdapterById(String id, Map __ctx, boolean __explicitCtx)
		throws AdapterNotFoundException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_LocatorDel __del;
		__checkTwowayOnly("findAdapterById");
		__delBase = __getDelegate(false);
		__del = (_LocatorDel)__delBase;
		return __del.findAdapterById(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_findAdapterById(String id)
	{
		return begin_findAdapterById(id, null, false, null);
	}

	public AsyncResult begin_findAdapterById(String id, Map __ctx)
	{
		return begin_findAdapterById(id, __ctx, true, null);
	}

	public AsyncResult begin_findAdapterById(String id, Callback __cb)
	{
		return begin_findAdapterById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAdapterById(String id, Map __ctx, Callback __cb)
	{
		return begin_findAdapterById(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAdapterById(String id, Callback_Locator_findAdapterById __cb)
	{
		return begin_findAdapterById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAdapterById(String id, Map __ctx, Callback_Locator_findAdapterById __cb)
	{
		return begin_findAdapterById(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_findAdapterById(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("findAdapterById");
		OutgoingAsync __result = new OutgoingAsync(this, "findAdapterById", __cb);
		try
		{
			__result.__prepare("findAdapterById", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ObjectPrx end_findAdapterById(AsyncResult __result)
		throws AdapterNotFoundException
	{
		AsyncResult.__check(__result, this, "findAdapterById");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
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
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findAdapterById_async(AMI_Locator_findAdapterById __cb, String id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findAdapterById");
			__r = begin_findAdapterById(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findAdapterById", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean findAdapterById_async(AMI_Locator_findAdapterById __cb, String id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findAdapterById");
			__r = begin_findAdapterById(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findAdapterById", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public ObjectPrx findObjectById(Identity id)
		throws ObjectNotFoundException
	{
		return findObjectById(id, null, false);
	}

	public ObjectPrx findObjectById(Identity id, Map __ctx)
		throws ObjectNotFoundException
	{
		return findObjectById(id, __ctx, true);
	}

	private ObjectPrx findObjectById(Identity id, Map __ctx, boolean __explicitCtx)
		throws ObjectNotFoundException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_LocatorDel __del;
		__checkTwowayOnly("findObjectById");
		__delBase = __getDelegate(false);
		__del = (_LocatorDel)__delBase;
		return __del.findObjectById(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_findObjectById(Identity id)
	{
		return begin_findObjectById(id, null, false, null);
	}

	public AsyncResult begin_findObjectById(Identity id, Map __ctx)
	{
		return begin_findObjectById(id, __ctx, true, null);
	}

	public AsyncResult begin_findObjectById(Identity id, Callback __cb)
	{
		return begin_findObjectById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectById(Identity id, Map __ctx, Callback __cb)
	{
		return begin_findObjectById(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectById(Identity id, Callback_Locator_findObjectById __cb)
	{
		return begin_findObjectById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectById(Identity id, Map __ctx, Callback_Locator_findObjectById __cb)
	{
		return begin_findObjectById(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_findObjectById(Identity id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("findObjectById");
		OutgoingAsync __result = new OutgoingAsync(this, "findObjectById", __cb);
		try
		{
			__result.__prepare("findObjectById", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			id.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_findObjectById(AsyncResult __result)
		throws ObjectNotFoundException
	{
		AsyncResult.__check(__result, this, "findObjectById");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ObjectNotFoundException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findObjectById_async(AMI_Locator_findObjectById __cb, Identity id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findObjectById");
			__r = begin_findObjectById(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findObjectById", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean findObjectById_async(AMI_Locator_findObjectById __cb, Identity id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findObjectById");
			__r = begin_findObjectById(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findObjectById", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public LocatorRegistryPrx getRegistry()
	{
		return getRegistry(null, false);
	}

	public LocatorRegistryPrx getRegistry(Map __ctx)
	{
		return getRegistry(__ctx, true);
	}

	private LocatorRegistryPrx getRegistry(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_LocatorDel __del;
		__checkTwowayOnly("getRegistry");
		__delBase = __getDelegate(false);
		__del = (_LocatorDel)__delBase;
		return __del.getRegistry(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getRegistry()
	{
		return begin_getRegistry(null, false, null);
	}

	public AsyncResult begin_getRegistry(Map __ctx)
	{
		return begin_getRegistry(__ctx, true, null);
	}

	public AsyncResult begin_getRegistry(Callback __cb)
	{
		return begin_getRegistry(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getRegistry(Map __ctx, Callback __cb)
	{
		return begin_getRegistry(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getRegistry(Callback_Locator_getRegistry __cb)
	{
		return begin_getRegistry(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getRegistry(Map __ctx, Callback_Locator_getRegistry __cb)
	{
		return begin_getRegistry(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getRegistry(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getRegistry");
		OutgoingAsync __result = new OutgoingAsync(this, "getRegistry", __cb);
		try
		{
			__result.__prepare("getRegistry", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public LocatorRegistryPrx end_getRegistry(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getRegistry");
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
		LocatorRegistryPrx __ret = LocatorRegistryPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public QueryPrx getLocalQuery()
	{
		return getLocalQuery(null, false);
	}

	public QueryPrx getLocalQuery(Map __ctx)
	{
		return getLocalQuery(__ctx, true);
	}

	private QueryPrx getLocalQuery(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_LocatorDel __del;
		__checkTwowayOnly("getLocalQuery");
		__delBase = __getDelegate(false);
		__del = (_LocatorDel)__delBase;
		return __del.getLocalQuery(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getLocalQuery()
	{
		return begin_getLocalQuery(null, false, null);
	}

	public AsyncResult begin_getLocalQuery(Map __ctx)
	{
		return begin_getLocalQuery(__ctx, true, null);
	}

	public AsyncResult begin_getLocalQuery(Callback __cb)
	{
		return begin_getLocalQuery(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLocalQuery(Map __ctx, Callback __cb)
	{
		return begin_getLocalQuery(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLocalQuery(Callback_Locator_getLocalQuery __cb)
	{
		return begin_getLocalQuery(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLocalQuery(Map __ctx, Callback_Locator_getLocalQuery __cb)
	{
		return begin_getLocalQuery(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getLocalQuery(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getLocalQuery");
		OutgoingAsync __result = new OutgoingAsync(this, "getLocalQuery", __cb);
		try
		{
			__result.__prepare("getLocalQuery", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public QueryPrx end_getLocalQuery(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getLocalQuery");
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
		QueryPrx __ret = QueryPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public RegistryPrx getLocalRegistry()
	{
		return getLocalRegistry(null, false);
	}

	public RegistryPrx getLocalRegistry(Map __ctx)
	{
		return getLocalRegistry(__ctx, true);
	}

	private RegistryPrx getLocalRegistry(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_LocatorDel __del;
		__checkTwowayOnly("getLocalRegistry");
		__delBase = __getDelegate(false);
		__del = (_LocatorDel)__delBase;
		return __del.getLocalRegistry(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getLocalRegistry()
	{
		return begin_getLocalRegistry(null, false, null);
	}

	public AsyncResult begin_getLocalRegistry(Map __ctx)
	{
		return begin_getLocalRegistry(__ctx, true, null);
	}

	public AsyncResult begin_getLocalRegistry(Callback __cb)
	{
		return begin_getLocalRegistry(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLocalRegistry(Map __ctx, Callback __cb)
	{
		return begin_getLocalRegistry(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLocalRegistry(Callback_Locator_getLocalRegistry __cb)
	{
		return begin_getLocalRegistry(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLocalRegistry(Map __ctx, Callback_Locator_getLocalRegistry __cb)
	{
		return begin_getLocalRegistry(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getLocalRegistry(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getLocalRegistry");
		OutgoingAsync __result = new OutgoingAsync(this, "getLocalRegistry", __cb);
		try
		{
			__result.__prepare("getLocalRegistry", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public RegistryPrx end_getLocalRegistry(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getLocalRegistry");
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
		RegistryPrx __ret = RegistryPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public static LocatorPrx checkedCast(ObjectPrx __obj)
	{
		LocatorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LocatorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					LocatorPrxHelper __h = new LocatorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static LocatorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		LocatorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LocatorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					LocatorPrxHelper __h = new LocatorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static LocatorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		LocatorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					LocatorPrxHelper __h = new LocatorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static LocatorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		LocatorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					LocatorPrxHelper __h = new LocatorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static LocatorPrx uncheckedCast(ObjectPrx __obj)
	{
		LocatorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (LocatorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				LocatorPrxHelper __h = new LocatorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static LocatorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		LocatorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			LocatorPrxHelper __h = new LocatorPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[2];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _LocatorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _LocatorDelD();
	}

	public static void __write(BasicStream __os, LocatorPrx v)
	{
		__os.writeProxy(v);
	}

	public static LocatorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			LocatorPrxHelper result = new LocatorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
