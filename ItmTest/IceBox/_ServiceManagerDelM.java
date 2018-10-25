// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceManagerDelM.java

package IceBox;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, _ServiceManagerDel, 
//			ServiceObserverPrxHelper, ServiceObserverPrx

public final class _ServiceManagerDelM extends _ObjectDelM
	implements _ServiceManagerDel
{

	public _ServiceManagerDelM()
	{
	}

	public void addObserver(ServiceObserverPrx observer, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("addObserver", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			ServiceObserverPrxHelper.__write(__os, observer);
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

	public Map getSliceChecksums(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getSliceChecksums", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		Map map;
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
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			Map __ret = SliceChecksumDictHelper.read(__is);
			__is.endReadEncaps();
			map = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return map;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void shutdown(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("shutdown", OperationMode.Normal, __ctx);
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

	public void startService(String service, Map __ctx)
		throws LocalExceptionWrapper, AlreadyStartedException, NoSuchServiceException
	{
		Outgoing __og = __handler.getOutgoing("startService", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(service);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AlreadyStartedException __ex)
				{
					throw __ex;
				}
				catch (NoSuchServiceException __ex)
				{
					throw __ex;
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

	public void stopService(String service, Map __ctx)
		throws LocalExceptionWrapper, AlreadyStoppedException, NoSuchServiceException
	{
		Outgoing __og = __handler.getOutgoing("stopService", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(service);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AlreadyStoppedException __ex)
				{
					throw __ex;
				}
				catch (NoSuchServiceException __ex)
				{
					throw __ex;
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
