// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdminPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_AdminDel, AccessDeniedException, DeploymentException, ObjectExistsException, 
//			NodeUnreachableException, ServerNotExistException, AdapterNotExistException, ApplicationNotExistException, 
//			ApplicationInfo, ApplicationDescriptor, NodeNotExistException, NodeInfo, 
//			LoadInfo, ObjectNotRegisteredException, ObjectInfo, RegistryNotExistException, 
//			RegistryUnreachableException, RegistryInfo, ServerInfo, PatchException, 
//			BadSignalException, ServerStartException, ServerStopException, AdminPrx, 
//			_AdminDelM, _AdminDelD, AdapterInfoSeqHelper, ObjectInfoSeqHelper, 
//			ServerState, ServerInstanceDescriptor, ApplicationUpdateDescriptor, Callback_Admin_addApplication, 
//			AMI_Admin_addApplication, Callback_Admin_addObject, AMI_Admin_addObject, Callback_Admin_addObjectWithType, 
//			AMI_Admin_addObjectWithType, Callback_Admin_enableServer, AMI_Admin_enableServer, AdapterInfo, 
//			Callback_Admin_getAdapterInfo, Callback_Admin_getAllAdapterIds, Callback_Admin_getAllApplicationNames, Callback_Admin_getAllNodeNames, 
//			Callback_Admin_getAllObjectInfos, Callback_Admin_getAllRegistryNames, Callback_Admin_getAllServerIds, Callback_Admin_getApplicationInfo, 
//			Callback_Admin_getDefaultApplicationDescriptor, Callback_Admin_getNodeHostname, Callback_Admin_getNodeInfo, Callback_Admin_getNodeLoad, 
//			AMI_Admin_getNodeLoad, Callback_Admin_getNodeProcessorSocketCount, Callback_Admin_getObjectInfo, Callback_Admin_getObjectInfosByType, 
//			Callback_Admin_getRegistryInfo, Callback_Admin_getServerAdmin, Callback_Admin_getServerAdminCategory, Callback_Admin_getServerInfo, 
//			Callback_Admin_getServerPid, Callback_Admin_getServerState, Callback_Admin_getSliceChecksums, Callback_Admin_instantiateServer, 
//			Callback_Admin_isServerEnabled, Callback_Admin_patchApplication, AMI_Admin_patchApplication, Callback_Admin_patchServer, 
//			AMI_Admin_patchServer, Callback_Admin_pingNode, Callback_Admin_pingRegistry, Callback_Admin_removeAdapter, 
//			AMI_Admin_removeAdapter, Callback_Admin_removeApplication, AMI_Admin_removeApplication, Callback_Admin_removeObject, 
//			AMI_Admin_removeObject, Callback_Admin_sendSignal, AMI_Admin_sendSignal, Callback_Admin_shutdown, 
//			Callback_Admin_shutdownNode, AMI_Admin_shutdownNode, Callback_Admin_shutdownRegistry, AMI_Admin_shutdownRegistry, 
//			Callback_Admin_startServer, AMI_Admin_startServer, Callback_Admin_stopServer, AMI_Admin_stopServer, 
//			Callback_Admin_syncApplication, AMI_Admin_syncApplication, Callback_Admin_updateApplication, AMI_Admin_updateApplication, 
//			Callback_Admin_updateObject, Callback_Admin_writeMessage, AMI_Admin_writeMessage

