// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceObserverDelD.java

package IceBox;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceBox:
//			_ServiceObserverDel, ServiceObserver

public final class _ServiceObserverDelD extends _ObjectDelD
	implements _ServiceObserverDel
{

	static final boolean $assertionsDisabled = !IceBox/_ServiceObserverDelD.desiredAssertionStatus();

	public _ServiceObserverDelD()
	{
	}

	public void servicesStarted(String services[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "servicesStarted", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(services) {

			final Current val$__current;
			final String val$services[];
			final _ServiceObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceObserver __servant = null;
				try
				{
					__servant = (ServiceObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.servicesStarted(services, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ServiceObserverDelD.this;
				__current = current;
				services = as;
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

	public void servicesStopped(String services[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "servicesStopped", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(services) {

			final Current val$__current;
			final String val$services[];
			final _ServiceObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceObserver __servant = null;
				try
				{
					__servant = (ServiceObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.servicesStopped(services, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ServiceObserverDelD.this;
				__current = current;
				services = as;
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
