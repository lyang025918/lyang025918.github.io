// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AMD_LocatorRegistry_setServerProcessProxy.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import IceInternal.IncomingAsync;

// Referenced classes of package Ice:
//			ServerNotFoundException, AMD_LocatorRegistry_setServerProcessProxy

final class _AMD_LocatorRegistry_setServerProcessProxy extends IncomingAsync
	implements AMD_LocatorRegistry_setServerProcessProxy
{

	public _AMD_LocatorRegistry_setServerProcessProxy(Incoming in)
	{
		super(in);
	}

	public void ice_response()
	{
		if (__validateResponse(true))
			__response(true);
	}

	public void ice_exception(Exception ex)
	{
		try
		{
			throw ex;
		}
		catch (ServerNotFoundException __ex)
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
