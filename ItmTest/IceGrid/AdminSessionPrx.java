// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdminSessionPrx.java

package IceGrid;

import Glacier2.SessionPrx;
import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ObserverAlreadyRegisteredException, AccessDeniedException, DeploymentException, FileNotAvailableException, 
//			NodeUnreachableException, ServerNotExistException, NodeNotExistException, RegistryNotExistException, 
//			RegistryUnreachableException, Callback_AdminSession_keepAlive, AdminPrx, Callback_AdminSession_getAdmin, 
//			Callback_AdminSession_getAdminCallbackTemplate, RegistryObserverPrx, NodeObserverPrx, ApplicationObserverPrx, 
//			AdapterObserverPrx, ObjectObserverPrx, Callback_AdminSession_setObservers, Callback_AdminSession_setObserversByIdentity, 
//			Callback_AdminSession_startUpdate, Callback_AdminSession_finishUpdate, Callback_AdminSession_getReplicaName, FileIteratorPrx, 
//			Callback_AdminSession_openServerLog, Callback_AdminSession_openServerStdErr, Callback_AdminSession_openServerStdOut, Callback_AdminSession_openNodeStdErr, 
//			Callback_AdminSession_openNodeStdOut, Callback_AdminSession_openRegistryStdErr, Callback_AdminSession_openRegistryStdOut

