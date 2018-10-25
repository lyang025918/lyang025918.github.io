// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMVersion.java

package com.iflytek.itm.util;


public final class ITMVersion extends Enum
{

	/**
	 * @deprecated Field ITM_4200 is deprecated
	 */
	public static final ITMVersion ITM_4200;
	/**
	 * @deprecated Field ITM_4300 is deprecated
	 */
	public static final ITMVersion ITM_4300;
	/**
	 * @deprecated Field ITM_4400 is deprecated
	 */
	public static final ITMVersion ITM_4400;
	/**
	 * @deprecated Field ITM_4500 is deprecated
	 */
	public static final ITMVersion ITM_4500;
	/**
	 * @deprecated Field ITM_4600 is deprecated
	 */
	public static final ITMVersion ITM_4600;
	/**
	 * @deprecated Field ITM_4700 is deprecated
	 */
	public static final ITMVersion ITM_4700;
	/**
	 * @deprecated Field ITM_4800 is deprecated
	 */
	public static final ITMVersion ITM_4800;
	public static final ITMVersion ITM_4900;
	/**
	 * @deprecated Field ITM_CURRENT is deprecated
	 */
	public static final ITMVersion ITM_CURRENT;
	private static final ITMVersion $VALUES[];

	public static ITMVersion[] values()
	{
		return (ITMVersion[])$VALUES.clone();
	}

	public static ITMVersion valueOf(String name)
	{
		return (ITMVersion)Enum.valueOf(com/iflytek/itm/util/ITMVersion, name);
	}

	private ITMVersion(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		ITM_4200 = new ITMVersion("ITM_4200", 0);
		ITM_4300 = new ITMVersion("ITM_4300", 1);
		ITM_4400 = new ITMVersion("ITM_4400", 2);
		ITM_4500 = new ITMVersion("ITM_4500", 3);
		ITM_4600 = new ITMVersion("ITM_4600", 4);
		ITM_4700 = new ITMVersion("ITM_4700", 5);
		ITM_4800 = new ITMVersion("ITM_4800", 6);
		ITM_4900 = new ITMVersion("ITM_4900", 7);
		ITM_CURRENT = new ITMVersion("ITM_CURRENT", 8);
		$VALUES = (new ITMVersion[] {
			ITM_4200, ITM_4300, ITM_4400, ITM_4500, ITM_4600, ITM_4700, ITM_4800, ITM_4900, ITM_CURRENT
		});
	}
}
