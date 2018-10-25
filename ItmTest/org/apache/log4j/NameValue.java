// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyConfigurator.java

package org.apache.log4j;


class NameValue
{

	String key;
	String value;

	public NameValue(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	public String toString()
	{
		return key + "=" + value;
	}
}
