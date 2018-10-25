// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminSessionOperationsNC.java

package IceGrid;

import Glacier2._SessionOperationsNC;
import Ice.Identity;
import Ice.ObjectPrx;

// Referenced classes of package IceGrid:
//			ObserverAlreadyRegisteredException, AccessDeniedException, DeploymentException, FileNotAvailableException, 
//			NodeUnreachableException, ServerNotExistException, NodeNotExistException, RegistryNotExistException, 
//			RegistryUnreachableException, AdminPrx, RegistryObserverPrx, NodeObserverPrx, 
//			ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx, FileIteratorPrx

public interface _AdminSessionOperationsNC
	extends _SessionOperationsNC
{

	public abstract void keepAlive();

	public abstract AdminPrx getAdmin();

	public abstract ObjectPrx getAdminCallbackTemplate();

	public abstract void setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx)
		throws ObserverAlreadyRegisteredException;

	public abstract void setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4)
		throws ObserverAlreadyRegisteredException;

	public abstract int startUpdate()
		throws AccessDeniedException;

	public abstract void finishUpdate()
		throws AccessDeniedException;

	public abstract String getReplicaName();

	public abstract FileIteratorPrx openServerLog(String s, String s1, int i)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdErr(String s, int i)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdOut(String s, int i)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openNodeStdErr(String s, int i)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openNodeStdOut(String s, int i)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openRegistryStdErr(String s, int i)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract FileIteratorPrx openRegistryStdOut(String s, int i)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;
}
