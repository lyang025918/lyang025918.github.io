// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _IdentitySetOperations.java

package Glacier2;

import Ice.Current;
import Ice.Identity;

public interface _IdentitySetOperations
{

	public abstract void add(Identity aidentity[], Current current);

	public abstract void remove(Identity aidentity[], Current current);

	public abstract Identity[] get(Current current);
}
