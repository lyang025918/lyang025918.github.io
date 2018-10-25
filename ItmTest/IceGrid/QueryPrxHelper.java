// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_QueryDel, QueryPrx, _QueryDelM, _QueryDelD, 
//			LoadSample, Callback_Query_findAllObjectsByType, AMI_Query_findAllObjectsByType, Callback_Query_findAllReplicas, 
//			AMI_Query_findAllReplicas, Callback_Query_findObjectById, AMI_Query_findObjectById, Callback_Query_findObjectByType, 
//			AMI_Query_findObjectByType, Callback_Query_findObjectByTypeOnLeastLoadedNode, AMI_Query_findObjectByTypeOnLeastLoadedNode

public final class QueryPrxHelper extends ObjectPrxHelperBase
	implements QueryPrx
{

	private static final String __findAllObjectsByType_name = "findAllObjectsByType";
	private static final String __findAllReplicas_name = "findAllReplicas";
	private static final String __findObjectById_name = "findObjectById";
	private static final String __findObjectByType_name = "findObjectByType";
	private static final String __findObjectByTypeOnLeastLoadedNode_name = "findObjectByTypeOnLeastLoadedNode";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::Query"
	};

	public QueryPrxHelper()
	{
	}

	public ObjectPrx[] findAllObjectsByType(String type)
	{
		return findAllObjectsByType(type, null, false);
	}

	public ObjectPrx[] findAllObjectsByType(String type, Map __ctx)
	{
		return findAllObjectsByType(type, __ctx, true);
	}

	private ObjectPrx[] findAllObjectsByType(String type, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_QueryDel __del;
		__checkTwowayOnly("findAllObjectsByType");
		__delBase = __getDelegate(false);
		__del = (_QueryDel)__delBase;
		return __del.findAllObjectsByType(type, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_findAllObjectsByType(String type)
	{
		return begin_findAllObjectsByType(type, null, false, null);
	}

	public AsyncResult begin_findAllObjectsByType(String type, Map __ctx)
	{
		return begin_findAllObjectsByType(type, __ctx, true, null);
	}

	public AsyncResult begin_findAllObjectsByType(String type, Callback __cb)
	{
		return begin_findAllObjectsByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAllObjectsByType(String type, Map __ctx, Callback __cb)
	{
		return begin_findAllObjectsByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAllObjectsByType(String type, Callback_Query_findAllObjectsByType __cb)
	{
		return begin_findAllObjectsByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAllObjectsByType(String type, Map __ctx, Callback_Query_findAllObjectsByType __cb)
	{
		return begin_findAllObjectsByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_findAllObjectsByType(String type, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("findAllObjectsByType");
		OutgoingAsync __result = new OutgoingAsync(this, "findAllObjectsByType", __cb);
		try
		{
			__result.__prepare("findAllObjectsByType", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(type);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx[] end_findAllObjectsByType(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "findAllObjectsByType");
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
		ObjectPrx __ret[] = ObjectProxySeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findAllObjectsByType_async(AMI_Query_findAllObjectsByType __cb, String type)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findAllObjectsByType");
			__r = begin_findAllObjectsByType(type, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findAllObjectsByType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean findAllObjectsByType_async(AMI_Query_findAllObjectsByType __cb, String type, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findAllObjectsByType");
			__r = begin_findAllObjectsByType(type, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findAllObjectsByType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public ObjectPrx[] findAllReplicas(ObjectPrx proxy)
	{
		return findAllReplicas(proxy, null, false);
	}

	public ObjectPrx[] findAllReplicas(ObjectPrx proxy, Map __ctx)
	{
		return findAllReplicas(proxy, __ctx, true);
	}

	private ObjectPrx[] findAllReplicas(ObjectPrx proxy, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_QueryDel __del;
		__checkTwowayOnly("findAllReplicas");
		__delBase = __getDelegate(false);
		__del = (_QueryDel)__delBase;
		return __del.findAllReplicas(proxy, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_findAllReplicas(ObjectPrx proxy)
	{
		return begin_findAllReplicas(proxy, null, false, null);
	}

	public AsyncResult begin_findAllReplicas(ObjectPrx proxy, Map __ctx)
	{
		return begin_findAllReplicas(proxy, __ctx, true, null);
	}

	public AsyncResult begin_findAllReplicas(ObjectPrx proxy, Callback __cb)
	{
		return begin_findAllReplicas(proxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAllReplicas(ObjectPrx proxy, Map __ctx, Callback __cb)
	{
		return begin_findAllReplicas(proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAllReplicas(ObjectPrx proxy, Callback_Query_findAllReplicas __cb)
	{
		return begin_findAllReplicas(proxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findAllReplicas(ObjectPrx proxy, Map __ctx, Callback_Query_findAllReplicas __cb)
	{
		return begin_findAllReplicas(proxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_findAllReplicas(ObjectPrx proxy, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("findAllReplicas");
		OutgoingAsync __result = new OutgoingAsync(this, "findAllReplicas", __cb);
		try
		{
			__result.__prepare("findAllReplicas", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
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

	public ObjectPrx[] end_findAllReplicas(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "findAllReplicas");
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
		ObjectPrx __ret[] = ObjectProxySeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findAllReplicas_async(AMI_Query_findAllReplicas __cb, ObjectPrx proxy)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findAllReplicas");
			__r = begin_findAllReplicas(proxy, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findAllReplicas", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean findAllReplicas_async(AMI_Query_findAllReplicas __cb, ObjectPrx proxy, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findAllReplicas");
			__r = begin_findAllReplicas(proxy, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findAllReplicas", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public ObjectPrx findObjectById(Identity id)
	{
		return findObjectById(id, null, false);
	}

	public ObjectPrx findObjectById(Identity id, Map __ctx)
	{
		return findObjectById(id, __ctx, true);
	}

	private ObjectPrx findObjectById(Identity id, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_QueryDel __del;
		__checkTwowayOnly("findObjectById");
		__delBase = __getDelegate(false);
		__del = (_QueryDel)__delBase;
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

	public AsyncResult begin_findObjectById(Identity id, Callback_Query_findObjectById __cb)
	{
		return begin_findObjectById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectById(Identity id, Map __ctx, Callback_Query_findObjectById __cb)
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
	{
		AsyncResult.__check(__result, this, "findObjectById");
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
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findObjectById_async(AMI_Query_findObjectById __cb, Identity id)
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

	public boolean findObjectById_async(AMI_Query_findObjectById __cb, Identity id, Map __ctx)
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

	public ObjectPrx findObjectByType(String type)
	{
		return findObjectByType(type, null, false);
	}

	public ObjectPrx findObjectByType(String type, Map __ctx)
	{
		return findObjectByType(type, __ctx, true);
	}

	private ObjectPrx findObjectByType(String type, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_QueryDel __del;
		__checkTwowayOnly("findObjectByType");
		__delBase = __getDelegate(false);
		__del = (_QueryDel)__delBase;
		return __del.findObjectByType(type, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_findObjectByType(String type)
	{
		return begin_findObjectByType(type, null, false, null);
	}

	public AsyncResult begin_findObjectByType(String type, Map __ctx)
	{
		return begin_findObjectByType(type, __ctx, true, null);
	}

	public AsyncResult begin_findObjectByType(String type, Callback __cb)
	{
		return begin_findObjectByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectByType(String type, Map __ctx, Callback __cb)
	{
		return begin_findObjectByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectByType(String type, Callback_Query_findObjectByType __cb)
	{
		return begin_findObjectByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectByType(String type, Map __ctx, Callback_Query_findObjectByType __cb)
	{
		return begin_findObjectByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_findObjectByType(String type, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("findObjectByType");
		OutgoingAsync __result = new OutgoingAsync(this, "findObjectByType", __cb);
		try
		{
			__result.__prepare("findObjectByType", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(type);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_findObjectByType(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "findObjectByType");
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
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findObjectByType_async(AMI_Query_findObjectByType __cb, String type)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findObjectByType");
			__r = begin_findObjectByType(type, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findObjectByType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean findObjectByType_async(AMI_Query_findObjectByType __cb, String type, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findObjectByType");
			__r = begin_findObjectByType(type, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findObjectByType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public ObjectPrx findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample)
	{
		return findObjectByTypeOnLeastLoadedNode(type, sample, null, false);
	}

	public ObjectPrx findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Map __ctx)
	{
		return findObjectByTypeOnLeastLoadedNode(type, sample, __ctx, true);
	}

	private ObjectPrx findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_QueryDel __del;
		__checkTwowayOnly("findObjectByTypeOnLeastLoadedNode");
		__delBase = __getDelegate(false);
		__del = (_QueryDel)__delBase;
		return __del.findObjectByTypeOnLeastLoadedNode(type, sample, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample)
	{
		return begin_findObjectByTypeOnLeastLoadedNode(type, sample, null, false, null);
	}

	public AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Map __ctx)
	{
		return begin_findObjectByTypeOnLeastLoadedNode(type, sample, __ctx, true, null);
	}

	public AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Callback __cb)
	{
		return begin_findObjectByTypeOnLeastLoadedNode(type, sample, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Map __ctx, Callback __cb)
	{
		return begin_findObjectByTypeOnLeastLoadedNode(type, sample, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Callback_Query_findObjectByTypeOnLeastLoadedNode __cb)
	{
		return begin_findObjectByTypeOnLeastLoadedNode(type, sample, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Map __ctx, Callback_Query_findObjectByTypeOnLeastLoadedNode __cb)
	{
		return begin_findObjectByTypeOnLeastLoadedNode(type, sample, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("findObjectByTypeOnLeastLoadedNode");
		OutgoingAsync __result = new OutgoingAsync(this, "findObjectByTypeOnLeastLoadedNode", __cb);
		try
		{
			__result.__prepare("findObjectByTypeOnLeastLoadedNode", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(type);
			sample.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_findObjectByTypeOnLeastLoadedNode(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "findObjectByTypeOnLeastLoadedNode");
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
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean findObjectByTypeOnLeastLoadedNode_async(AMI_Query_findObjectByTypeOnLeastLoadedNode __cb, String type, LoadSample sample)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findObjectByTypeOnLeastLoadedNode");
			__r = begin_findObjectByTypeOnLeastLoadedNode(type, sample, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findObjectByTypeOnLeastLoadedNode", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean findObjectByTypeOnLeastLoadedNode_async(AMI_Query_findObjectByTypeOnLeastLoadedNode __cb, String type, LoadSample sample, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("findObjectByTypeOnLeastLoadedNode");
			__r = begin_findObjectByTypeOnLeastLoadedNode(type, sample, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "findObjectByTypeOnLeastLoadedNode", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static QueryPrx checkedCast(ObjectPrx __obj)
	{
		QueryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (QueryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					QueryPrxHelper __h = new QueryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static QueryPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		QueryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (QueryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					QueryPrxHelper __h = new QueryPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static QueryPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		QueryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					QueryPrxHelper __h = new QueryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static QueryPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		QueryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					QueryPrxHelper __h = new QueryPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static QueryPrx uncheckedCast(ObjectPrx __obj)
	{
		QueryPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (QueryPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				QueryPrxHelper __h = new QueryPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static QueryPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		QueryPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			QueryPrxHelper __h = new QueryPrxHelper();
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
		return new _QueryDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _QueryDelD();
	}

	public static void __write(BasicStream __os, QueryPrx v)
	{
		__os.writeProxy(v);
	}

	public static QueryPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			QueryPrxHelper result = new QueryPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
