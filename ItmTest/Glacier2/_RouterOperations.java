// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterOperations.java

package Glacier2;

import Ice.Current;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, PermissionDeniedException, SessionNotExistException, AMD_Router_createSession, 
//			AMD_Router_createSessionFromSecureConnection

public interface _RouterOperations
	extends Ice._RouterOperations
{

	public abstract String getCategoryForClient(Current current);

	public abstract void createSession_async(AMD_Router_createSession amd_router_createsession, String s, String s1, Current current)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract void createSessionFromSecureConnection_async(AMD_Router_createSessionFromSecureConnection amd_router_createsessionfromsecureconnection, Current current)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract void refreshSession(Current current)
		throws SessionNotExistException;

	public abstract void destroySession(Current current)
		throws SessionNotExistException;

	public abstract long getSessionTimeout(Current current);
}
