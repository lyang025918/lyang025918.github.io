// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdapterObserverDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_AdapterObserverDel, AdapterInfo, AdapterObserver

public final class _AdapterObserverDelD extends _ObjectDelD
	implements _AdapterObserverDel
{

	static final boolean $assertionsDisabled = !IceGrid/_AdapterObserverDelD.desiredAssertionStatus();

	public _AdapterObserverDelD()
	{
	}

	public void adapterAdded(AdapterInfo info, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "adapterAdded", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(info) {

			final Current val$__current;
			final AdapterInfo val$info;
			final _AdapterObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdapterObserver __servant = null;
				try
				{
					__servant = (AdapterObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.adapterAdded(info, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdapterObserverDelD.this;
				__current = current;
				info = adapterinfo;
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

	public void adapterInit(AdapterInfo adpts[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "adapterInit", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(adpts) {

			final Current val$__current;
			final AdapterInfo val$adpts[];
			final _AdapterObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdapterObserver __servant = null;
				try
				{
					__servant = (AdapterObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.adapterInit(adpts, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdapterObserverDelD.this;
				__current = current;
				adpts = aadapterinfo;
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

	public void adapterRemoved(String id, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "adapterRemoved", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final String val$id;
			final _AdapterObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdapterObserver __servant = null;
				try
				{
					__servant = (AdapterObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.adapterRemoved(id, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdapterObserverDelD.this;
				__current = current;
				id = s;
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

	public void adapterUpdated(AdapterInfo info, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "adapterUpdated", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(info) {

			final Current val$__current;
			final AdapterInfo val$info;
			final _AdapterObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdapterObserver __servant = null;
				try
				{
					__servant = (AdapterObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.adapterUpdated(info, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdapterObserverDelD.this;
				__current = current;
				info = adapterinfo;
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
