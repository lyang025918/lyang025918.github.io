// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Topic_getNonReplicatedPublisher.java

package IceStorm;

import Ice.*;

// Referenced classes of package IceStorm:
//			TopicPrx

public abstract class Callback_Topic_getNonReplicatedPublisher extends TwowayCallback
{

	public Callback_Topic_getNonReplicatedPublisher()
	{
	}

	public abstract void response(ObjectPrx objectprx);

	public final void __completed(AsyncResult __result)
	{
		TopicPrx __proxy = (TopicPrx)__result.getProxy();
		ObjectPrx __ret = null;
		try
		{
			__ret = __proxy.end_getNonReplicatedPublisher(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
