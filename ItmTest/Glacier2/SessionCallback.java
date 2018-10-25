// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionCallback.java

package Glacier2;


// Referenced classes of package Glacier2:
//			SessionNotExistException, SessionHelper

public interface SessionCallback
{

	public abstract void createdCommunicator(SessionHelper sessionhelper);

	public abstract void connected(SessionHelper sessionhelper)
		throws SessionNotExistException;

	public abstract void disconnected(SessionHelper sessionhelper);

	public abstract void connectFailed(SessionHelper sessionhelper, Throwable throwable);
}
