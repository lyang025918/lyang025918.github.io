// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_TopicManager_create.java

package IceStorm;

import Ice.*;

// Referenced classes of package IceStorm:
//			TopicManagerPrx, TopicPrx

public abstract class Callback_TopicManager_create extends TwowayCallback
{

	public Callback_TopicManager_create()
	{
	}

	public abstract void response(TopicPrx topicprx);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		TopicManagerPrx __proxy = (TopicManagerPrx)__result.getProxy();
		TopicPrx __ret = null;
		try
		{
			__ret = __proxy.end_create(__result);
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
