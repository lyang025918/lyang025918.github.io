// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExternallyRolledFileAppender.java

package org.apache.log4j.varia;

import org.apache.log4j.RollingFileAppender;

// Referenced classes of package org.apache.log4j.varia:
//			HUP

public class ExternallyRolledFileAppender extends RollingFileAppender
{

	public static final String ROLL_OVER = "RollOver";
	public static final String OK = "OK";
	int port;
	HUP hup;

	public ExternallyRolledFileAppender()
	{
		port = 0;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public int getPort()
	{
		return port;
	}

	public void activateOptions()
	{
		super.activateOptions();
		if (port != 0)
		{
			if (hup != null)
				hup.interrupt();
			hup = new HUP(this, port);
			hup.setDaemon(true);
			hup.start();
		}
	}
}
