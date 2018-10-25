// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AMD_LocatorRegistry_setReplicatedAdapterDirectProxy.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import IceInternal.IncomingAsync;

// Referenced classes of package Ice:
//			AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException, AMD_LocatorRegistry_setReplicatedAdapterDirectProxy

final class _AMD_LocatorRegistry_setReplicatedAdapterDirectProxy extends IncomingAsync
	implements AMD_LocatorRegistry_setReplicatedAdapterDirectProxy
{

	public _AMD_LocatorRegistry_setReplicatedAdapterDirectProxy(Incoming in)
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
		catch (AdapterAlreadyActiveException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (AdapterNotFoundException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (InvalidReplicaGroupIdException __ex)
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
