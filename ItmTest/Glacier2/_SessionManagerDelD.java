// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionManagerDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			SessionPrxHolder, CannotCreateSessionException, _SessionManagerDel, SessionControlPrx, 
//			SessionPrx, SessionManager

public final class _SessionManagerDelD extends _ObjectDelD
	implements _SessionManagerDel
{

	static final boolean $assertionsDisabled = !Glacier2/_SessionManagerDelD.desiredAssertionStatus();

	public _SessionManagerDelD()
	{
	}

	public SessionPrx create(final String userId, SessionControlPrx control, Map __ctx)
		throws LocalExceptionWrapper, CannotCreateSessionException
	{
		final Current __current;
		final SessionPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "create", OperationMode.Normal, __ctx);
		__result = new SessionPrxHolder();
		__direct = null;
		__direct = new Direct(control) {

			final Current val$__current;
			final SessionPrxHolder val$__result;
			final String val$userId;
			final SessionControlPrx val$control;
			final _SessionManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SessionManager __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (SessionManager)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.create(userId, control, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _SessionManagerDelD.this;
				__current = current;
				__result = sessionprxholder;
				userId = s;
				control = sessioncontrolprx;
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
		CannotCreateSessionException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
