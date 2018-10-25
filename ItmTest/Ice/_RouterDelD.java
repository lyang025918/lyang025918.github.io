// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDelD.java

package Ice;

import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDelD, Current, ObjectProxySeqHolder, SystemException, 
//			ObjectPrxHolder, _RouterDel, OperationMode, Object, 
//			DispatchStatus, ObjectPrx, Router, OperationNotExistException, 
//			UserException

public final class _RouterDelD extends _ObjectDelD
	implements _RouterDel
{

	static final boolean $assertionsDisabled = !Ice/_RouterDelD.desiredAssertionStatus();

	public _RouterDelD()
	{
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

			public DispatchStatus run(Object __obj)
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

			public DispatchStatus run(Object __obj)
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

			public DispatchStatus run(Object __obj)
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

			public DispatchStatus run(Object __obj)
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
