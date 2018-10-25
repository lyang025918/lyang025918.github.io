// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CallbackBase.java

package IceInternal;

import Ice.AsyncResult;

public abstract class CallbackBase
{

	public CallbackBase()
	{
	}

	public abstract void __completed(AsyncResult asyncresult);

	public abstract void __sent(AsyncResult asyncresult);
}
