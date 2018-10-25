// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AllocationException, ObjectNotRegisteredException, _SessionDel, Session

public final class _SessionDelD extends _ObjectDelD
	implements _SessionDel
{

	static final boolean $assertionsDisabled = !IceGrid/_SessionDelD.desiredAssertionStatus();

	public _SessionDelD()
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
			final _SessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Session __servant = null;
				try
				{
					__servant = (Session)__obj;
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
				this$0 = _SessionDelD.this;
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

	public ObjectPrx allocateObjectById(Identity id, Map __ctx)
		throws LocalExceptionWrapper, AllocationException, ObjectNotRegisteredException
	{
		throw new CollocationOptimizationException();
	}

	public ObjectPrx allocateObjectByType(String type, Map __ctx)
		throws LocalExceptionWrapper, AllocationException
	{
		throw new CollocationOptimizationException();
	}

	public void keepAlive(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "keepAlive", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _SessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Session __servant = null;
				try
				{
					__servant = (Session)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.keepAlive(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionDelD.this;
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

	public void releaseObject(Identity id, Map __ctx)
		throws LocalExceptionWrapper, AllocationException, ObjectNotRegisteredException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "releaseObject", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final Identity val$id;
			final _SessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Session __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Session)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.releaseObject(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _SessionDelD.this;
				__current = current;
				id = identity;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AllocationException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void setAllocationTimeout(int timeout, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "setAllocationTimeout", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(timeout) {

			final Current val$__current;
			final int val$timeout;
			final _SessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Session __servant = null;
				try
				{
					__servant = (Session)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.setAllocationTimeout(timeout, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SessionDelD.this;
				__current = current;
				timeout = i;
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
