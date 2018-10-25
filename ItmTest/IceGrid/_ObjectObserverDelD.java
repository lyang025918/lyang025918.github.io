// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectObserverDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_ObjectObserverDel, ObjectInfo, ObjectObserver

public final class _ObjectObserverDelD extends _ObjectDelD
	implements _ObjectObserverDel
{

	static final boolean $assertionsDisabled = !IceGrid/_ObjectObserverDelD.desiredAssertionStatus();

	public _ObjectObserverDelD()
	{
	}

	public void objectAdded(ObjectInfo info, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "objectAdded", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(info) {

			final Current val$__current;
			final ObjectInfo val$info;
			final _ObjectObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ObjectObserver __servant = null;
				try
				{
					__servant = (ObjectObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.objectAdded(info, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ObjectObserverDelD.this;
				__current = current;
				info = objectinfo;
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

	public void objectInit(ObjectInfo objects[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "objectInit", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(objects) {

			final Current val$__current;
			final ObjectInfo val$objects[];
			final _ObjectObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ObjectObserver __servant = null;
				try
				{
					__servant = (ObjectObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.objectInit(objects, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ObjectObserverDelD.this;
				__current = current;
				objects = aobjectinfo;
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

	public void objectRemoved(Identity id, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "objectRemoved", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final Identity val$id;
			final _ObjectObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ObjectObserver __servant = null;
				try
				{
					__servant = (ObjectObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.objectRemoved(id, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ObjectObserverDelD.this;
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

	public void objectUpdated(ObjectInfo info, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "objectUpdated", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(info) {

			final Current val$__current;
			final ObjectInfo val$info;
			final _ObjectObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ObjectObserver __servant = null;
				try
				{
					__servant = (ObjectObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.objectUpdated(info, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ObjectObserverDelD.this;
				__current = current;
				info = objectinfo;
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
