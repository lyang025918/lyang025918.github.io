// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Version.java

package org.apache.lucene.util;

import java.util.Locale;

public final class Version extends Enum
{

	/**
	 * @deprecated Field LUCENE_30 is deprecated
	 */
	public static final Version LUCENE_30;
	/**
	 * @deprecated Field LUCENE_31 is deprecated
	 */
	public static final Version LUCENE_31;
	/**
	 * @deprecated Field LUCENE_32 is deprecated
	 */
	public static final Version LUCENE_32;
	/**
	 * @deprecated Field LUCENE_33 is deprecated
	 */
	public static final Version LUCENE_33;
	/**
	 * @deprecated Field LUCENE_34 is deprecated
	 */
	public static final Version LUCENE_34;
	/**
	 * @deprecated Field LUCENE_35 is deprecated
	 */
	public static final Version LUCENE_35;
	/**
	 * @deprecated Field LUCENE_36 is deprecated
	 */
	public static final Version LUCENE_36;
	public static final Version LUCENE_40;
	/**
	 * @deprecated Field LUCENE_CURRENT is deprecated
	 */
	public static final Version LUCENE_CURRENT;
	private static final Version $VALUES[];

	public static Version[] values()
	{
		return (Version[])$VALUES.clone();
	}

	public static Version valueOf(String name)
	{
		return (Version)Enum.valueOf(org/apache/lucene/util/Version, name);
	}

	private Version(String s, int i)
	{
		super(s, i);
	}

	public boolean onOrAfter(Version other)
	{
		return compareTo(other) >= 0;
	}

	public static Version parseLeniently(String version)
	{
		String parsedMatchVersion = version.toUpperCase(Locale.ROOT);
		return valueOf(parsedMatchVersion.replaceFirst("^(\\d)\\.(\\d)$", "LUCENE_$1$2"));
	}

	static 
	{
		LUCENE_30 = new Version("LUCENE_30", 0);
		LUCENE_31 = new Version("LUCENE_31", 1);
		LUCENE_32 = new Version("LUCENE_32", 2);
		LUCENE_33 = new Version("LUCENE_33", 3);
		LUCENE_34 = new Version("LUCENE_34", 4);
		LUCENE_35 = new Version("LUCENE_35", 5);
		LUCENE_36 = new Version("LUCENE_36", 6);
		LUCENE_40 = new Version("LUCENE_40", 7);
		LUCENE_CURRENT = new Version("LUCENE_CURRENT", 8);
		$VALUES = (new Version[] {
			LUCENE_30, LUCENE_31, LUCENE_32, LUCENE_33, LUCENE_34, LUCENE_35, LUCENE_36, LUCENE_40, LUCENE_CURRENT
		});
	}
}
