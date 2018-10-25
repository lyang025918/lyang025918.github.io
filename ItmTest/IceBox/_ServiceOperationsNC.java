// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceOperationsNC.java

package IceBox;

import Ice.Communicator;

public interface _ServiceOperationsNC
{

	public abstract void start(String s, Communicator communicator, String as[]);

	public abstract void stop();
}
