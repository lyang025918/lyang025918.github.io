// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AMD_Router_createSessionFromSecureConnection.java

package Glacier2;

import Ice.LocalException;
import IceInternal.*;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, PermissionDeniedException, AMD_Router_createSessionFromSecureConnection, SessionPrxHelper, 
//			SessionPrx

final class _AMD_Router_createSessionFromSecureConnection extends IncomingAsync
	implements AMD_Router_createSessionFromSecureConnection
{

	public _AMD_Router_createSessionFromSecureConnection(Incoming in)
	{
		super(in);
	}

	public void ice_response(SessionPrx __ret)
	{
		if (__validateResponse(true))
		{
			try
			{
				BasicStream __os = __os();
				SessionPrxHelper.__write(__os, __ret);
			}
			catch (LocalException __ex)
			{
				ice_exception(__ex);
			}
			__response(true);
		}
	}

	public void ice_exception(Exception ex)
	{
		try
		{
			throw ex;
		}
		catch (CannotCreateSessionException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (PermissionDeniedException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (Exception __ex)
		{
			super.ice_exception(__ex);
		}
	}
}
