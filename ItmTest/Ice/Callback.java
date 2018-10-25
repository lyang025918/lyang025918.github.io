// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback.java

package Ice;

import IceInternal.CallbackBase;

// Referenced classes of package Ice:
//			AsyncResult

public abstract class Callback extends CallbackBase
{

	public Callback()
	{
	}

	public abstract void completed(AsyncResult asyncresult);

	public void sent(AsyncResult asyncresult)
	{
	}

	public final void __completed(AsyncResult r)
	{
		completed(r);
	}

	public final void __sent(AsyncResult r)
	{
		sent(r);
	}
}
