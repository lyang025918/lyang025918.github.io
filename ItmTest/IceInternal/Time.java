// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Time.java

package IceInternal;


public final class Time
{

	public Time()
	{
	}

	public static long currentMonotonicTimeMillis()
	{
		return System.nanoTime() / 0xf4240L;
	}
}
