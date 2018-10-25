// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionControlDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			StringSetPrxHolder, IdentitySetPrxHolder, _SessionControlDel, StringSetPrx, 
//			IdentitySetPrx, SessionControl

public final class _SessionControlDelD extends _ObjectDelD
	implements _SessionControlDel
{

	static final boolean $assertionsDisabled = !Glacier2/_SessionControlDelD.desiredAssertionStatus();

	public _SessionControlDelD()
	{
	}

	public StringSetPrx adapterIds(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSetPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "adapterIds", OperationMode.Normal, __ctx);
		__result = new StringSetPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSetPrxHolder val$__result;
			final _SessionControlDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SessionControl __servant = null;
				try
				{
					__servant = (SessionControl)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.adapterIds(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionControlDelD.this;
				__current = current;
				__result = stringsetprxholder;
				super(x0);
			}
		};
		StringSetPrx stringsetprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		stringsetprx = __result.value;
		__direct.destroy();
		return stringsetprx;
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

	public StringSetPrx categories(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSetPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "categories", OperationMode.Normal, __ctx);
		__result = new StringSetPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSetPrxHolder val$__result;
			final _SessionControlDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SessionControl __servant = null;
				try
				{
					__servant = (SessionControl)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.categories(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionControlDelD.this;
				__current = current;
				__result = stringsetprxholder;
				super(x0);
			}
		};
		StringSetPrx stringsetprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		stringsetprx = __result.value;
		__direct.destroy();
		return stringsetprx;
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

	public void destroy(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "destroy", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _SessionControlDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SessionControl __servant = null;
				try
				{
					__servant = (SessionControl)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.destroy(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionControlDelD.this;
				__current = current;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_109;
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

	public int getSessionTimeout(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getSessionTimeout", OperationMode.Idempotent, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IntHolder val$__result;
			final _SessionControlDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SessionControl __servant = null;
				try
				{
					__servant = (SessionControl)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getSessionTimeout(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionControlDelD.this;
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

	public IdentitySetPrx identities(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IdentitySetPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "identities", OperationMode.Normal, __ctx);
		__result = new IdentitySetPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IdentitySetPrxHolder val$__result;
			final _SessionControlDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SessionControl __servant = null;
				try
				{
					__servant = (SessionControl)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.identities(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionControlDelD.this;
				__current = current;
				__result = identitysetprxholder;
				super(x0);
			}
		};
		IdentitySetPrx identitysetprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		identitysetprx = __result.value;
		__direct.destroy();
		return identitysetprx;
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
