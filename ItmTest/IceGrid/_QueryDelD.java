// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _QueryDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_QueryDel, LoadSample, Query

public final class _QueryDelD extends _ObjectDelD
	implements _QueryDel
{

	static final boolean $assertionsDisabled = !IceGrid/_QueryDelD.desiredAssertionStatus();

	public _QueryDelD()
	{
	}

	public ObjectPrx[] findAllObjectsByType(String type, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectProxySeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "findAllObjectsByType", OperationMode.Nonmutating, __ctx);
		__result = new ObjectProxySeqHolder();
		__direct = null;
		__direct = new Direct(type) {

			final Current val$__current;
			final ObjectProxySeqHolder val$__result;
			final String val$type;
			final _QueryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Query __servant = null;
				try
				{
					__servant = (Query)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.findAllObjectsByType(type, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _QueryDelD.this;
				__current = current;
				__result = objectproxyseqholder;
				type = s;
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

	public ObjectPrx[] findAllReplicas(ObjectPrx proxy, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectProxySeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "findAllReplicas", OperationMode.Idempotent, __ctx);
		__result = new ObjectProxySeqHolder();
		__direct = null;
		__direct = new Direct(proxy) {

			final Current val$__current;
			final ObjectProxySeqHolder val$__result;
			final ObjectPrx val$proxy;
			final _QueryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Query __servant = null;
				try
				{
					__servant = (Query)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.findAllReplicas(proxy, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _QueryDelD.this;
				__current = current;
				__result = objectproxyseqholder;
				proxy = objectprx;
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

	public ObjectPrx findObjectById(Identity id, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "findObjectById", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final Identity val$id;
			final _QueryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Query __servant = null;
				try
				{
					__servant = (Query)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.findObjectById(id, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _QueryDelD.this;
				__current = current;
				__result = objectprxholder;
				id = identity;
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

	public ObjectPrx findObjectByType(String type, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "findObjectByType", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(type) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final String val$type;
			final _QueryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Query __servant = null;
				try
				{
					__servant = (Query)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.findObjectByType(type, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _QueryDelD.this;
				__current = current;
				__result = objectprxholder;
				type = s;
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

	public ObjectPrx findObjectByTypeOnLeastLoadedNode(final String type, LoadSample sample, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "findObjectByTypeOnLeastLoadedNode", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(sample) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final String val$type;
			final LoadSample val$sample;
			final _QueryDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Query __servant = null;
				try
				{
					__servant = (Query)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.findObjectByTypeOnLeastLoadedNode(type, sample, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _QueryDelD.this;
				__current = current;
				__result = objectprxholder;
				type = s;
				sample = loadsample;
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
