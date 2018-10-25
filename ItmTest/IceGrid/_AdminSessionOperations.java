// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminSessionOperations.java

package IceGrid;

import Glacier2._SessionOperations;
import Ice.*;

// Referenced classes of package IceGrid:
//			ObserverAlreadyRegisteredException, AccessDeniedException, DeploymentException, FileNotAvailableException, 
//			NodeUnreachableException, ServerNotExistException, NodeNotExistException, RegistryNotExistException, 
//			RegistryUnreachableException, AdminPrx, RegistryObserverPrx, NodeObserverPrx, 
//			ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx, FileIteratorPrx

public interface _AdminSessionOperations
	extends _SessionOperations
{

	public abstract void keepAlive(Current current);

	public abstract AdminPrx getAdmin(Current current);

	public abstract ObjectPrx getAdminCallbackTemplate(Current current);

	public abstract void setObservers(RegistryObserverPrx registryobserverprx, NodeObserverPrx nodeobserverprx, ApplicationObserverPrx applicationobserverprx, AdapterObserverPrx adapterobserverprx, ObjectObserverPrx objectobserverprx, Current current)
		throws ObserverAlreadyRegisteredException;

	public abstract void setObserversByIdentity(Identity identity, Identity identity1, Identity identity2, Identity identity3, Identity identity4, Current current)
		throws ObserverAlreadyRegisteredException;

	public abstract int startUpdate(Current current)
		throws AccessDeniedException;

	public abstract void finishUpdate(Current current)
		throws AccessDeniedException;

	public abstract String getReplicaName(Current current);

	public abstract FileIteratorPrx openServerLog(String s, String s1, int i, Current current)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdErr(String s, int i, Current current)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openServerStdOut(String s, int i, Current current)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException;

	public abstract FileIteratorPrx openNodeStdErr(String s, int i, Current current)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openNodeStdOut(String s, int i, Current current)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException;

	public abstract FileIteratorPrx openRegistryStdErr(String s, int i, Current current)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;

	public abstract FileIteratorPrx openRegistryStdOut(String s, int i, Current current)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException;
}
