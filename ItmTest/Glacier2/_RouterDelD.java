// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			SessionNotExistException, _RouterDel, CannotCreateSessionException, PermissionDeniedException, 
//			SessionPrx, Router

public final class _RouterDelD extends _ObjectDelD
	implements _RouterDel
{

	static final boolean $assertionsDisabled = !Glacier2/_RouterDelD.desiredAssertionStatus();

	public _RouterDelD()
	{
	}

	public SessionPrx createSession(String userId, String password, Map __ctx)
		throws LocalExceptionWrapper, CannotCreateSessionException, PermissionDeniedException
	{
		throw new CollocationOptimizationException();
	}

	public SessionPrx createSessionFromSecureConnection(Map __ctx)
		throws LocalExceptionWrapper, CannotCreateSessionException, PermissionDeniedException
	{
		throw new CollocationOptimizationException();
	}

	public void destroySession(Map __ctx)
		throws LocalExceptionWrapper, SessionNotExistException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "destroySession", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.destroySession(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
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
		SessionNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public String getCategoryForClient(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getCategoryForClient", OperationMode.Nonmutating, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringHolder val$__result;
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getCategoryForClient(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
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

	public long getSessionTimeout(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		LongHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getSessionTimeout", OperationMode.Nonmutating, __ctx);
		__result = new LongHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final LongHolder val$__result;
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant = null;
				try
				{
					__servant = (Router)__obj;
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
				this$0 = _RouterDelD.this;
				__current = current;
				__result = longholder;
				super(x0);
			}
		};
		long l;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		l = __result.value;
		__direct.destroy();
		return l;
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

	public void refreshSession(Map __ctx)
		throws LocalExceptionWrapper, SessionNotExistException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "refreshSession", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.refreshSession(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
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
		SessionNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public ObjectPrx[] addProxies(ObjectPrx proxies[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectProxySeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "addProxies", OperationMode.Idempotent, __ctx);
		__result = new ObjectProxySeqHolder();
		__direct = null;
		__direct = new Direct(proxies) {

			final Current val$__current;
			final ObjectProxySeqHolder val$__result;
			final ObjectPrx val$proxies[];
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.addProxies(proxies, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
				__current = current;
				__result = objectproxyseqholder;
				proxies = aobjectprx;
				super(x0);
			}
		};
		ObjectPrx aobjectprx[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		aobjectprx = __result.value;
		__direct.destroy();
		return aobjectprx;
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

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public void addProxy(ObjectPrx proxy, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "addProxy", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(proxy) {

			final Current val$__current;
			final ObjectPrx val$proxy;
			final _RouterDelD this$0;

			/**
			 * @deprecated Method run is deprecated
			 */

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.addProxy(proxy, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
				__current = current;
				proxy = objectprx;
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

	public ObjectPrx getClientProxy(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getClientProxy", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getClientProxy(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
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

	public ObjectPrx getServerProxy(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getServerProxy", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final _RouterDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Router __servant = null;
				try
				{
					__servant = (Router)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getServerProxy(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _RouterDelD.this;
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

}
