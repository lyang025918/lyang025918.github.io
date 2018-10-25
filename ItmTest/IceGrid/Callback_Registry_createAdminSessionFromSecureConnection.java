// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Registry_createAdminSessionFromSecureConnection.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			RegistryPrx, AdminSessionPrx

public abstract class Callback_Registry_createAdminSessionFromSecureConnection extends TwowayCallback
{

	public Callback_Registry_createAdminSessionFromSecureConnection()
	{
	}

	public abstract void response(AdminSessionPrx adminsessionprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		RegistryPrx __proxy = (RegistryPrx)__result.getProxy();
		AdminSessionPrx __ret = null;
		try
		{
			__ret = __proxy.end_createAdminSessionFromSecureConnection(__result);
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
