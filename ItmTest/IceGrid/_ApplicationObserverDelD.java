// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ApplicationObserverDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.List;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_ApplicationObserverDel, ApplicationInfo, ApplicationUpdateInfo, ApplicationObserver

public final class _ApplicationObserverDelD extends _ObjectDelD
	implements _ApplicationObserverDel
{

	static final boolean $assertionsDisabled = !IceGrid/_ApplicationObserverDelD.desiredAssertionStatus();

	public _ApplicationObserverDelD()
	{
	}

	public void applicationAdded(final int serial, ApplicationInfo desc, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "applicationAdded", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(desc) {

			final Current val$__current;
			final int val$serial;
			final ApplicationInfo val$desc;
			final _ApplicationObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ApplicationObserver __servant = null;
				try
				{
					__servant = (ApplicationObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.applicationAdded(serial, desc, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ApplicationObserverDelD.this;
				__current = current;
				serial = i;
				desc = applicationinfo;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_122;
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

	public void applicationInit(final int serial, List applications, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "applicationInit", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(applications) {

			final Current val$__current;
			final int val$serial;
			final List val$applications;
			final _ApplicationObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ApplicationObserver __servant = null;
				try
				{
					__servant = (ApplicationObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.applicationInit(serial, applications, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ApplicationObserverDelD.this;
				__current = current;
				serial = i;
				applications = list;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_122;
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

	public void applicationRemoved(final int serial, String name, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "applicationRemoved", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final int val$serial;
			final String val$name;
			final _ApplicationObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ApplicationObserver __servant = null;
				try
				{
					__servant = (ApplicationObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.applicationRemoved(serial, name, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ApplicationObserverDelD.this;
				__current = current;
				serial = i;
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
		break MISSING_BLOCK_LABEL_122;
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

	public void applicationUpdated(final int serial, ApplicationUpdateInfo desc, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "applicationUpdated", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(desc) {

			final Current val$__current;
			final int val$serial;
			final ApplicationUpdateInfo val$desc;
			final _ApplicationObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				ApplicationObserver __servant = null;
				try
				{
					__servant = (ApplicationObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.applicationUpdated(serial, desc, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _ApplicationObserverDelD.this;
				__current = current;
				serial = i;
				desc = applicationupdateinfo;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_122;
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
