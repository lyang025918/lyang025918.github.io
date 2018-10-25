// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_Object_ice_flushBatchRequests.java

package Ice;


// Referenced classes of package Ice:
//			Callback_Object_ice_flushBatchRequests, AMISentCallback, LocalException

public abstract class AMI_Object_ice_flushBatchRequests extends Callback_Object_ice_flushBatchRequests
{

	public AMI_Object_ice_flushBatchRequests()
	{
	}

	public abstract void ice_exception(LocalException localexception);

	public final void exception(LocalException ex)
	{
		ice_exception(ex);
	}

	public final void sent(boolean sentSynchronously)
	{
		if (sentSynchronously && (this instanceof AMISentCallback))
			((AMISentCallback)this).ice_sent();
	}
}
