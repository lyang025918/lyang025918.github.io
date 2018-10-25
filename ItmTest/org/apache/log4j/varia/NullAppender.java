// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NullAppender.java

package org.apache.log4j.varia;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class NullAppender extends AppenderSkeleton
{

	private static NullAppender instance = new NullAppender();

	public NullAppender()
	{
	}

	public void activateOptions()
	{
	}

	/**
	 * @deprecated Method getInstance is deprecated
	 */

	public NullAppender getInstance()
	{
		return instance;
	}

	public static NullAppender getNullAppender()
	{
		return instance;
	}

	public void close()
	{
	}

	public void doAppend(LoggingEvent loggingevent)
	{
	}

	protected void append(LoggingEvent loggingevent)
	{
	}

	public boolean requiresLayout()
	{
		return false;
	}

}
