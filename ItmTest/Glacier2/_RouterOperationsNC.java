// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterOperationsNC.java

package Glacier2;


// Referenced classes of package Glacier2:
//			CannotCreateSessionException, PermissionDeniedException, SessionNotExistException, AMD_Router_createSession, 
//			AMD_Router_createSessionFromSecureConnection

public interface _RouterOperationsNC
	extends Ice._RouterOperationsNC
{

	public abstract String getCategoryForClient();

	public abstract void createSession_async(AMD_Router_createSession amd_router_createsession, String s, String s1)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract void createSessionFromSecureConnection_async(AMD_Router_createSessionFromSecureConnection amd_router_createsessionfromsecureconnection)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract void refreshSession()
		throws SessionNotExistException;

	public abstract void destroySession()
		throws SessionNotExistException;

	public abstract long getSessionTimeout();
}
