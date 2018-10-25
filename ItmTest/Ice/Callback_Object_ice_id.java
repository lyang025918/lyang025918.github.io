// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Object_ice_id.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, LocalException, AsyncResult, ObjectPrx

public abstract class Callback_Object_ice_id extends TwowayCallback
{

	public Callback_Object_ice_id()
	{
	}

	public abstract void response(String s);

	public final void __completed(AsyncResult __result)
	{
		String __ret = null;
		try
		{
			__ret = __result.getProxy().end_ice_id(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
