// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocalExceptionWrapper.java

package IceInternal;

import Ice.*;

// Referenced classes of package IceInternal:
//			Ex

public class LocalExceptionWrapper extends Exception
{

	private LocalException _ex;
	private boolean _retry;

	public LocalExceptionWrapper(LocalException ex, boolean retry)
	{
		_ex = ex;
		_retry = retry;
	}

	public LocalExceptionWrapper(LocalExceptionWrapper ex)
	{
		_ex = ex.get();
		_retry = ex._retry;
	}

	public LocalException get()
	{
		return _ex;
	}

	public boolean retry()
	{
		return _retry;
	}

	public static void throwWrapper(Throwable ex)
		throws LocalExceptionWrapper
	{
		if (ex instanceof UserException)
			throw new LocalExceptionWrapper(new UnknownUserException(((UserException)ex).ice_name()), false);
		if (ex instanceof LocalException)
		{
			if ((ex instanceof UnknownException) || (ex instanceof ObjectNotExistException) || (ex instanceof OperationNotExistException) || (ex instanceof FacetNotExistException))
				throw new LocalExceptionWrapper((LocalException)ex, false);
			else
				throw new LocalExceptionWrapper(new UnknownLocalException(((LocalException)ex).ice_name(), ex), false);
		} else
		{
			throw new LocalExceptionWrapper(new UnknownException(Ex.toString(ex), ex), false);
		}
	}
}
