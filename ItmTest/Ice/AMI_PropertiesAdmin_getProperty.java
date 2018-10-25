// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMI_PropertiesAdmin_getProperty.java

package Ice;


// Referenced classes of package Ice:
//			Callback_PropertiesAdmin_getProperty, AMISentCallback, LocalException

public abstract class AMI_PropertiesAdmin_getProperty extends Callback_PropertiesAdmin_getProperty
{

	public AMI_PropertiesAdmin_getProperty()
	{
	}

	public abstract void ice_response(String s);

	public abstract void ice_exception(LocalException localexception);

	public final void response(String __ret)
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
