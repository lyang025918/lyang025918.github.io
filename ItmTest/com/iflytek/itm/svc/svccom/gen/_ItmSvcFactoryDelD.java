// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcFactoryDelD.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvcPrxHolder, _ItmSvcFactoryDel, ItmSvcPrx, ItmSvcFactory

public final class _ItmSvcFactoryDelD extends _ObjectDelD
	implements _ItmSvcFactoryDel
{

	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_ItmSvcFactoryDelD.desiredAssertionStatus();

	public _ItmSvcFactoryDelD()
	{
	}

	public ItmSvcPrx create(String params, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ItmSvcPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "create", OperationMode.Normal, __ctx);
		__result = new ItmSvcPrxHolder();
		__direct = null;
		__direct = new Direct(params) {

			final Current val$__current;
			final ItmSvcPrxHolder val$__result;
			final String val$params;
			final _ItmSvcFactoryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ItmSvcFactory __servant = null;
				try
				{
					__servant = (ItmSvcFactory)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.create(params, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _ItmSvcFactoryDelD.this;
				__current = current;
				__result = itmsvcprxholder;
				params = s;
				super(x0);
			}
		};
		ItmSvcPrx itmsvcprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		itmsvcprx = __result.value;
		__direct.destroy();
		return itmsvcprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
