// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TTCCLayout.java

package org.apache.log4j;

import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			Level

public class TTCCLayout extends DateLayout
{

	private boolean threadPrinting;
	private boolean categoryPrefixing;
	private boolean contextPrinting;
	protected final StringBuffer buf;

	public TTCCLayout()
	{
		threadPrinting = true;
		categoryPrefixing = true;
		contextPrinting = true;
		buf = new StringBuffer(256);
		setDateFormat("RELATIVE", null);
	}

	public TTCCLayout(String dateFormatType)
	{
		threadPrinting = true;
		categoryPrefixing = true;
		contextPrinting = true;
		buf = new StringBuffer(256);
		setDateFormat(dateFormatType);
	}

	public void setThreadPrinting(boolean threadPrinting)
	{
		this.threadPrinting = threadPrinting;
	}

	public boolean getThreadPrinting()
	{
		return threadPrinting;
	}

	public void setCategoryPrefixing(boolean categoryPrefixing)
	{
		this.categoryPrefixing = categoryPrefixing;
	}

	public boolean getCategoryPrefixing()
	{
		return categoryPrefixing;
	}

	public void setContextPrinting(boolean contextPrinting)
	{
		this.contextPrinting = contextPrinting;
	}

	public boolean getContextPrinting()
	{
		return contextPrinting;
	}

	public String format(LoggingEvent event)
	{
		buf.setLength(0);
		dateFormat(buf, event);
		if (threadPrinting)
		{
			buf.append('[');
			buf.append(event.getThreadName());
			buf.append("] ");
		}
		buf.append(event.getLevel().toString());
		buf.append(' ');
		if (categoryPrefixing)
		{
			buf.append(event.getLoggerName());
			buf.append(' ');
		}
		if (contextPrinting)
		{
			String ndc = event.getNDC();
			if (ndc != null)
			{
				buf.append(ndc);
				buf.append(' ');
			}
		}
		buf.append("- ");
		buf.append(event.getRenderedMessage());
		buf.append(LINE_SEP);
		return buf.toString();
	}

	public boolean ignoresThrowable()
	{
		return true;
	}
}
