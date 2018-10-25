// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcSearchResultHolder.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcSearchResult, _SvcSearchResultDisp

public final class SvcSearchResultHolder extends ObjectHolderBase
{

	public SvcSearchResultHolder()
	{
	}

	public SvcSearchResultHolder(SvcSearchResult value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (SvcSearchResult)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _SvcSearchResultDisp.ice_staticId();
	}
}
