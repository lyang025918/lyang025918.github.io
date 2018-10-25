// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSLSessionManagerPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, SSLInfo, SessionControlPrx, SessionPrx, 
//			Callback_SSLSessionManager_create, AMI_SSLSessionManager_create

public interface SSLSessionManagerPrx
	extends ObjectPrx
{

	public abstract SessionPrx create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx)
		throws CannotCreateSessionException;

	public abstract SessionPrx create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Map map)
		throws CannotCreateSessionException;

	public abstract AsyncResult begin_create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx);

	public abstract AsyncResult begin_create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Map map);

	public abstract AsyncResult begin_create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Callback callback);

	public abstract AsyncResult begin_create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Map map, Callback callback);

	public abstract AsyncResult begin_create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Callback_SSLSessionManager_create callback_sslsessionmanager_create);

	public abstract AsyncResult begin_create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Map map, Callback_SSLSessionManager_create callback_sslsessionmanager_create);

	public abstract SessionPrx end_create(AsyncResult asyncresult)
		throws CannotCreateSessionException;

	public abstract boolean create_async(AMI_SSLSessionManager_create ami_sslsessionmanager_create, SSLInfo sslinfo, SessionControlPrx sessioncontrolprx);

	public abstract boolean create_async(AMI_SSLSessionManager_create ami_sslsessionmanager_create, SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Map map);
}
