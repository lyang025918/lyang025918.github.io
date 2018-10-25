// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminOperations.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, DeploymentException, ApplicationNotExistException, PatchException, 
//			ServerNotExistException, NodeUnreachableException, ServerStartException, ServerStopException, 
//			BadSignalException, AdapterNotExistException, ObjectExistsException, ObjectNotRegisteredException, 
//			NodeNotExistException, RegistryNotExistException, RegistryUnreachableException, ApplicationDescriptor, 
//			ApplicationUpdateDescriptor, ServerInstanceDescriptor, AMD_Admin_patchApplication, ApplicationInfo, 
//			ServerInfo, ServerState, AMD_Admin_startServer, AMD_Admin_stopServer, 
//			AMD_Admin_patchServer, AdapterInfo, ObjectInfo, LoadInfo, 
//			NodeInfo, RegistryInfo

public interface _AdminOperations
{

	public abstract void addApplication(ApplicationDescriptor applicationdescriptor, Current current)
		throws AccessDeniedException, DeploymentException;

	public abstract void syncApplication(ApplicationDescriptor applicationdescriptor, Current current)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Current current)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void removeApplication(String s, Current current)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Current current)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void patchApplication_async(AMD_Admin_patchApplication amd_admin_patchapplication, String s, boolean flag, Current current)
		throws ApplicationNotExistException, PatchException;

	public abstract ApplicationInfo getApplicationInfo(String s, Current current)
		throws ApplicationNotExistException;

	public abstract ApplicationDescriptor getDefaultApplicationDescriptor(Current current)
		throws DeploymentException;

	public abstract String[] getAllApplicationNames(Current current);

	public abstract ServerInfo getServerInfo(String s, Current current)
		throws ServerNotExistException;

	public abstract ServerState getServerState(String s, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract int getServerPid(String s, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String getServerAdminCategory(Current current);

	public abstract ObjectPrx getServerAdmin(String s, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void enableServer(String s, boolean flag, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract boolean isServerEnabled(String s, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void startServer_async(AMD_Admin_startServer amd_admin_startserver, String s, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException;

	public abstract void stopServer_async(AMD_Admin_stopServer amd_admin_stopserver, String s, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException;

	public abstract void patchServer_async(AMD_Admin_patchServer amd_admin_patchserver, String s, boolean flag, Current current)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException;

	public abstract void sendSignal(String s, String s1, Current current)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException;

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public abstract void writeMessage(String s, String s1, int i, Current current)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String[] getAllServerIds(Current current);

	public abstract AdapterInfo[] getAdapterInfo(String s, Current current)
		throws AdapterNotExistException;

	public abstract void removeAdapter(String s, Current current)
		throws AdapterNotExistException, DeploymentException;

	public abstract String[] getAllAdapterIds(Current current);

	public abstract void addObject(ObjectPrx objectprx, Current current)
		throws DeploymentException, ObjectExistsException;

	public abstract void updateObject(ObjectPrx objectprx, Current current)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract void addObjectWithType(ObjectPrx objectprx, String s, Current current)
		throws DeploymentException, ObjectExistsException;

	public abstract void removeObject(Identity identity, Current current)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract ObjectInfo getObjectInfo(Identity identity, Current current)
		throws ObjectNotRegisteredException;

	public abstract ObjectInfo[] getObjectInfosByType(String s, Current current);

	public abstract ObjectInfo[] getAllObjectInfos(String s, Current current);

	public abstract boolean pingNode(String s, Current current)
		throws NodeNotExistException;

	public abstract LoadInfo getNodeLoad(String s, Current current)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract NodeInfo getNodeInfo(String s, Current current)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract int getNodeProcessorSocketCount(String s, Current current)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract void shutdownNode(String s, Current current)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract String getNodeHostname(String s, Current current)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract String[] getAllNodeNames(Current current);

	public abstract boolean pingRegistry(String s, Current current)
		throws RegistryNotExistException;

	public abstract RegistryInfo getRegistryInfo(String s, Current current)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract void shutdownRegistry(String s, Current current)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract String[] getAllRegistryNames(Current current);

	public abstract void shutdown(Current current);

	public abstract Map getSliceChecksums(Current current);
}
