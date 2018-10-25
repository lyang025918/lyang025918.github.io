// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcFactoryDel.java

package com.iflytek.itm.svc.svccom.gen;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvcPrx

public interface _ItmSvcFactoryDel
	extends _ObjectDel
{

	public abstract ItmSvcPrx create(String s, Map map)
		throws LocalExceptionWrapper;
}
