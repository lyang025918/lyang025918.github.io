// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionManagerDelM.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, _SessionManagerDel, SessionControlPrxHelper, SessionPrxHelper, 
//			SessionControlPrx, SessionPrx

public final class _SessionManagerDelM extends _ObjectDelM
	implements _SessionManagerDel
{

	public _SessionManagerDelM()
	{
	}

	public SessionPrx create(String userId, SessionControlPrx control, Map __ctx)
		throws LocalExceptionWrapper, CannotCreateSessionException
	{
		Outgoing __og = __handler.getOutgoing("create", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(userId);
			SessionControlPrxHelper.__write(__os, control);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		SessionPrx sessionprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (CannotCreateSessionException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			SessionPrx __ret = SessionPrxHelper.__read(__is);
			__is.endReadEncaps();
			sessionprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return sessionprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
