// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getObjectInfosByType.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx, ObjectInfo

public abstract class Callback_Admin_getObjectInfosByType extends TwowayCallback
{

	public Callback_Admin_getObjectInfosByType()
	{
	}

	public abstract void response(ObjectInfo aobjectinfo[]);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		ObjectInfo __ret[] = null;
		try
		{
			__ret = __proxy.end_getObjectInfosByType(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
