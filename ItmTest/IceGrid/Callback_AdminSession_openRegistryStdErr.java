// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_AdminSession_openRegistryStdErr.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			AdminSessionPrx, FileIteratorPrx

public abstract class Callback_AdminSession_openRegistryStdErr extends TwowayCallback
{

	public Callback_AdminSession_openRegistryStdErr()
	{
	}

	public abstract void response(FileIteratorPrx fileiteratorprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		AdminSessionPrx __proxy = (AdminSessionPrx)__result.getProxy();
		FileIteratorPrx __ret = null;
		try
		{
			__ret = __proxy.end_openRegistryStdErr(__result);
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
