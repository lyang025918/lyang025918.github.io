// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SItmSvcFactory.java

package com.iflytek.itm.svc.server;

import Ice.*;
import com.iflytek.itm.svc.svccom.ItmSvcFactoryI;
import com.iflytek.itm.svc.svccom.gen.ItmSvcPrx;
import com.iflytek.itm.svc.svccom.gen.ItmSvcPrxHelper;

// Referenced classes of package com.iflytek.itm.svc.server:
//			SItmSvc

public class SItmSvcFactory extends ItmSvcFactoryI
{

	public static String idxRootDir = "";
	private ObjectAdapter _adapter;
	private Communicator _communicator;

	public SItmSvcFactory(ObjectAdapter adapter, Communicator ic)
	{
		_adapter = null;
		_communicator = null;
		_adapter = adapter;
		_communicator = ic;
	}

	public ItmSvcPrx create(String params, Current __current)
	{
		com.iflytek.itm.svc.svccom.ItmSvcI itmSvcI = new SItmSvc(_adapter, _communicator);
		Ice.ObjectPrx objectPrx = _adapter.addWithUUID(itmSvcI);
		ItmSvcPrx itmSvcPrx = ItmSvcPrxHelper.uncheckedCast(objectPrx);
		itmSvcI = null;
		return itmSvcPrx;
	}

}
