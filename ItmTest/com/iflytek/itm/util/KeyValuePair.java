// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeyValuePair.java

package com.iflytek.itm.util;


public class KeyValuePair
{

	private Object key;
	private Object value;

	public KeyValuePair()
	{
		key = null;
		value = null;
	}

	public KeyValuePair(Object key, Object value)
	{
		this.key = key;
		this.value = value;
	}

	public Object getKey()
	{
		return key;
	}

	public void setKey(Object key)
	{
		this.key = key;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
}
