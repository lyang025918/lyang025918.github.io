// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LevelMatchFilter.java

package org.apache.log4j.varia;

import org.apache.log4j.Level;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class LevelMatchFilter extends Filter
{

	boolean acceptOnMatch;
	Level levelToMatch;

	public LevelMatchFilter()
	{
		acceptOnMatch = true;
	}

	public void setLevelToMatch(String level)
	{
		levelToMatch = OptionConverter.toLevel(level, null);
	}

	public String getLevelToMatch()
	{
		return levelToMatch != null ? levelToMatch.toString() : null;
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
		if (levelToMatch == null)
			return 0;
		boolean matchOccured = false;
		if (levelToMatch.equals(event.getLevel()))
			matchOccured = true;
		if (matchOccured)
			return !acceptOnMatch ? -1 : 1;
		else
			return 0;
	}
}
