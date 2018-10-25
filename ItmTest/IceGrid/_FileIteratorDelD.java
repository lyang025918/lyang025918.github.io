// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileIteratorDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			FileNotAvailableException, _FileIteratorDel, FileIterator

public final class _FileIteratorDelD extends _ObjectDelD
	implements _FileIteratorDel
{

	static final boolean $assertionsDisabled = !IceGrid/_FileIteratorDelD.desiredAssertionStatus();

	public _FileIteratorDelD()
	{
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
			final _FileIteratorDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				FileIterator __servant = null;
				try
				{
					__servant = (FileIterator)__obj;
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
				this$0 = _FileIteratorDelD.this;
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

	public boolean read(final int size, StringSeqHolder lines, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException
	{
		final Current __current;
		final BooleanHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "read", OperationMode.Normal, __ctx);
		__result = new BooleanHolder();
		__direct = null;
		__direct = new Direct(lines) {

			final Current val$__current;
			final BooleanHolder val$__result;
			final int val$size;
			final StringSeqHolder val$lines;
			final _FileIteratorDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				FileIterator __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (FileIterator)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.read(size, lines, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _FileIteratorDelD.this;
				__current = current;
				__result = booleanholder;
				size = i;
				lines = stringseqholder;
				super(x0);
			}
		};
		boolean flag;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		flag = __result.value;
		__direct.destroy();
		return flag;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		FileNotAvailableException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
