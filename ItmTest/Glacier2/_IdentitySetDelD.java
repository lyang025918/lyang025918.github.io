// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _IdentitySetDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_IdentitySetDel, IdentitySet

public final class _IdentitySetDelD extends _ObjectDelD
	implements _IdentitySetDel
{

	static final boolean $assertionsDisabled = !Glacier2/_IdentitySetDelD.desiredAssertionStatus();

	public _IdentitySetDelD()
	{
	}

	public void add(Identity additions[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "add", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(additions) {

			final Current val$__current;
			final Identity val$additions[];
			final _IdentitySetDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				IdentitySet __servant = null;
				try
				{
					__servant = (IdentitySet)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.add(additions, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _IdentitySetDelD.this;
				__current = current;
				additions = aidentity;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_117;
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

	public Identity[] get(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IdentitySeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "get", OperationMode.Idempotent, __ctx);
		__result = new IdentitySeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IdentitySeqHolder val$__result;
			final _IdentitySetDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				IdentitySet __servant = null;
				try
				{
					__servant = (IdentitySet)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.get(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _IdentitySetDelD.this;
				__current = current;
				__result = identityseqholder;
				super(x0);
			}
		};
		Identity aidentity[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		aidentity = __result.value;
		__direct.destroy();
		return aidentity;
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

	public void remove(Identity deletions[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "remove", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(deletions) {

			final Current val$__current;
			final Identity val$deletions[];
			final _IdentitySetDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				IdentitySet __servant = null;
				try
				{
					__servant = (IdentitySet)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.remove(deletions, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _IdentitySetDelD.this;
				__current = current;
				deletions = aidentity;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_117;
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

}
