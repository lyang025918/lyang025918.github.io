// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceObserverOperations.java

package IceBox;

import Ice.Current;

public interface _ServiceObserverOperations
{

	public abstract void servicesStarted(String as[], Current current);

	public abstract void servicesStopped(String as[], Current current);
}
