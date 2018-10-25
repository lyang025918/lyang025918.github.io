// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_Process_shutdown.java

package Ice;


// Referenced classes of package Ice:
//			Callback_Process_shutdown, AMISentCallback, LocalException

public abstract class AMI_Process_shutdown extends Callback_Process_shutdown
{

	public AMI_Process_shutdown()
	{
	}

	public abstract void ice_response();

	public abstract void ice_exception(LocalException localexception);

	public final void response()
	{
		ice_response();
	}

	public final void exception(LocalException __ex)
	{
		ice_exception(__ex);
	}

	public final void sent(boolean sentSynchronously)
	{
		if (!sentSynchronously && (this instanceof AMISentCallback))
			((AMISentCallback)this).ice_sent();
	}
}
