// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadLocalMap.java

package org.apache.log4j.helpers;

import java.util.Hashtable;

public final class ThreadLocalMap extends InheritableThreadLocal
{

	public ThreadLocalMap()
	{
	}

	public final Object childValue(Object parentValue)
	{
		Hashtable ht = (Hashtable)parentValue;
		if (ht != null)
			return ht.clone();
		else
			return null;
	}
}
