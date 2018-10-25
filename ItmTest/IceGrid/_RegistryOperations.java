// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryOperations.java

package IceGrid;

import Ice.Current;

// Referenced classes of package IceGrid:
//			PermissionDeniedException, SessionPrx, AdminSessionPrx

public interface _RegistryOperations
{

	public abstract SessionPrx createSession(String s, String s1, Current current)
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSession(String s, String s1, Current current)
		throws PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection(Current current)
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSessionFromSecureConnection(Current current)
		throws PermissionDeniedException;

	public abstract int getSessionTimeout(Current current);
}
