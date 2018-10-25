// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_PropertiesAdmin_getProperty.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, PropertiesAdminPrx, LocalException, AsyncResult

public abstract class Callback_PropertiesAdmin_getProperty extends TwowayCallback
{

	public Callback_PropertiesAdmin_getProperty()
	{
	}

	public abstract void response(String s);

	public final void __completed(AsyncResult __result)
	{
		PropertiesAdminPrx __proxy = (PropertiesAdminPrx)__result.getProxy();
		String __ret = null;
		try
		{
			__ret = __proxy.end_getProperty(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
