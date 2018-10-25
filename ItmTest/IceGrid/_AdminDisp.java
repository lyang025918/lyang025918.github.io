// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ApplicationDescriptor, AccessDeniedException, DeploymentException, ApplicationNotExistException, 
//			ApplicationUpdateDescriptor, ServerInstanceDescriptor, _AMD_Admin_patchApplication, ServerNotExistException, 
//			NodeUnreachableException, _AMD_Admin_startServer, _AMD_Admin_stopServer, _AMD_Admin_patchServer, 
//			BadSignalException, AdapterNotExistException, ObjectExistsException, ObjectNotRegisteredException, 
//			NodeNotExistException, RegistryNotExistException, RegistryUnreachableException, Admin, 
//			PatchException, ServerStartException, ServerStopException, AMD_Admin_patchApplication, 
//			ApplicationInfo, ServerInfo, ServerState, AMD_Admin_startServer, 
//			AMD_Admin_stopServer, AMD_Admin_patchServer, AdapterInfoSeqHelper, ObjectInfo, 
//			ObjectInfoSeqHelper, LoadInfo, NodeInfo, RegistryInfo, 
//			AdapterInfo

public abstract class _AdminDisp extends ObjectImpl
	implements Admin
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::Admin"
	};
	private static final String __all[] = {
		"addApplication", "addObject", "addObjectWithType", "enableServer", "getAdapterInfo", "getAllAdapterIds", "getAllApplicationNames", "getAllNodeNames", "getAllObjectInfos", "getAllRegistryNames", 
		"getAllServerIds", "getApplicationInfo", "getDefaultApplicationDescriptor", "getNodeHostname", "getNodeInfo", "getNodeLoad", "getNodeProcessorSocketCount", "getObjectInfo", "getObjectInfosByType", "getRegistryInfo", 
		"getServerAdmin", "getServerAdminCategory", "getServerInfo", "getServerPid", "getServerState", "getSliceChecksums", "ice_id", "ice_ids", "ice_isA", "ice_ping", 
		"instantiateServer", "isServerEnabled", "patchApplication", "patchServer", "pingNode", "pingRegistry", "removeAdapter", "removeApplication", "removeObject", "sendSignal", 
		"shutdown", "shutdownNode", "shutdownRegistry", "startServer", "stopServer", "syncApplication", "updateApplication", "updateObject", "writeMessage"
	};
	static final boolean $assertionsDisabled = !IceGrid/_AdminDisp.desiredAssertionStatus();

	public _AdminDisp()
	{
	}

	protected void ice_copyStateFrom(Ice.Object __obj)
		throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}

	public boolean ice_isA(String s)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public boolean ice_isA(String s, Current __current)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public String[] ice_ids()
	{
		return __ids;
	}

	public String[] ice_ids(Current __current)
	{
		return __ids;
	}

	public String ice_id()
	{
		return __ids[1];
	}

	public String ice_id(Current __current)
	{
		return __ids[1];
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	public final void addApplication(ApplicationDescriptor descriptor)
		throws AccessDeniedException, DeploymentException
	{
		addApplication(descriptor, null);
	}

	public final void addObject(ObjectPrx obj)
		throws DeploymentException, ObjectExistsException
	{
		addObject(obj, null);
	}

	public final void addObjectWithType(ObjectPrx obj, String type)
		throws DeploymentException, ObjectExistsException
	{
		addObjectWithType(obj, type, null);
	}

	public final void enableServer(String id, boolean enabled)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		enableServer(id, enabled, null);
	}

	public final AdapterInfo[] getAdapterInfo(String id)
		throws AdapterNotExistException
	{
		return getAdapterInfo(id, null);
	}

	public final String[] getAllAdapterIds()
	{
		return getAllAdapterIds(null);
	}

	public final String[] getAllApplicationNames()
	{
		return getAllApplicationNames(null);
	}

	public final String[] getAllNodeNames()
	{
		return getAllNodeNames(null);
	}

	public final ObjectInfo[] getAllObjectInfos(String expr)
	{
		return getAllObjectInfos(expr, null);
	}

	public final String[] getAllRegistryNames()
	{
		return getAllRegistryNames(null);
	}

	public final String[] getAllServerIds()
	{
		return getAllServerIds(null);
	}

	public final ApplicationInfo getApplicationInfo(String name)
		throws ApplicationNotExistException
	{
		return getApplicationInfo(name, null);
	}

	public final ApplicationDescriptor getDefaultApplicationDescriptor()
		throws DeploymentException
	{
		return getDefaultApplicationDescriptor(null);
	}

	public final String getNodeHostname(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeHostname(name, null);
	}

	public final NodeInfo getNodeInfo(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeInfo(name, null);
	}

	public final LoadInfo getNodeLoad(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeLoad(name, null);
	}

	public final int getNodeProcessorSocketCount(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		return getNodeProcessorSocketCount(name, null);
	}

	public final ObjectInfo getObjectInfo(Identity id)
		throws ObjectNotRegisteredException
	{
		return getObjectInfo(id, null);
	}

	public final ObjectInfo[] getObjectInfosByType(String type)
	{
		return getObjectInfosByType(type, null);
	}

	public final RegistryInfo getRegistryInfo(String name)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		return getRegistryInfo(name, null);
	}

	public final ObjectPrx getServerAdmin(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerAdmin(id, null);
	}

	public final String getServerAdminCategory()
	{
		return getServerAdminCategory(null);
	}

	public final ServerInfo getServerInfo(String id)
		throws ServerNotExistException
	{
		return getServerInfo(id, null);
	}

	public final int getServerPid(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerPid(id, null);
	}

	public final ServerState getServerState(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return getServerState(id, null);
	}

	public final Map getSliceChecksums()
	{
		return getSliceChecksums(null);
	}

	public final void instantiateServer(String application, String node, ServerInstanceDescriptor desc)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		instantiateServer(application, node, desc, null);
	}

	public final boolean isServerEnabled(String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		return isServerEnabled(id, null);
	}

	public final void patchApplication_async(AMD_Admin_patchApplication __cb, String name, boolean shutdown)
		throws ApplicationNotExistException, PatchException
	{
		patchApplication_async(__cb, name, shutdown, null);
	}

	public final void patchServer_async(AMD_Admin_patchServer __cb, String id, boolean shutdown)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		patchServer_async(__cb, id, shutdown, null);
	}

	public final boolean pingNode(String name)
		throws NodeNotExistException
	{
		return pingNode(name, null);
	}

	public final boolean pingRegistry(String name)
		throws RegistryNotExistException
	{
		return pingRegistry(name, null);
	}

	public final void removeAdapter(String id)
		throws AdapterNotExistException, DeploymentException
	{
		removeAdapter(id, null);
	}

	public final void removeApplication(String name)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		removeApplication(name, null);
	}

	public final void removeObject(Identity id)
		throws DeploymentException, ObjectNotRegisteredException
	{
		removeObject(id, null);
	}

	public final void sendSignal(String id, String signal)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		sendSignal(id, signal, null);
	}

	public final void shutdown()
	{
		shutdown(null);
	}

	public final void shutdownNode(String name)
		throws NodeNotExistException, NodeUnreachableException
	{
		shutdownNode(name, null);
	}

	public final void shutdownRegistry(String name)
		throws RegistryNotExistException, RegistryUnreachableException
	{
		shutdownRegistry(name, null);
	}

	public final void startServer_async(AMD_Admin_startServer __cb, String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		startServer_async(__cb, id, null);
	}

	public final void stopServer_async(AMD_Admin_stopServer __cb, String id)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		stopServer_async(__cb, id, null);
	}

	public final void syncApplication(ApplicationDescriptor descriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		syncApplication(descriptor, null);
	}

	public final void updateApplication(ApplicationUpdateDescriptor descriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		updateApplication(descriptor, null);
	}

	public final void updateObject(ObjectPrx obj)
		throws DeploymentException, ObjectNotRegisteredException
	{
		updateObject(obj, null);
	}

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public final void writeMessage(String id, String message, int fd)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		writeMessage(id, message, fd, null);
	}

	public static DispatchStatus ___addApplication(Admin __obj, Incoming __inS, Current __current)
	{
		ApplicationDescriptor descriptor;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		descriptor = new ApplicationDescriptor();
		descriptor.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.addApplication(descriptor, __current);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___syncApplication(Admin __obj, Incoming __inS, Current __current)
	{
		ApplicationDescriptor descriptor;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		descriptor = new ApplicationDescriptor();
		descriptor.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.syncApplication(descriptor, __current);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___updateApplication(Admin __obj, Incoming __inS, Current __current)
	{
		ApplicationUpdateDescriptor descriptor;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		descriptor = new ApplicationUpdateDescriptor();
		descriptor.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.updateApplication(descriptor, __current);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___removeApplication(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.removeApplication(name, __current);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___instantiateServer(Admin __obj, Incoming __inS, Current __current)
	{
		String application;
		String node;
		ServerInstanceDescriptor desc;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		application = __is.readString();
		node = __is.readString();
		desc = new ServerInstanceDescriptor();
		desc.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.instantiateServer(application, node, desc, __current);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___patchApplication(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String name = __is.readString();
		boolean shutdown = __is.readBool();
		__is.endReadEncaps();
		AMD_Admin_patchApplication __cb = new _AMD_Admin_patchApplication(__inS);
		try
		{
			__obj.patchApplication_async(__cb, name, shutdown, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___getApplicationInfo(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		ApplicationInfo __ret = __obj.getApplicationInfo(name, __current);
		__ret.__write(__os);
		__os.writePendingObjects();
		return DispatchStatus.DispatchOK;
		ApplicationNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getDefaultApplicationDescriptor(Admin __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		ApplicationDescriptor __ret = __obj.getDefaultApplicationDescriptor(__current);
		__ret.__write(__os);
		__os.writePendingObjects();
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getAllApplicationNames(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret[] = __obj.getAllApplicationNames(__current);
		StringSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getServerInfo(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		ServerInfo __ret = __obj.getServerInfo(id, __current);
		__ret.__write(__os);
		__os.writePendingObjects();
		return DispatchStatus.DispatchOK;
		ServerNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getServerState(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		ServerState __ret = __obj.getServerState(id, __current);
		__ret.__write(__os);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getServerPid(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		int __ret = __obj.getServerPid(id, __current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getServerAdminCategory(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret = __obj.getServerAdminCategory(__current);
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getServerAdmin(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		ObjectPrx __ret = __obj.getServerAdmin(id, __current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___enableServer(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		boolean enabled;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		enabled = __is.readBool();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.enableServer(id, enabled, __current);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___isServerEnabled(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		boolean __ret = __obj.isServerEnabled(id, __current);
		__os.writeBool(__ret);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___startServer(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		__is.endReadEncaps();
		AMD_Admin_startServer __cb = new _AMD_Admin_startServer(__inS);
		try
		{
			__obj.startServer_async(__cb, id, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___stopServer(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		__is.endReadEncaps();
		AMD_Admin_stopServer __cb = new _AMD_Admin_stopServer(__inS);
		try
		{
			__obj.stopServer_async(__cb, id, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___patchServer(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		boolean shutdown = __is.readBool();
		__is.endReadEncaps();
		AMD_Admin_patchServer __cb = new _AMD_Admin_patchServer(__inS);
		try
		{
			__obj.patchServer_async(__cb, id, shutdown, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___sendSignal(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		String signal;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		signal = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.sendSignal(id, signal, __current);
		return DispatchStatus.DispatchOK;
		BadSignalException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	/**
	 * @deprecated Method ___writeMessage is deprecated
	 */

	public static DispatchStatus ___writeMessage(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		String message;
		int fd;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		message = __is.readString();
		fd = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.writeMessage(id, message, fd, __current);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getAllServerIds(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret[] = __obj.getAllServerIds(__current);
		StringSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getAdapterInfo(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		AdapterInfo __ret[] = __obj.getAdapterInfo(id, __current);
		AdapterInfoSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
		AdapterNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___removeAdapter(Admin __obj, Incoming __inS, Current __current)
	{
		String id;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.removeAdapter(id, __current);
		return DispatchStatus.DispatchOK;
		AdapterNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getAllAdapterIds(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret[] = __obj.getAllAdapterIds(__current);
		StringSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___addObject(Admin __obj, Incoming __inS, Current __current)
	{
		ObjectPrx obj;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		obj = __is.readProxy();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.addObject(obj, __current);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___updateObject(Admin __obj, Incoming __inS, Current __current)
	{
		ObjectPrx obj;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		obj = __is.readProxy();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.updateObject(obj, __current);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___addObjectWithType(Admin __obj, Incoming __inS, Current __current)
	{
		ObjectPrx obj;
		String type;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		obj = __is.readProxy();
		type = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.addObjectWithType(obj, type, __current);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___removeObject(Admin __obj, Incoming __inS, Current __current)
	{
		Identity id;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.removeObject(id, __current);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getObjectInfo(Admin __obj, Incoming __inS, Current __current)
	{
		Identity id;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		ObjectInfo __ret = __obj.getObjectInfo(id, __current);
		__ret.__write(__os);
		return DispatchStatus.DispatchOK;
		ObjectNotRegisteredException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getObjectInfosByType(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String type = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectInfo __ret[] = __obj.getObjectInfosByType(type, __current);
		ObjectInfoSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getAllObjectInfos(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String expr = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectInfo __ret[] = __obj.getAllObjectInfos(expr, __current);
		ObjectInfoSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___pingNode(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		boolean __ret = __obj.pingNode(name, __current);
		__os.writeBool(__ret);
		return DispatchStatus.DispatchOK;
		NodeNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getNodeLoad(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		LoadInfo __ret = __obj.getNodeLoad(name, __current);
		__ret.__write(__os);
		return DispatchStatus.DispatchOK;
		NodeNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getNodeInfo(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		NodeInfo __ret = __obj.getNodeInfo(name, __current);
		__ret.__write(__os);
		return DispatchStatus.DispatchOK;
		NodeNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getNodeProcessorSocketCount(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		int __ret = __obj.getNodeProcessorSocketCount(name, __current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
		NodeNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___shutdownNode(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.shutdownNode(name, __current);
		return DispatchStatus.DispatchOK;
		NodeNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getNodeHostname(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		String __ret = __obj.getNodeHostname(name, __current);
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
		NodeNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getAllNodeNames(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret[] = __obj.getAllNodeNames(__current);
		StringSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___pingRegistry(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		boolean __ret = __obj.pingRegistry(name, __current);
		__os.writeBool(__ret);
		return DispatchStatus.DispatchOK;
		RegistryNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getRegistryInfo(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		RegistryInfo __ret = __obj.getRegistryInfo(name, __current);
		__ret.__write(__os);
		return DispatchStatus.DispatchOK;
		RegistryNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___shutdownRegistry(Admin __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.shutdownRegistry(name, __current);
		return DispatchStatus.DispatchOK;
		RegistryNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getAllRegistryNames(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret[] = __obj.getAllRegistryNames(__current);
		StringSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___shutdown(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__obj.shutdown(__current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getSliceChecksums(Admin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		Map __ret = __obj.getSliceChecksums(__current);
		SliceChecksumDictHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public DispatchStatus __dispatch(Incoming in, Current __current)
	{
		int pos = Arrays.binarySearch(__all, __current.operation);
		if (pos < 0)
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
		switch (pos)
		{
		case 0: // '\0'
			return ___addApplication(this, in, __current);

		case 1: // '\001'
			return ___addObject(this, in, __current);

		case 2: // '\002'
			return ___addObjectWithType(this, in, __current);

		case 3: // '\003'
			return ___enableServer(this, in, __current);

		case 4: // '\004'
			return ___getAdapterInfo(this, in, __current);

		case 5: // '\005'
			return ___getAllAdapterIds(this, in, __current);

		case 6: // '\006'
			return ___getAllApplicationNames(this, in, __current);

		case 7: // '\007'
			return ___getAllNodeNames(this, in, __current);

		case 8: // '\b'
			return ___getAllObjectInfos(this, in, __current);

		case 9: // '\t'
			return ___getAllRegistryNames(this, in, __current);

		case 10: // '\n'
			return ___getAllServerIds(this, in, __current);

		case 11: // '\013'
			return ___getApplicationInfo(this, in, __current);

		case 12: // '\f'
			return ___getDefaultApplicationDescriptor(this, in, __current);

		case 13: // '\r'
			return ___getNodeHostname(this, in, __current);

		case 14: // '\016'
			return ___getNodeInfo(this, in, __current);

		case 15: // '\017'
			return ___getNodeLoad(this, in, __current);

		case 16: // '\020'
			return ___getNodeProcessorSocketCount(this, in, __current);

		case 17: // '\021'
			return ___getObjectInfo(this, in, __current);

		case 18: // '\022'
			return ___getObjectInfosByType(this, in, __current);

		case 19: // '\023'
			return ___getRegistryInfo(this, in, __current);

		case 20: // '\024'
			return ___getServerAdmin(this, in, __current);

		case 21: // '\025'
			return ___getServerAdminCategory(this, in, __current);

		case 22: // '\026'
			return ___getServerInfo(this, in, __current);

		case 23: // '\027'
			return ___getServerPid(this, in, __current);

		case 24: // '\030'
			return ___getServerState(this, in, __current);

		case 25: // '\031'
			return ___getSliceChecksums(this, in, __current);

		case 26: // '\032'
			return ___ice_id(this, in, __current);

		case 27: // '\033'
			return ___ice_ids(this, in, __current);

		case 28: // '\034'
			return ___ice_isA(this, in, __current);

		case 29: // '\035'
			return ___ice_ping(this, in, __current);

		case 30: // '\036'
			return ___instantiateServer(this, in, __current);

		case 31: // '\037'
			return ___isServerEnabled(this, in, __current);

		case 32: // ' '
			return ___patchApplication(this, in, __current);

		case 33: // '!'
			return ___patchServer(this, in, __current);

		case 34: // '"'
			return ___pingNode(this, in, __current);

		case 35: // '#'
			return ___pingRegistry(this, in, __current);

		case 36: // '$'
			return ___removeAdapter(this, in, __current);

		case 37: // '%'
			return ___removeApplication(this, in, __current);

		case 38: // '&'
			return ___removeObject(this, in, __current);

		case 39: // '\''
			return ___sendSignal(this, in, __current);

		case 40: // '('
			return ___shutdown(this, in, __current);

		case 41: // ')'
			return ___shutdownNode(this, in, __current);

		case 42: // '*'
			return ___shutdownRegistry(this, in, __current);

		case 43: // '+'
			return ___startServer(this, in, __current);

		case 44: // ','
			return ___stopServer(this, in, __current);

		case 45: // '-'
			return ___syncApplication(this, in, __current);

		case 46: // '.'
			return ___updateApplication(this, in, __current);

		case 47: // '/'
			return ___updateObject(this, in, __current);

		case 48: // '0'
			return ___writeMessage(this, in, __current);
		}
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
	}

	public void __write(BasicStream __os)
	{
		__os.writeTypeId(ice_staticId());
		__os.startWriteSlice();
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::Admin was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::Admin was not generated with stream support";
		throw ex;
	}

}
