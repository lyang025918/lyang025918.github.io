// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterPrx.java

package Glacier2;

import Ice.AsyncResult;
import Ice.Callback;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, PermissionDeniedException, SessionNotExistException, Callback_Router_getCategoryForClient, 
//			SessionPrx, Callback_Router_createSession, Callback_Router_createSessionFromSecureConnection, Callback_Router_refreshSession, 
//			AMI_Router_refreshSession, Callback_Router_destroySession, AMI_Router_destroySession, Callback_Router_getSessionTimeout

public interface RouterPrx
	extends Ice.RouterPrx
{

	public abstract String getCategoryForClient();

	public abstract String getCategoryForClient(Map map);

	public abstract AsyncResult begin_getCategoryForClient();

	public abstract AsyncResult begin_getCategoryForClient(Map map);

	public abstract AsyncResult begin_getCategoryForClient(Callback callback);

	public abstract AsyncResult begin_getCategoryForClient(Map map, Callback callback);

	public abstract AsyncResult begin_getCategoryForClient(Callback_Router_getCategoryForClient callback_router_getcategoryforclient);

	public abstract AsyncResult begin_getCategoryForClient(Map map, Callback_Router_getCategoryForClient callback_router_getcategoryforclient);

	public abstract String end_getCategoryForClient(AsyncResult asyncresult);

	public abstract SessionPrx createSession(String s, String s1)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract SessionPrx createSession(String s, String s1, Map map)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract AsyncResult begin_createSession(String s, String s1);

	public abstract AsyncResult begin_createSession(String s, String s1, Map map);

	public abstract AsyncResult begin_createSession(String s, String s1, Callback callback);

	public abstract AsyncResult begin_createSession(String s, String s1, Map map, Callback callback);

	public abstract AsyncResult begin_createSession(String s, String s1, Callback_Router_createSession callback_router_createsession);

	public abstract AsyncResult begin_createSession(String s, String s1, Map map, Callback_Router_createSession callback_router_createsession);

	public abstract SessionPrx end_createSession(AsyncResult asyncresult)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection()
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection(Map map)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract AsyncResult begin_createSessionFromSecureConnection();

	public abstract AsyncResult begin_createSessionFromSecureConnection(Map map);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Callback callback);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Map map, Callback callback);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Callback_Router_createSessionFromSecureConnection callback_router_createsessionfromsecureconnection);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Map map, Callback_Router_createSessionFromSecureConnection callback_router_createsessionfromsecureconnection);

	public abstract SessionPrx end_createSessionFromSecureConnection(AsyncResult asyncresult)
		throws CannotCreateSessionException, PermissionDeniedException;

	public abstract void refreshSession()
		throws SessionNotExistException;

	public abstract void refreshSession(Map map)
		throws SessionNotExistException;

	public abstract AsyncResult begin_refreshSession();

	public abstract AsyncResult begin_refreshSession(Map map);

	public abstract AsyncResult begin_refreshSession(Callback callback);

	public abstract AsyncResult begin_refreshSession(Map map, Callback callback);

	public abstract AsyncResult begin_refreshSession(Callback_Router_refreshSession callback_router_refreshsession);

	public abstract AsyncResult begin_refreshSession(Map map, Callback_Router_refreshSession callback_router_refreshsession);

	public abstract void end_refreshSession(AsyncResult asyncresult)
		throws SessionNotExistException;

	public abstract boolean refreshSession_async(AMI_Router_refreshSession ami_router_refreshsession);

	public abstract boolean refreshSession_async(AMI_Router_refreshSession ami_router_refreshsession, Map map);

	public abstract void destroySession()
		throws SessionNotExistException;

	public abstract void destroySession(Map map)
		throws SessionNotExistException;

	public abstract AsyncResult begin_destroySession();

	public abstract AsyncResult begin_destroySession(Map map);

	public abstract AsyncResult begin_destroySession(Callback callback);

	public abstract AsyncResult begin_destroySession(Map map, Callback callback);

	public abstract AsyncResult begin_destroySession(Callback_Router_destroySession callback_router_destroysession);

	public abstract AsyncResult begin_destroySession(Map map, Callback_Router_destroySession callback_router_destroysession);

	public abstract void end_destroySession(AsyncResult asyncresult)
		throws SessionNotExistException;

	public abstract boolean destroySession_async(AMI_Router_destroySession ami_router_destroysession);

	public abstract boolean destroySession_async(AMI_Router_destroySession ami_router_destroysession, Map map);

	public abstract long getSessionTimeout();

	public abstract long getSessionTimeout(Map map);

	public abstract AsyncResult begin_getSessionTimeout();

	public abstract AsyncResult begin_getSessionTimeout(Map map);

	public abstract AsyncResult begin_getSessionTimeout(Callback callback);

	public abstract AsyncResult begin_getSessionTimeout(Map map, Callback callback);

	public abstract AsyncResult begin_getSessionTimeout(Callback_Router_getSessionTimeout callback_router_getsessiontimeout);

	public abstract AsyncResult begin_getSessionTimeout(Map map, Callback_Router_getSessionTimeout callback_router_getsessiontimeout);

	public abstract long end_getSessionTimeout(AsyncResult asyncresult);
}
