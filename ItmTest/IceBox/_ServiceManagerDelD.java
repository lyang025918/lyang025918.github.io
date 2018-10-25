// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceManagerDelD.java

package IceBox;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, _ServiceManagerDel, 
//			ServiceObserverPrx, ServiceManager

public final class _ServiceManagerDelD extends _ObjectDelD
	implements _ServiceManagerDel
{

	static final boolean $assertionsDisabled = !IceBox/_ServiceManagerDelD.desiredAssertionStatus();

	public _ServiceManagerDelD()
	{
	}

	public void addObserver(ServiceObserverPrx observer, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "addObserver", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(observer) {

			final Current val$__current;
			final ServiceObserverPrx val$observer;
			final _ServiceManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceManager __servant = null;
				try
				{
					__servant = (ServiceManager)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.addObserver(observer, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ServiceManagerDelD.this;
				__current = current;
				observer = serviceobserverprx;
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

	public Map getSliceChecksums(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		SliceChecksumDictHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getSliceChecksums", OperationMode.Nonmutating, __ctx);
		__result = new SliceChecksumDictHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final SliceChecksumDictHolder val$__result;
			final _ServiceManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceManager __servant = null;
				try
				{
					__servant = (ServiceManager)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getSliceChecksums(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ServiceManagerDelD.this;
				__current = current;
				__result = slicechecksumdictholder;
				super(x0);
			}
		};
		Map map;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		map = __result.value;
		__direct.destroy();
		return map;
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

	public void shutdown(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "shutdown", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _ServiceManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceManager __servant = null;
				try
				{
					__servant = (ServiceManager)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.shutdown(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ServiceManagerDelD.this;
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

	public void startService(String service, Map __ctx)
		throws LocalExceptionWrapper, AlreadyStartedException, NoSuchServiceException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "startService", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(service) {

			final Current val$__current;
			final String val$service;
			final _ServiceManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceManager __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (ServiceManager)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.startService(service, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _ServiceManagerDelD.this;
				__current = current;
				service = s;
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
		AlreadyStartedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void stopService(String service, Map __ctx)
		throws LocalExceptionWrapper, AlreadyStoppedException, NoSuchServiceException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "stopService", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(service) {

			final Current val$__current;
			final String val$service;
			final _ServiceManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ServiceManager __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (ServiceManager)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.stopService(service, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _ServiceManagerDelD.this;
				__current = current;
				service = s;
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
		AlreadyStoppedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

}
