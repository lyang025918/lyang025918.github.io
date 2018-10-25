// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlobjectAsync.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;

// Referenced classes of package Ice:
//			ObjectImpl, _AMD_Object_ice_invoke, AMD_Object_ice_invoke, DispatchStatus, 
//			Current

public abstract class BlobjectAsync extends ObjectImpl
{

	public BlobjectAsync()
	{
	}

	public abstract void ice_invoke_async(AMD_Object_ice_invoke amd_object_ice_invoke, byte abyte0[], Current current);

	public DispatchStatus __dispatch(Incoming in, Current current)
	{
		BasicStream is = in.is();
		is.startReadEncaps();
		int sz = is.getReadEncapsSize();
		byte inParams[] = is.readBlob(sz);
		is.endReadEncaps();
		AMD_Object_ice_invoke cb = new _AMD_Object_ice_invoke(in);
		try
		{
			ice_invoke_async(cb, inParams, current);
		}
		catch (Exception ex)
		{
			cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}
}
