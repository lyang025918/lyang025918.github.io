// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcSearchResultDelD.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_SvcSearchResultDel, SvcSearchResult

public final class _SvcSearchResultDelD extends _ObjectDelD
	implements _SvcSearchResultDel
{

	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_SvcSearchResultDelD.desiredAssertionStatus();

	public _SvcSearchResultDelD()
	{
	}

	public int close(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "close", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IntHolder val$__result;
			final _SvcSearchResultDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcSearchResult __servant = null;
				try
				{
					__servant = (SvcSearchResult)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.close(__current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcSearchResultDelD.this;
				__current = current;
				__result = intholder;
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

	public int docs(Holder svcdocs, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "docs", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(svcdocs) {

			final Current val$__current;
			final IntHolder val$__result;
			final Holder val$svcdocs;
			final _SvcSearchResultDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcSearchResult __servant = null;
				try
				{
					__servant = (SvcSearchResult)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.docs(svcdocs, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcSearchResultDelD.this;
				__current = current;
				__result = intholder;
				svcdocs = holder;
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

	public int getGroupTotalHits(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getGroupTotalHits", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IntHolder val$__result;
			final _SvcSearchResultDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcSearchResult __servant = null;
				try
				{
					__servant = (SvcSearchResult)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getGroupTotalHits(__current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcSearchResultDelD.this;
				__current = current;
				__result = intholder;
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

	public int getTotalHits(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getTotalHits", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IntHolder val$__result;
			final _SvcSearchResultDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcSearchResult __servant = null;
				try
				{
					__servant = (SvcSearchResult)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getTotalHits(__current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcSearchResultDelD.this;
				__current = current;
				__result = intholder;
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

	public int groups(Holder svcgroups, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "groups", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(svcgroups) {

			final Current val$__current;
			final IntHolder val$__result;
			final Holder val$svcgroups;
			final _SvcSearchResultDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SvcSearchResult __servant = null;
				try
				{
					__servant = (SvcSearchResult)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.groups(svcgroups, __current);
				return DispatchStatus.DispatchOK;
			}

			
			{
				this.this$0 = _SvcSearchResultDelD.this;
				__current = current;
				__result = intholder;
				svcgroups = holder;
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
