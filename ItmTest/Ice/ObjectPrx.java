// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectPrx.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			Communicator, AsyncResult, Callback, Callback_Object_ice_isA, 
//			Callback_Object_ice_ping, Callback_Object_ice_ids, Callback_Object_ice_id, OperationMode, 
//			ByteSeqHolder, Callback_Object_ice_invoke, AMI_Object_ice_invoke, Identity, 
//			Endpoint, EndpointSelectionType, RouterPrx, LocatorPrx, 
//			Connection, AMI_Object_ice_flushBatchRequests, Callback_Object_ice_flushBatchRequests

public interface ObjectPrx
{

	/**
	 * @deprecated Method ice_getHash is deprecated
	 */

	public abstract int ice_getHash();

	public abstract Communicator ice_getCommunicator();

	/**
	 * @deprecated Method ice_toString is deprecated
	 */

	public abstract String ice_toString();

	public abstract boolean ice_isA(String s);

	public abstract boolean ice_isA(String s, Map map);

	public abstract AsyncResult begin_ice_isA(String s);

	public abstract AsyncResult begin_ice_isA(String s, Map map);

	public abstract AsyncResult begin_ice_isA(String s, Callback callback);

	public abstract AsyncResult begin_ice_isA(String s, Map map, Callback callback);

	public abstract AsyncResult begin_ice_isA(String s, Callback_Object_ice_isA callback_object_ice_isa);

	public abstract AsyncResult begin_ice_isA(String s, Map map, Callback_Object_ice_isA callback_object_ice_isa);

	public abstract boolean end_ice_isA(AsyncResult asyncresult);

	public abstract void ice_ping();

	public abstract void ice_ping(Map map);

	public abstract AsyncResult begin_ice_ping();

	public abstract AsyncResult begin_ice_ping(Map map);

	public abstract AsyncResult begin_ice_ping(Callback callback);

	public abstract AsyncResult begin_ice_ping(Map map, Callback callback);

	public abstract AsyncResult begin_ice_ping(Callback_Object_ice_ping callback_object_ice_ping);

	public abstract AsyncResult begin_ice_ping(Map map, Callback_Object_ice_ping callback_object_ice_ping);

	public abstract void end_ice_ping(AsyncResult asyncresult);

	public abstract String[] ice_ids();

	public abstract String[] ice_ids(Map map);

	public abstract AsyncResult begin_ice_ids();

	public abstract AsyncResult begin_ice_ids(Map map);

	public abstract AsyncResult begin_ice_ids(Callback callback);

	public abstract AsyncResult begin_ice_ids(Map map, Callback callback);

	public abstract AsyncResult begin_ice_ids(Callback_Object_ice_ids callback_object_ice_ids);

	public abstract AsyncResult begin_ice_ids(Map map, Callback_Object_ice_ids callback_object_ice_ids);

	public abstract String[] end_ice_ids(AsyncResult asyncresult);

	public abstract String ice_id();

	public abstract String ice_id(Map map);

	public abstract AsyncResult begin_ice_id();

	public abstract AsyncResult begin_ice_id(Map map);

	public abstract AsyncResult begin_ice_id(Callback callback);

	public abstract AsyncResult begin_ice_id(Map map, Callback callback);

	public abstract AsyncResult begin_ice_id(Callback_Object_ice_id callback_object_ice_id);

	public abstract AsyncResult begin_ice_id(Map map, Callback_Object_ice_id callback_object_ice_id);

	public abstract String end_ice_id(AsyncResult asyncresult);

	public abstract boolean ice_invoke(String s, OperationMode operationmode, byte abyte0[], ByteSeqHolder byteseqholder);

	public abstract boolean ice_invoke(String s, OperationMode operationmode, byte abyte0[], ByteSeqHolder byteseqholder, Map map);

	public abstract AsyncResult begin_ice_invoke(String s, OperationMode operationmode, byte abyte0[]);

	public abstract AsyncResult begin_ice_invoke(String s, OperationMode operationmode, byte abyte0[], Map map);

