// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringUtils.java

package org.apache.lucene.queryparser.flexible.core.util;


public final class StringUtils
{

	public StringUtils()
	{
	}

	public static String toString(Object obj)
	{
		if (obj != null)
			return obj.toString();
		else
			return null;
	}
}
