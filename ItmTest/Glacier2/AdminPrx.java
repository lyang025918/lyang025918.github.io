// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdminPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			Callback_Admin_shutdown

public interface AdminPrx
	extends ObjectPrx
{

	public abstract void shutdown();

	public abstract void shutdown(Map map);

	public abstract AsyncResult begin_shutdown();

	public abstract AsyncResult begin_shutdown(Map map);

	public abstract AsyncResult begin_shutdown(Callback callback);

	public abstract AsyncResult begin_shutdown(Map map, Callback callback);

	public abstract AsyncResult begin_shutdown(Callback_Admin_shutdown callback_admin_shutdown);

	public abstract AsyncResult begin_shutdown(Map map, Callback_Admin_shutdown callback_admin_shutdown);

	public abstract void end_shutdown(AsyncResult asyncresult);
}
