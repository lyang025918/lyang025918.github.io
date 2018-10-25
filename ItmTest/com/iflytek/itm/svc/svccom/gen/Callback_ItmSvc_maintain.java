// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_ItmSvc_maintain.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvcPrx

public abstract class Callback_ItmSvc_maintain extends TwowayCallback
{

	public Callback_ItmSvc_maintain()
	{
	}

	public abstract void response(int i);

	public final void __completed(AsyncResult __result)
	{
		ItmSvcPrx __proxy = (ItmSvcPrx)__result.getProxy();
		int __ret = 0;
		try
		{
			__ret = __proxy.end_maintain(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