	public abstract AsyncResult begin_ice_invoke(String s, OperationMode operationmode, byte abyte0[], Callback callback);

	public abstract AsyncResult begin_ice_invoke(String s, OperationMode operationmode, byte abyte0[], Map map, Callback callback);

	public abstract AsyncResult begin_ice_invoke(String s, OperationMode operationmode, byte abyte0[], Callback_Object_ice_invoke callback_object_ice_invoke);

	public abstract AsyncResult begin_ice_invoke(String s, OperationMode operationmode, byte abyte0[], Map map, Callback_Object_ice_invoke callback_object_ice_invoke);

	public abstract boolean end_ice_invoke(ByteSeqHolder byteseqholder, AsyncResult asyncresult);

	public abstract boolean ice_invoke_async(AMI_Object_ice_invoke ami_object_ice_invoke, String s, OperationMode operationmode, byte abyte0[]);

	public abstract boolean ice_invoke_async(AMI_Object_ice_invoke ami_object_ice_invoke, String s, OperationMode operationmode, byte abyte0[], Map map);

	public abstract Identity ice_getIdentity();

	public abstract ObjectPrx ice_identity(Identity identity);

	public abstract Map ice_getContext();

	public abstract ObjectPrx ice_context(Map map);

	public abstract String ice_getFacet();

	public abstract ObjectPrx ice_facet(String s);

	public abstract String ice_getAdapterId();

	public abstract ObjectPrx ice_adapterId(String s);

	public abstract Endpoint[] ice_getEndpoints();

	public abstract ObjectPrx ice_endpoints(Endpoint aendpoint[]);

	public abstract int ice_getLocatorCacheTimeout();

	public abstract String ice_getConnectionId();

	public abstract ObjectPrx ice_locatorCacheTimeout(int i);

	public abstract boolean ice_isConnectionCached();

	public abstract ObjectPrx ice_connectionCached(boolean flag);

	public abstract EndpointSelectionType ice_getEndpointSelection();

	public abstract ObjectPrx ice_endpointSelection(EndpointSelectionType endpointselectiontype);

	public abstract boolean ice_isSecure();

	public abstract ObjectPrx ice_secure(boolean flag);

	public abstract boolean ice_isPreferSecure();

	public abstract ObjectPrx ice_preferSecure(boolean flag);

	public abstract RouterPrx ice_getRouter();

	public abstract ObjectPrx ice_router(RouterPrx routerprx);

	public abstract LocatorPrx ice_getLocator();

	public abstract ObjectPrx ice_locator(LocatorPrx locatorprx);

	public abstract boolean ice_isCollocationOptimized();

	public abstract ObjectPrx ice_collocationOptimized(boolean flag);

	public abstract ObjectPrx ice_twoway();

	public abstract boolean ice_isTwoway();

	public abstract ObjectPrx ice_oneway();

	public abstract boolean ice_isOneway();

	public abstract ObjectPrx ice_batchOneway();

	public abstract boolean ice_isBatchOneway();

	public abstract ObjectPrx ice_datagram();

	public abstract boolean ice_isDatagram();

	public abstract ObjectPrx ice_batchDatagram();

	public abstract boolean ice_isBatchDatagram();

	public abstract ObjectPrx ice_compress(boolean flag);

	public abstract ObjectPrx ice_timeout(int i);

	public abstract ObjectPrx ice_connectionId(String s);

	public abstract Connection ice_getConnection();

	public abstract Connection ice_getCachedConnection();

	public abstract void ice_flushBatchRequests();

	public abstract boolean ice_flushBatchRequests_async(AMI_Object_ice_flushBatchRequests ami_object_ice_flushbatchrequests);

	public abstract AsyncResult begin_ice_flushBatchRequests();

	public abstract AsyncResult begin_ice_flushBatchRequests(Callback callback);

	public abstract AsyncResult begin_ice_flushBatchRequests(Callback_Object_ice_flushBatchRequests callback_object_ice_flushbatchrequests);

	public abstract void end_ice_flushBatchRequests(AsyncResult asyncresult);

	public abstract boolean equals(Object obj);
}
