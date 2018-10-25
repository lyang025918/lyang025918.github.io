// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_Topic_getLinkInfoSeq.java

package IceStorm;

import Ice.*;

// Referenced classes of package IceStorm:
//			TopicPrx, LinkInfo

public abstract class Callback_Topic_getLinkInfoSeq extends TwowayCallback
{

	public Callback_Topic_getLinkInfoSeq()
	{
	}

	public abstract void response(LinkInfo alinkinfo[]);

	public final void __completed(AsyncResult __result)
	{
		TopicPrx __proxy = (TopicPrx)__result.getProxy();
		LinkInfo __ret[] = null;
		try
		{
			__ret = __proxy.end_getLinkInfoSeq(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
