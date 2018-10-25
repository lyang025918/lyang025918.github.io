// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserAccountMapperPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			UserAccountNotFoundException, Callback_UserAccountMapper_getUserAccount

public interface UserAccountMapperPrx
	extends ObjectPrx
{

	public abstract String getUserAccount(String s)
		throws UserAccountNotFoundException;

	public abstract String getUserAccount(String s, Map map)
		throws UserAccountNotFoundException;

	public abstract AsyncResult begin_getUserAccount(String s);

	public abstract AsyncResult begin_getUserAccount(String s, Map map);

	public abstract AsyncResult begin_getUserAccount(String s, Callback callback);

	public abstract AsyncResult begin_getUserAccount(String s, Map map, Callback callback);

	public abstract AsyncResult begin_getUserAccount(String s, Callback_UserAccountMapper_getUserAccount callback_useraccountmapper_getuseraccount);

	public abstract AsyncResult begin_getUserAccount(String s, Map map, Callback_UserAccountMapper_getUserAccount callback_useraccountmapper_getuseraccount);

	public abstract String end_getUserAccount(AsyncResult asyncresult)
		throws UserAccountNotFoundException;
}
