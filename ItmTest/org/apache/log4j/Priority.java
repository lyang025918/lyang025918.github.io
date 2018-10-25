// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Priority.java

package org.apache.log4j;


// Referenced classes of package org.apache.log4j:
//			Level

public class Priority
{

	transient int level;
	transient String levelStr;
	transient int syslogEquivalent;
	public static final int OFF_INT = 0x7fffffff;
	public static final int FATAL_INT = 50000;
	public static final int ERROR_INT = 40000;
	public static final int WARN_INT = 30000;
	public static final int INFO_INT = 20000;
	public static final int DEBUG_INT = 10000;
	public static final int ALL_INT = 0x80000000;
	/**
	 * @deprecated Field FATAL is deprecated
	 */
	public static final Priority FATAL = new Level(50000, "FATAL", 0);
	/**
	 * @deprecated Field ERROR is deprecated
	 */
	public static final Priority ERROR = new Level(40000, "ERROR", 3);
	/**
	 * @deprecated Field WARN is deprecated
	 */
	public static final Priority WARN = new Level(30000, "WARN", 4);
	/**
	 * @deprecated Field INFO is deprecated
	 */
	public static final Priority INFO = new Level(20000, "INFO", 6);
	/**
	 * @deprecated Field DEBUG is deprecated
	 */
	public static final Priority DEBUG = new Level(10000, "DEBUG", 7);

	protected Priority()
	{
		level = 10000;
		levelStr = "DEBUG";
		syslogEquivalent = 7;
	}

	protected Priority(int level, String levelStr, int syslogEquivalent)
	{
		this.level = level;
		this.levelStr = levelStr;
		this.syslogEquivalent = syslogEquivalent;
	}

	public boolean equals(Object o)
	{
		if (o instanceof Priority)
		{
			Priority r = (Priority)o;
			return level == r.level;
		} else
		{
			return false;
		}
	}

	public final int getSyslogEquivalent()
	{
		return syslogEquivalent;
	}

	public boolean isGreaterOrEqual(Priority r)
	{
		return level >= r.level;
	}

	/**
	 * @deprecated Method getAllPossiblePriorities is deprecated
	 */

	public static Priority[] getAllPossiblePriorities()
	{
		return (new Priority[] {
			FATAL, ERROR, Level.WARN, INFO, DEBUG
		});
	}

	public final String toString()
	{
		return levelStr;
	}

	public final int toInt()
	{
		return level;
	}

	/**
	 * @deprecated Method toPriority is deprecated
	 */

	public static Priority toPriority(String sArg)
	{
		return Level.toLevel(sArg);
	}

	/**
	 * @deprecated Method toPriority is deprecated
	 */

	public static Priority toPriority(int val)
	{
		return toPriority(val, DEBUG);
	}

	/**
	 * @deprecated Method toPriority is deprecated
	 */

	public static Priority toPriority(int val, Priority defaultPriority)
	{
		return Level.toLevel(val, (Level)defaultPriority);
	}

	/**
	 * @deprecated Method toPriority is deprecated
	 */

	public static Priority toPriority(String sArg, Priority defaultPriority)
	{
		return Level.toLevel(sArg, (Level)defaultPriority);
	}

}
