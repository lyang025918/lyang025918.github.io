// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectDelD.java

package Ice;

import IceInternal.Direct;
import IceInternal.Instance;
import IceInternal.LocalExceptionWrapper;
import IceInternal.Reference;
import IceInternal.RequestHandler;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package Ice:
//			Current, BooleanHolder, UserException, UnknownUserException, 
//			StringSeqHolder, StringHolder, CollocationOptimizationException, _ObjectDel, 
//			OperationMode, Object, DispatchStatus, ImplicitContextI, 
//			ObjectAdapter, ByteSeqHolder

public class _ObjectDelD
	implements _ObjectDel
{

	protected Reference __reference;
	protected ObjectAdapter __adapter;
	static final boolean $assertionsDisabled = !Ice/_ObjectDelD.desiredAssertionStatus();

	public _ObjectDelD()
	{
	}

	public boolean ice_isA(final String __id, Map __context)
		throws LocalExceptionWrapper
	{
		final BooleanHolder __result;
		Direct __direct;
		Throwable __ex;
		final Current __current = new Current();
		__initCurrent(__current, "ice_isA", OperationMode.Nonmutating, __context);
		__result = new BooleanHolder();
		__direct = null;
		try
		{
			__direct = new Direct(__current) {

				final BooleanHolder val$__result;
				final String val$__id;
				final Current val$__current;
				final _ObjectDelD this$0;

				public DispatchStatus run(Object __servant)
				{
					__result.value = __servant.ice_isA(__id, __current);
					return DispatchStatus.DispatchOK;
				}

			
				throws UserException
			{
				this$0 = _ObjectDelD.this;
				__result = booleanholder;
				__id = s;
				__current = current;
				super(x0);
			}
			};
		}
		// Misplaced declaration of an exception variable
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		boolean flag;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		flag = __result.value;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		return flag;
		__status;
		LocalExceptionWrapper.throwWrapper(__status);
		try
		{
			__direct.destroy();
		}
		// Misplaced declaration of an exception variable
		catch (DispatchStatus __status)
		{
			LocalExceptionWrapper.throwWrapper(__status);
		}
		break MISSING_BLOCK_LABEL_162;
		Exception exception;
		exception;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		throw exception;
		return false;
	}

	public void ice_ping(Map __context)
		throws LocalExceptionWrapper
	{
		Direct __direct;
		Throwable __ex;
		final Current __current = new Current();
		__initCurrent(__current, "ice_ping", OperationMode.Nonmutating, __context);
		__direct = null;
		try
		{
			__direct = new Direct(__current) {

				final Current val$__current;
				final _ObjectDelD this$0;

				public DispatchStatus run(Object __servant)
				{
					__servant.ice_ping(__current);
					return DispatchStatus.DispatchOK;
				}

			
				throws UserException
			{
				this$0 = _ObjectDelD.this;
				__current = current;
				super(x0);
			}
			};
		}
		// Misplaced declaration of an exception variable
		catch (Throwable __ex)
		{
			UnknownUserException ex = new UnknownUserException(__ex.ice_name(), __ex);
			throw new LocalExceptionWrapper(ex, false);
		}
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		try
		{
			__direct.destroy();
		}
		// Misplaced declaration of an exception variable
		catch (DispatchStatus __status)
		{
			LocalExceptionWrapper.throwWrapper(__status);
		}
		break MISSING_BLOCK_LABEL_158;
		__status;
		LocalExceptionWrapper.throwWrapper(__status);
		try
		{
			__direct.destroy();
		}
		// Misplaced declaration of an exception variable
		catch (DispatchStatus __status)
		{
			LocalExceptionWrapper.throwWrapper(__status);
		}
		break MISSING_BLOCK_LABEL_158;
		Exception exception;
		exception;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		throw exception;
	}

	public String[] ice_ids(Map __context)
		throws LocalExceptionWrapper
	{
		final StringSeqHolder __result;
		Direct __direct;
		Throwable __ex;
		final Current __current = new Current();
		__initCurrent(__current, "ice_ids", OperationMode.Nonmutating, __context);
		__result = new StringSeqHolder();
		__direct = null;
		try
		{
			__direct = new Direct(__current) {

				final StringSeqHolder val$__result;
				final Current val$__current;
				final _ObjectDelD this$0;

				public DispatchStatus run(Object __servant)
				{
					__result.value = __servant.ice_ids(__current);
					return DispatchStatus.DispatchOK;
				}

			
				throws UserException
			{
				this$0 = _ObjectDelD.this;
				__result = stringseqholder;
				__current = current;
				super(x0);
			}
			};
		}
		// Misplaced declaration of an exception variable
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		return as;
		__status;
		LocalExceptionWrapper.throwWrapper(__status);
		try
		{
			__direct.destroy();
		}
		// Misplaced declaration of an exception variable
		catch (DispatchStatus __status)
		{
			LocalExceptionWrapper.throwWrapper(__status);
		}
		break MISSING_BLOCK_LABEL_158;
		Exception exception;
		exception;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		throw exception;
		return __result.value;
	}

	public String ice_id(Map __context)
		throws LocalExceptionWrapper
	{
		final StringHolder __result;
		Direct __direct;
		Throwable __ex;
		final Current __current = new Current();
		__initCurrent(__current, "ice_id", OperationMode.Nonmutating, __context);
		__result = new StringHolder();
		__direct = null;
		try
		{
			__direct = new Direct(__current) {

				final StringHolder val$__result;
				final Current val$__current;
				final _ObjectDelD this$0;

				public DispatchStatus run(Object __servant)
				{
					__result.value = __servant.ice_id(__current);
					return DispatchStatus.DispatchOK;
				}

			
				throws UserException
			{
				this$0 = _ObjectDelD.this;
				__result = stringholder;
				__current = current;
				super(x0);
			}
			};
		}
		// Misplaced declaration of an exception variable
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		String s;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		s = __result.value;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		return s;
		__status;
		LocalExceptionWrapper.throwWrapper(__status);
		try
		{
			__direct.destroy();
		}
		// Misplaced declaration of an exception variable
		catch (DispatchStatus __status)
		{
			LocalExceptionWrapper.throwWrapper(__status);
		}
		break MISSING_BLOCK_LABEL_158;
		Exception exception;
		exception;
		try
		{
			__direct.destroy();
		}
		catch (Throwable __ex)
		{
			LocalExceptionWrapper.throwWrapper(__ex);
		}
		throw exception;
		return __result.value;
	}

	public boolean ice_invoke(String operation, OperationMode mode, byte inParams[], ByteSeqHolder outParams, Map context)
	{
		throw new CollocationOptimizationException();
	}

	public void ice_flushBatchRequests()
	{
		throw new CollocationOptimizationException();
	}

	public RequestHandler __getRequestHandler()
	{
		throw new CollocationOptimizationException();
	}

	public void __setRequestHandler(RequestHandler handler)
	{
		throw new CollocationOptimizationException();
	}

	final void __copyFrom(_ObjectDelD from)
	{
		if (!$assertionsDisabled && __reference != null)
			throw new AssertionError();
		if (!$assertionsDisabled && __adapter != null)
		{
			throw new AssertionError();
		} else
		{
			__reference = from.__reference;
			__adapter = from.__adapter;
			return;
		}
	}

	protected final void __initCurrent(Current current, String op, OperationMode mode, Map context)
	{
		current.adapter = __adapter;
		current.id = __reference.getIdentity();
		current.facet = __reference.getFacet();
		current.operation = op;
		current.mode = mode;
		if (context != null)
		{
			current.ctx = context;
		} else
		{
			ImplicitContextI implicitContext = __reference.getInstance().getImplicitContext();
			Map prxContext = __reference.getContext();
			if (implicitContext == null)
				current.ctx = new HashMap(prxContext);
			else
				current.ctx = implicitContext.combine(prxContext);
		}
		current.requestId = -1;
	}

	public void setup(Reference ref, ObjectAdapter adapter)
	{
		if (!$assertionsDisabled && __reference != null)
			throw new AssertionError();
		if (!$assertionsDisabled && __adapter != null)
		{
			throw new AssertionError();
		} else
		{
			__reference = ref;
			__adapter = adapter;
			return;
		}
	}

}
