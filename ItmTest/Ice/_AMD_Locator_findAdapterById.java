// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AMD_Locator_findAdapterById.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import IceInternal.IncomingAsync;

// Referenced classes of package Ice:
//			LocalException, AdapterNotFoundException, AMD_Locator_findAdapterById, ObjectPrx

final class _AMD_Locator_findAdapterById extends IncomingAsync
	implements AMD_Locator_findAdapterById
{

	public _AMD_Locator_findAdapterById(Incoming in)
	{
		super(in);
	}

	public void ice_response(ObjectPrx __ret)
	{
		if (__validateResponse(true))
		{
			try
			{
				BasicStream __os = __os();
				__os.writeProxy(__ret);
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
		catch (AdapterNotFoundException __ex)
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
