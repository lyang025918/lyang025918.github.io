// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_ItmSvcFactory_create.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvcFactoryPrx, ItmSvcPrx

public abstract class Callback_ItmSvcFactory_create extends TwowayCallback
{

	public Callback_ItmSvcFactory_create()
	{
	}

	public abstract void response(ItmSvcPrx itmsvcprx);

	public final void __completed(AsyncResult __result)
	{
		ItmSvcFactoryPrx __proxy = (ItmSvcFactoryPrx)__result.getProxy();
		ItmSvcPrx __ret = null;
		try
		{
			__ret = __proxy.end_create(__result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret);
	}
}
