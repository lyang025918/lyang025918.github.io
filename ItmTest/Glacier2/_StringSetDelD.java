// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _StringSetDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_StringSetDel, StringSet

public final class _StringSetDelD extends _ObjectDelD
	implements _StringSetDel
{

	static final boolean $assertionsDisabled = !Glacier2/_StringSetDelD.desiredAssertionStatus();

	public _StringSetDelD()
	{
	}

	public void add(String additions[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "add", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(additions) {

			final Current val$__current;
			final String val$additions[];
			final _StringSetDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				StringSet __servant = null;
				try
				{
					__servant = (StringSet)__obj;
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
				this$0 = _StringSetDelD.this;
				__current = current;
				additions = as;
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

	public String[] get(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "get", OperationMode.Idempotent, __ctx);
		__result = new StringSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSeqHolder val$__result;
			final _StringSetDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				StringSet __servant = null;
				try
				{
					__servant = (StringSet)__obj;
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
				this$0 = _StringSetDelD.this;
				__current = current;
				__result = stringseqholder;
				super(x0);
			}
		};
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		__direct.destroy();
		return as;
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

	public void remove(String deletions[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "remove", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(deletions) {

			final Current val$__current;
			final String val$deletions[];
			final _StringSetDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				StringSet __servant = null;
				try
				{
					__servant = (StringSet)__obj;
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
				this$0 = _StringSetDelD.this;
				__current = current;
				deletions = as;
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
