// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionControlPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			StringSetPrx, Callback_SessionControl_categories, Callback_SessionControl_adapterIds, IdentitySetPrx, 
//			Callback_SessionControl_identities, Callback_SessionControl_getSessionTimeout, Callback_SessionControl_destroy, AMI_SessionControl_destroy

public interface SessionControlPrx
	extends ObjectPrx
{

	public abstract StringSetPrx categories();

	public abstract StringSetPrx categories(Map map);

	public abstract AsyncResult begin_categories();

	public abstract AsyncResult begin_categories(Map map);

	public abstract AsyncResult begin_categories(Callback callback);

	public abstract AsyncResult begin_categories(Map map, Callback callback);

	public abstract AsyncResult begin_categories(Callback_SessionControl_categories callback_sessioncontrol_categories);

	public abstract AsyncResult begin_categories(Map map, Callback_SessionControl_categories callback_sessioncontrol_categories);

	public abstract StringSetPrx end_categories(AsyncResult asyncresult);

	public abstract StringSetPrx adapterIds();

	public abstract StringSetPrx adapterIds(Map map);

	public abstract AsyncResult begin_adapterIds();

	public abstract AsyncResult begin_adapterIds(Map map);

	public abstract AsyncResult begin_adapterIds(Callback callback);

	public abstract AsyncResult begin_adapterIds(Map map, Callback callback);

	public abstract AsyncResult begin_adapterIds(Callback_SessionControl_adapterIds callback_sessioncontrol_adapterids);

	public abstract AsyncResult begin_adapterIds(Map map, Callback_SessionControl_adapterIds callback_sessioncontrol_adapterids);

	public abstract StringSetPrx end_adapterIds(AsyncResult asyncresult);

	public abstract IdentitySetPrx identities();

	public abstract IdentitySetPrx identities(Map map);

	public abstract AsyncResult begin_identities();

	public abstract AsyncResult begin_identities(Map map);

	public abstract AsyncResult begin_identities(Callback callback);

	public abstract AsyncResult begin_identities(Map map, Callback callback);

	public abstract AsyncResult begin_identities(Callback_SessionControl_identities callback_sessioncontrol_identities);

	public abstract AsyncResult begin_identities(Map map, Callback_SessionControl_identities callback_sessioncontrol_identities);

	public abstract IdentitySetPrx end_identities(AsyncResult asyncresult);

	public abstract int getSessionTimeout();

	public abstract int getSessionTimeout(Map map);

	public abstract AsyncResult begin_getSessionTimeout();

	public abstract AsyncResult begin_getSessionTimeout(Map map);

	public abstract AsyncResult begin_getSessionTimeout(Callback callback);

	public abstract AsyncResult begin_getSessionTimeout(Map map, Callback callback);

	public abstract AsyncResult begin_getSessionTimeout(Callback_SessionControl_getSessionTimeout callback_sessioncontrol_getsessiontimeout);

	public abstract AsyncResult begin_getSessionTimeout(Map map, Callback_SessionControl_getSessionTimeout callback_sessioncontrol_getsessiontimeout);

	public abstract int end_getSessionTimeout(AsyncResult asyncresult);

	public abstract void destroy();

	public abstract void destroy(Map map);

	public abstract AsyncResult begin_destroy();

	public abstract AsyncResult begin_destroy(Map map);

	public abstract AsyncResult begin_destroy(Callback callback);

	public abstract AsyncResult begin_destroy(Map map, Callback callback);

	public abstract AsyncResult begin_destroy(Callback_SessionControl_destroy callback_sessioncontrol_destroy);

	public abstract AsyncResult begin_destroy(Map map, Callback_SessionControl_destroy callback_sessioncontrol_destroy);

	public abstract void end_destroy(AsyncResult asyncresult);

	public abstract boolean destroy_async(AMI_SessionControl_destroy ami_sessioncontrol_destroy);

	public abstract boolean destroy_async(AMI_SessionControl_destroy ami_sessioncontrol_destroy, Map map);
}
