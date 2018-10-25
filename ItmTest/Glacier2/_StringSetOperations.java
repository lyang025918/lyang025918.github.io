// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _StringSetOperations.java

package Glacier2;

import Ice.Current;

public interface _StringSetOperations
{

	public abstract void add(String as[], Current current);

	public abstract void remove(String as[], Current current);

	public abstract String[] get(Current current);
}
