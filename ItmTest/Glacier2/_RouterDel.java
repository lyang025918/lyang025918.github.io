// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDel.java

package Glacier2;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, PermissionDeniedException, SessionNotExistException, SessionPrx

public interface _RouterDel
	extends Ice._RouterDel
{

	public abstract String getCategoryForClient(Map map)
		throws LocalExceptionWrapper;

	public abstract SessionPrx createSession(String s, String s1, Map map)
		throws LocalExceptionWrapper, CannotCreateSessionException, PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection(Map map)
		throws LocalExceptionWrapper, CannotCreateSessionException, PermissionDeniedException;

	public abstract void refreshSession(Map map)
		throws LocalExceptionWrapper, SessionNotExistException;

	public abstract void destroySession(Map map)
		throws LocalExceptionWrapper, SessionNotExistException;

	public abstract long getSessionTimeout(Map map)
		throws LocalExceptionWrapper;
}
