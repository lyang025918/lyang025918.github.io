// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdminPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, DeploymentException, ApplicationNotExistException, PatchException, 
//			ServerNotExistException, NodeUnreachableException, ServerStartException, ServerStopException, 
//			BadSignalException, AdapterNotExistException, ObjectExistsException, ObjectNotRegisteredException, 
//			NodeNotExistException, RegistryNotExistException, RegistryUnreachableException, ApplicationDescriptor, 
//			Callback_Admin_addApplication, AMI_Admin_addApplication, Callback_Admin_syncApplication, AMI_Admin_syncApplication, 
//			ApplicationUpdateDescriptor, Callback_Admin_updateApplication, AMI_Admin_updateApplication, Callback_Admin_removeApplication, 
//			AMI_Admin_removeApplication, ServerInstanceDescriptor, Callback_Admin_instantiateServer, Callback_Admin_patchApplication, 
//			AMI_Admin_patchApplication, ApplicationInfo, Callback_Admin_getApplicationInfo, Callback_Admin_getDefaultApplicationDescriptor, 
//			Callback_Admin_getAllApplicationNames, ServerInfo, Callback_Admin_getServerInfo, ServerState, 
//			Callback_Admin_getServerState, Callback_Admin_getServerPid, Callback_Admin_getServerAdminCategory, Callback_Admin_getServerAdmin, 
//			Callback_Admin_enableServer, AMI_Admin_enableServer, Callback_Admin_isServerEnabled, Callback_Admin_startServer, 
//			AMI_Admin_startServer, Callback_Admin_stopServer, AMI_Admin_stopServer, Callback_Admin_patchServer, 
//			AMI_Admin_patchServer, Callback_Admin_sendSignal, AMI_Admin_sendSignal, Callback_Admin_writeMessage, 
//			AMI_Admin_writeMessage, Callback_Admin_getAllServerIds, AdapterInfo, Callback_Admin_getAdapterInfo, 
//			Callback_Admin_removeAdapter, AMI_Admin_removeAdapter, Callback_Admin_getAllAdapterIds, Callback_Admin_addObject, 
//			AMI_Admin_addObject, Callback_Admin_updateObject, Callback_Admin_addObjectWithType, AMI_Admin_addObjectWithType, 
//			Callback_Admin_removeObject, AMI_Admin_removeObject, ObjectInfo, Callback_Admin_getObjectInfo, 
//			Callback_Admin_getObjectInfosByType, Callback_Admin_getAllObjectInfos, Callback_Admin_pingNode, LoadInfo, 
//			Callback_Admin_getNodeLoad, AMI_Admin_getNodeLoad, NodeInfo, Callback_Admin_getNodeInfo, 
//			Callback_Admin_getNodeProcessorSocketCount, Callback_Admin_shutdownNode, AMI_Admin_shutdownNode, Callback_Admin_getNodeHostname, 
//			Callback_Admin_getAllNodeNames, Callback_Admin_pingRegistry, RegistryInfo, Callback_Admin_getRegistryInfo, 
//			Callback_Admin_shutdownRegistry, AMI_Admin_shutdownRegistry, Callback_Admin_getAllRegistryNames, Callback_Admin_shutdown, 
//			Callback_Admin_getSliceChecksums

