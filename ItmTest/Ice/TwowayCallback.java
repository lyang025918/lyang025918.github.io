// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TwowayCallback.java

package Ice;

import IceInternal.CallbackBase;

// Referenced classes of package Ice:
//			AsyncResult, LocalException

public abstract class TwowayCallback extends CallbackBase
{

	public TwowayCallback()
	{
	}

	public abstract void exception(LocalException localexception);

	public void sent(boolean flag)
	{
	}

	public final void __sent(AsyncResult __result)
	{
		sent(__result.sentSynchronously());
	}
}
