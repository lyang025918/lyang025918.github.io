// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutgoingAsyncMessageCallback.java

package IceInternal;

import Ice.ConnectionI;
import Ice.LocalException;

public interface OutgoingAsyncMessageCallback
{

	public abstract boolean __sent(ConnectionI connectioni);

	public abstract void __sent();

	public abstract void __finished(LocalException localexception, boolean flag);
}
