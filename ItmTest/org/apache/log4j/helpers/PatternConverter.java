// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternConverter.java

package org.apache.log4j.helpers;

import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.helpers:
//			FormattingInfo

public abstract class PatternConverter
{

	public PatternConverter next;
	int min;
	int max;
	boolean leftAlign;
	static String SPACES[] = {
		" ", "  ", "    ", "        ", "                ", "                                "
	};

	protected PatternConverter()
	{
		min = -1;
		max = 0x7fffffff;
		leftAlign = false;
	}

	protected PatternConverter(FormattingInfo fi)
	{
		min = -1;
		max = 0x7fffffff;
		leftAlign = false;
		min = fi.min;
		max = fi.max;
		leftAlign = fi.leftAlign;
	}

	protected abstract String convert(LoggingEvent loggingevent);

	public void format(StringBuffer sbuf, LoggingEvent e)
	{
		String s = convert(e);
		if (s == null)
		{
			if (0 < min)
				spacePad(sbuf, min);
			return;
		}
		int len = s.length();
		if (len > max)
			sbuf.append(s.substring(len - max));
		else
		if (len < min)
		{
			if (leftAlign)
			{
				sbuf.append(s);
				spacePad(sbuf, min - len);
			} else
			{
				spacePad(sbuf, min - len);
				sbuf.append(s);
			}
		} else
		{
			sbuf.append(s);
		}
	}

	public void spacePad(StringBuffer sbuf, int length)
	{
		for (; length >= 32; length -= 32)
			sbuf.append(SPACES[5]);

		for (int i = 4; i >= 0; i--)
			if ((length & 1 << i) != 0)
				sbuf.append(SPACES[i]);

	}

}
