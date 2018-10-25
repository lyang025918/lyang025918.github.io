// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LiteralPatternConverter.java

package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class LiteralPatternConverter extends LoggingEventPatternConverter
{

	private final String literal;

	public LiteralPatternConverter(String literal)
	{
		super("Literal", "literal");
		this.literal = literal;
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		toAppendTo.append(literal);
	}

	public void format(Object obj, StringBuffer toAppendTo)
	{
		toAppendTo.append(literal);
	}
}
