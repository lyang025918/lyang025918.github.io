// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionManagerPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, SessionControlPrx, SessionPrx, Callback_SessionManager_create, 
//			AMI_SessionManager_create

public interface SessionManagerPrx
	extends ObjectPrx
{

	public abstract SessionPrx create(String s, SessionControlPrx sessioncontrolprx)
		throws CannotCreateSessionException;

	public abstract SessionPrx create(String s, SessionControlPrx sessioncontrolprx, Map map)
		throws CannotCreateSessionException;

	public abstract AsyncResult begin_create(String s, SessionControlPrx sessioncontrolprx);

	public abstract AsyncResult begin_create(String s, SessionControlPrx sessioncontrolprx, Map map);

	public abstract AsyncResult begin_create(String s, SessionControlPrx sessioncontrolprx, Callback callback);

	public abstract AsyncResult begin_create(String s, SessionControlPrx sessioncontrolprx, Map map, Callback callback);

	public abstract AsyncResult begin_create(String s, SessionControlPrx sessioncontrolprx, Callback_SessionManager_create callback_sessionmanager_create);

	public abstract AsyncResult begin_create(String s, SessionControlPrx sessioncontrolprx, Map map, Callback_SessionManager_create callback_sessionmanager_create);

	public abstract SessionPrx end_create(AsyncResult asyncresult)
		throws CannotCreateSessionException;

	public abstract boolean create_async(AMI_SessionManager_create ami_sessionmanager_create, String s, SessionControlPrx sessioncontrolprx);

	public abstract boolean create_async(AMI_SessionManager_create ami_sessionmanager_create, String s, SessionControlPrx sessioncontrolprx, Map map);
}
