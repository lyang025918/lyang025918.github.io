// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcDelD.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcSearchResultPrxHolder, _ItmSvcDel, SvcBuildCallbackPrx, SvcSearchResultPrx, 
//			ItmSvc

public final class _ItmSvcDelD extends _ObjectDelD
	implements _ItmSvcDel
{

	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_ItmSvcDelD.desiredAssertionStatus();

	public _ItmSvcDelD()
	{
	}

	public int build(final String indexPath, final String params, SvcBuildCallbackPrx builder, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "build", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(builder) {

			final Current val$__current;
			final IntHolder val$__result;
			final String val$indexPath;
			final String val$params;
			final SvcBuildCallbackPrx val$builder;
			final _ItmSvcDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ItmSvc __servant = null;
				try
				{
					__servant = (ItmSvc)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.build(indexPath, params, builder, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _ItmSvcDelD.this;
				__current = current;
				__result = intholder;
				indexPath = s;
				params = s1;
				builder = svcbuildcallbackprx;
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

	public int maintain(final String indexPath, final String action, String params, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "maintain", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(params) {

			final Current val$__current;
			final IntHolder val$__result;
			final String val$indexPath;
			final String val$action;
			final String val$params;
			final _ItmSvcDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ItmSvc __servant = null;
				try
				{
					__servant = (ItmSvc)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.maintain(indexPath, action, params, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _ItmSvcDelD.this;
				__current = current;
				__result = intholder;
				indexPath = s;
				action = s1;
				params = s2;
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

	public int mining(final String indexPath, final String type, final String params, StringHolder buffer, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "mining", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(buffer) {

			final Current val$__current;
			final IntHolder val$__result;
			final String val$indexPath;
			final String val$type;
			final String val$params;
			final StringHolder val$buffer;
			final _ItmSvcDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ItmSvc __servant = null;
				try
				{
					__servant = (ItmSvc)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.mining(indexPath, type, params, buffer, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _ItmSvcDelD.this;
				__current = current;
				__result = intholder;
				indexPath = s;
				type = s1;
				params = s2;
				buffer = stringholder;
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

	public SvcSearchResultPrx search(final String indexPath, final String querySyntax, final String params, IntHolder errcode, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final SvcSearchResultPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "search", OperationMode.Normal, __ctx);
		__result = new SvcSearchResultPrxHolder();
		__direct = null;
		__direct = new Direct(errcode) {

			final Current val$__current;
			final SvcSearchResultPrxHolder val$__result;
			final String val$indexPath;
			final String val$querySyntax;
			final String val$params;
			final IntHolder val$errcode;
			final _ItmSvcDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ItmSvc __servant = null;
				try
				{
					__servant = (ItmSvc)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.search(indexPath, querySyntax, params, errcode, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _ItmSvcDelD.this;
				__current = current;
				__result = svcsearchresultprxholder;
				indexPath = s;
				querySyntax = s1;
				params = s2;
				errcode = intholder;
				super(x0);
			}
		};
		SvcSearchResultPrx svcsearchresultprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		svcsearchresultprx = __result.value;
		__direct.destroy();
		return svcsearchresultprx;
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
