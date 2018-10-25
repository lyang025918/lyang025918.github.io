// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItmSvcFactoryPrx.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvcPrx, Callback_ItmSvcFactory_create

public interface ItmSvcFactoryPrx
	extends ObjectPrx
{

	public abstract ItmSvcPrx create(String s);

	public abstract ItmSvcPrx create(String s, Map map);

	public abstract AsyncResult begin_create(String s);

	public abstract AsyncResult begin_create(String s, Map map);

	public abstract AsyncResult begin_create(String s, Callback callback);

	public abstract AsyncResult begin_create(String s, Map map, Callback callback);

	public abstract AsyncResult begin_create(String s, Callback_ItmSvcFactory_create callback_itmsvcfactory_create);

	public abstract AsyncResult begin_create(String s, Map map, Callback_ItmSvcFactory_create callback_itmsvcfactory_create);

	public abstract ItmSvcPrx end_create(AsyncResult asyncresult);
}
