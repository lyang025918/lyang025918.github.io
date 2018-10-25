// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _StringSetDel.java

package Glacier2;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

public interface _StringSetDel
	extends _ObjectDel
{

	public abstract void add(String as[], Map map)
		throws LocalExceptionWrapper;

	public abstract void remove(String as[], Map map)
		throws LocalExceptionWrapper;

	public abstract String[] get(Map map)
		throws LocalExceptionWrapper;
}
