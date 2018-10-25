// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDelM.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.LocalExceptionWrapper;
import IceInternal.Outgoing;
import IceInternal.RequestHandler;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDelM, LocalException, UserException, UnknownUserException, 
//			_RouterDel, OperationMode, ObjectProxySeqHelper, ObjectPrx

public final class _RouterDelM extends _ObjectDelM
	implements _RouterDel
{

	public _RouterDelM()
	{
	}

	public ObjectPrx[] addProxies(ObjectPrx proxies[], Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("addProxies", OperationMode.Idempotent, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			ObjectProxySeqHelper.write(__os, proxies);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectPrx aobjectprx[];
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
			ObjectPrx __ret[] = ObjectProxySeqHelper.read(__is);
			__is.endReadEncaps();
			aobjectprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return aobjectprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void addProxy(ObjectPrx proxy, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("addProxy", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeProxy(proxy);
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

	public ObjectPrx getClientProxy(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getClientProxy", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		ObjectPrx objectprx;
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

	public ObjectPrx getServerProxy(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getServerProxy", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		ObjectPrx objectprx;
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
}
