// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminSessionDel.java

package IceGrid;

import Glacier2._SessionDel;
import Ice.Identity;
import Ice.ObjectPrx;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ObserverAlreadyRegisteredException, AccessDeniedException, DeploymentException, FileNotAvailableException, 
//			NodeUnreachableException, ServerNotExistException, NodeNotExistException, RegistryNotExistException, 
//			RegistryUnreachableException, AdminPrx, RegistryObserverPrx, NodeObserverPrx, 
//			ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx, FileIteratorPrx

public interface _AdminSessionDel
	extends _SessionDel
{

	public abstract void keepAlive(Map map)
		throws LocalExceptionWrapper;

	public abstract AdminPrx getAdmin(Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx getAdminCallbackTemplate(Map map)
		throws LocalExceptionWrapper;

	public abstract void setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Map map)
		throws LocalExceptionWrapper, ObserverAlreadyRegisteredException;

	public abstract void setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Map map)
		throws LocalExceptionWrapper, ObserverAlreadyRegisteredException;

	public abstract int startUpdate(Map map)
		throws LocalExceptionWrapper, AccessDeniedException;

	public abstract void finishUpdate(Map map)
		throws LocalExceptionWrapper, AccessDeniedException;

	public abstract String getReplicaName(Map map)
		throws LocalExceptionWrapper;

	public abstract FileIteratorPrx openServerLog(String s, String s1, int i, Map map)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdErr(String s, int i, Map map)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdOut(String s, int i, Map map)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openNodeStdErr(String s, int i, Map map)
		throws LocalExceptionWrapper, FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openNodeStdOut(String s, int i, Map map)
		throws LocalExceptionWrapper, FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openRegistryStdErr(String s, int i, Map map)
		throws LocalExceptionWrapper, FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract FileIteratorPrx openRegistryStdOut(String s, int i, Map map)
		throws LocalExceptionWrapper, FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;
}
