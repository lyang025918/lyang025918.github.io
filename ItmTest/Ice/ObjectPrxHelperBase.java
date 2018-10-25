// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectPrxHelperBase.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.EndpointI;
import IceInternal.Instance;
import IceInternal.LocalExceptionWrapper;
import IceInternal.LocatorInfo;
import IceInternal.ObjectAdapterFactory;
import IceInternal.OutgoingAsync;
import IceInternal.ProxyBatchOutgoingAsync;
import IceInternal.ProxyFactory;
import IceInternal.Reference;
import IceInternal.RequestHandler;
import IceInternal.RouterInfo;
import java.io.*;
import java.util.*;

// Referenced classes of package Ice:
//			LocalException, UserException, UnknownUserException, Identity, 
//			IllegalIdentityException, _ObjectDelM, _ObjectDelD, CommunicatorDestroyedException, 
//			TwowayOnlyException, ObjectInputStream, ObjectPrx, _ObjectDel, 
//			OperationMode, AsyncResult, StringSeqHelper, ByteSeqHolder, 
//			Communicator, Callback, Callback_Object_ice_isA, Callback_Object_ice_ping, 
//			Callback_Object_ice_ids, Callback_Object_ice_id, Callback_Object_ice_invoke, AMI_Object_ice_invoke, 
//			Endpoint, EndpointSelectionType, RouterPrx, LocatorPrx, 
//			Connection, AMI_Object_ice_flushBatchRequests, Callback_Object_ice_flushBatchRequests, IntHolder

