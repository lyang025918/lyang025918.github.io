// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			PermissionDeniedException, SessionPrx, Callback_Registry_createSession, AdminSessionPrx, 
//			Callback_Registry_createAdminSession, Callback_Registry_createSessionFromSecureConnection, Callback_Registry_createAdminSessionFromSecureConnection, Callback_Registry_getSessionTimeout

public interface RegistryPrx
	extends ObjectPrx
{

	public abstract SessionPrx createSession(String s, String s1)
		throws PermissionDeniedException;

	public abstract SessionPrx createSession(String s, String s1, Map map)
		throws PermissionDeniedException;

	public abstract AsyncResult begin_createSession(String s, String s1);

	public abstract AsyncResult begin_createSession(String s, String s1, Map map);

	public abstract AsyncResult begin_createSession(String s, String s1, Callback callback);

	public abstract AsyncResult begin_createSession(String s, String s1, Map map, Callback callback);

	public abstract AsyncResult begin_createSession(String s, String s1, Callback_Registry_createSession callback_registry_createsession);

	public abstract AsyncResult begin_createSession(String s, String s1, Map map, Callback_Registry_createSession callback_registry_createsession);

	public abstract SessionPrx end_createSession(AsyncResult asyncresult)
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSession(String s, String s1)
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSession(String s, String s1, Map map)
		throws PermissionDeniedException;

	public abstract AsyncResult begin_createAdminSession(String s, String s1);

	public abstract AsyncResult begin_createAdminSession(String s, String s1, Map map);

	public abstract AsyncResult begin_createAdminSession(String s, String s1, Callback callback);

	public abstract AsyncResult begin_createAdminSession(String s, String s1, Map map, Callback callback);

	public abstract AsyncResult begin_createAdminSession(String s, String s1, Callback_Registry_createAdminSession callback_registry_createadminsession);

	public abstract AsyncResult begin_createAdminSession(String s, String s1, Map map, Callback_Registry_createAdminSession callback_registry_createadminsession);

	public abstract AdminSessionPrx end_createAdminSession(AsyncResult asyncresult)
		throws PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection()
		throws PermissionDeniedException;

	public abstract SessionPrx createSessionFromSecureConnection(Map map)
		throws PermissionDeniedException;

	public abstract AsyncResult begin_createSessionFromSecureConnection();

	public abstract AsyncResult begin_createSessionFromSecureConnection(Map map);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Callback callback);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Map map, Callback callback);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Callback_Registry_createSessionFromSecureConnection callback_registry_createsessionfromsecureconnection);

	public abstract AsyncResult begin_createSessionFromSecureConnection(Map map, Callback_Registry_createSessionFromSecureConnection callback_registry_createsessionfromsecureconnection);

	public abstract SessionPrx end_createSessionFromSecureConnection(AsyncResult asyncresult)
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSessionFromSecureConnection()
		throws PermissionDeniedException;

	public abstract AdminSessionPrx createAdminSessionFromSecureConnection(Map map)
		throws PermissionDeniedException;

	public abstract AsyncResult begin_createAdminSessionFromSecureConnection();

	public abstract AsyncResult begin_createAdminSessionFromSecureConnection(Map map);

	public abstract AsyncResult begin_createAdminSessionFromSecureConnection(Callback callback);

	public abstract AsyncResult begin_createAdminSessionFromSecureConnection(Map map, Callback callback);

	public abstract AsyncResult begin_createAdminSessionFromSecureConnection(Callback_Registry_createAdminSessionFromSecureConnection callback_registry_createadminsessionfromsecureconnection);

	public abstract AsyncResult begin_createAdminSessionFromSecureConnection(Map map, Callback_Registry_createAdminSessionFromSecureConnection callback_registry_createadminsessionfromsecureconnection);

	public abstract AdminSessionPrx end_createAdminSessionFromSecureConnection(AsyncResult asyncresult)
		throws PermissionDeniedException;

	public abstract int getSessionTimeout();

	public abstract int getSessionTimeout(Map map);

	public abstract AsyncResult begin_getSessionTimeout();

	public abstract AsyncResult begin_getSessionTimeout(Map map);

	public abstract AsyncResult begin_getSessionTimeout(Callback callback);

	public abstract AsyncResult begin_getSessionTimeout(Map map, Callback callback);

	public abstract AsyncResult begin_getSessionTimeout(Callback_Registry_getSessionTimeout callback_registry_getsessiontimeout);

	public abstract AsyncResult begin_getSessionTimeout(Map map, Callback_Registry_getSessionTimeout callback_registry_getsessiontimeout);

	public abstract int end_getSessionTimeout(AsyncResult asyncresult);
}