public interface AdminPrx
	extends ObjectPrx
{

	public abstract void addApplication(ApplicationDescriptor applicationdescriptor)
		throws AccessDeniedException, DeploymentException;

	public abstract void addApplication(ApplicationDescriptor applicationdescriptor, Map map)
		throws AccessDeniedException, DeploymentException;

	public abstract AsyncResult begin_addApplication(ApplicationDescriptor applicationdescriptor);

	public abstract AsyncResult begin_addApplication(ApplicationDescriptor applicationdescriptor, Map map);

	public abstract AsyncResult begin_addApplication(ApplicationDescriptor applicationdescriptor, Callback callback);

	public abstract AsyncResult begin_addApplication(ApplicationDescriptor applicationdescriptor, Map map, Callback callback);

	public abstract AsyncResult begin_addApplication(ApplicationDescriptor applicationdescriptor, Callback_Admin_addApplication callback_admin_addapplication);

	public abstract AsyncResult begin_addApplication(ApplicationDescriptor applicationdescriptor, Map map, Callback_Admin_addApplication callback_admin_addapplication);

	public abstract void end_addApplication(AsyncResult asyncresult)
		throws AccessDeniedException, DeploymentException;

	public abstract boolean addApplication_async(AMI_Admin_addApplication ami_admin_addapplication, ApplicationDescriptor applicationdescriptor);

	public abstract boolean addApplication_async(AMI_Admin_addApplication ami_admin_addapplication, ApplicationDescriptor applicationdescriptor, Map map);

	public abstract void syncApplication(ApplicationDescriptor applicationdescriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void syncApplication(ApplicationDescriptor applicationdescriptor, Map map)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract AsyncResult begin_syncApplication(ApplicationDescriptor applicationdescriptor);

	public abstract AsyncResult begin_syncApplication(ApplicationDescriptor applicationdescriptor, Map map);

	public abstract AsyncResult begin_syncApplication(ApplicationDescriptor applicationdescriptor, Callback callback);

	public abstract AsyncResult begin_syncApplication(ApplicationDescriptor applicationdescriptor, Map map, Callback callback);

	public abstract AsyncResult begin_syncApplication(ApplicationDescriptor applicationdescriptor, Callback_Admin_syncApplication callback_admin_syncapplication);

	public abstract AsyncResult begin_syncApplication(ApplicationDescriptor applicationdescriptor, Map map, Callback_Admin_syncApplication callback_admin_syncapplication);

	public abstract void end_syncApplication(AsyncResult asyncresult)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract boolean syncApplication_async(AMI_Admin_syncApplication ami_admin_syncapplication, ApplicationDescriptor applicationdescriptor);

	public abstract boolean syncApplication_async(AMI_Admin_syncApplication ami_admin_syncapplication, ApplicationDescriptor applicationdescriptor, Map map);

	public abstract void updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Map map)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract AsyncResult begin_updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor);

	public abstract AsyncResult begin_updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Map map);

	public abstract AsyncResult begin_updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Callback callback);

	public abstract AsyncResult begin_updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Map map, Callback callback);

	public abstract AsyncResult begin_updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Callback_Admin_updateApplication callback_admin_updateapplication);

	public abstract AsyncResult begin_updateApplication(ApplicationUpdateDescriptor applicationupdatedescriptor, Map map, Callback_Admin_updateApplication callback_admin_updateapplication);

	public abstract void end_updateApplication(AsyncResult asyncresult)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract boolean updateApplication_async(AMI_Admin_updateApplication ami_admin_updateapplication, ApplicationUpdateDescriptor applicationupdatedescriptor);

	public abstract boolean updateApplication_async(AMI_Admin_updateApplication ami_admin_updateapplication, ApplicationUpdateDescriptor applicationupdatedescriptor, Map map);

	public abstract void removeApplication(String s)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void removeApplication(String s, Map map)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract AsyncResult begin_removeApplication(String s);

	public abstract AsyncResult begin_removeApplication(String s, Map map);

	public abstract AsyncResult begin_removeApplication(String s, Callback callback);

	public abstract AsyncResult begin_removeApplication(String s, Map map, Callback callback);

	public abstract AsyncResult begin_removeApplication(String s, Callback_Admin_removeApplication callback_admin_removeapplication);

	public abstract AsyncResult begin_removeApplication(String s, Map map, Callback_Admin_removeApplication callback_admin_removeapplication);

	public abstract void end_removeApplication(AsyncResult asyncresult)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract boolean removeApplication_async(AMI_Admin_removeApplication ami_admin_removeapplication, String s);

	public abstract boolean removeApplication_async(AMI_Admin_removeApplication ami_admin_removeapplication, String s, Map map);

	public abstract void instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Map map)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract AsyncResult begin_instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor);

	public abstract AsyncResult begin_instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Map map);

	public abstract AsyncResult begin_instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Callback callback);

	public abstract AsyncResult begin_instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Map map, Callback callback);

	public abstract AsyncResult begin_instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Callback_Admin_instantiateServer callback_admin_instantiateserver);

	public abstract AsyncResult begin_instantiateServer(String s, String s1, ServerInstanceDescriptor serverinstancedescriptor, Map map, Callback_Admin_instantiateServer callback_admin_instantiateserver);

	public abstract void end_instantiateServer(AsyncResult asyncresult)
		throws AccessDeniedException, ApplicationNotExistException, DeploymentException;

	public abstract void patchApplication(String s, boolean flag)
		throws ApplicationNotExistException, PatchException;

	public abstract void patchApplication(String s, boolean flag, Map map)
		throws ApplicationNotExistException, PatchException;

	public abstract AsyncResult begin_patchApplication(String s, boolean flag);

	public abstract AsyncResult begin_patchApplication(String s, boolean flag, Map map);

	public abstract AsyncResult begin_patchApplication(String s, boolean flag, Callback callback);

	public abstract AsyncResult begin_patchApplication(String s, boolean flag, Map map, Callback callback);

	public abstract AsyncResult begin_patchApplication(String s, boolean flag, Callback_Admin_patchApplication callback_admin_patchapplication);

	public abstract AsyncResult begin_patchApplication(String s, boolean flag, Map map, Callback_Admin_patchApplication callback_admin_patchapplication);

	public abstract void end_patchApplication(AsyncResult asyncresult)
		throws ApplicationNotExistException, PatchException;

	public abstract boolean patchApplication_async(AMI_Admin_patchApplication ami_admin_patchapplication, String s, boolean flag);

	public abstract boolean patchApplication_async(AMI_Admin_patchApplication ami_admin_patchapplication, String s, boolean flag, Map map);

	public abstract ApplicationInfo getApplicationInfo(String s)
		throws ApplicationNotExistException;

	public abstract ApplicationInfo getApplicationInfo(String s, Map map)
		throws ApplicationNotExistException;

	public abstract AsyncResult begin_getApplicationInfo(String s);

	public abstract AsyncResult begin_getApplicationInfo(String s, Map map);

	public abstract AsyncResult begin_getApplicationInfo(String s, Callback callback);

	public abstract AsyncResult begin_getApplicationInfo(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getApplicationInfo(String s, Callback_Admin_getApplicationInfo callback_admin_getapplicationinfo);

	public abstract AsyncResult begin_getApplicationInfo(String s, Map map, Callback_Admin_getApplicationInfo callback_admin_getapplicationinfo);

	public abstract ApplicationInfo end_getApplicationInfo(AsyncResult asyncresult)
		throws ApplicationNotExistException;

	public abstract ApplicationDescriptor getDefaultApplicationDescriptor()
		throws DeploymentException;

	public abstract ApplicationDescriptor getDefaultApplicationDescriptor(Map map)
		throws DeploymentException;

	public abstract AsyncResult begin_getDefaultApplicationDescriptor();

	public abstract AsyncResult begin_getDefaultApplicationDescriptor(Map map);

	public abstract AsyncResult begin_getDefaultApplicationDescriptor(Callback callback);

	public abstract AsyncResult begin_getDefaultApplicationDescriptor(Map map, Callback callback);

	public abstract AsyncResult begin_getDefaultApplicationDescriptor(Callback_Admin_getDefaultApplicationDescriptor callback_admin_getdefaultapplicationdescriptor);

	public abstract AsyncResult begin_getDefaultApplicationDescriptor(Map map, Callback_Admin_getDefaultApplicationDescriptor callback_admin_getdefaultapplicationdescriptor);

	public abstract ApplicationDescriptor end_getDefaultApplicationDescriptor(AsyncResult asyncresult)
		throws DeploymentException;

	public abstract String[] getAllApplicationNames();

	public abstract String[] getAllApplicationNames(Map map);

	public abstract AsyncResult begin_getAllApplicationNames();

	public abstract AsyncResult begin_getAllApplicationNames(Map map);

	public abstract AsyncResult begin_getAllApplicationNames(Callback callback);

	public abstract AsyncResult begin_getAllApplicationNames(Map map, Callback callback);

	public abstract AsyncResult begin_getAllApplicationNames(Callback_Admin_getAllApplicationNames callback_admin_getallapplicationnames);

	public abstract AsyncResult begin_getAllApplicationNames(Map map, Callback_Admin_getAllApplicationNames callback_admin_getallapplicationnames);

	public abstract String[] end_getAllApplicationNames(AsyncResult asyncresult);

	public abstract ServerInfo getServerInfo(String s)
		throws ServerNotExistException;

	public abstract ServerInfo getServerInfo(String s, Map map)
		throws ServerNotExistException;

	public abstract AsyncResult begin_getServerInfo(String s);

	public abstract AsyncResult begin_getServerInfo(String s, Map map);

	public abstract AsyncResult begin_getServerInfo(String s, Callback callback);

	public abstract AsyncResult begin_getServerInfo(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getServerInfo(String s, Callback_Admin_getServerInfo callback_admin_getserverinfo);

	public abstract AsyncResult begin_getServerInfo(String s, Map map, Callback_Admin_getServerInfo callback_admin_getserverinfo);

	public abstract ServerInfo end_getServerInfo(AsyncResult asyncresult)
		throws ServerNotExistException;

	public abstract ServerState getServerState(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract ServerState getServerState(String s, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_getServerState(String s);

	public abstract AsyncResult begin_getServerState(String s, Map map);

	public abstract AsyncResult begin_getServerState(String s, Callback callback);

	public abstract AsyncResult begin_getServerState(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getServerState(String s, Callback_Admin_getServerState callback_admin_getserverstate);

	public abstract AsyncResult begin_getServerState(String s, Map map, Callback_Admin_getServerState callback_admin_getserverstate);

	public abstract ServerState end_getServerState(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract int getServerPid(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract int getServerPid(String s, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_getServerPid(String s);

	public abstract AsyncResult begin_getServerPid(String s, Map map);

	public abstract AsyncResult begin_getServerPid(String s, Callback callback);

	public abstract AsyncResult begin_getServerPid(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getServerPid(String s, Callback_Admin_getServerPid callback_admin_getserverpid);

	public abstract AsyncResult begin_getServerPid(String s, Map map, Callback_Admin_getServerPid callback_admin_getserverpid);

	public abstract int end_getServerPid(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract String getServerAdminCategory();

	public abstract String getServerAdminCategory(Map map);

	public abstract AsyncResult begin_getServerAdminCategory();

	public abstract AsyncResult begin_getServerAdminCategory(Map map);

	public abstract AsyncResult begin_getServerAdminCategory(Callback callback);

	public abstract AsyncResult begin_getServerAdminCategory(Map map, Callback callback);

	public abstract AsyncResult begin_getServerAdminCategory(Callback_Admin_getServerAdminCategory callback_admin_getserveradmincategory);

	public abstract AsyncResult begin_getServerAdminCategory(Map map, Callback_Admin_getServerAdminCategory callback_admin_getserveradmincategory);

	public abstract String end_getServerAdminCategory(AsyncResult asyncresult);

	public abstract ObjectPrx getServerAdmin(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract ObjectPrx getServerAdmin(String s, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_getServerAdmin(String s);

	public abstract AsyncResult begin_getServerAdmin(String s, Map map);

	public abstract AsyncResult begin_getServerAdmin(String s, Callback callback);

	public abstract AsyncResult begin_getServerAdmin(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getServerAdmin(String s, Callback_Admin_getServerAdmin callback_admin_getserveradmin);

	public abstract AsyncResult begin_getServerAdmin(String s, Map map, Callback_Admin_getServerAdmin callback_admin_getserveradmin);

	public abstract ObjectPrx end_getServerAdmin(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void enableServer(String s, boolean flag)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void enableServer(String s, boolean flag, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_enableServer(String s, boolean flag);

	public abstract AsyncResult begin_enableServer(String s, boolean flag, Map map);

	public abstract AsyncResult begin_enableServer(String s, boolean flag, Callback callback);

	public abstract AsyncResult begin_enableServer(String s, boolean flag, Map map, Callback callback);

	public abstract AsyncResult begin_enableServer(String s, boolean flag, Callback_Admin_enableServer callback_admin_enableserver);

	public abstract AsyncResult begin_enableServer(String s, boolean flag, Map map, Callback_Admin_enableServer callback_admin_enableserver);

	public abstract void end_enableServer(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract boolean enableServer_async(AMI_Admin_enableServer ami_admin_enableserver, String s, boolean flag);

	public abstract boolean enableServer_async(AMI_Admin_enableServer ami_admin_enableserver, String s, boolean flag, Map map);

	public abstract boolean isServerEnabled(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract boolean isServerEnabled(String s, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_isServerEnabled(String s);

	public abstract AsyncResult begin_isServerEnabled(String s, Map map);

	public abstract AsyncResult begin_isServerEnabled(String s, Callback callback);

	public abstract AsyncResult begin_isServerEnabled(String s, Map map, Callback callback);

	public abstract AsyncResult begin_isServerEnabled(String s, Callback_Admin_isServerEnabled callback_admin_isserverenabled);

	public abstract AsyncResult begin_isServerEnabled(String s, Map map, Callback_Admin_isServerEnabled callback_admin_isserverenabled);

	public abstract boolean end_isServerEnabled(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void startServer(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException;

	public abstract void startServer(String s, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException;

	public abstract AsyncResult begin_startServer(String s);

	public abstract AsyncResult begin_startServer(String s, Map map);

	public abstract AsyncResult begin_startServer(String s, Callback callback);

	public abstract AsyncResult begin_startServer(String s, Map map, Callback callback);

	public abstract AsyncResult begin_startServer(String s, Callback_Admin_startServer callback_admin_startserver);

	public abstract AsyncResult begin_startServer(String s, Map map, Callback_Admin_startServer callback_admin_startserver);

	public abstract void end_startServer(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException;

	public abstract boolean startServer_async(AMI_Admin_startServer ami_admin_startserver, String s);

	public abstract boolean startServer_async(AMI_Admin_startServer ami_admin_startserver, String s, Map map);

	public abstract void stopServer(String s)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException;

	public abstract void stopServer(String s, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException;

	public abstract AsyncResult begin_stopServer(String s);

	public abstract AsyncResult begin_stopServer(String s, Map map);

	public abstract AsyncResult begin_stopServer(String s, Callback callback);

	public abstract AsyncResult begin_stopServer(String s, Map map, Callback callback);

	public abstract AsyncResult begin_stopServer(String s, Callback_Admin_stopServer callback_admin_stopserver);

	public abstract AsyncResult begin_stopServer(String s, Map map, Callback_Admin_stopServer callback_admin_stopserver);

	public abstract void end_stopServer(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException;

	public abstract boolean stopServer_async(AMI_Admin_stopServer ami_admin_stopserver, String s);

	public abstract boolean stopServer_async(AMI_Admin_stopServer ami_admin_stopserver, String s, Map map);

	public abstract void patchServer(String s, boolean flag)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException;

	public abstract void patchServer(String s, boolean flag, Map map)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException;

	public abstract AsyncResult begin_patchServer(String s, boolean flag);

	public abstract AsyncResult begin_patchServer(String s, boolean flag, Map map);

	public abstract AsyncResult begin_patchServer(String s, boolean flag, Callback callback);

	public abstract AsyncResult begin_patchServer(String s, boolean flag, Map map, Callback callback);

	public abstract AsyncResult begin_patchServer(String s, boolean flag, Callback_Admin_patchServer callback_admin_patchserver);

	public abstract AsyncResult begin_patchServer(String s, boolean flag, Map map, Callback_Admin_patchServer callback_admin_patchserver);

	public abstract void end_patchServer(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException;

	public abstract boolean patchServer_async(AMI_Admin_patchServer ami_admin_patchserver, String s, boolean flag);

	public abstract boolean patchServer_async(AMI_Admin_patchServer ami_admin_patchserver, String s, boolean flag, Map map);

	public abstract void sendSignal(String s, String s1)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract void sendSignal(String s, String s1, Map map)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_sendSignal(String s, String s1);

	public abstract AsyncResult begin_sendSignal(String s, String s1, Map map);

	public abstract AsyncResult begin_sendSignal(String s, String s1, Callback callback);

	public abstract AsyncResult begin_sendSignal(String s, String s1, Map map, Callback callback);

	public abstract AsyncResult begin_sendSignal(String s, String s1, Callback_Admin_sendSignal callback_admin_sendsignal);

	public abstract AsyncResult begin_sendSignal(String s, String s1, Map map, Callback_Admin_sendSignal callback_admin_sendsignal);

	public abstract void end_sendSignal(AsyncResult asyncresult)
		throws BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException;

	public abstract boolean sendSignal_async(AMI_Admin_sendSignal ami_admin_sendsignal, String s, String s1);

	public abstract boolean sendSignal_async(AMI_Admin_sendSignal ami_admin_sendsignal, String s, String s1, Map map);

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public abstract void writeMessage(String s, String s1, int i)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public abstract void writeMessage(String s, String s1, int i, Map map)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public abstract AsyncResult begin_writeMessage(String s, String s1, int i);

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public abstract AsyncResult begin_writeMessage(String s, String s1, int i, Map map);

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public abstract AsyncResult begin_writeMessage(String s, String s1, int i, Callback callback);

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public abstract AsyncResult begin_writeMessage(String s, String s1, int i, Map map, Callback callback);

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public abstract AsyncResult begin_writeMessage(String s, String s1, int i, Callback_Admin_writeMessage callback_admin_writemessage);

	/**
	 * @deprecated Method begin_writeMessage is deprecated
	 */

	public abstract AsyncResult begin_writeMessage(String s, String s1, int i, Map map, Callback_Admin_writeMessage callback_admin_writemessage);

	public abstract void end_writeMessage(AsyncResult asyncresult)
		throws DeploymentException, NodeUnreachableException, ServerNotExistException;

	/**
	 * @deprecated Method writeMessage_async is deprecated
	 */

	public abstract boolean writeMessage_async(AMI_Admin_writeMessage ami_admin_writemessage, String s, String s1, int i);

	/**
	 * @deprecated Method writeMessage_async is deprecated
	 */

	public abstract boolean writeMessage_async(AMI_Admin_writeMessage ami_admin_writemessage, String s, String s1, int i, Map map);

	public abstract String[] getAllServerIds();

	public abstract String[] getAllServerIds(Map map);

	public abstract AsyncResult begin_getAllServerIds();

	public abstract AsyncResult begin_getAllServerIds(Map map);

	public abstract AsyncResult begin_getAllServerIds(Callback callback);

	public abstract AsyncResult begin_getAllServerIds(Map map, Callback callback);

	public abstract AsyncResult begin_getAllServerIds(Callback_Admin_getAllServerIds callback_admin_getallserverids);

	public abstract AsyncResult begin_getAllServerIds(Map map, Callback_Admin_getAllServerIds callback_admin_getallserverids);

	public abstract String[] end_getAllServerIds(AsyncResult asyncresult);

	public abstract AdapterInfo[] getAdapterInfo(String s)
		throws AdapterNotExistException;

	public abstract AdapterInfo[] getAdapterInfo(String s, Map map)
		throws AdapterNotExistException;

	public abstract AsyncResult begin_getAdapterInfo(String s);

	public abstract AsyncResult begin_getAdapterInfo(String s, Map map);

	public abstract AsyncResult begin_getAdapterInfo(String s, Callback callback);

	public abstract AsyncResult begin_getAdapterInfo(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getAdapterInfo(String s, Callback_Admin_getAdapterInfo callback_admin_getadapterinfo);

	public abstract AsyncResult begin_getAdapterInfo(String s, Map map, Callback_Admin_getAdapterInfo callback_admin_getadapterinfo);

	public abstract AdapterInfo[] end_getAdapterInfo(AsyncResult asyncresult)
		throws AdapterNotExistException;

	public abstract void removeAdapter(String s)
		throws AdapterNotExistException, DeploymentException;

	public abstract void removeAdapter(String s, Map map)
		throws AdapterNotExistException, DeploymentException;

	public abstract AsyncResult begin_removeAdapter(String s);

	public abstract AsyncResult begin_removeAdapter(String s, Map map);

	public abstract AsyncResult begin_removeAdapter(String s, Callback callback);

	public abstract AsyncResult begin_removeAdapter(String s, Map map, Callback callback);

	public abstract AsyncResult begin_removeAdapter(String s, Callback_Admin_removeAdapter callback_admin_removeadapter);

	public abstract AsyncResult begin_removeAdapter(String s, Map map, Callback_Admin_removeAdapter callback_admin_removeadapter);

	public abstract void end_removeAdapter(AsyncResult asyncresult)
		throws AdapterNotExistException, DeploymentException;

	public abstract boolean removeAdapter_async(AMI_Admin_removeAdapter ami_admin_removeadapter, String s);

	public abstract boolean removeAdapter_async(AMI_Admin_removeAdapter ami_admin_removeadapter, String s, Map map);

	public abstract String[] getAllAdapterIds();

	public abstract String[] getAllAdapterIds(Map map);

	public abstract AsyncResult begin_getAllAdapterIds();

	public abstract AsyncResult begin_getAllAdapterIds(Map map);

	public abstract AsyncResult begin_getAllAdapterIds(Callback callback);

	public abstract AsyncResult begin_getAllAdapterIds(Map map, Callback callback);

	public abstract AsyncResult begin_getAllAdapterIds(Callback_Admin_getAllAdapterIds callback_admin_getalladapterids);

	public abstract AsyncResult begin_getAllAdapterIds(Map map, Callback_Admin_getAllAdapterIds callback_admin_getalladapterids);

	public abstract String[] end_getAllAdapterIds(AsyncResult asyncresult);

	public abstract void addObject(ObjectPrx objectprx)
		throws DeploymentException, ObjectExistsException;

	public abstract void addObject(ObjectPrx objectprx, Map map)
		throws DeploymentException, ObjectExistsException;

	public abstract AsyncResult begin_addObject(ObjectPrx objectprx);

	public abstract AsyncResult begin_addObject(ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_addObject(ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_addObject(ObjectPrx objectprx, Map map, Callback callback);

	public abstract AsyncResult begin_addObject(ObjectPrx objectprx, Callback_Admin_addObject callback_admin_addobject);

	public abstract AsyncResult begin_addObject(ObjectPrx objectprx, Map map, Callback_Admin_addObject callback_admin_addobject);

	public abstract void end_addObject(AsyncResult asyncresult)
		throws DeploymentException, ObjectExistsException;

	public abstract boolean addObject_async(AMI_Admin_addObject ami_admin_addobject, ObjectPrx objectprx);

	public abstract boolean addObject_async(AMI_Admin_addObject ami_admin_addobject, ObjectPrx objectprx, Map map);

	public abstract void updateObject(ObjectPrx objectprx)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract void updateObject(ObjectPrx objectprx, Map map)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract AsyncResult begin_updateObject(ObjectPrx objectprx);

	public abstract AsyncResult begin_updateObject(ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_updateObject(ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_updateObject(ObjectPrx objectprx, Map map, Callback callback);

	public abstract AsyncResult begin_updateObject(ObjectPrx objectprx, Callback_Admin_updateObject callback_admin_updateobject);

	public abstract AsyncResult begin_updateObject(ObjectPrx objectprx, Map map, Callback_Admin_updateObject callback_admin_updateobject);

	public abstract void end_updateObject(AsyncResult asyncresult)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract void addObjectWithType(ObjectPrx objectprx, String s)
		throws DeploymentException, ObjectExistsException;

	public abstract void addObjectWithType(ObjectPrx objectprx, String s, Map map)
		throws DeploymentException, ObjectExistsException;

	public abstract AsyncResult begin_addObjectWithType(ObjectPrx objectprx, String s);

	public abstract AsyncResult begin_addObjectWithType(ObjectPrx objectprx, String s, Map map);

	public abstract AsyncResult begin_addObjectWithType(ObjectPrx objectprx, String s, Callback callback);

	public abstract AsyncResult begin_addObjectWithType(ObjectPrx objectprx, String s, Map map, Callback callback);

	public abstract AsyncResult begin_addObjectWithType(ObjectPrx objectprx, String s, Callback_Admin_addObjectWithType callback_admin_addobjectwithtype);

	public abstract AsyncResult begin_addObjectWithType(ObjectPrx objectprx, String s, Map map, Callback_Admin_addObjectWithType callback_admin_addobjectwithtype);

	public abstract void end_addObjectWithType(AsyncResult asyncresult)
		throws DeploymentException, ObjectExistsException;

	public abstract boolean addObjectWithType_async(AMI_Admin_addObjectWithType ami_admin_addobjectwithtype, ObjectPrx objectprx, String s);

	public abstract boolean addObjectWithType_async(AMI_Admin_addObjectWithType ami_admin_addobjectwithtype, ObjectPrx objectprx, String s, Map map);

	public abstract void removeObject(Identity identity)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract void removeObject(Identity identity, Map map)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract AsyncResult begin_removeObject(Identity identity);

	public abstract AsyncResult begin_removeObject(Identity identity, Map map);

	public abstract AsyncResult begin_removeObject(Identity identity, Callback callback);

	public abstract AsyncResult begin_removeObject(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_removeObject(Identity identity, Callback_Admin_removeObject callback_admin_removeobject);

	public abstract AsyncResult begin_removeObject(Identity identity, Map map, Callback_Admin_removeObject callback_admin_removeobject);

	public abstract void end_removeObject(AsyncResult asyncresult)
		throws DeploymentException, ObjectNotRegisteredException;

	public abstract boolean removeObject_async(AMI_Admin_removeObject ami_admin_removeobject, Identity identity);

	public abstract boolean removeObject_async(AMI_Admin_removeObject ami_admin_removeobject, Identity identity, Map map);

	public abstract ObjectInfo getObjectInfo(Identity identity)
		throws ObjectNotRegisteredException;

	public abstract ObjectInfo getObjectInfo(Identity identity, Map map)
		throws ObjectNotRegisteredException;

	public abstract AsyncResult begin_getObjectInfo(Identity identity);

	public abstract AsyncResult begin_getObjectInfo(Identity identity, Map map);

	public abstract AsyncResult begin_getObjectInfo(Identity identity, Callback callback);

	public abstract AsyncResult begin_getObjectInfo(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_getObjectInfo(Identity identity, Callback_Admin_getObjectInfo callback_admin_getobjectinfo);

	public abstract AsyncResult begin_getObjectInfo(Identity identity, Map map, Callback_Admin_getObjectInfo callback_admin_getobjectinfo);

	public abstract ObjectInfo end_getObjectInfo(AsyncResult asyncresult)
		throws ObjectNotRegisteredException;

	public abstract ObjectInfo[] getObjectInfosByType(String s);

	public abstract ObjectInfo[] getObjectInfosByType(String s, Map map);

	public abstract AsyncResult begin_getObjectInfosByType(String s);

	public abstract AsyncResult begin_getObjectInfosByType(String s, Map map);

	public abstract AsyncResult begin_getObjectInfosByType(String s, Callback callback);

	public abstract AsyncResult begin_getObjectInfosByType(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getObjectInfosByType(String s, Callback_Admin_getObjectInfosByType callback_admin_getobjectinfosbytype);

	public abstract AsyncResult begin_getObjectInfosByType(String s, Map map, Callback_Admin_getObjectInfosByType callback_admin_getobjectinfosbytype);

	public abstract ObjectInfo[] end_getObjectInfosByType(AsyncResult asyncresult);

	public abstract ObjectInfo[] getAllObjectInfos(String s);

	public abstract ObjectInfo[] getAllObjectInfos(String s, Map map);

	public abstract AsyncResult begin_getAllObjectInfos(String s);

	public abstract AsyncResult begin_getAllObjectInfos(String s, Map map);

	public abstract AsyncResult begin_getAllObjectInfos(String s, Callback callback);

	public abstract AsyncResult begin_getAllObjectInfos(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getAllObjectInfos(String s, Callback_Admin_getAllObjectInfos callback_admin_getallobjectinfos);

	public abstract AsyncResult begin_getAllObjectInfos(String s, Map map, Callback_Admin_getAllObjectInfos callback_admin_getallobjectinfos);

	public abstract ObjectInfo[] end_getAllObjectInfos(AsyncResult asyncresult);

	public abstract boolean pingNode(String s)
		throws NodeNotExistException;

	public abstract boolean pingNode(String s, Map map)
		throws NodeNotExistException;

	public abstract AsyncResult begin_pingNode(String s);

	public abstract AsyncResult begin_pingNode(String s, Map map);

	public abstract AsyncResult begin_pingNode(String s, Callback callback);

	public abstract AsyncResult begin_pingNode(String s, Map map, Callback callback);

	public abstract AsyncResult begin_pingNode(String s, Callback_Admin_pingNode callback_admin_pingnode);

	public abstract AsyncResult begin_pingNode(String s, Map map, Callback_Admin_pingNode callback_admin_pingnode);

	public abstract boolean end_pingNode(AsyncResult asyncresult)
		throws NodeNotExistException;

	public abstract LoadInfo getNodeLoad(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract LoadInfo getNodeLoad(String s, Map map)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_getNodeLoad(String s);

	public abstract AsyncResult begin_getNodeLoad(String s, Map map);

	public abstract AsyncResult begin_getNodeLoad(String s, Callback callback);

	public abstract AsyncResult begin_getNodeLoad(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getNodeLoad(String s, Callback_Admin_getNodeLoad callback_admin_getnodeload);

	public abstract AsyncResult begin_getNodeLoad(String s, Map map, Callback_Admin_getNodeLoad callback_admin_getnodeload);

	public abstract LoadInfo end_getNodeLoad(AsyncResult asyncresult)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract boolean getNodeLoad_async(AMI_Admin_getNodeLoad ami_admin_getnodeload, String s);

	public abstract boolean getNodeLoad_async(AMI_Admin_getNodeLoad ami_admin_getnodeload, String s, Map map);

	public abstract NodeInfo getNodeInfo(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract NodeInfo getNodeInfo(String s, Map map)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_getNodeInfo(String s);

	public abstract AsyncResult begin_getNodeInfo(String s, Map map);

	public abstract AsyncResult begin_getNodeInfo(String s, Callback callback);

	public abstract AsyncResult begin_getNodeInfo(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getNodeInfo(String s, Callback_Admin_getNodeInfo callback_admin_getnodeinfo);

	public abstract AsyncResult begin_getNodeInfo(String s, Map map, Callback_Admin_getNodeInfo callback_admin_getnodeinfo);

	public abstract NodeInfo end_getNodeInfo(AsyncResult asyncresult)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract int getNodeProcessorSocketCount(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract int getNodeProcessorSocketCount(String s, Map map)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_getNodeProcessorSocketCount(String s);

	public abstract AsyncResult begin_getNodeProcessorSocketCount(String s, Map map);

	public abstract AsyncResult begin_getNodeProcessorSocketCount(String s, Callback callback);

	public abstract AsyncResult begin_getNodeProcessorSocketCount(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getNodeProcessorSocketCount(String s, Callback_Admin_getNodeProcessorSocketCount callback_admin_getnodeprocessorsocketcount);

	public abstract AsyncResult begin_getNodeProcessorSocketCount(String s, Map map, Callback_Admin_getNodeProcessorSocketCount callback_admin_getnodeprocessorsocketcount);

	public abstract int end_getNodeProcessorSocketCount(AsyncResult asyncresult)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract void shutdownNode(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract void shutdownNode(String s, Map map)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_shutdownNode(String s);

	public abstract AsyncResult begin_shutdownNode(String s, Map map);

	public abstract AsyncResult begin_shutdownNode(String s, Callback callback);

	public abstract AsyncResult begin_shutdownNode(String s, Map map, Callback callback);

	public abstract AsyncResult begin_shutdownNode(String s, Callback_Admin_shutdownNode callback_admin_shutdownnode);

	public abstract AsyncResult begin_shutdownNode(String s, Map map, Callback_Admin_shutdownNode callback_admin_shutdownnode);

	public abstract void end_shutdownNode(AsyncResult asyncresult)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract boolean shutdownNode_async(AMI_Admin_shutdownNode ami_admin_shutdownnode, String s);

	public abstract boolean shutdownNode_async(AMI_Admin_shutdownNode ami_admin_shutdownnode, String s, Map map);

	public abstract String getNodeHostname(String s)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract String getNodeHostname(String s, Map map)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_getNodeHostname(String s);

	public abstract AsyncResult begin_getNodeHostname(String s, Map map);

	public abstract AsyncResult begin_getNodeHostname(String s, Callback callback);

	public abstract AsyncResult begin_getNodeHostname(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getNodeHostname(String s, Callback_Admin_getNodeHostname callback_admin_getnodehostname);

	public abstract AsyncResult begin_getNodeHostname(String s, Map map, Callback_Admin_getNodeHostname callback_admin_getnodehostname);

	public abstract String end_getNodeHostname(AsyncResult asyncresult)
		throws NodeNotExistException, NodeUnreachableException;

	public abstract String[] getAllNodeNames();

	public abstract String[] getAllNodeNames(Map map);

	public abstract AsyncResult begin_getAllNodeNames();

	public abstract AsyncResult begin_getAllNodeNames(Map map);

	public abstract AsyncResult begin_getAllNodeNames(Callback callback);

	public abstract AsyncResult begin_getAllNodeNames(Map map, Callback callback);

	public abstract AsyncResult begin_getAllNodeNames(Callback_Admin_getAllNodeNames callback_admin_getallnodenames);

	public abstract AsyncResult begin_getAllNodeNames(Map map, Callback_Admin_getAllNodeNames callback_admin_getallnodenames);

	public abstract String[] end_getAllNodeNames(AsyncResult asyncresult);

	public abstract boolean pingRegistry(String s)
		throws RegistryNotExistException;

	public abstract boolean pingRegistry(String s, Map map)
		throws RegistryNotExistException;

	public abstract AsyncResult begin_pingRegistry(String s);

	public abstract AsyncResult begin_pingRegistry(String s, Map map);

	public abstract AsyncResult begin_pingRegistry(String s, Callback callback);

	public abstract AsyncResult begin_pingRegistry(String s, Map map, Callback callback);

	public abstract AsyncResult begin_pingRegistry(String s, Callback_Admin_pingRegistry callback_admin_pingregistry);

	public abstract AsyncResult begin_pingRegistry(String s, Map map, Callback_Admin_pingRegistry callback_admin_pingregistry);

	public abstract boolean end_pingRegistry(AsyncResult asyncresult)
		throws RegistryNotExistException;

	public abstract RegistryInfo getRegistryInfo(String s)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract RegistryInfo getRegistryInfo(String s, Map map)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract AsyncResult begin_getRegistryInfo(String s);

	public abstract AsyncResult begin_getRegistryInfo(String s, Map map);

	public abstract AsyncResult begin_getRegistryInfo(String s, Callback callback);

	public abstract AsyncResult begin_getRegistryInfo(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getRegistryInfo(String s, Callback_Admin_getRegistryInfo callback_admin_getregistryinfo);

	public abstract AsyncResult begin_getRegistryInfo(String s, Map map, Callback_Admin_getRegistryInfo callback_admin_getregistryinfo);

	public abstract RegistryInfo end_getRegistryInfo(AsyncResult asyncresult)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract void shutdownRegistry(String s)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract void shutdownRegistry(String s, Map map)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract AsyncResult begin_shutdownRegistry(String s);

	public abstract AsyncResult begin_shutdownRegistry(String s, Map map);

	public abstract AsyncResult begin_shutdownRegistry(String s, Callback callback);

	public abstract AsyncResult begin_shutdownRegistry(String s, Map map, Callback callback);

	public abstract AsyncResult begin_shutdownRegistry(String s, Callback_Admin_shutdownRegistry callback_admin_shutdownregistry);

	public abstract AsyncResult begin_shutdownRegistry(String s, Map map, Callback_Admin_shutdownRegistry callback_admin_shutdownregistry);

	public abstract void end_shutdownRegistry(AsyncResult asyncresult)
		throws RegistryNotExistException, RegistryUnreachableException;

	public abstract boolean shutdownRegistry_async(AMI_Admin_shutdownRegistry ami_admin_shutdownregistry, String s);

	public abstract boolean shutdownRegistry_async(AMI_Admin_shutdownRegistry ami_admin_shutdownregistry, String s, Map map);

	public abstract String[] getAllRegistryNames();

	public abstract String[] getAllRegistryNames(Map map);

	public abstract AsyncResult begin_getAllRegistryNames();

	public abstract AsyncResult begin_getAllRegistryNames(Map map);

	public abstract AsyncResult begin_getAllRegistryNames(Callback callback);

	public abstract AsyncResult begin_getAllRegistryNames(Map map, Callback callback);

	public abstract AsyncResult begin_getAllRegistryNames(Callback_Admin_getAllRegistryNames callback_admin_getallregistrynames);

	public abstract AsyncResult begin_getAllRegistryNames(Map map, Callback_Admin_getAllRegistryNames callback_admin_getallregistrynames);

	public abstract String[] end_getAllRegistryNames(AsyncResult asyncresult);

	public abstract void shutdown();

	public abstract void shutdown(Map map);

	public abstract AsyncResult begin_shutdown();

	public abstract AsyncResult begin_shutdown(Map map);

	public abstract AsyncResult begin_shutdown(Callback callback);

	public abstract AsyncResult begin_shutdown(Map map, Callback callback);

	public abstract AsyncResult begin_shutdown(Callback_Admin_shutdown callback_admin_shutdown);

	public abstract AsyncResult begin_shutdown(Map map, Callback_Admin_shutdown callback_admin_shutdown);

	public abstract void end_shutdown(AsyncResult asyncresult);

	public abstract Map getSliceChecksums();

	public abstract Map getSliceChecksums(Map map);

	public abstract AsyncResult begin_getSliceChecksums();

	public abstract AsyncResult begin_getSliceChecksums(Map map);

	public abstract AsyncResult begin_getSliceChecksums(Callback callback);

	public abstract AsyncResult begin_getSliceChecksums(Map map, Callback callback);

	public abstract AsyncResult begin_getSliceChecksums(Callback_Admin_getSliceChecksums callback_admin_getslicechecksums);

	public abstract AsyncResult begin_getSliceChecksums(Map map, Callback_Admin_getSliceChecksums callback_admin_getslicechecksums);

	public abstract Map end_getSliceChecksums(AsyncResult asyncresult);
}
