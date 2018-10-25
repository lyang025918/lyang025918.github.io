// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Service.java

package IceBox;

import Ice.Communicator;

public interface Service
{

	public abstract void start(String s, Communicator communicator, String as[]);

	public abstract void stop();
}
