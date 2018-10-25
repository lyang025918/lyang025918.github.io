// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BridgePatternConverter.java

package org.apache.log4j.pattern;

import java.util.*;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter, FormattingInfo, LiteralPatternConverter, PatternParser

public final class BridgePatternConverter extends PatternConverter
{

	private LoggingEventPatternConverter patternConverters[];
	private FormattingInfo patternFields[];
	private boolean handlesExceptions;

	public BridgePatternConverter(String pattern)
	{
		next = null;
		handlesExceptions = false;
		List converters = new ArrayList();
		List fields = new ArrayList();
		java.util.Map converterRegistry = null;
		PatternParser.parse(pattern, converters, fields, converterRegistry, PatternParser.getPatternLayoutRules());
		patternConverters = new LoggingEventPatternConverter[converters.size()];
		patternFields = new FormattingInfo[converters.size()];
		int i = 0;
		Iterator converterIter = converters.iterator();
		Iterator fieldIter = fields.iterator();
		while (converterIter.hasNext()) 
		{
			Object converter = converterIter.next();
			if (converter instanceof LoggingEventPatternConverter)
			{
				patternConverters[i] = (LoggingEventPatternConverter)converter;
				handlesExceptions |= patternConverters[i].handlesThrowable();
			} else
			{
				patternConverters[i] = new LiteralPatternConverter("");
			}
			if (fieldIter.hasNext())
				patternFields[i] = (FormattingInfo)fieldIter.next();
			else
				patternFields[i] = FormattingInfo.getDefault();
			i++;
		}
	}

	protected String convert(LoggingEvent event)
	{
		StringBuffer sbuf = new StringBuffer();
		format(sbuf, event);
		return sbuf.toString();
	}

	public void format(StringBuffer sbuf, LoggingEvent e)
	{
		for (int i = 0; i < patternConverters.length; i++)
		{
			int startField = sbuf.length();
			patternConverters[i].format(e, sbuf);
			patternFields[i].format(startField, sbuf);
		}

	}

	public boolean ignoresThrowable()
	{
		return !handlesExceptions;
	}
}
