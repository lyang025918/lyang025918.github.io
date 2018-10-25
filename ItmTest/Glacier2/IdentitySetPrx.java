// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IdentitySetPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			Callback_IdentitySet_add, Callback_IdentitySet_remove, Callback_IdentitySet_get

public interface IdentitySetPrx
	extends ObjectPrx
{

	public abstract void add(Identity aidentity[]);

	public abstract void add(Identity aidentity[], Map map);

	public abstract AsyncResult begin_add(Identity aidentity[]);

	public abstract AsyncResult begin_add(Identity aidentity[], Map map);

	public abstract AsyncResult begin_add(Identity aidentity[], Callback callback);

	public abstract AsyncResult begin_add(Identity aidentity[], Map map, Callback callback);

	public abstract AsyncResult begin_add(Identity aidentity[], Callback_IdentitySet_add callback_identityset_add);

	public abstract AsyncResult begin_add(Identity aidentity[], Map map, Callback_IdentitySet_add callback_identityset_add);

	public abstract void end_add(AsyncResult asyncresult);

	public abstract void remove(Identity aidentity[]);

	public abstract void remove(Identity aidentity[], Map map);

	public abstract AsyncResult begin_remove(Identity aidentity[]);

	public abstract AsyncResult begin_remove(Identity aidentity[], Map map);

	public abstract AsyncResult begin_remove(Identity aidentity[], Callback callback);

	public abstract AsyncResult begin_remove(Identity aidentity[], Map map, Callback callback);

	public abstract AsyncResult begin_remove(Identity aidentity[], Callback_IdentitySet_remove callback_identityset_remove);

	public abstract AsyncResult begin_remove(Identity aidentity[], Map map, Callback_IdentitySet_remove callback_identityset_remove);

	public abstract void end_remove(AsyncResult asyncresult);

	public abstract Identity[] get();

	public abstract Identity[] get(Map map);

	public abstract AsyncResult begin_get();

	public abstract AsyncResult begin_get(Map map);

	public abstract AsyncResult begin_get(Callback callback);

	public abstract AsyncResult begin_get(Map map, Callback callback);

	public abstract AsyncResult begin_get(Callback_IdentitySet_get callback_identityset_get);

	public abstract AsyncResult begin_get(Map map, Callback_IdentitySet_get callback_identityset_get);

	public abstract Identity[] end_get(AsyncResult asyncresult);
}
