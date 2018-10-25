// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_LocatorDel, QueryPrxHelper, RegistryPrxHelper, QueryPrx, 
//			RegistryPrx

public final class _LocatorDelM extends _ObjectDelM
	implements _LocatorDel
{

	public _LocatorDelM()
	{
	}

	public ObjectPrx findAdapterById(String id, Map __ctx)
		throws LocalExceptionWrapper, AdapterNotFoundException
	{
		Outgoing __og = __handler.getOutgoing("findAdapterById", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectPrx objectprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AdapterNotFoundException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ObjectPrx __ret = __is.readProxy();
			__is.endReadEncaps();
			objectprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return objectprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ObjectPrx findObjectById(Identity id, Map __ctx)
		throws LocalExceptionWrapper, ObjectNotFoundException
	{
		Outgoing __og = __handler.getOutgoing("findObjectById", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			id.__write(__os);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectPrx objectprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (ObjectNotFoundException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ObjectPrx __ret = __is.readProxy();
			__is.endReadEncaps();
			objectprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return objectprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public LocatorRegistryPrx getRegistry(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getRegistry", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		LocatorRegistryPrx locatorregistryprx;
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
			LocatorRegistryPrx __ret = LocatorRegistryPrxHelper.__read(__is);
			__is.endReadEncaps();
			locatorregistryprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return locatorregistryprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public QueryPrx getLocalQuery(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getLocalQuery", OperationMode.Idempotent, __ctx);
		boolean __ok = __og.invoke();
		QueryPrx queryprx;
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
			QueryPrx __ret = QueryPrxHelper.__read(__is);
			__is.endReadEncaps();
			queryprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return queryprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public RegistryPrx getLocalRegistry(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getLocalRegistry", OperationMode.Idempotent, __ctx);
		boolean __ok = __og.invoke();
		RegistryPrx registryprx;
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
			RegistryPrx __ret = RegistryPrxHelper.__read(__is);
			__is.endReadEncaps();
			registryprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return registryprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