public final class AdminPrxHelper extends ObjectPrxHelperBase
	implements AdminPrx
{

	private static final String __addApplication_name = "addApplication";
	private static final String __addObject_name = "addObject";
	private static final String __addObjectWithType_name = "addObjectWithType";
	private static final String __enableServer_name = "enableServer";
	private static final String __getAdapterInfo_name = "getAdapterInfo";
	private static final String __getAllAdapterIds_name = "getAllAdapterIds";
	private static final String __getAllApplicationNames_name = "getAllApplicationNames";
	private static final String __getAllNodeNames_name = "getAllNodeNames";
	private static final String __getAllObjectInfos_name = "getAllObjectInfos";
	private static final String __getAllRegistryNames_name = "getAllRegistryNames";
	private static final String __getAllServerIds_name = "getAllServerIds";
	private static final String __getApplicationInfo_name = "getApplicationInfo";
	private static final String __getDefaultApplicationDescriptor_name = "getDefaultApplicationDescriptor";
	private static final String __getNodeHostname_name = "getNodeHostname";
	private static final String __getNodeInfo_name = "getNodeInfo";
	private static final String __getNodeLoad_name = "getNodeLoad";
	private static final String __getNodeProcessorSocketCount_name = "getNodeProcessorSocketCount";
	private static final String __getObjectInfo_name = "getObjectInfo";
	private static final String __getObjectInfosByType_name = "getObjectInfosByType";
	private static final String __getRegistryInfo_name = "getRegistryInfo";
	private static final String __getServerAdmin_name = "getServerAdmin";
	private static final String __getServerAdminCategory_name = "getServerAdminCategory";
	private static final String __getServerInfo_name = "getServerInfo";
	private static final String __getServerPid_name = "getServerPid";
	private static final String __getServerState_name = "getServerState";
	private static final String __getSliceChecksums_name = "getSliceChecksums";
	private static final String __instantiateServer_name = "instantiateServer";
	private static final String __isServerEnabled_name = "isServerEnabled";
	private static final String __patchApplication_name = "patchApplication";
	private static final String __patchServer_name = "patchServer";
	private static final String __pingNode_name = "pingNode";
	private static final String __pingRegistry_name = "pingRegistry";
	private static final String __removeAdapter_name = "removeAdapter";
	private static final String __removeApplication_name = "removeApplication";
	private static final String __removeObject_name = "removeObject";
	private static final String __sendSignal_name = "sendSignal";
	private static final String __shutdown_name = "shutdown";
	private static final String __shutdownNode_name = "shutdownNode";
	private static final String __shutdownRegistry_name = "shutdownRegistry";
	private static final String __startServer_name = "startServer";
	private static final String __stopServer_name = "stopServer";
	private static final String __syncApplication_name = "syncApplication";
	private static final String __updateApplication_name = "updateApplication";
	private static final String __updateObject_name = "updateObject";
	private static final String __writeMessage_name = "writeMessage";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::Admin"
	};

	public AdminPrxHelper()
	{
	}

	public void addApplication(ApplicationDescriptor descriptor)
		throws AccessDeniedException, DeploymentException
	{
		addApplication(descriptor, null, false);
	}

	public void addApplication(ApplicationDescriptor descriptor, Map __ctx)
		throws AccessDeniedException, DeploymentException
	{
		addApplication(descriptor, __ctx, true);
	}

	private void addApplication(ApplicationDescriptor descriptor, Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException, DeploymentException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("addApplication");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.addApplication(descriptor, __ctx);
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

	public AsyncResult begin_addApplication(ApplicationDescriptor descriptor)
	{
		return begin_addApplication(descriptor, null, false, null);
	}

	public AsyncResult begin_addApplication(ApplicationDescriptor descriptor, Map __ctx)
	{
		return begin_addApplication(descriptor, __ctx, true, null);
	}

	public AsyncResult begin_addApplication(ApplicationDescriptor descriptor, Callback __cb)
	{
		return begin_addApplication(descriptor, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addApplication(ApplicationDescriptor descriptor, Map __ctx, Callback __cb)
	{
		return begin_addApplication(descriptor, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addApplication(ApplicationDescriptor descriptor, Callback_Admin_addApplication __cb)
	{
		return begin_addApplication(descriptor, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addApplication(ApplicationDescriptor descriptor, Map __ctx, Callback_Admin_addApplication __cb)
	{
		return begin_addApplication(descriptor, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_addApplication(ApplicationDescriptor descriptor, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("addApplication");
		OutgoingAsync __result = new OutgoingAsync(this, "addApplication", __cb);
		try
		{
			__result.__prepare("addApplication", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			descriptor.__write(__os);
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

	public void end_addApplication(AsyncResult __result)
		throws AccessDeniedException, DeploymentException
	{
		AsyncResult.__check(__result, this, "addApplication");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
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

	public boolean addApplication_async(AMI_Admin_addApplication __cb, ApplicationDescriptor descriptor)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addApplication");
			__r = begin_addApplication(descriptor, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean addApplication_async(AMI_Admin_addApplication __cb, ApplicationDescriptor descriptor, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addApplication");
			__r = begin_addApplication(descriptor, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void addObject(ObjectPrx obj)
		throws DeploymentException, ObjectExistsException
	{
		addObject(obj, null, false);
	}

	public void addObject(ObjectPrx obj, Map __ctx)
		throws DeploymentException, ObjectExistsException
	{
		addObject(obj, __ctx, true);
	}

	private void addObject(ObjectPrx obj, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, ObjectExistsException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("addObject");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.addObject(obj, __ctx);
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

	public AsyncResult begin_addObject(ObjectPrx obj)
	{
		return begin_addObject(obj, null, false, null);
	}

	public AsyncResult begin_addObject(ObjectPrx obj, Map __ctx)
	{
		return begin_addObject(obj, __ctx, true, null);
	}

	public AsyncResult begin_addObject(ObjectPrx obj, Callback __cb)
	{
		return begin_addObject(obj, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObject(ObjectPrx obj, Map __ctx, Callback __cb)
	{
		return begin_addObject(obj, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObject(ObjectPrx obj, Callback_Admin_addObject __cb)
	{
		return begin_addObject(obj, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObject(ObjectPrx obj, Map __ctx, Callback_Admin_addObject __cb)
	{
		return begin_addObject(obj, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_addObject(ObjectPrx obj, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("addObject");
		OutgoingAsync __result = new OutgoingAsync(this, "addObject", __cb);
		try
		{
			__result.__prepare("addObject", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeProxy(obj);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_addObject(AsyncResult __result)
		throws DeploymentException, ObjectExistsException
	{
		AsyncResult.__check(__result, this, "addObject");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (ObjectExistsException __ex)
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

	public boolean addObject_async(AMI_Admin_addObject __cb, ObjectPrx obj)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addObject");
			__r = begin_addObject(obj, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addObject", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean addObject_async(AMI_Admin_addObject __cb, ObjectPrx obj, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addObject");
			__r = begin_addObject(obj, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addObject", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void addObjectWithType(ObjectPrx obj, String type)
		throws DeploymentException, ObjectExistsException
	{
		addObjectWithType(obj, type, null, false);
	}

	public void addObjectWithType(ObjectPrx obj, String type, Map __ctx)
		throws DeploymentException, ObjectExistsException
	{
		addObjectWithType(obj, type, __ctx, true);
	}

	private void addObjectWithType(ObjectPrx obj, String type, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, ObjectExistsException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("addObjectWithType");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.addObjectWithType(obj, type, __ctx);
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

	public AsyncResult begin_addObjectWithType(ObjectPrx obj, String type)
	{
		return begin_addObjectWithType(obj, type, null, false, null);
	}

	public AsyncResult begin_addObjectWithType(ObjectPrx obj, String type, Map __ctx)
	{
		return begin_addObjectWithType(obj, type, __ctx, true, null);
	}

	public AsyncResult begin_addObjectWithType(ObjectPrx obj, String type, Callback __cb)
	{
		return begin_addObjectWithType(obj, type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObjectWithType(ObjectPrx obj, String type, Map __ctx, Callback __cb)
	{
		return begin_addObjectWithType(obj, type, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObjectWithType(ObjectPrx obj, String type, Callback_Admin_addObjectWithType __cb)
	{
		return begin_addObjectWithType(obj, type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_addObjectWithType(ObjectPrx obj, String type, Map __ctx, Callback_Admin_addObjectWithType __cb)
	{
		return begin_addObjectWithType(obj, type, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_addObjectWithType(ObjectPrx obj, String type, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("addObjectWithType");
		OutgoingAsync __result = new OutgoingAsync(this, "addObjectWithType", __cb);
		try
		{
			__result.__prepare("addObjectWithType", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeProxy(obj);
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

	public void end_addObjectWithType(AsyncResult __result)
		throws DeploymentException, ObjectExistsException
	{
		AsyncResult.__check(__result, this, "addObjectWithType");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (ObjectExistsException __ex)
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

	public boolean addObjectWithType_async(AMI_Admin_addObjectWithType __cb, ObjectPrx obj, String type)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addObjectWithType");
			__r = begin_addObjectWithType(obj, type, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addObjectWithType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean addObjectWithType_async(AMI_Admin_addObjectWithType __cb, ObjectPrx obj, String type, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("addObjectWithType");
			__r = begin_addObjectWithType(obj, type, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "addObjectWithType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void enableServer(String id, boolean enabled)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		enableServer(id, enabled, null, false);
	}

	public void enableServer(String id, boolean enabled, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		enableServer(id, enabled, __ctx, true);
	}

	private void enableServer(String id, boolean enabled, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("enableServer");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.enableServer(id, enabled, __ctx);
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

	public AsyncResult begin_enableServer(String id, boolean enabled)
	{
		return begin_enableServer(id, enabled, null, false, null);
	}

	public AsyncResult begin_enableServer(String id, boolean enabled, Map __ctx)
	{
		return begin_enableServer(id, enabled, __ctx, true, null);
	}

	public AsyncResult begin_enableServer(String id, boolean enabled, Callback __cb)
	{
		return begin_enableServer(id, enabled, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_enableServer(String id, boolean enabled, Map __ctx, Callback __cb)
	{
		return begin_enableServer(id, enabled, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_enableServer(String id, boolean enabled, Callback_Admin_enableServer __cb)
	{
		return begin_enableServer(id, enabled, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_enableServer(String id, boolean enabled, Map __ctx, Callback_Admin_enableServer __cb)
	{
		return begin_enableServer(id, enabled, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_enableServer(String id, boolean enabled, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("enableServer");
		OutgoingAsync __result = new OutgoingAsync(this, "enableServer", __cb);
		try
		{
			__result.__prepare("enableServer", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeBool(enabled);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_enableServer(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "enableServer");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
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

	public boolean enableServer_async(AMI_Admin_enableServer __cb, String id, boolean enabled)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("enableServer");
			__r = begin_enableServer(id, enabled, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "enableServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean enableServer_async(AMI_Admin_enableServer __cb, String id, boolean enabled, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("enableServer");
			__r = begin_enableServer(id, enabled, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "enableServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public AdapterInfo[] getAdapterInfo(String id)
		throws AdapterNotExistException
	{
		return getAdapterInfo(id, null, false);
	}

	public AdapterInfo[] getAdapterInfo(String id, Map __ctx)
		throws AdapterNotExistException
	{
		return getAdapterInfo(id, __ctx, true);
	}

	private AdapterInfo[] getAdapterInfo(String id, Map __ctx, boolean __explicitCtx)
		throws AdapterNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAdapterInfo");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAdapterInfo(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAdapterInfo(String id)
	{
		return begin_getAdapterInfo(id, null, false, null);
	}

	public AsyncResult begin_getAdapterInfo(String id, Map __ctx)
	{
		return begin_getAdapterInfo(id, __ctx, true, null);
	}

	public AsyncResult begin_getAdapterInfo(String id, Callback __cb)
	{
		return begin_getAdapterInfo(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdapterInfo(String id, Map __ctx, Callback __cb)
	{
		return begin_getAdapterInfo(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdapterInfo(String id, Callback_Admin_getAdapterInfo __cb)
	{
		return begin_getAdapterInfo(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdapterInfo(String id, Map __ctx, Callback_Admin_getAdapterInfo __cb)
	{
		return begin_getAdapterInfo(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAdapterInfo(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAdapterInfo");
		OutgoingAsync __result = new OutgoingAsync(this, "getAdapterInfo", __cb);
		try
		{
			__result.__prepare("getAdapterInfo", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public AdapterInfo[] end_getAdapterInfo(AsyncResult __result)
		throws AdapterNotExistException
	{
		AsyncResult.__check(__result, this, "getAdapterInfo");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AdapterNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		AdapterInfo __ret[] = AdapterInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public String[] getAllAdapterIds()
	{
		return getAllAdapterIds(null, false);
	}

	public String[] getAllAdapterIds(Map __ctx)
	{
		return getAllAdapterIds(__ctx, true);
	}

	private String[] getAllAdapterIds(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAllAdapterIds");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAllAdapterIds(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAllAdapterIds()
	{
		return begin_getAllAdapterIds(null, false, null);
	}

	public AsyncResult begin_getAllAdapterIds(Map __ctx)
	{
		return begin_getAllAdapterIds(__ctx, true, null);
	}

	public AsyncResult begin_getAllAdapterIds(Callback __cb)
	{
		return begin_getAllAdapterIds(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllAdapterIds(Map __ctx, Callback __cb)
	{
		return begin_getAllAdapterIds(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllAdapterIds(Callback_Admin_getAllAdapterIds __cb)
	{
		return begin_getAllAdapterIds(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllAdapterIds(Map __ctx, Callback_Admin_getAllAdapterIds __cb)
	{
		return begin_getAllAdapterIds(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAllAdapterIds(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAllAdapterIds");
		OutgoingAsync __result = new OutgoingAsync(this, "getAllAdapterIds", __cb);
		try
		{
			__result.__prepare("getAllAdapterIds", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public String[] end_getAllAdapterIds(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAllAdapterIds");
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
		String __ret[] = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public String[] getAllApplicationNames()
	{
		return getAllApplicationNames(null, false);
	}

	public String[] getAllApplicationNames(Map __ctx)
	{
		return getAllApplicationNames(__ctx, true);
	}

	private String[] getAllApplicationNames(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAllApplicationNames");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAllApplicationNames(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAllApplicationNames()
	{
		return begin_getAllApplicationNames(null, false, null);
	}

	public AsyncResult begin_getAllApplicationNames(Map __ctx)
	{
		return begin_getAllApplicationNames(__ctx, true, null);
	}

	public AsyncResult begin_getAllApplicationNames(Callback __cb)
	{
		return begin_getAllApplicationNames(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllApplicationNames(Map __ctx, Callback __cb)
	{
		return begin_getAllApplicationNames(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllApplicationNames(Callback_Admin_getAllApplicationNames __cb)
	{
		return begin_getAllApplicationNames(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllApplicationNames(Map __ctx, Callback_Admin_getAllApplicationNames __cb)
	{
		return begin_getAllApplicationNames(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAllApplicationNames(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAllApplicationNames");
		OutgoingAsync __result = new OutgoingAsync(this, "getAllApplicationNames", __cb);
		try
		{
			__result.__prepare("getAllApplicationNames", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public String[] end_getAllApplicationNames(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAllApplicationNames");
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
		String __ret[] = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public String[] getAllNodeNames()
	{
		return getAllNodeNames(null, false);
	}

	public String[] getAllNodeNames(Map __ctx)
	{
		return getAllNodeNames(__ctx, true);
	}

	private String[] getAllNodeNames(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAllNodeNames");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAllNodeNames(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAllNodeNames()
	{
		return begin_getAllNodeNames(null, false, null);
	}

	public AsyncResult begin_getAllNodeNames(Map __ctx)
	{
		return begin_getAllNodeNames(__ctx, true, null);
	}

	public AsyncResult begin_getAllNodeNames(Callback __cb)
	{
		return begin_getAllNodeNames(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllNodeNames(Map __ctx, Callback __cb)
	{
		return begin_getAllNodeNames(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllNodeNames(Callback_Admin_getAllNodeNames __cb)
	{
		return begin_getAllNodeNames(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllNodeNames(Map __ctx, Callback_Admin_getAllNodeNames __cb)
	{
		return begin_getAllNodeNames(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAllNodeNames(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAllNodeNames");
		OutgoingAsync __result = new OutgoingAsync(this, "getAllNodeNames", __cb);
		try
		{
			__result.__prepare("getAllNodeNames", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public String[] end_getAllNodeNames(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAllNodeNames");
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
		String __ret[] = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectInfo[] getAllObjectInfos(String expr)
	{
		return getAllObjectInfos(expr, null, false);
	}

	public ObjectInfo[] getAllObjectInfos(String expr, Map __ctx)
	{
		return getAllObjectInfos(expr, __ctx, true);
	}

	private ObjectInfo[] getAllObjectInfos(String expr, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAllObjectInfos");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAllObjectInfos(expr, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAllObjectInfos(String expr)
	{
		return begin_getAllObjectInfos(expr, null, false, null);
	}

	public AsyncResult begin_getAllObjectInfos(String expr, Map __ctx)
	{
		return begin_getAllObjectInfos(expr, __ctx, true, null);
	}

	public AsyncResult begin_getAllObjectInfos(String expr, Callback __cb)
	{
		return begin_getAllObjectInfos(expr, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllObjectInfos(String expr, Map __ctx, Callback __cb)
	{
		return begin_getAllObjectInfos(expr, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllObjectInfos(String expr, Callback_Admin_getAllObjectInfos __cb)
	{
		return begin_getAllObjectInfos(expr, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllObjectInfos(String expr, Map __ctx, Callback_Admin_getAllObjectInfos __cb)
	{
		return begin_getAllObjectInfos(expr, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAllObjectInfos(String expr, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAllObjectInfos");
		OutgoingAsync __result = new OutgoingAsync(this, "getAllObjectInfos", __cb);
		try
		{
			__result.__prepare("getAllObjectInfos", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(expr);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectInfo[] end_getAllObjectInfos(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAllObjectInfos");
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
		ObjectInfo __ret[] = ObjectInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public String[] getAllRegistryNames()
	{
		return getAllRegistryNames(null, false);
	}

	public String[] getAllRegistryNames(Map __ctx)
	{
		return getAllRegistryNames(__ctx, true);
	}

	private String[] getAllRegistryNames(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAllRegistryNames");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAllRegistryNames(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAllRegistryNames()
	{
		return begin_getAllRegistryNames(null, false, null);
	}

	public AsyncResult begin_getAllRegistryNames(Map __ctx)
	{
		return begin_getAllRegistryNames(__ctx, true, null);
	}

	public AsyncResult begin_getAllRegistryNames(Callback __cb)
	{
		return begin_getAllRegistryNames(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllRegistryNames(Map __ctx, Callback __cb)
	{
		return begin_getAllRegistryNames(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllRegistryNames(Callback_Admin_getAllRegistryNames __cb)
	{
		return begin_getAllRegistryNames(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllRegistryNames(Map __ctx, Callback_Admin_getAllRegistryNames __cb)
	{
		return begin_getAllRegistryNames(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAllRegistryNames(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAllRegistryNames");
		OutgoingAsync __result = new OutgoingAsync(this, "getAllRegistryNames", __cb);
		try
		{
			__result.__prepare("getAllRegistryNames", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public String[] end_getAllRegistryNames(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAllRegistryNames");
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
		String __ret[] = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public String[] getAllServerIds()
	{
		return getAllServerIds(null, false);
	}

	public String[] getAllServerIds(Map __ctx)
	{
		return getAllServerIds(__ctx, true);
	}

	private String[] getAllServerIds(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getAllServerIds");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getAllServerIds(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAllServerIds()
	{
		return begin_getAllServerIds(null, false, null);
	}

	public AsyncResult begin_getAllServerIds(Map __ctx)
	{
		return begin_getAllServerIds(__ctx, true, null);
	}

	public AsyncResult begin_getAllServerIds(Callback __cb)
	{
		return begin_getAllServerIds(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllServerIds(Map __ctx, Callback __cb)
	{
		return begin_getAllServerIds(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllServerIds(Callback_Admin_getAllServerIds __cb)
	{
		return begin_getAllServerIds(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAllServerIds(Map __ctx, Callback_Admin_getAllServerIds __cb)
	{
		return begin_getAllServerIds(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAllServerIds(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAllServerIds");
		OutgoingAsync __result = new OutgoingAsync(this, "getAllServerIds", __cb);
		try
		{
			__result.__prepare("getAllServerIds", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public String[] end_getAllServerIds(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAllServerIds");
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
		String __ret[] = StringSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public ApplicationInfo getApplicationInfo(String name)
		throws ApplicationNotExistException
	{
		return getApplicationInfo(name, null, false);
	}

	public ApplicationInfo getApplicationInfo(String name, Map __ctx)
		throws ApplicationNotExistException
	{
		return getApplicationInfo(name, __ctx, true);
	}

	private ApplicationInfo getApplicationInfo(String name, Map __ctx, boolean __explicitCtx)
		throws ApplicationNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getApplicationInfo");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getApplicationInfo(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getApplicationInfo(String name)
	{
		return begin_getApplicationInfo(name, null, false, null);
	}

	public AsyncResult begin_getApplicationInfo(String name, Map __ctx)
	{
		return begin_getApplicationInfo(name, __ctx, true, null);
	}

	public AsyncResult begin_getApplicationInfo(String name, Callback __cb)
	{
		return begin_getApplicationInfo(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getApplicationInfo(String name, Map __ctx, Callback __cb)
	{
		return begin_getApplicationInfo(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getApplicationInfo(String name, Callback_Admin_getApplicationInfo __cb)
	{
		return begin_getApplicationInfo(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getApplicationInfo(String name, Map __ctx, Callback_Admin_getApplicationInfo __cb)
	{
		return begin_getApplicationInfo(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getApplicationInfo(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getApplicationInfo");
		OutgoingAsync __result = new OutgoingAsync(this, "getApplicationInfo", __cb);
		try
		{
			__result.__prepare("getApplicationInfo", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ApplicationInfo end_getApplicationInfo(AsyncResult __result)
		throws ApplicationNotExistException
	{
		AsyncResult.__check(__result, this, "getApplicationInfo");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ApplicationNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ApplicationInfo __ret = new ApplicationInfo();
		__ret.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		return __ret;
	}

	public ApplicationDescriptor getDefaultApplicationDescriptor()
		throws DeploymentException
	{
		return getDefaultApplicationDescriptor(null, false);
	}

	public ApplicationDescriptor getDefaultApplicationDescriptor(Map __ctx)
		throws DeploymentException
	{
		return getDefaultApplicationDescriptor(__ctx, true);
	}

	private ApplicationDescriptor getDefaultApplicationDescriptor(Map __ctx, boolean __explicitCtx)
		throws DeploymentException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getDefaultApplicationDescriptor");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getDefaultApplicationDescriptor(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getDefaultApplicationDescriptor()
	{
		return begin_getDefaultApplicationDescriptor(null, false, null);
	}

	public AsyncResult begin_getDefaultApplicationDescriptor(Map __ctx)
	{
		return begin_getDefaultApplicationDescriptor(__ctx, true, null);
	}

	public AsyncResult begin_getDefaultApplicationDescriptor(Callback __cb)
	{
		return begin_getDefaultApplicationDescriptor(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getDefaultApplicationDescriptor(Map __ctx, Callback __cb)
	{
		return begin_getDefaultApplicationDescriptor(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getDefaultApplicationDescriptor(Callback_Admin_getDefaultApplicationDescriptor __cb)
	{
		return begin_getDefaultApplicationDescriptor(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getDefaultApplicationDescriptor(Map __ctx, Callback_Admin_getDefaultApplicationDescriptor __cb)
	{
		return begin_getDefaultApplicationDescriptor(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getDefaultApplicationDescriptor(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getDefaultApplicationDescriptor");
		OutgoingAsync __result = new OutgoingAsync(this, "getDefaultApplicationDescriptor", __cb);
		try
		{
			__result.__prepare("getDefaultApplicationDescriptor", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ApplicationDescriptor end_getDefaultApplicationDescriptor(AsyncResult __result)
		throws DeploymentException
	{
		AsyncResult.__check(__result, this, "getDefaultApplicationDescriptor");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ApplicationDescriptor __ret = new ApplicationDescriptor();
		__ret.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		return __ret;
	}

	public String getNodeHostname(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeHostname(name, null, false);
	}

	public String getNodeHostname(String name, Map __ctx)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeHostname(name, __ctx, true);
	}

	private String getNodeHostname(String name, Map __ctx, boolean __explicitCtx)
		throws NodeNotExistException, NodeUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getNodeHostname");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getNodeHostname(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getNodeHostname(String name)
	{
		return begin_getNodeHostname(name, null, false, null);
	}

	public AsyncResult begin_getNodeHostname(String name, Map __ctx)
	{
		return begin_getNodeHostname(name, __ctx, true, null);
	}

	public AsyncResult begin_getNodeHostname(String name, Callback __cb)
	{
		return begin_getNodeHostname(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeHostname(String name, Map __ctx, Callback __cb)
	{
		return begin_getNodeHostname(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeHostname(String name, Callback_Admin_getNodeHostname __cb)
	{
		return begin_getNodeHostname(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeHostname(String name, Map __ctx, Callback_Admin_getNodeHostname __cb)
	{
		return begin_getNodeHostname(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getNodeHostname(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getNodeHostname");
		OutgoingAsync __result = new OutgoingAsync(this, "getNodeHostname", __cb);
		try
		{
			__result.__prepare("getNodeHostname", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public String end_getNodeHostname(AsyncResult __result)
		throws NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "getNodeHostname");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		String __ret = __is.readString();
		__is.endReadEncaps();
		return __ret;
	}

	public NodeInfo getNodeInfo(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeInfo(name, null, false);
	}

	public NodeInfo getNodeInfo(String name, Map __ctx)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeInfo(name, __ctx, true);
	}

	private NodeInfo getNodeInfo(String name, Map __ctx, boolean __explicitCtx)
		throws NodeNotExistException, NodeUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getNodeInfo");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getNodeInfo(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getNodeInfo(String name)
	{
		return begin_getNodeInfo(name, null, false, null);
	}

	public AsyncResult begin_getNodeInfo(String name, Map __ctx)
	{
		return begin_getNodeInfo(name, __ctx, true, null);
	}

	public AsyncResult begin_getNodeInfo(String name, Callback __cb)
	{
		return begin_getNodeInfo(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeInfo(String name, Map __ctx, Callback __cb)
	{
		return begin_getNodeInfo(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeInfo(String name, Callback_Admin_getNodeInfo __cb)
	{
		return begin_getNodeInfo(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeInfo(String name, Map __ctx, Callback_Admin_getNodeInfo __cb)
	{
		return begin_getNodeInfo(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getNodeInfo(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getNodeInfo");
		OutgoingAsync __result = new OutgoingAsync(this, "getNodeInfo", __cb);
		try
		{
			__result.__prepare("getNodeInfo", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public NodeInfo end_getNodeInfo(AsyncResult __result)
		throws NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "getNodeInfo");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		NodeInfo __ret = new NodeInfo();
		__ret.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public LoadInfo getNodeLoad(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeLoad(name, null, false);
	}

	public LoadInfo getNodeLoad(String name, Map __ctx)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeLoad(name, __ctx, true);
	}

	private LoadInfo getNodeLoad(String name, Map __ctx, boolean __explicitCtx)
		throws NodeNotExistException, NodeUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getNodeLoad");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getNodeLoad(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getNodeLoad(String name)
	{
		return begin_getNodeLoad(name, null, false, null);
	}

	public AsyncResult begin_getNodeLoad(String name, Map __ctx)
	{
		return begin_getNodeLoad(name, __ctx, true, null);
	}

	public AsyncResult begin_getNodeLoad(String name, Callback __cb)
	{
		return begin_getNodeLoad(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeLoad(String name, Map __ctx, Callback __cb)
	{
		return begin_getNodeLoad(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeLoad(String name, Callback_Admin_getNodeLoad __cb)
	{
		return begin_getNodeLoad(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeLoad(String name, Map __ctx, Callback_Admin_getNodeLoad __cb)
	{
		return begin_getNodeLoad(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getNodeLoad(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getNodeLoad");
		OutgoingAsync __result = new OutgoingAsync(this, "getNodeLoad", __cb);
		try
		{
			__result.__prepare("getNodeLoad", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public LoadInfo end_getNodeLoad(AsyncResult __result)
		throws NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "getNodeLoad");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		LoadInfo __ret = new LoadInfo();
		__ret.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public boolean getNodeLoad_async(AMI_Admin_getNodeLoad __cb, String name)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getNodeLoad");
			__r = begin_getNodeLoad(name, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getNodeLoad", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean getNodeLoad_async(AMI_Admin_getNodeLoad __cb, String name, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("getNodeLoad");
			__r = begin_getNodeLoad(name, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "getNodeLoad", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public int getNodeProcessorSocketCount(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeProcessorSocketCount(name, null, false);
	}

	public int getNodeProcessorSocketCount(String name, Map __ctx)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeProcessorSocketCount(name, __ctx, true);
	}

	private int getNodeProcessorSocketCount(String name, Map __ctx, boolean __explicitCtx)
		throws NodeNotExistException, NodeUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getNodeProcessorSocketCount");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getNodeProcessorSocketCount(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getNodeProcessorSocketCount(String name)
	{
		return begin_getNodeProcessorSocketCount(name, null, false, null);
	}

	public AsyncResult begin_getNodeProcessorSocketCount(String name, Map __ctx)
	{
		return begin_getNodeProcessorSocketCount(name, __ctx, true, null);
	}

	public AsyncResult begin_getNodeProcessorSocketCount(String name, Callback __cb)
	{
		return begin_getNodeProcessorSocketCount(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeProcessorSocketCount(String name, Map __ctx, Callback __cb)
	{
		return begin_getNodeProcessorSocketCount(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeProcessorSocketCount(String name, Callback_Admin_getNodeProcessorSocketCount __cb)
	{
		return begin_getNodeProcessorSocketCount(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNodeProcessorSocketCount(String name, Map __ctx, Callback_Admin_getNodeProcessorSocketCount __cb)
	{
		return begin_getNodeProcessorSocketCount(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getNodeProcessorSocketCount(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getNodeProcessorSocketCount");
		OutgoingAsync __result = new OutgoingAsync(this, "getNodeProcessorSocketCount", __cb);
		try
		{
			__result.__prepare("getNodeProcessorSocketCount", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public int end_getNodeProcessorSocketCount(AsyncResult __result)
		throws NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "getNodeProcessorSocketCount");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectInfo getObjectInfo(Identity id)
		throws ObjectNotRegisteredException
	{
		return getObjectInfo(id, null, false);
	}

	public ObjectInfo getObjectInfo(Identity id, Map __ctx)
		throws ObjectNotRegisteredException
	{
		return getObjectInfo(id, __ctx, true);
	}

	private ObjectInfo getObjectInfo(Identity id, Map __ctx, boolean __explicitCtx)
		throws ObjectNotRegisteredException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getObjectInfo");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getObjectInfo(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getObjectInfo(Identity id)
	{
		return begin_getObjectInfo(id, null, false, null);
	}

	public AsyncResult begin_getObjectInfo(Identity id, Map __ctx)
	{
		return begin_getObjectInfo(id, __ctx, true, null);
	}

	public AsyncResult begin_getObjectInfo(Identity id, Callback __cb)
	{
		return begin_getObjectInfo(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getObjectInfo(Identity id, Map __ctx, Callback __cb)
	{
		return begin_getObjectInfo(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getObjectInfo(Identity id, Callback_Admin_getObjectInfo __cb)
	{
		return begin_getObjectInfo(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getObjectInfo(Identity id, Map __ctx, Callback_Admin_getObjectInfo __cb)
	{
		return begin_getObjectInfo(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getObjectInfo(Identity id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getObjectInfo");
		OutgoingAsync __result = new OutgoingAsync(this, "getObjectInfo", __cb);
		try
		{
			__result.__prepare("getObjectInfo", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ObjectInfo end_getObjectInfo(AsyncResult __result)
		throws ObjectNotRegisteredException
	{
		AsyncResult.__check(__result, this, "getObjectInfo");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ObjectNotRegisteredException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectInfo __ret = new ObjectInfo();
		__ret.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectInfo[] getObjectInfosByType(String type)
	{
		return getObjectInfosByType(type, null, false);
	}

	public ObjectInfo[] getObjectInfosByType(String type, Map __ctx)
	{
		return getObjectInfosByType(type, __ctx, true);
	}

	private ObjectInfo[] getObjectInfosByType(String type, Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getObjectInfosByType");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getObjectInfosByType(type, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getObjectInfosByType(String type)
	{
		return begin_getObjectInfosByType(type, null, false, null);
	}

	public AsyncResult begin_getObjectInfosByType(String type, Map __ctx)
	{
		return begin_getObjectInfosByType(type, __ctx, true, null);
	}

	public AsyncResult begin_getObjectInfosByType(String type, Callback __cb)
	{
		return begin_getObjectInfosByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getObjectInfosByType(String type, Map __ctx, Callback __cb)
	{
		return begin_getObjectInfosByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getObjectInfosByType(String type, Callback_Admin_getObjectInfosByType __cb)
	{
		return begin_getObjectInfosByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getObjectInfosByType(String type, Map __ctx, Callback_Admin_getObjectInfosByType __cb)
	{
		return begin_getObjectInfosByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getObjectInfosByType(String type, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getObjectInfosByType");
		OutgoingAsync __result = new OutgoingAsync(this, "getObjectInfosByType", __cb);
		try
		{
			__result.__prepare("getObjectInfosByType", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ObjectInfo[] end_getObjectInfosByType(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getObjectInfosByType");
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
		ObjectInfo __ret[] = ObjectInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public RegistryInfo getRegistryInfo(String name)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		return getRegistryInfo(name, null, false);
	}

	public RegistryInfo getRegistryInfo(String name, Map __ctx)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		return getRegistryInfo(name, __ctx, true);
	}

	private RegistryInfo getRegistryInfo(String name, Map __ctx, boolean __explicitCtx)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getRegistryInfo");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getRegistryInfo(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getRegistryInfo(String name)
	{
		return begin_getRegistryInfo(name, null, false, null);
	}

	public AsyncResult begin_getRegistryInfo(String name, Map __ctx)
	{
		return begin_getRegistryInfo(name, __ctx, true, null);
	}

	public AsyncResult begin_getRegistryInfo(String name, Callback __cb)
	{
		return begin_getRegistryInfo(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getRegistryInfo(String name, Map __ctx, Callback __cb)
	{
		return begin_getRegistryInfo(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getRegistryInfo(String name, Callback_Admin_getRegistryInfo __cb)
	{
		return begin_getRegistryInfo(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getRegistryInfo(String name, Map __ctx, Callback_Admin_getRegistryInfo __cb)
	{
		return begin_getRegistryInfo(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getRegistryInfo(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getRegistryInfo");
		OutgoingAsync __result = new OutgoingAsync(this, "getRegistryInfo", __cb);
		try
		{
			__result.__prepare("getRegistryInfo", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public RegistryInfo end_getRegistryInfo(AsyncResult __result)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		AsyncResult.__check(__result, this, "getRegistryInfo");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (RegistryNotExistException __ex)
			{
				throw __ex;
			}
			catch (RegistryUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		RegistryInfo __ret = new RegistryInfo();
		__ret.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectPrx getServerAdmin(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerAdmin(id, null, false);
	}

	public ObjectPrx getServerAdmin(String id, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerAdmin(id, __ctx, true);
	}

	private ObjectPrx getServerAdmin(String id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getServerAdmin");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getServerAdmin(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getServerAdmin(String id)
	{
		return begin_getServerAdmin(id, null, false, null);
	}

	public AsyncResult begin_getServerAdmin(String id, Map __ctx)
	{
		return begin_getServerAdmin(id, __ctx, true, null);
	}

	public AsyncResult begin_getServerAdmin(String id, Callback __cb)
	{
		return begin_getServerAdmin(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerAdmin(String id, Map __ctx, Callback __cb)
	{
		return begin_getServerAdmin(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerAdmin(String id, Callback_Admin_getServerAdmin __cb)
	{
		return begin_getServerAdmin(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerAdmin(String id, Map __ctx, Callback_Admin_getServerAdmin __cb)
	{
		return begin_getServerAdmin(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getServerAdmin(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getServerAdmin");
		OutgoingAsync __result = new OutgoingAsync(this, "getServerAdmin", __cb);
		try
		{
			__result.__prepare("getServerAdmin", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public ObjectPrx end_getServerAdmin(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "getServerAdmin");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
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

	public String getServerAdminCategory()
	{
		return getServerAdminCategory(null, false);
	}

	public String getServerAdminCategory(Map __ctx)
	{
		return getServerAdminCategory(__ctx, true);
	}

	private String getServerAdminCategory(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getServerAdminCategory");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getServerAdminCategory(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getServerAdminCategory()
	{
		return begin_getServerAdminCategory(null, false, null);
	}

	public AsyncResult begin_getServerAdminCategory(Map __ctx)
	{
		return begin_getServerAdminCategory(__ctx, true, null);
	}

	public AsyncResult begin_getServerAdminCategory(Callback __cb)
	{
		return begin_getServerAdminCategory(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerAdminCategory(Map __ctx, Callback __cb)
	{
		return begin_getServerAdminCategory(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerAdminCategory(Callback_Admin_getServerAdminCategory __cb)
	{
		return begin_getServerAdminCategory(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerAdminCategory(Map __ctx, Callback_Admin_getServerAdminCategory __cb)
	{
		return begin_getServerAdminCategory(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getServerAdminCategory(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getServerAdminCategory");
		OutgoingAsync __result = new OutgoingAsync(this, "getServerAdminCategory", __cb);
		try
		{
			__result.__prepare("getServerAdminCategory", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public String end_getServerAdminCategory(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getServerAdminCategory");
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
		String __ret = __is.readString();
		__is.endReadEncaps();
		return __ret;
	}

	public ServerInfo getServerInfo(String id)
		throws ServerNotExistException
	{
		return getServerInfo(id, null, false);
	}

	public ServerInfo getServerInfo(String id, Map __ctx)
		throws ServerNotExistException
	{
		return getServerInfo(id, __ctx, true);
	}

	private ServerInfo getServerInfo(String id, Map __ctx, boolean __explicitCtx)
		throws ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getServerInfo");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getServerInfo(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getServerInfo(String id)
	{
		return begin_getServerInfo(id, null, false, null);
	}

	public AsyncResult begin_getServerInfo(String id, Map __ctx)
	{
		return begin_getServerInfo(id, __ctx, true, null);
	}

	public AsyncResult begin_getServerInfo(String id, Callback __cb)
	{
		return begin_getServerInfo(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerInfo(String id, Map __ctx, Callback __cb)
	{
		return begin_getServerInfo(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerInfo(String id, Callback_Admin_getServerInfo __cb)
	{
		return begin_getServerInfo(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerInfo(String id, Map __ctx, Callback_Admin_getServerInfo __cb)
	{
		return begin_getServerInfo(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getServerInfo(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getServerInfo");
		OutgoingAsync __result = new OutgoingAsync(this, "getServerInfo", __cb);
		try
		{
			__result.__prepare("getServerInfo", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ServerInfo end_getServerInfo(AsyncResult __result)
		throws ServerNotExistException
	{
		AsyncResult.__check(__result, this, "getServerInfo");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ServerInfo __ret = new ServerInfo();
		__ret.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		return __ret;
	}

	public int getServerPid(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerPid(id, null, false);
	}

	public int getServerPid(String id, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerPid(id, __ctx, true);
	}

	private int getServerPid(String id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getServerPid");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getServerPid(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getServerPid(String id)
	{
		return begin_getServerPid(id, null, false, null);
	}

	public AsyncResult begin_getServerPid(String id, Map __ctx)
	{
		return begin_getServerPid(id, __ctx, true, null);
	}

	public AsyncResult begin_getServerPid(String id, Callback __cb)
	{
		return begin_getServerPid(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerPid(String id, Map __ctx, Callback __cb)
	{
		return begin_getServerPid(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerPid(String id, Callback_Admin_getServerPid __cb)
	{
		return begin_getServerPid(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerPid(String id, Map __ctx, Callback_Admin_getServerPid __cb)
	{
		return begin_getServerPid(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getServerPid(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getServerPid");
		OutgoingAsync __result = new OutgoingAsync(this, "getServerPid", __cb);
		try
		{
			__result.__prepare("getServerPid", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public int end_getServerPid(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "getServerPid");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public ServerState getServerState(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerState(id, null, false);
	}

	public ServerState getServerState(String id, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerState(id, __ctx, true);
	}

	private ServerState getServerState(String id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("getServerState");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.getServerState(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getServerState(String id)
	{
		return begin_getServerState(id, null, false, null);
	}

	public AsyncResult begin_getServerState(String id, Map __ctx)
	{
		return begin_getServerState(id, __ctx, true, null);
	}

	public AsyncResult begin_getServerState(String id, Callback __cb)
	{
		return begin_getServerState(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerState(String id, Map __ctx, Callback __cb)
	{
		return begin_getServerState(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerState(String id, Callback_Admin_getServerState __cb)
	{
		return begin_getServerState(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getServerState(String id, Map __ctx, Callback_Admin_getServerState __cb)
	{
		return begin_getServerState(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getServerState(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getServerState");
		OutgoingAsync __result = new OutgoingAsync(this, "getServerState", __cb);
		try
		{
			__result.__prepare("getServerState", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public ServerState end_getServerState(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "getServerState");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ServerState __ret = ServerState.__read(__is);
		__is.endReadEncaps();
		return __ret;
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
		_AdminDel __del;
		__checkTwowayOnly("getSliceChecksums");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
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

	public AsyncResult begin_getSliceChecksums(Callback_Admin_getSliceChecksums __cb)
	{
		return begin_getSliceChecksums(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getSliceChecksums(Map __ctx, Callback_Admin_getSliceChecksums __cb)
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

	public void instantiateServer(String application, String node, ServerInstanceDescriptor desc)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		instantiateServer(application, node, desc, null, false);
	}

	public void instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		instantiateServer(application, node, desc, __ctx, true);
	}

	private void instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("instantiateServer");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.instantiateServer(application, node, desc, __ctx);
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

	public AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc)
	{
		return begin_instantiateServer(application, node, desc, null, false, null);
	}

	public AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx)
	{
		return begin_instantiateServer(application, node, desc, __ctx, true, null);
	}

	public AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc, Callback __cb)
	{
		return begin_instantiateServer(application, node, desc, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx, Callback __cb)
	{
		return begin_instantiateServer(application, node, desc, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc, Callback_Admin_instantiateServer __cb)
	{
		return begin_instantiateServer(application, node, desc, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx, Callback_Admin_instantiateServer __cb)
	{
		return begin_instantiateServer(application, node, desc, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("instantiateServer");
		OutgoingAsync __result = new OutgoingAsync(this, "instantiateServer", __cb);
		try
		{
			__result.__prepare("instantiateServer", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(application);
			__os.writeString(node);
			desc.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_instantiateServer(AsyncResult __result)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		AsyncResult.__check(__result, this, "instantiateServer");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
			{
				throw __ex;
			}
			catch (ApplicationNotExistException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
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

	public boolean isServerEnabled(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return isServerEnabled(id, null, false);
	}

	public boolean isServerEnabled(String id, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return isServerEnabled(id, __ctx, true);
	}

	private boolean isServerEnabled(String id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("isServerEnabled");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.isServerEnabled(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_isServerEnabled(String id)
	{
		return begin_isServerEnabled(id, null, false, null);
	}

	public AsyncResult begin_isServerEnabled(String id, Map __ctx)
	{
		return begin_isServerEnabled(id, __ctx, true, null);
	}

	public AsyncResult begin_isServerEnabled(String id, Callback __cb)
	{
		return begin_isServerEnabled(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_isServerEnabled(String id, Map __ctx, Callback __cb)
	{
		return begin_isServerEnabled(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_isServerEnabled(String id, Callback_Admin_isServerEnabled __cb)
	{
		return begin_isServerEnabled(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_isServerEnabled(String id, Map __ctx, Callback_Admin_isServerEnabled __cb)
	{
		return begin_isServerEnabled(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_isServerEnabled(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("isServerEnabled");
		OutgoingAsync __result = new OutgoingAsync(this, "isServerEnabled", __cb);
		try
		{
			__result.__prepare("isServerEnabled", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public boolean end_isServerEnabled(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "isServerEnabled");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
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

	public void patchApplication(String name, boolean shutdown)
		throws ApplicationNotExistException, PatchException
	{
		patchApplication(name, shutdown, null, false);
	}

	public void patchApplication(String name, boolean shutdown, Map __ctx)
		throws ApplicationNotExistException, PatchException
	{
		patchApplication(name, shutdown, __ctx, true);
	}

	private void patchApplication(String name, boolean shutdown, Map __ctx, boolean __explicitCtx)
		throws ApplicationNotExistException, PatchException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("patchApplication");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.patchApplication(name, shutdown, __ctx);
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

	public AsyncResult begin_patchApplication(String name, boolean shutdown)
	{
		return begin_patchApplication(name, shutdown, null, false, null);
	}

	public AsyncResult begin_patchApplication(String name, boolean shutdown, Map __ctx)
	{
		return begin_patchApplication(name, shutdown, __ctx, true, null);
	}

	public AsyncResult begin_patchApplication(String name, boolean shutdown, Callback __cb)
	{
		return begin_patchApplication(name, shutdown, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_patchApplication(String name, boolean shutdown, Map __ctx, Callback __cb)
	{
		return begin_patchApplication(name, shutdown, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_patchApplication(String name, boolean shutdown, Callback_Admin_patchApplication __cb)
	{
		return begin_patchApplication(name, shutdown, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_patchApplication(String name, boolean shutdown, Map __ctx, Callback_Admin_patchApplication __cb)
	{
		return begin_patchApplication(name, shutdown, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_patchApplication(String name, boolean shutdown, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("patchApplication");
		OutgoingAsync __result = new OutgoingAsync(this, "patchApplication", __cb);
		try
		{
			__result.__prepare("patchApplication", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.writeBool(shutdown);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_patchApplication(AsyncResult __result)
		throws ApplicationNotExistException, PatchException
	{
		AsyncResult.__check(__result, this, "patchApplication");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ApplicationNotExistException __ex)
			{
				throw __ex;
			}
			catch (PatchException __ex)
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

	public boolean patchApplication_async(AMI_Admin_patchApplication __cb, String name, boolean shutdown)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("patchApplication");
			__r = begin_patchApplication(name, shutdown, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "patchApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean patchApplication_async(AMI_Admin_patchApplication __cb, String name, boolean shutdown, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("patchApplication");
			__r = begin_patchApplication(name, shutdown, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "patchApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void patchServer(String id, boolean shutdown)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		patchServer(id, shutdown, null, false);
	}

	public void patchServer(String id, boolean shutdown, Map __ctx)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		patchServer(id, shutdown, __ctx, true);
	}

	private void patchServer(String id, boolean shutdown, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("patchServer");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.patchServer(id, shutdown, __ctx);
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

	public AsyncResult begin_patchServer(String id, boolean shutdown)
	{
		return begin_patchServer(id, shutdown, null, false, null);
	}

	public AsyncResult begin_patchServer(String id, boolean shutdown, Map __ctx)
	{
		return begin_patchServer(id, shutdown, __ctx, true, null);
	}

	public AsyncResult begin_patchServer(String id, boolean shutdown, Callback __cb)
	{
		return begin_patchServer(id, shutdown, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_patchServer(String id, boolean shutdown, Map __ctx, Callback __cb)
	{
		return begin_patchServer(id, shutdown, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_patchServer(String id, boolean shutdown, Callback_Admin_patchServer __cb)
	{
		return begin_patchServer(id, shutdown, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_patchServer(String id, boolean shutdown, Map __ctx, Callback_Admin_patchServer __cb)
	{
		return begin_patchServer(id, shutdown, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_patchServer(String id, boolean shutdown, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("patchServer");
		OutgoingAsync __result = new OutgoingAsync(this, "patchServer", __cb);
		try
		{
			__result.__prepare("patchServer", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeBool(shutdown);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_patchServer(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "patchServer");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (PatchException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
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

	public boolean patchServer_async(AMI_Admin_patchServer __cb, String id, boolean shutdown)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("patchServer");
			__r = begin_patchServer(id, shutdown, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "patchServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean patchServer_async(AMI_Admin_patchServer __cb, String id, boolean shutdown, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("patchServer");
			__r = begin_patchServer(id, shutdown, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "patchServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean pingNode(String name)
		throws NodeNotExistException
	{
		return pingNode(name, null, false);
	}

	public boolean pingNode(String name, Map __ctx)
		throws NodeNotExistException
	{
		return pingNode(name, __ctx, true);
	}

	private boolean pingNode(String name, Map __ctx, boolean __explicitCtx)
		throws NodeNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("pingNode");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.pingNode(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_pingNode(String name)
	{
		return begin_pingNode(name, null, false, null);
	}

	public AsyncResult begin_pingNode(String name, Map __ctx)
	{
		return begin_pingNode(name, __ctx, true, null);
	}

	public AsyncResult begin_pingNode(String name, Callback __cb)
	{
		return begin_pingNode(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_pingNode(String name, Map __ctx, Callback __cb)
	{
		return begin_pingNode(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_pingNode(String name, Callback_Admin_pingNode __cb)
	{
		return begin_pingNode(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_pingNode(String name, Map __ctx, Callback_Admin_pingNode __cb)
	{
		return begin_pingNode(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_pingNode(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("pingNode");
		OutgoingAsync __result = new OutgoingAsync(this, "pingNode", __cb);
		try
		{
			__result.__prepare("pingNode", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public boolean end_pingNode(AsyncResult __result)
		throws NodeNotExistException
	{
		AsyncResult.__check(__result, this, "pingNode");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
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

	public boolean pingRegistry(String name)
		throws RegistryNotExistException
	{
		return pingRegistry(name, null, false);
	}

	public boolean pingRegistry(String name, Map __ctx)
		throws RegistryNotExistException
	{
		return pingRegistry(name, __ctx, true);
	}

	private boolean pingRegistry(String name, Map __ctx, boolean __explicitCtx)
		throws RegistryNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminDel __del;
		__checkTwowayOnly("pingRegistry");
		__delBase = __getDelegate(false);
		__del = (_AdminDel)__delBase;
		return __del.pingRegistry(name, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_pingRegistry(String name)
	{
		return begin_pingRegistry(name, null, false, null);
	}

	public AsyncResult begin_pingRegistry(String name, Map __ctx)
	{
		return begin_pingRegistry(name, __ctx, true, null);
	}

	public AsyncResult begin_pingRegistry(String name, Callback __cb)
	{
		return begin_pingRegistry(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_pingRegistry(String name, Map __ctx, Callback __cb)
	{
		return begin_pingRegistry(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_pingRegistry(String name, Callback_Admin_pingRegistry __cb)
	{
		return begin_pingRegistry(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_pingRegistry(String name, Map __ctx, Callback_Admin_pingRegistry __cb)
	{
		return begin_pingRegistry(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_pingRegistry(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("pingRegistry");
		OutgoingAsync __result = new OutgoingAsync(this, "pingRegistry", __cb);
		try
		{
			__result.__prepare("pingRegistry", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public boolean end_pingRegistry(AsyncResult __result)
		throws RegistryNotExistException
	{
		AsyncResult.__check(__result, this, "pingRegistry");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (RegistryNotExistException __ex)
			{
				throw __ex;
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

	public void removeAdapter(String id)
		throws AdapterNotExistException, DeploymentException
	{
		removeAdapter(id, null, false);
	}

	public void removeAdapter(String id, Map __ctx)
		throws AdapterNotExistException, DeploymentException
	{
		removeAdapter(id, __ctx, true);
	}

	private void removeAdapter(String id, Map __ctx, boolean __explicitCtx)
		throws AdapterNotExistException, DeploymentException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("removeAdapter");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.removeAdapter(id, __ctx);
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

	public AsyncResult begin_removeAdapter(String id)
	{
		return begin_removeAdapter(id, null, false, null);
	}

	public AsyncResult begin_removeAdapter(String id, Map __ctx)
	{
		return begin_removeAdapter(id, __ctx, true, null);
	}

	public AsyncResult begin_removeAdapter(String id, Callback __cb)
	{
		return begin_removeAdapter(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeAdapter(String id, Map __ctx, Callback __cb)
	{
		return begin_removeAdapter(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeAdapter(String id, Callback_Admin_removeAdapter __cb)
	{
		return begin_removeAdapter(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeAdapter(String id, Map __ctx, Callback_Admin_removeAdapter __cb)
	{
		return begin_removeAdapter(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_removeAdapter(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("removeAdapter");
		OutgoingAsync __result = new OutgoingAsync(this, "removeAdapter", __cb);
		try
		{
			__result.__prepare("removeAdapter", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_removeAdapter(AsyncResult __result)
		throws AdapterNotExistException, DeploymentException
	{
		AsyncResult.__check(__result, this, "removeAdapter");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AdapterNotExistException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
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

	public boolean removeAdapter_async(AMI_Admin_removeAdapter __cb, String id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("removeAdapter");
			__r = begin_removeAdapter(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "removeAdapter", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean removeAdapter_async(AMI_Admin_removeAdapter __cb, String id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("removeAdapter");
			__r = begin_removeAdapter(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "removeAdapter", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void removeApplication(String name)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		removeApplication(name, null, false);
	}

	public void removeApplication(String name, Map __ctx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		removeApplication(name, __ctx, true);
	}

	private void removeApplication(String name, Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("removeApplication");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.removeApplication(name, __ctx);
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

	public AsyncResult begin_removeApplication(String name)
	{
		return begin_removeApplication(name, null, false, null);
	}

	public AsyncResult begin_removeApplication(String name, Map __ctx)
	{
		return begin_removeApplication(name, __ctx, true, null);
	}

	public AsyncResult begin_removeApplication(String name, Callback __cb)
	{
		return begin_removeApplication(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeApplication(String name, Map __ctx, Callback __cb)
	{
		return begin_removeApplication(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeApplication(String name, Callback_Admin_removeApplication __cb)
	{
		return begin_removeApplication(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeApplication(String name, Map __ctx, Callback_Admin_removeApplication __cb)
	{
		return begin_removeApplication(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_removeApplication(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("removeApplication");
		OutgoingAsync __result = new OutgoingAsync(this, "removeApplication", __cb);
		try
		{
			__result.__prepare("removeApplication", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_removeApplication(AsyncResult __result)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		AsyncResult.__check(__result, this, "removeApplication");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
			{
				throw __ex;
			}
			catch (ApplicationNotExistException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
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

	public boolean removeApplication_async(AMI_Admin_removeApplication __cb, String name)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("removeApplication");
			__r = begin_removeApplication(name, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "removeApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean removeApplication_async(AMI_Admin_removeApplication __cb, String name, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("removeApplication");
			__r = begin_removeApplication(name, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "removeApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void removeObject(Identity id)
		throws DeploymentException, ObjectNotRegisteredException
	{
		removeObject(id, null, false);
	}

	public void removeObject(Identity id, Map __ctx)
		throws DeploymentException, ObjectNotRegisteredException
	{
		removeObject(id, __ctx, true);
	}

	private void removeObject(Identity id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, ObjectNotRegisteredException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("removeObject");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.removeObject(id, __ctx);
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

	public AsyncResult begin_removeObject(Identity id)
	{
		return begin_removeObject(id, null, false, null);
	}

	public AsyncResult begin_removeObject(Identity id, Map __ctx)
	{
		return begin_removeObject(id, __ctx, true, null);
	}

	public AsyncResult begin_removeObject(Identity id, Callback __cb)
	{
		return begin_removeObject(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeObject(Identity id, Map __ctx, Callback __cb)
	{
		return begin_removeObject(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeObject(Identity id, Callback_Admin_removeObject __cb)
	{
		return begin_removeObject(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_removeObject(Identity id, Map __ctx, Callback_Admin_removeObject __cb)
	{
		return begin_removeObject(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_removeObject(Identity id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("removeObject");
		OutgoingAsync __result = new OutgoingAsync(this, "removeObject", __cb);
		try
		{
			__result.__prepare("removeObject", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_removeObject(AsyncResult __result)
		throws DeploymentException, ObjectNotRegisteredException
	{
		AsyncResult.__check(__result, this, "removeObject");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (ObjectNotRegisteredException __ex)
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

	public boolean removeObject_async(AMI_Admin_removeObject __cb, Identity id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("removeObject");
			__r = begin_removeObject(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "removeObject", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean removeObject_async(AMI_Admin_removeObject __cb, Identity id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("removeObject");
			__r = begin_removeObject(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "removeObject", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void sendSignal(String id, String signal)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		sendSignal(id, signal, null, false);
	}

	public void sendSignal(String id, String signal, Map __ctx)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		sendSignal(id, signal, __ctx, true);
	}

	private void sendSignal(String id, String signal, Map __ctx, boolean __explicitCtx)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("sendSignal");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.sendSignal(id, signal, __ctx);
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

	public AsyncResult begin_sendSignal(String id, String signal)
	{
		return begin_sendSignal(id, signal, null, false, null);
	}

	public AsyncResult begin_sendSignal(String id, String signal, Map __ctx)
	{
		return begin_sendSignal(id, signal, __ctx, true, null);
	}

	public AsyncResult begin_sendSignal(String id, String signal, Callback __cb)
	{
		return begin_sendSignal(id, signal, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_sendSignal(String id, String signal, Map __ctx, Callback __cb)
	{
		return begin_sendSignal(id, signal, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_sendSignal(String id, String signal, Callback_Admin_sendSignal __cb)
	{
		return begin_sendSignal(id, signal, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_sendSignal(String id, String signal, Map __ctx, Callback_Admin_sendSignal __cb)
	{
		return begin_sendSignal(id, signal, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_sendSignal(String id, String signal, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("sendSignal");
		OutgoingAsync __result = new OutgoingAsync(this, "sendSignal", __cb);
		try
		{
			__result.__prepare("sendSignal", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeString(signal);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_sendSignal(AsyncResult __result)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "sendSignal");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (BadSignalException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
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

	public boolean sendSignal_async(AMI_Admin_sendSignal __cb, String id, String signal)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("sendSignal");
			__r = begin_sendSignal(id, signal, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "sendSignal", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean sendSignal_async(AMI_Admin_sendSignal __cb, String id, String signal, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("sendSignal");
			__r = begin_sendSignal(id, signal, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "sendSignal", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
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
				_AdminDel __del = (_AdminDel)__delBase;
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

	public AsyncResult begin_shutdown(Callback_Admin_shutdown __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback_Admin_shutdown __cb)
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

	public void shutdownNode(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		shutdownNode(name, null, false);
	}

	public void shutdownNode(String name, Map __ctx)
		throws NodeNotExistException, NodeUnreachableException
	{
		shutdownNode(name, __ctx, true);
	}

	private void shutdownNode(String name, Map __ctx, boolean __explicitCtx)
		throws NodeNotExistException, NodeUnreachableException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("shutdownNode");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.shutdownNode(name, __ctx);
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

	public AsyncResult begin_shutdownNode(String name)
	{
		return begin_shutdownNode(name, null, false, null);
	}

	public AsyncResult begin_shutdownNode(String name, Map __ctx)
	{
		return begin_shutdownNode(name, __ctx, true, null);
	}

	public AsyncResult begin_shutdownNode(String name, Callback __cb)
	{
		return begin_shutdownNode(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdownNode(String name, Map __ctx, Callback __cb)
	{
		return begin_shutdownNode(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdownNode(String name, Callback_Admin_shutdownNode __cb)
	{
		return begin_shutdownNode(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdownNode(String name, Map __ctx, Callback_Admin_shutdownNode __cb)
	{
		return begin_shutdownNode(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_shutdownNode(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("shutdownNode");
		OutgoingAsync __result = new OutgoingAsync(this, "shutdownNode", __cb);
		try
		{
			__result.__prepare("shutdownNode", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_shutdownNode(AsyncResult __result)
		throws NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "shutdownNode");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
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

	public boolean shutdownNode_async(AMI_Admin_shutdownNode __cb, String name)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("shutdownNode");
			__r = begin_shutdownNode(name, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "shutdownNode", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean shutdownNode_async(AMI_Admin_shutdownNode __cb, String name, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("shutdownNode");
			__r = begin_shutdownNode(name, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "shutdownNode", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void shutdownRegistry(String name)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		shutdownRegistry(name, null, false);
	}

	public void shutdownRegistry(String name, Map __ctx)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		shutdownRegistry(name, __ctx, true);
	}

	private void shutdownRegistry(String name, Map __ctx, boolean __explicitCtx)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("shutdownRegistry");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.shutdownRegistry(name, __ctx);
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

	public AsyncResult begin_shutdownRegistry(String name)
	{
		return begin_shutdownRegistry(name, null, false, null);
	}

	public AsyncResult begin_shutdownRegistry(String name, Map __ctx)
	{
		return begin_shutdownRegistry(name, __ctx, true, null);
	}

	public AsyncResult begin_shutdownRegistry(String name, Callback __cb)
	{
		return begin_shutdownRegistry(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdownRegistry(String name, Map __ctx, Callback __cb)
	{
		return begin_shutdownRegistry(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdownRegistry(String name, Callback_Admin_shutdownRegistry __cb)
	{
		return begin_shutdownRegistry(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdownRegistry(String name, Map __ctx, Callback_Admin_shutdownRegistry __cb)
	{
		return begin_shutdownRegistry(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_shutdownRegistry(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("shutdownRegistry");
		OutgoingAsync __result = new OutgoingAsync(this, "shutdownRegistry", __cb);
		try
		{
			__result.__prepare("shutdownRegistry", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public void end_shutdownRegistry(AsyncResult __result)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		AsyncResult.__check(__result, this, "shutdownRegistry");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (RegistryNotExistException __ex)
			{
				throw __ex;
			}
			catch (RegistryUnreachableException __ex)
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

	public boolean shutdownRegistry_async(AMI_Admin_shutdownRegistry __cb, String name)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("shutdownRegistry");
			__r = begin_shutdownRegistry(name, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "shutdownRegistry", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean shutdownRegistry_async(AMI_Admin_shutdownRegistry __cb, String name, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("shutdownRegistry");
			__r = begin_shutdownRegistry(name, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "shutdownRegistry", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void startServer(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		startServer(id, null, false);
	}

	public void startServer(String id, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		startServer(id, __ctx, true);
	}

	private void startServer(String id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("startServer");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.startServer(id, __ctx);
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

	public AsyncResult begin_startServer(String id)
	{
		return begin_startServer(id, null, false, null);
	}

	public AsyncResult begin_startServer(String id, Map __ctx)
	{
		return begin_startServer(id, __ctx, true, null);
	}

	public AsyncResult begin_startServer(String id, Callback __cb)
	{
		return begin_startServer(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startServer(String id, Map __ctx, Callback __cb)
	{
		return begin_startServer(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startServer(String id, Callback_Admin_startServer __cb)
	{
		return begin_startServer(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startServer(String id, Map __ctx, Callback_Admin_startServer __cb)
	{
		return begin_startServer(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_startServer(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("startServer");
		OutgoingAsync __result = new OutgoingAsync(this, "startServer", __cb);
		try
		{
			__result.__prepare("startServer", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_startServer(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		AsyncResult.__check(__result, this, "startServer");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (ServerStartException __ex)
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

	public boolean startServer_async(AMI_Admin_startServer __cb, String id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("startServer");
			__r = begin_startServer(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "startServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean startServer_async(AMI_Admin_startServer __cb, String id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("startServer");
			__r = begin_startServer(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "startServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void stopServer(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		stopServer(id, null, false);
	}

	public void stopServer(String id, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		stopServer(id, __ctx, true);
	}

	private void stopServer(String id, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("stopServer");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.stopServer(id, __ctx);
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

	public AsyncResult begin_stopServer(String id)
	{
		return begin_stopServer(id, null, false, null);
	}

	public AsyncResult begin_stopServer(String id, Map __ctx)
	{
		return begin_stopServer(id, __ctx, true, null);
	}

	public AsyncResult begin_stopServer(String id, Callback __cb)
	{
		return begin_stopServer(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_stopServer(String id, Map __ctx, Callback __cb)
	{
		return begin_stopServer(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_stopServer(String id, Callback_Admin_stopServer __cb)
	{
		return begin_stopServer(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_stopServer(String id, Map __ctx, Callback_Admin_stopServer __cb)
	{
		return begin_stopServer(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_stopServer(String id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("stopServer");
		OutgoingAsync __result = new OutgoingAsync(this, "stopServer", __cb);
		try
		{
			__result.__prepare("stopServer", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_stopServer(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		AsyncResult.__check(__result, this, "stopServer");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (ServerStopException __ex)
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

	public boolean stopServer_async(AMI_Admin_stopServer __cb, String id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("stopServer");
			__r = begin_stopServer(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "stopServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean stopServer_async(AMI_Admin_stopServer __cb, String id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("stopServer");
			__r = begin_stopServer(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "stopServer", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void syncApplication(ApplicationDescriptor descriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		syncApplication(descriptor, null, false);
	}

	public void syncApplication(ApplicationDescriptor descriptor, Map __ctx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		syncApplication(descriptor, __ctx, true);
	}

	private void syncApplication(ApplicationDescriptor descriptor, Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("syncApplication");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.syncApplication(descriptor, __ctx);
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

	public AsyncResult begin_syncApplication(ApplicationDescriptor descriptor)
	{
		return begin_syncApplication(descriptor, null, false, null);
	}

	public AsyncResult begin_syncApplication(ApplicationDescriptor descriptor, Map __ctx)
	{
		return begin_syncApplication(descriptor, __ctx, true, null);
	}

	public AsyncResult begin_syncApplication(ApplicationDescriptor descriptor, Callback __cb)
	{
		return begin_syncApplication(descriptor, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_syncApplication(ApplicationDescriptor descriptor, Map __ctx, Callback __cb)
	{
		return begin_syncApplication(descriptor, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_syncApplication(ApplicationDescriptor descriptor, Callback_Admin_syncApplication __cb)
	{
		return begin_syncApplication(descriptor, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_syncApplication(ApplicationDescriptor descriptor, Map __ctx, Callback_Admin_syncApplication __cb)
	{
		return begin_syncApplication(descriptor, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_syncApplication(ApplicationDescriptor descriptor, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("syncApplication");
		OutgoingAsync __result = new OutgoingAsync(this, "syncApplication", __cb);
		try
		{
			__result.__prepare("syncApplication", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			descriptor.__write(__os);
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

	public void end_syncApplication(AsyncResult __result)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		AsyncResult.__check(__result, this, "syncApplication");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
			{
				throw __ex;
			}
			catch (ApplicationNotExistException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
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

	public boolean syncApplication_async(AMI_Admin_syncApplication __cb, ApplicationDescriptor descriptor)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("syncApplication");
			__r = begin_syncApplication(descriptor, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "syncApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean syncApplication_async(AMI_Admin_syncApplication __cb, ApplicationDescriptor descriptor, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("syncApplication");
			__r = begin_syncApplication(descriptor, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "syncApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void updateApplication(ApplicationUpdateDescriptor descriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		updateApplication(descriptor, null, false);
	}

	public void updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		updateApplication(descriptor, __ctx, true);
	}

	private void updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("updateApplication");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.updateApplication(descriptor, __ctx);
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

	public AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor)
	{
		return begin_updateApplication(descriptor, null, false, null);
	}

	public AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx)
	{
		return begin_updateApplication(descriptor, __ctx, true, null);
	}

	public AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor, Callback __cb)
	{
		return begin_updateApplication(descriptor, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx, Callback __cb)
	{
		return begin_updateApplication(descriptor, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor, Callback_Admin_updateApplication __cb)
	{
		return begin_updateApplication(descriptor, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx, Callback_Admin_updateApplication __cb)
	{
		return begin_updateApplication(descriptor, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("updateApplication");
		OutgoingAsync __result = new OutgoingAsync(this, "updateApplication", __cb);
		try
		{
			__result.__prepare("updateApplication", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			descriptor.__write(__os);
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

	public void end_updateApplication(AsyncResult __result)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		AsyncResult.__check(__result, this, "updateApplication");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
			{
				throw __ex;
			}
			catch (ApplicationNotExistException __ex)
			{
				throw __ex;
			}
			catch (DeploymentException __ex)
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

	public boolean updateApplication_async(AMI_Admin_updateApplication __cb, ApplicationUpdateDescriptor descriptor)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("updateApplication");
			__r = begin_updateApplication(descriptor, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "updateApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean updateApplication_async(AMI_Admin_updateApplication __cb, ApplicationUpdateDescriptor descriptor, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("updateApplication");
			__r = begin_updateApplication(descriptor, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "updateApplication", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void updateObject(ObjectPrx obj)
		throws DeploymentException, ObjectNotRegisteredException
	{
		updateObject(obj, null, false);
	}

	public void updateObject(ObjectPrx obj, Map __ctx)
		throws DeploymentException, ObjectNotRegisteredException
	{
		updateObject(obj, __ctx, true);
	}

	private void updateObject(ObjectPrx obj, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, ObjectNotRegisteredException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("updateObject");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.updateObject(obj, __ctx);
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

	public AsyncResult begin_updateObject(ObjectPrx obj)
	{
		return begin_updateObject(obj, null, false, null);
	}

	public AsyncResult begin_updateObject(ObjectPrx obj, Map __ctx)
	{
		return begin_updateObject(obj, __ctx, true, null);
	}

	public AsyncResult begin_updateObject(ObjectPrx obj, Callback __cb)
	{
		return begin_updateObject(obj, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateObject(ObjectPrx obj, Map __ctx, Callback __cb)
	{
		return begin_updateObject(obj, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateObject(ObjectPrx obj, Callback_Admin_updateObject __cb)
	{
		return begin_updateObject(obj, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateObject(ObjectPrx obj, Map __ctx, Callback_Admin_updateObject __cb)
	{
		return begin_updateObject(obj, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_updateObject(ObjectPrx obj, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("updateObject");
		OutgoingAsync __result = new OutgoingAsync(this, "updateObject", __cb);
		try
		{
			__result.__prepare("updateObject", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeProxy(obj);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_updateObject(AsyncResult __result)
		throws DeploymentException, ObjectNotRegisteredException
	{
		AsyncResult.__check(__result, this, "updateObject");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (ObjectNotRegisteredException __ex)
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

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public void writeMessage(String id, String message, int fd)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		writeMessage(id, message, fd, null, false);
	}

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public void writeMessage(String id, String message, int fd, Map __ctx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		writeMessage(id, message, fd, __ctx, true);
	}

	private void writeMessage(String id, String message, int fd, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("writeMessage");
				__delBase = __getDelegate(false);
				_AdminDel __del = (_AdminDel)__delBase;
				__del.writeMessage(id, message, fd, __ctx);
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

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public AsyncResult begin_writeMessage(String id, String message, int fd)
	{
		return begin_writeMessage(id, message, fd, null, false, null);
	}

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public AsyncResult begin_writeMessage(String id, String message, int fd, Map __ctx)
	{
		return begin_writeMessage(id, message, fd, __ctx, true, null);
	}

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public AsyncResult begin_writeMessage(String id, String message, int fd, Callback __cb)
	{
		return begin_writeMessage(id, message, fd, null, false, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public AsyncResult begin_writeMessage(String id, String message, int fd, Map __ctx, Callback __cb)
	{
		return begin_writeMessage(id, message, fd, __ctx, true, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public AsyncResult begin_writeMessage(String id, String message, int fd, Callback_Admin_writeMessage __cb)
	{
		return begin_writeMessage(id, message, fd, null, false, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public AsyncResult begin_writeMessage(String id, String message, int fd, Map __ctx, Callback_Admin_writeMessage __cb)
	{
		return begin_writeMessage(id, message, fd, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_writeMessage(String id, String message, int fd, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("writeMessage");
		OutgoingAsync __result = new OutgoingAsync(this, "writeMessage", __cb);
		try
		{
			__result.__prepare("writeMessage", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeString(message);
			__os.writeInt(fd);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_writeMessage(AsyncResult __result)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "writeMessage");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
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

	/**
	 * @deprecated Method writeMessage_async is deprecated
	 */

	public boolean writeMessage_async(AMI_Admin_writeMessage __cb, String id, String message, int fd)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("writeMessage");
			__r = begin_writeMessage(id, message, fd, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "writeMessage", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	/**
	 * @deprecated Method writeMessage_async is deprecated
	 */

	public boolean writeMessage_async(AMI_Admin_writeMessage __cb, String id, String message, int fd, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("writeMessage");
			__r = begin_writeMessage(id, message, fd, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "writeMessage", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public static AdminPrx checkedCast(ObjectPrx __obj)
	{
		AdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdminPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		AdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdminPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		AdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdminPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		AdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					AdminPrxHelper __h = new AdminPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdminPrx uncheckedCast(ObjectPrx __obj)
	{
		AdminPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				AdminPrxHelper __h = new AdminPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static AdminPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		AdminPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			AdminPrxHelper __h = new AdminPrxHelper();
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
		return new _AdminDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _AdminDelD();
	}

	public static void __write(BasicStream __os, AdminPrx v)
	{
		__os.writeProxy(v);
	}

	public static AdminPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			AdminPrxHelper result = new AdminPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
