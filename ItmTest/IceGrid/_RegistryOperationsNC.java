// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryOperationsNC.java

package IceGrid;


// Referenced classes of package IceGrid:
//			PermissionDeniedException, SessionPrx, AdminSessionPrx

public interface _RegistryOperationsNC
{

	public abstract SessionPrx createSession(String s, String s1)
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSession(String s, String s1)
		throws PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection()
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSessionFromSecureConnection()
		throws PermissionDeniedException;

	public abstract int getSessionTimeout();
}
