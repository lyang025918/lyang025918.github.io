// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcBuildCallbackDelD.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_SvcBuildCallbackDel, SvcDocumentHolder, SvcBuildCallback

public final class _SvcBuildCallbackDelD extends _ObjectDelD
	implements _SvcBuildCallbackDel
{

	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_SvcBuildCallbackDelD.desiredAssertionStatus();

	public _SvcBuildCallbackDelD()
	{
	}

	public void event(final String id, final int evt, String msg, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "event", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(msg) {

			final Current val$__current;
			final String val$id;
			final int val$evt;
			final String val$msg;
			final _SvcBuildCallbackDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcBuildCallback __servant = null;
				try
				{
					__servant = (SvcBuildCallback)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.event(id, evt, msg, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcBuildCallbackDelD.this;
				__current = current;
				id = s;
				evt = i;
				msg = s1;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_124;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public int read(SvcDocumentHolder doc, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "read", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(doc) {

			final Current val$__current;
			final IntHolder val$__result;
			final SvcDocumentHolder val$doc;
			final _SvcBuildCallbackDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcBuildCallback __servant = null;
				try
				{
					__servant = (SvcBuildCallback)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.read(doc, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcBuildCallbackDelD.this;
				__current = current;
				__result = intholder;
				doc = svcdocumentholder;
				super(x0);
			}
		};
		int i;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		i = __result.value;
		__direct.destroy();
		return i;
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
