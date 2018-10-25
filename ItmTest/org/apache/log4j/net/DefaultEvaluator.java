// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SMTPAppender.java

package org.apache.log4j.net;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

class DefaultEvaluator
	implements TriggeringEventEvaluator
{

	DefaultEvaluator()
	{
	}

	public boolean isTriggeringEvent(LoggingEvent event)
	{
		return event.getLevel().isGreaterOrEqual(Level.ERROR);
	}
}
