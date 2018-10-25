// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NullEnumeration.java

package org.apache.log4j.helpers;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class NullEnumeration
	implements Enumeration
{

	private static final NullEnumeration instance = new NullEnumeration();

	private NullEnumeration()
	{
	}

	public static NullEnumeration getInstance()
	{
		return instance;
	}

	public boolean hasMoreElements()
	{
		return false;
	}

	public Object nextElement()
	{
		throw new NoSuchElementException();
	}

}
