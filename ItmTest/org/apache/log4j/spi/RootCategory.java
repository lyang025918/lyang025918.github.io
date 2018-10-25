// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RootCategory.java

package org.apache.log4j.spi;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

/**
 * @deprecated Class RootCategory is deprecated
 */

public final class RootCategory extends Logger
{

	public RootCategory(Level level)
	{
		super("root");
		setLevel(level);
	}

	public final Level getChainedLevel()
	{
		return level;
	}

	public final void setLevel(Level level)
	{
		if (level == null)
			LogLog.error("You have tried to set a null level to root.", new Throwable());
		else
			this.level = level;
	}

	public final void setPriority(Level level)
	{
		setLevel(level);
	}
}
