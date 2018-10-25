// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AMD_Object_ice_invoke.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import IceInternal.IncomingAsync;

// Referenced classes of package Ice:
//			LocalException, AMD_Object_ice_invoke

final class _AMD_Object_ice_invoke extends IncomingAsync
	implements AMD_Object_ice_invoke
{

	public _AMD_Object_ice_invoke(Incoming in)
	{
		super(in);
	}

	public void ice_response(boolean ok, byte outParams[])
	{
		if (__validateResponse(ok))
		{
			try
			{
				__os().writeBlob(outParams);
			}
			catch (LocalException ex)
			{
				__exception(ex);
				return;
			}
			__response(ok);
		}
	}
}