public interface AdminSessionPrx
	extends SessionPrx
{

	public abstract void keepAlive();

	public abstract void keepAlive(Map map);

	public abstract AsyncResult begin_keepAlive();

	public abstract AsyncResult begin_keepAlive(Map map);

	public abstract AsyncResult begin_keepAlive(Callback callback);

	public abstract AsyncResult begin_keepAlive(Map map, Callback callback);

	public abstract AsyncResult begin_keepAlive(Callback_AdminSession_keepAlive callback_adminsession_keepalive);

	public abstract AsyncResult begin_keepAlive(Map map, Callback_AdminSession_keepAlive callback_adminsession_keepalive);

	public abstract void end_keepAlive(AsyncResult asyncresult);

	public abstract AdminPrx getAdmin();

	public abstract AdminPrx getAdmin(Map map);

	public abstract AsyncResult begin_getAdmin();

	public abstract AsyncResult begin_getAdmin(Map map);

	public abstract AsyncResult begin_getAdmin(Callback callback);

	public abstract AsyncResult begin_getAdmin(Map map, Callback callback);

	public abstract AsyncResult begin_getAdmin(Callback_AdminSession_getAdmin callback_adminsession_getadmin);

	public abstract AsyncResult begin_getAdmin(Map map, Callback_AdminSession_getAdmin callback_adminsession_getadmin);

	public abstract AdminPrx end_getAdmin(AsyncResult asyncresult);

	public abstract ObjectPrx getAdminCallbackTemplate();

	public abstract ObjectPrx getAdminCallbackTemplate(Map map);

	public abstract AsyncResult begin_getAdminCallbackTemplate();

	public abstract AsyncResult begin_getAdminCallbackTemplate(Map map);

	public abstract AsyncResult begin_getAdminCallbackTemplate(Callback callback);

	public abstract AsyncResult begin_getAdminCallbackTemplate(Map map, Callback callback);

	public abstract AsyncResult begin_getAdminCallbackTemplate(Callback_AdminSession_getAdminCallbackTemplate callback_adminsession_getadmincallbacktemplate);

	public abstract AsyncResult begin_getAdminCallbackTemplate(Map map, Callback_AdminSession_getAdminCallbackTemplate callback_adminsession_getadmincallbacktemplate);

	public abstract ObjectPrx end_getAdminCallbackTemplate(AsyncResult asyncresult);

	public abstract void setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx)
		throws ObserverAlreadyRegisteredException;

	public abstract void setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Map map)
		throws ObserverAlreadyRegisteredException;

	public abstract AsyncResult begin_setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx);

	public abstract AsyncResult begin_setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Map map);

	public abstract AsyncResult begin_setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Callback callback);

	public abstract AsyncResult begin_setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Map map, Callback callback);

	public abstract AsyncResult begin_setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Callback_AdminSession_setObservers callback_adminsession_setobservers);

	public abstract AsyncResult begin_setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Map map, Callback_AdminSession_setObservers callback_adminsession_setobservers);

	public abstract void end_setObservers(AsyncResult asyncresult)
		throws ObserverAlreadyRegisteredException;

	public abstract void setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4)
		throws ObserverAlreadyRegisteredException;

	public abstract void setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Map map)
		throws ObserverAlreadyRegisteredException;

	public abstract AsyncResult begin_setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4);

	public abstract AsyncResult begin_setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Map map);

	public abstract AsyncResult begin_setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Callback callback);

	public abstract AsyncResult begin_setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Map map, Callback callback);

	public abstract AsyncResult begin_setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Callback_AdminSession_setObserversByIdentity callback_adminsession_setobserversbyidentity);

	public abstract AsyncResult begin_setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Map map, Callback_AdminSession_setObserversByIdentity callback_adminsession_setobserversbyidentity);

	public abstract void end_setObserversByIdentity(AsyncResult asyncresult)
		throws ObserverAlreadyRegisteredException;

	public abstract int startUpdate()
		throws AccessDeniedException;

	public abstract int startUpdate(Map map)
		throws AccessDeniedException;

	public abstract AsyncResult begin_startUpdate();

	public abstract AsyncResult begin_startUpdate(Map map);

	public abstract AsyncResult begin_startUpdate(Callback callback);

	public abstract AsyncResult begin_startUpdate(Map map, Callback callback);

	public abstract AsyncResult begin_startUpdate(Callback_AdminSession_startUpdate callback_adminsession_startupdate);

	public abstract AsyncResult begin_startUpdate(Map map, Callback_AdminSession_startUpdate callback_adminsession_startupdate);

	public abstract int end_startUpdate(AsyncResult asyncresult)
		throws AccessDeniedException;

	public abstract void finishUpdate()
		throws AccessDeniedException;

	public abstract void finishUpdate(Map map)
		throws AccessDeniedException;

	public abstract AsyncResult begin_finishUpdate();

	public abstract AsyncResult begin_finishUpdate(Map map);

	public abstract AsyncResult begin_finishUpdate(Callback callback);

	public abstract AsyncResult begin_finishUpdate(Map map, Callback callback);

	public abstract AsyncResult begin_finishUpdate(Callback_AdminSession_finishUpdate callback_adminsession_finishupdate);

	public abstract AsyncResult begin_finishUpdate(Map map, Callback_AdminSession_finishUpdate callback_adminsession_finishupdate);

	public abstract void end_finishUpdate(AsyncResult asyncresult)
		throws AccessDeniedException;

	public abstract String getReplicaName();

	public abstract String getReplicaName(Map map);

	public abstract AsyncResult begin_getReplicaName();

	public abstract AsyncResult begin_getReplicaName(Map map);

	public abstract AsyncResult begin_getReplicaName(Callback callback);

	public abstract AsyncResult begin_getReplicaName(Map map, Callback callback);

	public abstract AsyncResult begin_getReplicaName(Callback_AdminSession_getReplicaName callback_adminsession_getreplicaname);

	public abstract AsyncResult begin_getReplicaName(Map map, Callback_AdminSession_getReplicaName callback_adminsession_getreplicaname);

	public abstract String end_getReplicaName(AsyncResult asyncresult);

	public abstract FileIteratorPrx openServerLog(String s, String s1, int i)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerLog(String s, String s1, int i, Map map)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_openServerLog(String s, String s1, int i);

	public abstract AsyncResult begin_openServerLog(String s, String s1, int i, Map map);

	public abstract AsyncResult begin_openServerLog(String s, String s1, int i, Callback callback);

	public abstract AsyncResult begin_openServerLog(String s, String s1, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openServerLog(String s, String s1, int i, Callback_AdminSession_openServerLog callback_adminsession_openserverlog);

	public abstract AsyncResult begin_openServerLog(String s, String s1, int i, Map map, Callback_AdminSession_openServerLog callback_adminsession_openserverlog);

	public abstract FileIteratorPrx end_openServerLog(AsyncResult asyncresult)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdErr(String s, int i)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdErr(String s, int i, Map map)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_openServerStdErr(String s, int i);

	public abstract AsyncResult begin_openServerStdErr(String s, int i, Map map);

	public abstract AsyncResult begin_openServerStdErr(String s, int i, Callback callback);

	public abstract AsyncResult begin_openServerStdErr(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openServerStdErr(String s, int i, Callback_AdminSession_openServerStdErr callback_adminsession_openserverstderr);

	public abstract AsyncResult begin_openServerStdErr(String s, int i, Map map, Callback_AdminSession_openServerStdErr callback_adminsession_openserverstderr);

	public abstract FileIteratorPrx end_openServerStdErr(AsyncResult asyncresult)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdOut(String s, int i)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdOut(String s, int i, Map map)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract AsyncResult begin_openServerStdOut(String s, int i);

	public abstract AsyncResult begin_openServerStdOut(String s, int i, Map map);

	public abstract AsyncResult begin_openServerStdOut(String s, int i, Callback callback);

	public abstract AsyncResult begin_openServerStdOut(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openServerStdOut(String s, int i, Callback_AdminSession_openServerStdOut callback_adminsession_openserverstdout);

	public abstract AsyncResult begin_openServerStdOut(String s, int i, Map map, Callback_AdminSession_openServerStdOut callback_adminsession_openserverstdout);

	public abstract FileIteratorPrx end_openServerStdOut(AsyncResult asyncresult)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openNodeStdErr(String s, int i)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openNodeStdErr(String s, int i, Map map)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_openNodeStdErr(String s, int i);

	public abstract AsyncResult begin_openNodeStdErr(String s, int i, Map map);

	public abstract AsyncResult begin_openNodeStdErr(String s, int i, Callback callback);

	public abstract AsyncResult begin_openNodeStdErr(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openNodeStdErr(String s, int i, Callback_AdminSession_openNodeStdErr callback_adminsession_opennodestderr);

	public abstract AsyncResult begin_openNodeStdErr(String s, int i, Map map, Callback_AdminSession_openNodeStdErr callback_adminsession_opennodestderr);

	public abstract FileIteratorPrx end_openNodeStdErr(AsyncResult asyncresult)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openNodeStdOut(String s, int i)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openNodeStdOut(String s, int i, Map map)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract AsyncResult begin_openNodeStdOut(String s, int i);

	public abstract AsyncResult begin_openNodeStdOut(String s, int i, Map map);

	public abstract AsyncResult begin_openNodeStdOut(String s, int i, Callback callback);

	public abstract AsyncResult begin_openNodeStdOut(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openNodeStdOut(String s, int i, Callback_AdminSession_openNodeStdOut callback_adminsession_opennodestdout);

	public abstract AsyncResult begin_openNodeStdOut(String s, int i, Map map, Callback_AdminSession_openNodeStdOut callback_adminsession_opennodestdout);

	public abstract FileIteratorPrx end_openNodeStdOut(AsyncResult asyncresult)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openRegistryStdErr(String s, int i)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract FileIteratorPrx openRegistryStdErr(String s, int i, Map map)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract AsyncResult begin_openRegistryStdErr(String s, int i);

	public abstract AsyncResult begin_openRegistryStdErr(String s, int i, Map map);

	public abstract AsyncResult begin_openRegistryStdErr(String s, int i, Callback callback);

	public abstract AsyncResult begin_openRegistryStdErr(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openRegistryStdErr(String s, int i, Callback_AdminSession_openRegistryStdErr callback_adminsession_openregistrystderr);

	public abstract AsyncResult begin_openRegistryStdErr(String s, int i, Map map, Callback_AdminSession_openRegistryStdErr callback_adminsession_openregistrystderr);

	public abstract FileIteratorPrx end_openRegistryStdErr(AsyncResult asyncresult)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract FileIteratorPrx openRegistryStdOut(String s, int i)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract FileIteratorPrx openRegistryStdOut(String s, int i, Map map)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract AsyncResult begin_openRegistryStdOut(String s, int i);

	public abstract AsyncResult begin_openRegistryStdOut(String s, int i, Map map);

	public abstract AsyncResult begin_openRegistryStdOut(String s, int i, Callback callback);

	public abstract AsyncResult begin_openRegistryStdOut(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_openRegistryStdOut(String s, int i, Callback_AdminSession_openRegistryStdOut callback_adminsession_openregistrystdout);

	public abstract AsyncResult begin_openRegistryStdOut(String s, int i, Map map, Callback_AdminSession_openRegistryStdOut callback_adminsession_openregistrystdout);

	public abstract FileIteratorPrx end_openRegistryStdOut(AsyncResult asyncresult)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;
}
