// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _UserAccountMapperDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			UserAccountNotFoundException, _UserAccountMapperDel, UserAccountMapper

public final class _UserAccountMapperDelD extends _ObjectDelD
	implements _UserAccountMapperDel
{

	static final boolean $assertionsDisabled = !IceGrid/_UserAccountMapperDelD.desiredAssertionStatus();

	public _UserAccountMapperDelD()
	{
	}

	public String getUserAccount(String user, Map __ctx)
		throws LocalExceptionWrapper, UserAccountNotFoundException
	{
		final Current __current;
		final StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getUserAccount", OperationMode.Normal, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(user) {

			final Current val$__current;
			final StringHolder val$__result;
			final String val$user;
			final _UserAccountMapperDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				UserAccountMapper __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (UserAccountMapper)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getUserAccount(user, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _UserAccountMapperDelD.this;
				__current = current;
				__result = stringholder;
				user = s;
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
		UserAccountNotFoundException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
