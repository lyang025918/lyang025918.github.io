// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Admin_getDefaultApplicationDescriptor.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminPrx, ApplicationDescriptor

public abstract class Callback_Admin_getDefaultApplicationDescriptor extends TwowayCallback
{

	public Callback_Admin_getDefaultApplicationDescriptor()
	{
	}

	public abstract void response(ApplicationDescriptor applicationdescriptor);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminPrx __proxy = (AdminPrx)__result.getProxy();
		ApplicationDescriptor __ret = null;
		try
		{
			__ret = __proxy.end_getDefaultApplicationDescriptor(__result);
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
