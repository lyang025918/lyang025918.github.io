// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleLayout.java

package org.apache.log4j;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			Layout, Level

public class SimpleLayout extends Layout
{

	StringBuffer sbuf;

	public SimpleLayout()
	{
		sbuf = new StringBuffer(128);
	}

	public void activateOptions()
	{
	}

	public String format(LoggingEvent event)
	{
		sbuf.setLength(0);
		sbuf.append(event.getLevel().toString());
		sbuf.append(" - ");
		sbuf.append(event.getRenderedMessage());
		sbuf.append(LINE_SEP);
		return sbuf.toString();
	}

	public boolean ignoresThrowable()
	{
		return true;
	}
}
