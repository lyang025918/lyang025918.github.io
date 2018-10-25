// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Topic_subscribeAndGetPublisher.java

package IceStorm;

import Ice.*;

// Referenced classes of package IceStorm:
//			TopicPrx

public abstract class Callback_Topic_subscribeAndGetPublisher extends TwowayCallback
{

	public Callback_Topic_subscribeAndGetPublisher()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		TopicPrx __proxy = (TopicPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_subscribeAndGetPublisher(__result);
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
