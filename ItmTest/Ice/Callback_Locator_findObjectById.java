// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Locator_findObjectById.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, LocatorPrx, UserException, LocalException, 
//			AsyncResult, ObjectPrx

public abstract class Callback_Locator_findObjectById extends TwowayCallback
{

	public Callback_Locator_findObjectById()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		LocatorPrx __proxy = (LocatorPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_findObjectById(__result);
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
