// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionDelM.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			_SessionDel

public final class _SessionDelM extends _ObjectDelM
	implements _SessionDel
{

	public _SessionDelM()
	{
	}

	public void destroy(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("destroy", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_110;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
