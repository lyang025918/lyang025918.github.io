// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminSessionDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, AdminPrxHolder, FileIteratorPrxHolder, FileNotAvailableException, 
//			NodeNotExistException, NodeUnreachableException, RegistryNotExistException, RegistryUnreachableException, 
//			DeploymentException, ServerNotExistException, ObserverAlreadyRegisteredException, _AdminSessionDel, 
//			AdminPrx, FileIteratorPrx, RegistryObserverPrx, NodeObserverPrx, 
//			ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx, AdminSession

public final class _AdminSessionDelD extends _ObjectDelD
	implements _AdminSessionDel
{

	static final boolean $assertionsDisabled = !IceGrid/_AdminSessionDelD.desiredAssertionStatus();

	public _AdminSessionDelD()
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
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant = null;
				try
				{
					__servant = (AdminSession)__obj;
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
				this$0 = _AdminSessionDelD.this;
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

	public void finishUpdate(Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "finishUpdate", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.finishUpdate(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
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
		break MISSING_BLOCK_LABEL_114;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public AdminPrx getAdmin(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		AdminPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAdmin", OperationMode.Nonmutating, __ctx);
		__result = new AdminPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final AdminPrxHolder val$__result;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAdmin(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = adminprxholder;
				super(x0);
			}
		};
		AdminPrx adminprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		adminprx = __result.value;
		__direct.destroy();
		return adminprx;
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

	public ObjectPrx getAdminCallbackTemplate(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAdminCallbackTemplate", OperationMode.Idempotent, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAdminCallbackTemplate(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = objectprxholder;
				super(x0);
			}
		};
		ObjectPrx objectprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		objectprx = __result.value;
		__direct.destroy();
		return objectprx;
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

	public String getReplicaName(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getReplicaName", OperationMode.Idempotent, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringHolder val$__result;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getReplicaName(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = stringholder;
				super(x0);
			}
		};
		String s;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		s = __result.value;
		__direct.destroy();
		return s;
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
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant = null;
				try
				{
					__servant = (AdminSession)__obj;
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
				this$0 = _AdminSessionDelD.this;
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

	public FileIteratorPrx openNodeStdErr(final String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openNodeStdErr", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$name;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openNodeStdErr(name, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				name = s;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
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
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public FileIteratorPrx openNodeStdOut(final String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openNodeStdOut", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$name;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openNodeStdOut(name, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				name = s;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
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
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public FileIteratorPrx openRegistryStdErr(final String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openRegistryStdErr", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$name;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openRegistryStdErr(name, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				name = s;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
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
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public FileIteratorPrx openRegistryStdOut(final String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openRegistryStdOut", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$name;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openRegistryStdOut(name, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				name = s;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
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
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public FileIteratorPrx openServerLog(final String id, final String path, int count, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openServerLog", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$id;
			final String val$path;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openServerLog(id, path, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				id = s;
				path = s1;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public FileIteratorPrx openServerStdErr(final String id, int count, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openServerStdErr", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$id;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openServerStdErr(id, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				id = s;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public FileIteratorPrx openServerStdOut(final String id, int count, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final FileIteratorPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "openServerStdOut", OperationMode.Normal, __ctx);
		__result = new FileIteratorPrxHolder();
		__direct = null;
		__direct = new Direct(count) {

			final Current val$__current;
			final FileIteratorPrxHolder val$__result;
			final String val$id;
			final int val$count;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.openServerStdOut(id, count, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = fileiteratorprxholder;
				id = s;
				count = i;
				super(x0);
			}
		};
		FileIteratorPrx fileiteratorprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		fileiteratorprx = __result.value;
		__direct.destroy();
		return fileiteratorprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public void setObservers(final RegistryObserverPrx registryObs, final NodeObserverPrx nodeObs, final ApplicationObserverPrx appObs, final AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx)
		throws LocalExceptionWrapper, ObserverAlreadyRegisteredException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "setObservers", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(objObs) {

			final Current val$__current;
			final RegistryObserverPrx val$registryObs;
			final NodeObserverPrx val$nodeObs;
			final ApplicationObserverPrx val$appObs;
			final AdapterObserverPrx val$adptObs;
			final ObjectObserverPrx val$objObs;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				registryObs = registryobserverprx;
				nodeObs = nodeobserverprx;
				appObs = applicationobserverprx;
				adptObs = adapterobserverprx;
				objObs = objectobserverprx;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_133;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		ObserverAlreadyRegisteredException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void setObserversByIdentity(final Identity registryObs, final Identity nodeObs, final Identity appObs, final Identity adptObs, Identity objObs, Map __ctx)
		throws LocalExceptionWrapper, ObserverAlreadyRegisteredException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "setObserversByIdentity", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(objObs) {

			final Current val$__current;
			final Identity val$registryObs;
			final Identity val$nodeObs;
			final Identity val$appObs;
			final Identity val$adptObs;
			final Identity val$objObs;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				registryObs = identity;
				nodeObs = identity1;
				appObs = identity2;
				adptObs = identity3;
				objObs = identity4;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_133;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		ObserverAlreadyRegisteredException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public int startUpdate(Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException
	{
		final Current __current;
		IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "startUpdate", OperationMode.Normal, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IntHolder val$__result;
			final _AdminSessionDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				AdminSession __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (AdminSession)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.startUpdate(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminSessionDelD.this;
				__current = current;
				__result = intholder;
				super(x0);
			}
		};
		int i;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		i = __result.value;
		__direct.destroy();
		return i;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
