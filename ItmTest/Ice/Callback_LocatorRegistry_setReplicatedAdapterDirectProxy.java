// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_LocatorRegistry_setReplicatedAdapterDirectProxy.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, LocatorRegistryPrx, UserException, LocalException, 
//			AsyncResult

public abstract class Callback_LocatorRegistry_setReplicatedAdapterDirectProxy extends TwowayCallback
{

	public Callback_LocatorRegistry_setReplicatedAdapterDirectProxy()
	{
	}

	public abstract void response();

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		LocatorRegistryPrx __proxy = (LocatorRegistryPrx)__result.getProxy();
		try
		{
			__proxy.end_setReplicatedAdapterDirectProxy(__result);
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
