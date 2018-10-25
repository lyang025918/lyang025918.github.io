// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Appender.java

package org.apache.log4j;

import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			Layout

public interface Appender
{

	public abstract void addFilter(Filter filter);

	public abstract Filter getFilter();

	public abstract void clearFilters();

	public abstract void close();

	public abstract void doAppend(LoggingEvent loggingevent);

	public abstract String getName();

	public abstract void setErrorHandler(ErrorHandler errorhandler);

	public abstract ErrorHandler getErrorHandler();

	public abstract void setLayout(Layout layout);

	public abstract Layout getLayout();

	public abstract void setName(String s);

	public abstract boolean requiresLayout();
}
