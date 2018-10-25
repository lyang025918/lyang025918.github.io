// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminOperationsNC.java

package IceGrid;

import Ice.Identity;
import Ice.ObjectPrx;
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

public interface _AdminOperationsNC
{

	public abstract void addApplication(ApplicationDescriptor applicationdescriptor)
		throws AccessDeniedException, DeploymentException;

	public abstract void syncApplication(ApplicationDescriptor applicationdescriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void removeApplication(String s)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void patchApplication_async(AMD_Admin_patchApplication amd_admin_patchapplication, String s, boolean flag)
		throws ApplicationNotExistException, PatchException;

	public abstract ApplicationInfo getApplicationInfo(String s)
		throws ApplicationNotExistException;

	public abstract ApplicationDescriptor getDefaultApplicationDescriptor()
		throws DeploymentException;

	public abstract String[] getAllApplicationNames();

	public abstract ServerInfo getServerInfo(String s)
		throws ServerNotExistException;

	public abstract ServerState getServerState(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract int getServerPid(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String getServerAdminCategory();

	public abstract ObjectPrx getServerAdmin(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void enableServer(String s, boolean flag)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract boolean isServerEnabled(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void startServer_async(AMD_Admin_startServer amd_admin_startserver, String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException;

	public abstract void stopServer_async(AMD_Admin_stopServer amd_admin_stopserver, String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException;

	public abstract void patchServer_async(AMD_Admin_patchServer amd_admin_patchserver, String s, boolean flag)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException;

	public abstract void sendSignal(String s, String s1)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException;

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public abstract void writeMessage(String s, String s1, int i)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String[] getAllServerIds();

	public abstract AdapterInfo[] getAdapterInfo(String s)
		throws AdapterNotExistException;

	public abstract void removeAdapter(String s)
		throws AdapterNotExistException, DeploymentException;

	public abstract String[] getAllAdapterIds();

	public abstract void addObject(ObjectPrx objectprx)
		throws DeploymentException, ObjectExistsException;

	public abstract void updateObject(ObjectPrx objectprx)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract void addObjectWithType(ObjectPrx objectprx, String s)
		throws DeploymentException, ObjectExistsException;

	public abstract void removeObject(Identity identity)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract ObjectInfo getObjectInfo(Identity identity)
		throws ObjectNotRegisteredException;

	public abstract ObjectInfo[] getObjectInfosByType(String s);

	public abstract ObjectInfo[] getAllObjectInfos(String s);

	public abstract boolean pingNode(String s)
		throws NodeNotExistException;

	public abstract LoadInfo getNodeLoad(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract NodeInfo getNodeInfo(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract int getNodeProcessorSocketCount(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract void shutdownNode(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract String getNodeHostname(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract String[] getAllNodeNames();

	public abstract boolean pingRegistry(String s)
		throws RegistryNotExistException;

	public abstract RegistryInfo getRegistryInfo(String s)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract void shutdownRegistry(String s)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract String[] getAllRegistryNames();

	public abstract void shutdown();

	public abstract Map getSliceChecksums();
}
