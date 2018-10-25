// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			PermissionDeniedException, _RegistryDel, AdminSessionPrxHelper, SessionPrxHelper, 
//			AdminSessionPrx, SessionPrx

public final class _RegistryDelM extends _ObjectDelM
	implements _RegistryDel
{

	public _RegistryDelM()
	{
	}

	public AdminSessionPrx createAdminSession(String userId, String password, Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
	{
		Outgoing __og = __handler.getOutgoing("createAdminSession", OperationMode.Normal, __ctx);
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
		AdminSessionPrx adminsessionprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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
			AdminSessionPrx __ret = AdminSessionPrxHelper.__read(__is);
			__is.endReadEncaps();
			adminsessionprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return adminsessionprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public AdminSessionPrx createAdminSessionFromSecureConnection(Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
	{
		Outgoing __og = __handler.getOutgoing("createAdminSessionFromSecureConnection", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		AdminSessionPrx adminsessionprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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
			AdminSessionPrx __ret = AdminSessionPrxHelper.__read(__is);
			__is.endReadEncaps();
			adminsessionprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return adminsessionprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public SessionPrx createSession(String userId, String password, Map __ctx)
		throws LocalExceptionWrapper, PermissionDeniedException
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
		throws LocalExceptionWrapper, PermissionDeniedException
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

	public int getSessionTimeout(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getSessionTimeout", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		int i;
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
			int __ret = __is.readInt();
			__is.endReadEncaps();
			i = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return i;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
