// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryDel.java

package IceGrid;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			PermissionDeniedException, SessionPrx, AdminSessionPrx

public interface _RegistryDel
	extends _ObjectDel
{

	public abstract SessionPrx createSession(String s, String s1, Map map)
		throws LocalExceptionWrapper, PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSession(String s, String s1, Map map)
		throws LocalExceptionWrapper, PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection(Map map)
		throws LocalExceptionWrapper, PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSessionFromSecureConnection(Map map)
		throws LocalExceptionWrapper, PermissionDeniedException;

	public abstract int getSessionTimeout(Map map)
		throws LocalExceptionWrapper;
}
