// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			Callback_Session_destroy, AMI_Session_destroy

public interface SessionPrx
	extends ObjectPrx
{

	public abstract void destroy();

	public abstract void destroy(Map map);

	public abstract AsyncResult begin_destroy();

	public abstract AsyncResult begin_destroy(Map map);

	public abstract AsyncResult begin_destroy(Callback callback);

	public abstract AsyncResult begin_destroy(Map map, Callback callback);

	public abstract AsyncResult begin_destroy(Callback_Session_destroy callback_session_destroy);

	public abstract AsyncResult begin_destroy(Map map, Callback_Session_destroy callback_session_destroy);

	public abstract void end_destroy(AsyncResult asyncresult);

	public abstract boolean destroy_async(AMI_Session_destroy ami_session_destroy);

	public abstract boolean destroy_async(AMI_Session_destroy ami_session_destroy, Map map);
}
