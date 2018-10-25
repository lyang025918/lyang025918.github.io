// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringMatchFilter.java

package org.apache.log4j.varia;

import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class StringMatchFilter extends Filter
{

	/**
	 * @deprecated Field STRING_TO_MATCH_OPTION is deprecated
	 */
	public static final String STRING_TO_MATCH_OPTION = "StringToMatch";
	/**
	 * @deprecated Field ACCEPT_ON_MATCH_OPTION is deprecated
	 */
	public static final String ACCEPT_ON_MATCH_OPTION = "AcceptOnMatch";
	boolean acceptOnMatch;
	String stringToMatch;

	public StringMatchFilter()
	{
		acceptOnMatch = true;
	}

	/**
	 * @deprecated Method getOptionStrings is deprecated
	 */

	public String[] getOptionStrings()
	{
		return (new String[] {
			"StringToMatch", "AcceptOnMatch"
		});
	}

	/**
	 * @deprecated Method setOption is deprecated
	 */

	public void setOption(String key, String value)
	{
		if (key.equalsIgnoreCase("StringToMatch"))
			stringToMatch = value;
		else
		if (key.equalsIgnoreCase("AcceptOnMatch"))
			acceptOnMatch = OptionConverter.toBoolean(value, acceptOnMatch);
	}

	public void setStringToMatch(String s)
	{
		stringToMatch = s;
	}

	public String getStringToMatch()
	{
		return stringToMatch;
	}

	public void setAcceptOnMatch(boolean acceptOnMatch)
	{
		this.acceptOnMatch = acceptOnMatch;
	}

	public boolean getAcceptOnMatch()
	{
		return acceptOnMatch;
	}

	public int decide(LoggingEvent event)
	{
		String msg = event.getRenderedMessage();
		if (msg == null || stringToMatch == null)
			return 0;
		if (msg.indexOf(stringToMatch) == -1)
			return 0;
		return !acceptOnMatch ? -1 : 1;
	}
}
