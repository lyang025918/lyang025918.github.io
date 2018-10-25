// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_AdminDel, Admin

public final class _AdminDelD extends _ObjectDelD
	implements _AdminDel
{

	static final boolean $assertionsDisabled = !Glacier2/_AdminDelD.desiredAssertionStatus();

	public _AdminDelD()
	{
	}

	public void shutdown(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "shutdown", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.shutdown(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
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

}
