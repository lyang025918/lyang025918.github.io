// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Topic_unlink.java

package IceStorm;

import Ice.*;

// Referenced classes of package IceStorm:
//			TopicPrx

public abstract class Callback_Topic_unlink extends TwowayCallback
{

	public Callback_Topic_unlink()
	{
	}

	public abstract void response();

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		TopicPrx __proxy = (TopicPrx)__result.getProxy();
		try
		{
			__proxy.end_unlink(__result);
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
