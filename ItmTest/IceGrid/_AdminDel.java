// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminDel.java

package IceGrid;

import Ice.*;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, DeploymentException, ApplicationNotExistException, PatchException, 
//			ServerNotExistException, NodeUnreachableException, ServerStartException, ServerStopException, 
//			BadSignalException, AdapterNotExistException, ObjectExistsException, ObjectNotRegisteredException, 
//			NodeNotExistException, RegistryNotExistException, RegistryUnreachableException, ApplicationDescriptor, 
//			ApplicationUpdateDescriptor, ServerInstanceDescriptor, ApplicationInfo, ServerInfo, 
//			ServerState, AdapterInfo, ObjectInfo, LoadInfo, 
//			NodeInfo, RegistryInfo

public interface _AdminDel
	extends _ObjectDel
{

	public abstract void addApplication(ApplicationDescriptor applicationdescriptor, Map map)
		throws LocalExceptionWrapper, AccessDeniedException, DeploymentException;

	public abstract void syncApplication(ApplicationDescriptor applicationdescriptor, Map map)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Map map)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void removeApplication(String s, Map map)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Map map)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void patchApplication(String s, boolean flag, Map map)
		throws LocalExceptionWrapper, ApplicationNotExistException, PatchException;

	public abstract ApplicationInfo getApplicationInfo(String s, Map map)
		throws LocalExceptionWrapper, ApplicationNotExistException;

	public abstract ApplicationDescriptor getDefaultApplicationDescriptor(Map map)
		throws LocalExceptionWrapper, DeploymentException;

	public abstract String[] getAllApplicationNames(Map map)
		throws LocalExceptionWrapper;

	public abstract ServerInfo getServerInfo(String s, Map map)
		throws LocalExceptionWrapper, ServerNotExistException;

	public abstract ServerState getServerState(String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract int getServerPid(String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String getServerAdminCategory(Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx getServerAdmin(String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void enableServer(String s, boolean flag, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract boolean isServerEnabled(String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void startServer(String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException;

	public abstract void stopServer(String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException;

	public abstract void patchServer(String s, boolean flag, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException;

	public abstract void sendSignal(String s, String s1, Map map)
		throws LocalExceptionWrapper, BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void writeMessage(String s, String s1, int i, Map map)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String[] getAllServerIds(Map map)
		throws LocalExceptionWrapper;

	public abstract AdapterInfo[] getAdapterInfo(String s, Map map)
		throws LocalExceptionWrapper, AdapterNotExistException;

	public abstract void removeAdapter(String s, Map map)
		throws LocalExceptionWrapper, AdapterNotExistException, DeploymentException;

	public abstract String[] getAllAdapterIds(Map map)
		throws LocalExceptionWrapper;

	public abstract void addObject(ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper, DeploymentException, ObjectExistsException;

	public abstract void updateObject(ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper, DeploymentException, ObjectNotRegisteredException;

	public abstract void addObjectWithType(ObjectPrx objectprx, String s, Map map)
		throws LocalExceptionWrapper, DeploymentException, ObjectExistsException;

	public abstract void removeObject(Identity identity, Map map)
		throws LocalExceptionWrapper, DeploymentException, ObjectNotRegisteredException;

	public abstract ObjectInfo getObjectInfo(Identity identity, Map map)
		throws LocalExceptionWrapper, ObjectNotRegisteredException;

	public abstract ObjectInfo[] getObjectInfosByType(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectInfo[] getAllObjectInfos(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract boolean pingNode(String s, Map map)
		throws LocalExceptionWrapper, NodeNotExistException;

	public abstract LoadInfo getNodeLoad(String s, Map map)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException;

	public abstract NodeInfo getNodeInfo(String s, Map map)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException;

	public abstract int getNodeProcessorSocketCount(String s, Map map)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException;

	public abstract void shutdownNode(String s, Map map)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException;

	public abstract String getNodeHostname(String s, Map map)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException;

	public abstract String[] getAllNodeNames(Map map)
		throws LocalExceptionWrapper;

	public abstract boolean pingRegistry(String s, Map map)
		throws LocalExceptionWrapper, RegistryNotExistException;

	public abstract RegistryInfo getRegistryInfo(String s, Map map)
		throws LocalExceptionWrapper, RegistryNotExistException, RegistryUnreachableException;

	public abstract void shutdownRegistry(String s, Map map)
		throws LocalExceptionWrapper, RegistryNotExistException, RegistryUnreachableException;

	public abstract String[] getAllRegistryNames(Map map)
		throws LocalExceptionWrapper;

	public abstract void shutdown(Map map)
		throws LocalExceptionWrapper;

	public abstract Map getSliceChecksums(Map map)
		throws LocalExceptionWrapper;
}
