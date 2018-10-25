// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AdminSessionPrxHolder, PermissionDeniedException, SessionPrxHolder, _RegistryDel, 
//			AdminSessionPrx, SessionPrx, Registry

public final class _RegistryDelD extends _ObjectDelD
	implements _RegistryDel
{

	static final boolean $assertionsDisabled = !IceGrid/_RegistryDelD.desiredAssertionStatus();

	public _RegistryDelD()
	{
	}

	public AdminSessionPrx createAdminSession(final String userId, String password, Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
	{
		final Current __current;
		final AdminSessionPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "createAdminSession", OperationMode.Normal, __ctx);
		__result = new AdminSessionPrxHolder();
		__direct = null;
		__direct = new Direct(password) {

			final Current val$__current;
			final AdminSessionPrxHolder val$__result;
			final String val$userId;
			final String val$password;
			final _RegistryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Registry __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Registry)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.createAdminSession(userId, password, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _RegistryDelD.this;
				__current = current;
				__result = adminsessionprxholder;
				userId = s;
				password = s1;
				super(x0);
			}
		};
		AdminSessionPrx adminsessionprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		adminsessionprx = __result.value;
		__direct.destroy();
		return adminsessionprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		PermissionDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public AdminSessionPrx createAdminSessionFromSecureConnection(Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
	{
		final Current __current;
		AdminSessionPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "createAdminSessionFromSecureConnection", OperationMode.Normal, __ctx);
		__result = new AdminSessionPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final AdminSessionPrxHolder val$__result;
			final _RegistryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Registry __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Registry)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.createAdminSessionFromSecureConnection(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _RegistryDelD.this;
				__current = current;
				__result = adminsessionprxholder;
				super(x0);
			}
		};
		AdminSessionPrx adminsessionprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		adminsessionprx = __result.value;
		__direct.destroy();
		return adminsessionprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		PermissionDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public SessionPrx createSession(final String userId, String password, Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
	{
		final Current __current;
		final SessionPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "createSession", OperationMode.Normal, __ctx);
		__result = new SessionPrxHolder();
		__direct = null;
		__direct = new Direct(password) {

			final Current val$__current;
			final SessionPrxHolder val$__result;
			final String val$userId;
			final String val$password;
			final _RegistryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Registry __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Registry)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.createSession(userId, password, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _RegistryDelD.this;
				__current = current;
				__result = sessionprxholder;
				userId = s;
				password = s1;
				super(x0);
			}
		};
		SessionPrx sessionprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		sessionprx = __result.value;
		__direct.destroy();
		return sessionprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		PermissionDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public SessionPrx createSessionFromSecureConnection(Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
	{
		final Current __current;
		SessionPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "createSessionFromSecureConnection", OperationMode.Normal, __ctx);
		__result = new SessionPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final SessionPrxHolder val$__result;
			final _RegistryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Registry __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Registry)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.createSessionFromSecureConnection(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _RegistryDelD.this;
				__current = current;
				__result = sessionprxholder;
				super(x0);
			}
		};
		SessionPrx sessionprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		sessionprx = __result.value;
		__direct.destroy();
		return sessionprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		PermissionDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public int getSessionTimeout(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getSessionTimeout", OperationMode.Nonmutating, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final IntHolder val$__result;
			final _RegistryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Registry __servant = null;
				try
				{
					__servant = (Registry)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getSessionTimeout(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RegistryDelD.this;
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
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
