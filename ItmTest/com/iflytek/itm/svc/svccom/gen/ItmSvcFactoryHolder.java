// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItmSvcFactoryHolder.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvcFactory, _ItmSvcFactoryDisp

public final class ItmSvcFactoryHolder extends ObjectHolderBase
{

	public ItmSvcFactoryHolder()
	{
	}

	public ItmSvcFactoryHolder(ItmSvcFactory value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (ItmSvcFactory)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _ItmSvcFactoryDisp.ice_staticId();
	}
}
