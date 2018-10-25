// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryObserverDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_RegistryObserverDel, RegistryInfo, RegistryObserver

public final class _RegistryObserverDelD extends _ObjectDelD
	implements _RegistryObserverDel
{

	static final boolean $assertionsDisabled = !IceGrid/_RegistryObserverDelD.desiredAssertionStatus();

	public _RegistryObserverDelD()
	{
	}

	public void registryDown(String name, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "registryDown", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final String val$name;
			final _RegistryObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				RegistryObserver __servant = null;
				try
				{
					__servant = (RegistryObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.registryDown(name, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RegistryObserverDelD.this;
				__current = current;
				name = s;
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

	public void registryInit(RegistryInfo registries[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "registryInit", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(registries) {

			final Current val$__current;
			final RegistryInfo val$registries[];
			final _RegistryObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				RegistryObserver __servant = null;
				try
				{
					__servant = (RegistryObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.registryInit(registries, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RegistryObserverDelD.this;
				__current = current;
				registries = aregistryinfo;
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

	public void registryUp(RegistryInfo node, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "registryUp", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(node) {

			final Current val$__current;
			final RegistryInfo val$node;
			final _RegistryObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				RegistryObserver __servant = null;
				try
				{
					__servant = (RegistryObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.registryUp(node, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RegistryObserverDelD.this;
				__current = current;
				node = registryinfo;
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
