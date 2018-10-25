// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _IdentitySetOperationsNC.java

package Glacier2;

import Ice.Identity;

public interface _IdentitySetOperationsNC
{

	public abstract void add(Identity aidentity[]);

	public abstract void remove(Identity aidentity[]);

	public abstract Identity[] get();
}
