// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PermissionsVerifierDel.java

package Glacier2;

import Ice.StringHolder;
import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

public interface _PermissionsVerifierDel
	extends _ObjectDel
{

	public abstract boolean checkPermissions(String s, String s1, StringHolder stringholder, Map map)
		throws LocalExceptionWrapper;
}
