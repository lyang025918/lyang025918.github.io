// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SSLPermissionsVerifierDelD.java

package Glacier2;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_SSLPermissionsVerifierDel, SSLInfo, SSLPermissionsVerifier

public final class _SSLPermissionsVerifierDelD extends _ObjectDelD
	implements _SSLPermissionsVerifierDel
{

	static final boolean $assertionsDisabled = !Glacier2/_SSLPermissionsVerifierDelD.desiredAssertionStatus();

	public _SSLPermissionsVerifierDelD()
	{
	}

	public boolean authorize(final SSLInfo info, StringHolder reason, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final BooleanHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "authorize", OperationMode.Nonmutating, __ctx);
		__result = new BooleanHolder();
		__direct = null;
		__direct = new Direct(reason) {

			final Current val$__current;
			final BooleanHolder val$__result;
			final SSLInfo val$info;
			final StringHolder val$reason;
			final _SSLPermissionsVerifierDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				SSLPermissionsVerifier __servant = null;
				try
				{
					__servant = (SSLPermissionsVerifier)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.authorize(info, reason, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _SSLPermissionsVerifierDelD.this;
				__current = current;
				__result = booleanholder;
				info = sslinfo;
				reason = stringholder;
				super(x0);
			}
		};
		boolean flag;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		flag = __result.value;
		__direct.destroy();
		return flag;
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
