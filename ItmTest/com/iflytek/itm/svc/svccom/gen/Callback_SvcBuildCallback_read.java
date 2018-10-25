// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SvcBuildCallback_read.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcBuildCallbackPrx, SvcDocumentHolder, SvcDocument

public abstract class Callback_SvcBuildCallback_read extends TwowayCallback
{

	public Callback_SvcBuildCallback_read()
	{
	}

	public abstract void response(int i, SvcDocument svcdocument);

	public final void __completed(AsyncResult __result)
	{
		SvcBuildCallbackPrx __proxy = (SvcBuildCallbackPrx)__result.getProxy();
		int __ret = 0;
		SvcDocumentHolder doc = new SvcDocumentHolder();
		try
		{
			__ret = __proxy.end_read(doc, __result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret, doc.value);
	}
}
