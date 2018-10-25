// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DenyAllFilter.java

package org.apache.log4j.varia;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class DenyAllFilter extends Filter
{

	public DenyAllFilter()
	{
	}

	/**
	 * @deprecated Method getOptionStrings is deprecated
	 */

	public String[] getOptionStrings()
	{
		return null;
	}

	/**
	 * @deprecated Method setOption is deprecated
	 */

	public void setOption(String s, String s1)
	{
	}

	public int decide(LoggingEvent event)
	{
		return -1;
	}
}
