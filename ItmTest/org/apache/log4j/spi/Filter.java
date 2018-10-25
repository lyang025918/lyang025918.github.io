// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Filter.java

package org.apache.log4j.spi;


// Referenced classes of package org.apache.log4j.spi:
//			OptionHandler, LoggingEvent

public abstract class Filter
	implements OptionHandler
{

	/**
	 * @deprecated Field next is deprecated
	 */
	public Filter next;
	public static final int DENY = -1;
	public static final int NEUTRAL = 0;
	public static final int ACCEPT = 1;

	public Filter()
	{
	}

	public void activateOptions()
	{
	}

	public abstract int decide(LoggingEvent loggingevent);

	public void setNext(Filter next)
	{
		this.next = next;
	}

	public Filter getNext()
	{
		return next;
	}
}
