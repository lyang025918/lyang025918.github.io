// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryKey.java

package org.apache.log4j;


class CategoryKey
{

	String name;
	int hashCache;

	CategoryKey(String name)
	{
		this.name = name;
		hashCache = name.hashCode();
	}

	public final int hashCode()
	{
		return hashCache;
	}

	public final boolean equals(Object rArg)
	{
		if (this == rArg)
			return true;
		if (rArg != null && (org.apache.log4j.CategoryKey.class) == rArg.getClass())
			return name.equals(((CategoryKey)rArg).name);
		else
			return false;
	}
}
