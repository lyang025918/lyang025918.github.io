// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceObserverDelM.java

package IceBox;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceBox:
//			_ServiceObserverDel

public final class _ServiceObserverDelM extends _ObjectDelM
	implements _ServiceObserverDel
{

	public _ServiceObserverDelM()
	{
	}

	public void servicesStarted(String services[], Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("servicesStarted", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			StringSeqHelper.write(__os, services);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
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
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void servicesStopped(String services[], Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("servicesStopped", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			StringSeqHelper.write(__os, services);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
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
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