public class ObjectPrxHelperBase
	implements ObjectPrx, Serializable
{

	private static final String __ice_isA_name = "ice_isA";
	private static final String __ice_ping_name = "ice_ping";
	private static final String __ice_ids_name = "ice_ids";
	private static final String __ice_id_name = "ice_id";
	private static final String __ice_invoke_name = "ice_invoke";
	private static final String __ice_flushBatchRequests_name = "ice_flushBatchRequests";
	protected static final Map _emptyContext = new HashMap();
	private transient Reference _reference;
	private transient _ObjectDel _delegate;
	static final boolean $assertionsDisabled = !Ice/ObjectPrxHelperBase.desiredAssertionStatus();

	public ObjectPrxHelperBase()
	{
	}

	public final int hashCode()
	{
		return _reference.hashCode();
	}

	/**
	 * @deprecated Method ice_getHash is deprecated
	 */

	public final int ice_getHash()
	{
		return _reference.hashCode();
	}

	public final Communicator ice_getCommunicator()
	{
		return _reference.getCommunicator();
	}

	public final String toString()
	{
		return _reference.toString();
	}

	/**
	 * @deprecated Method ice_toString is deprecated
	 */

	public final String ice_toString()
	{
		return toString();
	}

	public final boolean ice_isA(String __id)
	{
		return ice_isA(__id, null, false);
	}

	public final boolean ice_isA(String __id, Map __context)
	{
		return ice_isA(__id, __context, true);
	}

	private boolean ice_isA(String __id, Map __context, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __context == null)
			__context = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __del = null;
		__checkTwowayOnly("ice_isA");
		__del = __getDelegate(false);
		return __del.ice_isA(__id, __context);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__del, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__del, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public final AsyncResult begin_ice_isA(String __id)
	{
		return begin_ice_isA(__id, null, false, null);
	}

	public final AsyncResult begin_ice_isA(String __id, Map __context)
	{
		return begin_ice_isA(__id, __context, true, null);
	}

	public final AsyncResult begin_ice_isA(String __id, Callback __cb)
	{
		return begin_ice_isA(__id, null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_isA(String __id, Map __context, Callback __cb)
	{
		return begin_ice_isA(__id, __context, true, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_isA(String __id, Callback_Object_ice_isA __cb)
	{
		return begin_ice_isA(__id, null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_isA(String __id, Map __context, Callback_Object_ice_isA __cb)
	{
		return begin_ice_isA(__id, __context, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_ice_isA(String __id, Map __context, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "ice_isA", __cb);
		__checkAsyncTwowayOnly("ice_isA");
		try
		{
			__result.__prepare("ice_isA", OperationMode.Nonmutating, __context, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(__id);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public final boolean end_ice_isA(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "ice_isA");
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
		boolean __ret = __is.readBool();
		__is.endReadEncaps();
		return __ret;
	}

	public final void ice_ping()
	{
		ice_ping(null, false);
	}

	public final void ice_ping(Map __context)
	{
		ice_ping(__context, true);
	}

	private void ice_ping(Map __context, boolean __explicitCtx)
	{
		if (__explicitCtx && __context == null)
			__context = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __del = null;
			try
			{
				__del = __getDelegate(false);
				__del.ice_ping(__context);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__cnt = __handleExceptionWrapperRelaxed(__del, __ex, null, __cnt);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__del, __ex, null, __cnt);
			}
		} while (true);
	}

	public final AsyncResult begin_ice_ping()
	{
		return begin_ice_ping(null, false, null);
	}

	public final AsyncResult begin_ice_ping(Map __context)
	{
		return begin_ice_ping(__context, true, null);
	}

	public final AsyncResult begin_ice_ping(Callback __cb)
	{
		return begin_ice_ping(null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_ping(Map __context, Callback __cb)
	{
		return begin_ice_ping(__context, true, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_ping(Callback_Object_ice_ping __cb)
	{
		return begin_ice_ping(null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_ping(Map __context, Callback_Object_ice_ping __cb)
	{
		return begin_ice_ping(__context, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_ice_ping(Map __context, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "ice_ping", __cb);
		try
		{
			__result.__prepare("ice_ping", OperationMode.Nonmutating, __context, __explicitCtx);
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

	public final void end_ice_ping(AsyncResult __result)
	{
		__end(__result, "ice_ping");
	}

	public final String[] ice_ids()
	{
		return ice_ids(null, false);
	}

	public final String[] ice_ids(Map __context)
	{
		return ice_ids(__context, true);
	}

	private String[] ice_ids(Map __context, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __context == null)
			__context = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __del = null;
		__checkTwowayOnly("ice_ids");
		__del = __getDelegate(false);
		return __del.ice_ids(__context);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__del, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__del, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public final AsyncResult begin_ice_ids()
	{
		return begin_ice_ids(null, false, null);
	}

	public final AsyncResult begin_ice_ids(Map __context)
	{
		return begin_ice_ids(__context, true, null);
	}

	public final AsyncResult begin_ice_ids(Callback __cb)
	{
		return begin_ice_ids(null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_ids(Map __context, Callback __cb)
	{
		return begin_ice_ids(__context, true, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_ids(Callback_Object_ice_ids __cb)
	{
		return begin_ice_ids(null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_ids(Map __context, Callback_Object_ice_ids __cb)
	{
		return begin_ice_ids(__context, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_ice_ids(Map __context, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "ice_ids", __cb);
		__checkAsyncTwowayOnly("ice_ids");
		try
		{
			__result.__prepare("ice_ids", OperationMode.Nonmutating, __context, __explicitCtx);
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

	public final String[] end_ice_ids(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "ice_ids");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		String __ret[] = null;
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		__ret = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public final String ice_id()
	{
		return ice_id(null, false);
	}

	public final String ice_id(Map __context)
	{
		return ice_id(__context, true);
	}

	private String ice_id(Map __context, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __context == null)
			__context = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __del = null;
		__checkTwowayOnly("ice_id");
		__del = __getDelegate(false);
		return __del.ice_id(__context);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__del, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__del, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public final AsyncResult begin_ice_id()
	{
		return begin_ice_id(null, false, null);
	}

	public final AsyncResult begin_ice_id(Map __context)
	{
		return begin_ice_id(__context, true, null);
	}

	public final AsyncResult begin_ice_id(Callback __cb)
	{
		return begin_ice_id(null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_id(Map __context, Callback __cb)
	{
		return begin_ice_id(__context, true, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_id(Callback_Object_ice_id __cb)
	{
		return begin_ice_id(null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_id(Map __context, Callback_Object_ice_id __cb)
	{
		return begin_ice_id(__context, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_ice_id(Map __context, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "ice_id", __cb);
		__checkAsyncTwowayOnly("ice_id");
		try
		{
			__result.__prepare("ice_id", OperationMode.Nonmutating, __context, __explicitCtx);
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

	public final String end_ice_id(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "ice_id");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		String __ret = null;
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		__ret = __is.readString();
		__is.endReadEncaps();
		return __ret;
	}

	public final boolean ice_invoke(String operation, OperationMode mode, byte inParams[], ByteSeqHolder outParams)
	{
		return ice_invoke(operation, mode, inParams, outParams, null, false);
	}

	public final boolean ice_invoke(String operation, OperationMode mode, byte inParams[], ByteSeqHolder outParams, Map context)
	{
		return ice_invoke(operation, mode, inParams, outParams, context, true);
	}

	private boolean ice_invoke(String operation, OperationMode mode, byte inParams[], ByteSeqHolder outParams, Map context, boolean explicitCtx)
	{
		int __cnt;
		if (explicitCtx && context == null)
			context = _emptyContext;
		__cnt = 0;
_L2:
		_ObjectDel __del = null;
		__del = __getDelegate(false);
		return __del.ice_invoke(operation, mode, inParams, outParams, context);
		LocalExceptionWrapper __ex;
		__ex;
		if (mode == OperationMode.Nonmutating || mode == OperationMode.Idempotent)
			__cnt = __handleExceptionWrapperRelaxed(__del, __ex, null, __cnt);
		else
			__handleExceptionWrapper(__del, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__del, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public final AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[])
	{
		return begin_ice_invoke(operation, mode, inParams, null, false, null);
	}

	public final AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[], Map __context)
	{
		return begin_ice_invoke(operation, mode, inParams, __context, true, null);
	}

	public final AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[], Callback __cb)
	{
		return begin_ice_invoke(operation, mode, inParams, null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[], Map __context, Callback __cb)
	{
		return begin_ice_invoke(operation, mode, inParams, __context, true, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[], Callback_Object_ice_invoke __cb)
	{
		return begin_ice_invoke(operation, mode, inParams, null, false, ((CallbackBase) (__cb)));
	}

	public final AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[], Map __context, Callback_Object_ice_invoke __cb)
	{
		return begin_ice_invoke(operation, mode, inParams, __context, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_ice_invoke(String operation, OperationMode mode, byte inParams[], Map __context, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "ice_invoke", __cb);
		try
		{
			__result.__prepare(operation, mode, __context, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeBlob(inParams);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public final boolean end_ice_invoke(ByteSeqHolder outParams, AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "ice_invoke");
		boolean ok = __result.__wait();
		if (_reference.getMode() == 0)
		{
			BasicStream __is = __result.__is();
			__is.startReadEncaps();
			int sz = __is.getReadEncapsSize();
			if (outParams != null)
				outParams.value = __is.readBlob(sz);
			__is.endReadEncaps();
		}
		return ok;
	}

	public final boolean ice_invoke_async(AMI_Object_ice_invoke cb, String operation, OperationMode mode, byte inParams[])
	{
		AsyncResult __result = begin_ice_invoke(operation, mode, inParams, cb);
		return __result.sentSynchronously();
	}

	public final boolean ice_invoke_async(AMI_Object_ice_invoke cb, String operation, OperationMode mode, byte inParams[], Map context)
	{
		AsyncResult __result = begin_ice_invoke(operation, mode, inParams, context, cb);
		return __result.sentSynchronously();
	}

	public final Identity ice_getIdentity()
	{
		return (Identity)_reference.getIdentity().clone();
	}

	public final ObjectPrx ice_identity(Identity newIdentity)
	{
		if (newIdentity.name.equals(""))
			throw new IllegalIdentityException();
		if (newIdentity.equals(_reference.getIdentity()))
		{
			return this;
		} else
		{
			ObjectPrxHelperBase proxy = new ObjectPrxHelperBase();
			proxy.setup(_reference.changeIdentity(newIdentity));
			return proxy;
		}
	}

	public final Map ice_getContext()
	{
		return new HashMap(_reference.getContext());
	}

	public final ObjectPrx ice_context(Map newContext)
	{
		return newInstance(_reference.changeContext(newContext));
	}

	public final String ice_getFacet()
	{
		return _reference.getFacet();
	}

	public final ObjectPrx ice_facet(String newFacet)
	{
		if (newFacet == null)
			newFacet = "";
		if (newFacet.equals(_reference.getFacet()))
		{
			return this;
		} else
		{
			ObjectPrxHelperBase proxy = new ObjectPrxHelperBase();
			proxy.setup(_reference.changeFacet(newFacet));
			return proxy;
		}
	}

	public final String ice_getAdapterId()
	{
		return _reference.getAdapterId();
	}

	public final ObjectPrx ice_adapterId(String newAdapterId)
	{
		if (newAdapterId == null)
			newAdapterId = "";
		if (newAdapterId.equals(_reference.getAdapterId()))
			return this;
		else
			return newInstance(_reference.changeAdapterId(newAdapterId));
	}

	public final Endpoint[] ice_getEndpoints()
	{
		return (Endpoint[])_reference.getEndpoints().clone();
	}

	public final ObjectPrx ice_endpoints(Endpoint newEndpoints[])
	{
		if (Arrays.equals(newEndpoints, _reference.getEndpoints()))
		{
			return this;
		} else
		{
			EndpointI edpts[] = new EndpointI[newEndpoints.length];
			edpts = (EndpointI[])(EndpointI[])Arrays.asList(newEndpoints).toArray(edpts);
			return newInstance(_reference.changeEndpoints(edpts));
		}
	}

	public final int ice_getLocatorCacheTimeout()
	{
		return _reference.getLocatorCacheTimeout();
	}

	public final String ice_getConnectionId()
	{
		return _reference.getConnectionId();
	}

	public final ObjectPrx ice_locatorCacheTimeout(int newTimeout)
	{
		if (newTimeout == _reference.getLocatorCacheTimeout())
			return this;
		else
			return newInstance(_reference.changeLocatorCacheTimeout(newTimeout));
	}

	public final boolean ice_isConnectionCached()
	{
		return _reference.getCacheConnection();
	}

	public final ObjectPrx ice_connectionCached(boolean newCache)
	{
		if (newCache == _reference.getCacheConnection())
			return this;
		else
			return newInstance(_reference.changeCacheConnection(newCache));
	}

	public final EndpointSelectionType ice_getEndpointSelection()
	{
		return _reference.getEndpointSelection();
	}

	public final ObjectPrx ice_endpointSelection(EndpointSelectionType newType)
	{
		if (newType == _reference.getEndpointSelection())
			return this;
		else
			return newInstance(_reference.changeEndpointSelection(newType));
	}

	public final boolean ice_isSecure()
	{
		return _reference.getSecure();
	}

	public final ObjectPrx ice_secure(boolean b)
	{
		if (b == _reference.getSecure())
			return this;
		else
			return newInstance(_reference.changeSecure(b));
	}

	public final boolean ice_isPreferSecure()
	{
		return _reference.getPreferSecure();
	}

	public final ObjectPrx ice_preferSecure(boolean b)
	{
		if (b == _reference.getPreferSecure())
			return this;
		else
			return newInstance(_reference.changePreferSecure(b));
	}

	public final RouterPrx ice_getRouter()
	{
		RouterInfo ri = _reference.getRouterInfo();
		return ri == null ? null : ri.getRouter();
	}

	public final ObjectPrx ice_router(RouterPrx router)
	{
		Reference ref = _reference.changeRouter(router);
		if (ref.equals(_reference))
			return this;
		else
			return newInstance(ref);
	}

	public final LocatorPrx ice_getLocator()
	{
		LocatorInfo ri = _reference.getLocatorInfo();
		return ri == null ? null : ri.getLocator();
	}

	public final ObjectPrx ice_locator(LocatorPrx locator)
	{
		Reference ref = _reference.changeLocator(locator);
		if (ref.equals(_reference))
			return this;
		else
			return newInstance(ref);
	}

	public final boolean ice_isCollocationOptimized()
	{
		return _reference.getCollocationOptimized();
	}

	public final ObjectPrx ice_collocationOptimized(boolean b)
	{
		if (b == _reference.getCollocationOptimized())
			return this;
		else
			return newInstance(_reference.changeCollocationOptimized(b));
	}

	public final ObjectPrx ice_twoway()
	{
		if (_reference.getMode() == 0)
			return this;
		else
			return newInstance(_reference.changeMode(0));
	}

	public final boolean ice_isTwoway()
	{
		return _reference.getMode() == 0;
	}

	public final ObjectPrx ice_oneway()
	{
		if (_reference.getMode() == 1)
			return this;
		else
			return newInstance(_reference.changeMode(1));
	}

	public final boolean ice_isOneway()
	{
		return _reference.getMode() == 1;
	}

	public final ObjectPrx ice_batchOneway()
	{
		if (_reference.getMode() == 2)
			return this;
		else
			return newInstance(_reference.changeMode(2));
	}

	public final boolean ice_isBatchOneway()
	{
		return _reference.getMode() == 2;
	}

	public final ObjectPrx ice_datagram()
	{
		if (_reference.getMode() == 3)
			return this;
		else
			return newInstance(_reference.changeMode(3));
	}

	public final boolean ice_isDatagram()
	{
		return _reference.getMode() == 3;
	}

	public final ObjectPrx ice_batchDatagram()
	{
		if (_reference.getMode() == 4)
			return this;
		else
			return newInstance(_reference.changeMode(4));
	}

	public final boolean ice_isBatchDatagram()
	{
		return _reference.getMode() == 4;
	}

	public final ObjectPrx ice_compress(boolean co)
	{
		Reference ref = _reference.changeCompress(co);
		if (ref.equals(_reference))
			return this;
		else
			return newInstance(ref);
	}

	public final ObjectPrx ice_timeout(int t)
	{
		Reference ref = _reference.changeTimeout(t);
		if (ref.equals(_reference))
			return this;
		else
			return newInstance(ref);
	}

	public final ObjectPrx ice_connectionId(String id)
	{
		Reference ref = _reference.changeConnectionId(id);
		if (ref.equals(_reference))
			return this;
		else
			return newInstance(ref);
	}

	public final Connection ice_getConnection()
	{
		int __cnt = 0;
_L2:
		_ObjectDel __del = null;
		__del = __getDelegate(false);
		return __del.__getRequestHandler().getConnection(true);
		LocalException __ex;
		__ex;
		__cnt = __handleException(__del, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public final Connection ice_getCachedConnection()
	{
		_ObjectDel __del;
		__del = null;
		synchronized (this)
		{
			__del = _delegate;
		}
		if (__del == null)
			break MISSING_BLOCK_LABEL_39;
		return __del.__getRequestHandler().getConnection(false);
		LocalException ex;
		ex;
		return null;
	}

	public void ice_flushBatchRequests()
	{
		_ObjectDel __del = null;
		int __cnt = -1;
		try
		{
			__del = __getDelegate(false);
			__del.ice_flushBatchRequests();
			return;
		}
		catch (LocalException __ex)
		{
			__cnt = __handleException(__del, __ex, null, __cnt);
		}
	}

	public boolean ice_flushBatchRequests_async(AMI_Object_ice_flushBatchRequests cb)
	{
		AsyncResult result = begin_ice_flushBatchRequests(cb);
		return result.sentSynchronously();
	}

	public AsyncResult begin_ice_flushBatchRequests()
	{
		AsyncResult result = begin_ice_flushBatchRequestsInternal(null);
		return result;
	}

	public AsyncResult begin_ice_flushBatchRequests(Callback __cb)
	{
		AsyncResult result = begin_ice_flushBatchRequestsInternal(__cb);
		return result;
	}

	public AsyncResult begin_ice_flushBatchRequests(Callback_Object_ice_flushBatchRequests __cb)
	{
		AsyncResult result = begin_ice_flushBatchRequestsInternal(__cb);
		return result;
	}

	private AsyncResult begin_ice_flushBatchRequestsInternal(CallbackBase __cb)
	{
		ProxyBatchOutgoingAsync __result = new ProxyBatchOutgoingAsync(this, "ice_flushBatchRequests", __cb);
		try
		{
			__result.__send();
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_ice_flushBatchRequests(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "ice_flushBatchRequests");
		__result.__wait();
	}

	public final boolean equals(Object r)
	{
		if (this == r)
			return true;
		if (r instanceof ObjectPrxHelperBase)
			return _reference.equals(((ObjectPrxHelperBase)r)._reference);
		else
			return false;
	}

	public final Reference __reference()
	{
		return _reference;
	}

	public final void __copyFrom(ObjectPrx from)
	{
		ObjectPrxHelperBase h = (ObjectPrxHelperBase)from;
		Reference ref = null;
		_ObjectDelM delegateM = null;
		_ObjectDelD delegateD = null;
		synchronized (from)
		{
			ref = h._reference;
			try
			{
				delegateM = (_ObjectDelM)h._delegate;
			}
			catch (ClassCastException ex) { }
			try
			{
				delegateD = (_ObjectDelD)h._delegate;
			}
			catch (ClassCastException ex) { }
		}
		if (!$assertionsDisabled && _reference != null)
			throw new AssertionError();
		if (!$assertionsDisabled && _delegate != null)
			throw new AssertionError();
		_reference = ref;
		if (_reference.getCacheConnection())
			if (delegateD != null)
			{
				_ObjectDelD delegate = __createDelegateD();
				delegate.__copyFrom(delegateD);
				_delegate = delegate;
			} else
			if (delegateM != null)
			{
				_ObjectDelM delegate = __createDelegateM();
				delegate.__copyFrom(delegateM);
				_delegate = delegate;
			}
	}

	public final int __handleException(_ObjectDel delegate, LocalException ex, IntHolder interval, int cnt)
	{
		synchronized (this)
		{
			if (delegate == _delegate)
				_delegate = null;
		}
		if (cnt == -1)
			throw ex;
		return _reference.getInstance().proxyFactory().checkRetryAfterException(ex, _reference, interval, cnt);
		CommunicatorDestroyedException e;
		e;
		throw ex;
	}

	public final void __handleExceptionWrapper(_ObjectDel delegate, LocalExceptionWrapper ex)
	{
		synchronized (this)
		{
			if (delegate == _delegate)
				_delegate = null;
		}
		if (!ex.retry())
			throw ex.get();
		else
			return;
	}

	public final int __handleExceptionWrapperRelaxed(_ObjectDel delegate, LocalExceptionWrapper ex, IntHolder interval, int cnt)
	{
		if (!ex.retry())
			return __handleException(delegate, ex.get(), interval, cnt);
		synchronized (this)
		{
			if (delegate == _delegate)
				_delegate = null;
		}
		return cnt;
	}

	public final void __checkTwowayOnly(String name)
	{
		if (!ice_isTwoway())
		{
			TwowayOnlyException ex = new TwowayOnlyException();
			ex.operation = name;
			throw ex;
		} else
		{
			return;
		}
	}

	public final void __checkAsyncTwowayOnly(String name)
	{
		if (!ice_isTwoway())
			throw new IllegalArgumentException((new StringBuilder()).append("`").append(name).append("' can only be called with a twoway proxy").toString());
		else
			return;
	}

	public final _ObjectDel __getDelegate(boolean ami)
	{
		if (!_reference.getCacheConnection())
			break MISSING_BLOCK_LABEL_49;
		ObjectPrxHelperBase objectprxhelperbase = this;
		JVM INSTR monitorenter ;
		if (_delegate != null)
			return _delegate;
		_delegate = createDelegate(true);
		_delegate;
		objectprxhelperbase;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
		int mode = _reference.getMode();
		return createDelegate(ami || mode == 2 || mode == 4);
	}

	public synchronized void __setRequestHandler(_ObjectDel delegate, RequestHandler handler)
	{
		if (_reference.getCacheConnection() && delegate == _delegate)
			if (_delegate instanceof _ObjectDelM)
			{
				_delegate = __createDelegateM();
				_delegate.__setRequestHandler(handler);
			} else
			if (_delegate instanceof _ObjectDelD)
			{
				_delegate = __createDelegateD();
				_delegate.__setRequestHandler(handler);
			}
	}

	public final void __end(AsyncResult __result, String operation)
	{
		AsyncResult.__check(__result, this, operation);
		boolean ok = __result.__wait();
		if (_reference.getMode() == 0)
		{
			if (!ok)
				try
				{
					__result.__throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __result.__is();
			__is.skipEmptyEncaps();
		}
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _ObjectDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ObjectDelD();
	}

	_ObjectDel createDelegate(boolean async)
	{
		if (_reference.getCollocationOptimized())
		{
			ObjectAdapter adapter = _reference.getInstance().objectAdapterFactory().findObjectAdapter(this);
			if (adapter != null)
			{
				_ObjectDelD d = __createDelegateD();
				d.setup(_reference, adapter);
				return d;
			}
		}
		_ObjectDelM d = __createDelegateM();
		d.setup(_reference, this, async);
		return d;
	}

	public final void setup(Reference ref)
	{
		if (!$assertionsDisabled && _reference != null)
			throw new AssertionError();
		if (!$assertionsDisabled && _delegate != null)
		{
			throw new AssertionError();
		} else
		{
			_reference = ref;
			return;
		}
	}

	private final ObjectPrxHelperBase newInstance(Reference ref)
	{
		ObjectPrxHelperBase proxy;
		proxy = (ObjectPrxHelperBase)getClass().newInstance();
		proxy.setup(ref);
		return proxy;
		InstantiationException e;
		e;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return null;
		e;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return null;
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		out.writeUTF(toString());
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		String s = in.readUTF();
		try
		{
			Communicator communicator = ((Ice.ObjectInputStream)in).getCommunicator();
			if (communicator == null)
				throw new IOException("Cannot deserialize proxy: no communicator provided");
			ObjectPrxHelperBase proxy = (ObjectPrxHelperBase)communicator.stringToProxy(s);
			_reference = proxy._reference;
			if (!$assertionsDisabled && proxy._delegate != null)
				throw new AssertionError();
		}
		catch (ClassCastException ex)
		{
			IOException e = new IOException("Cannot deserialize proxy: Ice.ObjectInputStream not found");
			e.initCause(ex);
			throw e;
		}
		catch (LocalException ex)
		{
			IOException e = new IOException("Failure occurred while deserializing proxy");
			e.initCause(ex);
			throw e;
		}
	}

}
