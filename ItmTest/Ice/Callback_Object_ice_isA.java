// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Object_ice_isA.java

package Ice;


// Referenced classes of package Ice:
//			TwowayCallback, LocalException, AsyncResult, ObjectPrx

public abstract class Callback_Object_ice_isA extends TwowayCallback
{

	public Callback_Object_ice_isA()
	{
	}

	public abstract void response(boolean flag);

	public final void __completed(AsyncResult __result)
	{
		boolean __ret = false;
		try
		{
			__ret = __result.getProxy().end_ice_isA(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
