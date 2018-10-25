// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_Router_addProxies.java

package Ice;


// Referenced classes of package Ice:
//			Callback_Router_addProxies, AMISentCallback, ObjectPrx, LocalException

public abstract class AMI_Router_addProxies extends Callback_Router_addProxies
{

	public AMI_Router_addProxies()
	{
	}

	public abstract void ice_response(ObjectPrx aobjectprx[]);

	public abstract void ice_exception(LocalException localexception);

	public final void response(ObjectPrx __ret[])
	{
		ice_response(__ret);
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
