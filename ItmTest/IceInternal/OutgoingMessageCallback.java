// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutgoingMessageCallback.java

package IceInternal;

import Ice.LocalException;

public interface OutgoingMessageCallback
{

	public abstract void sent(boolean flag);

	public abstract void finished(LocalException localexception, boolean flag);
}
