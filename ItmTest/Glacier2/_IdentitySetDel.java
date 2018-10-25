// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _IdentitySetDel.java

package Glacier2;

import Ice.Identity;
import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

public interface _IdentitySetDel
	extends _ObjectDel
{

	public abstract void add(Identity aidentity[], Map map)
		throws LocalExceptionWrapper;

	public abstract void remove(Identity aidentity[], Map map)
		throws LocalExceptionWrapper;

	public abstract Identity[] get(Map map)
		throws LocalExceptionWrapper;
}
