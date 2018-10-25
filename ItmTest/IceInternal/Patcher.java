// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Patcher.java

package IceInternal;

import Ice.Object;

public interface Patcher
{

	public abstract void patch(Ice.Object obj);

	public abstract String type();
}
