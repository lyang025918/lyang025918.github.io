// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getObjectInfo.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx, ObjectInfo

public abstract class Callback_Admin_getObjectInfo extends TwowayCallback
{

	public Callback_Admin_getObjectInfo()
	{
	}

	public abstract void response(ObjectInfo objectinfo);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		ObjectInfo __ret = null;
		try
		{
			__ret = __proxy.end_getObjectInfo(__result);
		}
		catch (UserException __ex)
		{
			exception(__ex);
			return;
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
