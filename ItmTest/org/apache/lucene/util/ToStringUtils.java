// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ToStringUtils.java

package org.apache.lucene.util;


public final class ToStringUtils
{

	private ToStringUtils()
	{
	}

	public static String boost(float boost)
	{
		if (boost != 1.0F)
			return (new StringBuilder()).append("^").append(Float.toString(boost)).toString();
		else
			return "";
	}

	public static void byteArray(StringBuilder buffer, byte bytes[])
	{
		for (int i = 0; i < bytes.length; i++)
		{
			buffer.append("b[").append(i).append("]=").append(bytes[i]);
			if (i < bytes.length - 1)
				buffer.append(',');
		}

	}
}
