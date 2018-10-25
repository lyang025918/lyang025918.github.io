// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MathUtil.java

package org.apache.lucene.util;


public final class MathUtil
{

	private MathUtil()
	{
	}

	public static int log(long x, int base)
	{
		if (base <= 1)
			throw new IllegalArgumentException("base must be > 1");
		int ret;
		for (ret = 0; x >= (long)base; ret++)
			x /= base;

		return ret;
	}
}
