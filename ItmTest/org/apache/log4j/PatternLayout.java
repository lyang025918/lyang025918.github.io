// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternLayout.java

package org.apache.log4j;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j:
//			Layout

public class PatternLayout extends Layout
{

	public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
	public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
	protected final int BUF_SIZE = 256;
	protected final int MAX_CAPACITY = 1024;
	private StringBuffer sbuf;
	private String pattern;
	private PatternConverter head;

	public PatternLayout()
	{
		this("%m%n");
	}

	public PatternLayout(String pattern)
	{
		sbuf = new StringBuffer(256);
		this.pattern = pattern;
		head = createPatternParser(pattern != null ? pattern : "%m%n").parse();
	}

	public void setConversionPattern(String conversionPattern)
	{
		pattern = conversionPattern;
		head = createPatternParser(conversionPattern).parse();
	}

	public String getConversionPattern()
	{
		return pattern;
	}

	public void activateOptions()
	{
	}

	public boolean ignoresThrowable()
	{
		return true;
	}

	protected PatternParser createPatternParser(String pattern)
	{
		return new PatternParser(pattern);
	}

	public String format(LoggingEvent event)
	{
		if (sbuf.capacity() > 1024)
			sbuf = new StringBuffer(256);
		else
			sbuf.setLength(0);
		for (PatternConverter c = head; c != null; c = c.next)
			c.format(sbuf, event);

		return sbuf.toString();
	}
}
