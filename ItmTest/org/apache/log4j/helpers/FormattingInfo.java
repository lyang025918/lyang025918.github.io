// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FormattingInfo.java

package org.apache.log4j.helpers;


// Referenced classes of package org.apache.log4j.helpers:
//			LogLog

public class FormattingInfo
{

	int min;
	int max;
	boolean leftAlign;

	public FormattingInfo()
	{
		min = -1;
		max = 0x7fffffff;
		leftAlign = false;
	}

	void reset()
	{
		min = -1;
		max = 0x7fffffff;
		leftAlign = false;
	}

	void dump()
	{
		LogLog.debug("min=" + min + ", max=" + max + ", leftAlign=" + leftAlign);
	}
}
