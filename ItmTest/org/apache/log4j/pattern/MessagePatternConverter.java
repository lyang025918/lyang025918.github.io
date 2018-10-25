// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MessagePatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class MessagePatternConverter extends LoggingEventPatternConverter
{

	private static final MessagePatternConverter INSTANCE = new MessagePatternConverter();

	private MessagePatternConverter()
	{
		super("Message", "message");
	}

	public static MessagePatternConverter newInstance(String options[])
	{
		return INSTANCE;
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		toAppendTo.append(event.getRenderedMessage());
	}

}
