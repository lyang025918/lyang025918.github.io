// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_addApplication.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx

public abstract class Callback_Admin_addApplication extends TwowayCallback
{

	public Callback_Admin_addApplication()
	{
	}

	public abstract void response();

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		try
		{
			__proxy.end_addApplication(__result);
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
		response();
	}
}
