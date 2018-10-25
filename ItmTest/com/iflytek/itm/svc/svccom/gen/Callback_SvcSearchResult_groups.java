// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_SvcSearchResult_groups.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import java.util.List;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcSearchResultPrx

public abstract class Callback_SvcSearchResult_groups extends TwowayCallback
{

	public Callback_SvcSearchResult_groups()
	{
	}

	public abstract void response(int i, List list);

	public final void __completed(AsyncResult __result)
	{
		SvcSearchResultPrx __proxy = (SvcSearchResultPrx)__result.getProxy();
		int __ret = 0;
		Holder svcgroups = new Holder();
		try
		{
			__ret = __proxy.end_groups(svcgroups, __result);
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret, (List)svcgroups.value);
	}
}
