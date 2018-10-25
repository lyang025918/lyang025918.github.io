// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			QueryPrxHolder, RegistryPrxHolder, _LocatorDel, QueryPrx, 
//			RegistryPrx, Locator

public final class _LocatorDelD extends _ObjectDelD
	implements _LocatorDel
{

	static final boolean $assertionsDisabled = !IceGrid/_LocatorDelD.desiredAssertionStatus();

	public _LocatorDelD()
	{
	}

	public ObjectPrx findAdapterById(String id, Map __ctx)
		throws LocalExceptionWrapper, AdapterNotFoundException
	{
		throw new CollocationOptimizationException();
	}

	public ObjectPrx findObjectById(Identity id, Map __ctx)
		throws LocalExceptionWrapper, ObjectNotFoundException
	{
		throw new CollocationOptimizationException();
	}

	public LocatorRegistryPrx getRegistry(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		LocatorRegistryPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getRegistry", OperationMode.Nonmutating, __ctx);
		__result = new LocatorRegistryPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final LocatorRegistryPrxHolder val$__result;
			final _LocatorDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Locator __servant = null;
				try
				{
					__servant = (Locator)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getRegistry(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _LocatorDelD.this;
				__current = current;
				__result = locatorregistryprxholder;
				super(x0);
			}
		};
		LocatorRegistryPrx locatorregistryprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		locatorregistryprx = __result.value;
		__direct.destroy();
		return locatorregistryprx;
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

	public QueryPrx getLocalQuery(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		QueryPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getLocalQuery", OperationMode.Idempotent, __ctx);
		__result = new QueryPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final QueryPrxHolder val$__result;
			final _LocatorDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Locator __servant = null;
				try
				{
					__servant = (Locator)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getLocalQuery(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _LocatorDelD.this;
				__current = current;
				__result = queryprxholder;
				super(x0);
			}
		};
		QueryPrx queryprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		queryprx = __result.value;
		__direct.destroy();
		return queryprx;
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

	public RegistryPrx getLocalRegistry(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		RegistryPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getLocalRegistry", OperationMode.Idempotent, __ctx);
		__result = new RegistryPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final RegistryPrxHolder val$__result;
			final _LocatorDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Locator __servant = null;
				try
				{
					__servant = (Locator)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getLocalRegistry(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _LocatorDelD.this;
				__current = current;
				__result = registryprxholder;
				super(x0);
			}
		};
		RegistryPrx registryprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		registryprx = __result.value;
		__direct.destroy();
		return registryprx;
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
