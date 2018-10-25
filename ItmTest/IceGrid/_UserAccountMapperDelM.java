// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _UserAccountMapperDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			UserAccountNotFoundException, _UserAccountMapperDel

public final class _UserAccountMapperDelM extends _ObjectDelM
	implements _UserAccountMapperDel
{

	public _UserAccountMapperDelM()
	{
	}

	public String getUserAccount(String user, Map __ctx)
		throws LocalExceptionWrapper, UserAccountNotFoundException
	{
		Outgoing __og = __handler.getOutgoing("getUserAccount", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(user);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		String s;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserAccountNotFoundException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			String __ret = __is.readString();
			__is.endReadEncaps();
			s = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return s;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
