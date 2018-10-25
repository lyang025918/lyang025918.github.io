// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyslogQuietWriter.java

package org.apache.log4j.helpers;

import java.io.Writer;
import org.apache.log4j.spi.ErrorHandler;

// Referenced classes of package org.apache.log4j.helpers:
//			QuietWriter

public class SyslogQuietWriter extends QuietWriter
{

	int syslogFacility;
	int level;

	public SyslogQuietWriter(Writer writer, int syslogFacility, ErrorHandler eh)
	{
		super(writer, eh);
		this.syslogFacility = syslogFacility;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public void setSyslogFacility(int syslogFacility)
	{
		this.syslogFacility = syslogFacility;
	}

	public void write(String string)
	{
		super.write("<" + (syslogFacility | level) + ">" + string);
	}
}
