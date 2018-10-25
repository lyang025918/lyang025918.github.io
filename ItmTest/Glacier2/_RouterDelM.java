// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDelM.java

package Glacier2;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, PermissionDeniedException, SessionNotExistException, _RouterDel, 
//			SessionPrxHelper, SessionPrx

public final class _RouterDelM extends _ObjectDelM
	implements _RouterDel
{

	public _RouterDelM()
	{
	}

	public SessionPrx createSession(String userId, String password, Map __ctx)
		throws LocalExceptionWrapper, CannotCreateSessionException, PermissionDeniedException
	{
		Outgoing __og = __handler.getOutgoing("createSession", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(userId);
			__os.writeString(password);
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
				catch (PermissionDeniedException __ex)
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

	public SessionPrx createSessionFromSecureConnection(Map __ctx)
		throws LocalExceptionWrapper, CannotCreateSessionException, PermissionDeniedException
	{
		Outgoing __og = __handler.getOutgoing("createSessionFromSecureConnection", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
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
				catch (PermissionDeniedException __ex)
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

	public void destroySession(Map __ctx)
		throws LocalExceptionWrapper, SessionNotExistException
	{
		Outgoing __og = __handler.getOutgoing("destroySession", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (SessionNotExistException __ex)
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
		break MISSING_BLOCK_LABEL_105;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String getCategoryForClient(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getCategoryForClient", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		String s;
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

	public long getSessionTimeout(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getSessionTimeout", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		long l;
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
			long __ret = __is.readLong();
			__is.endReadEncaps();
			l = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return l;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void refreshSession(Map __ctx)
		throws LocalExceptionWrapper, SessionNotExistException
	{
		Outgoing __og = __handler.getOutgoing("refreshSession", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (SessionNotExistException __ex)
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
		break MISSING_BLOCK_LABEL_105;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
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
