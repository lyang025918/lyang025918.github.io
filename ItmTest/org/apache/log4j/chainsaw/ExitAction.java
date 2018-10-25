// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExitAction.java

package org.apache.log4j.chainsaw;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.apache.log4j.Logger;

class ExitAction extends AbstractAction
{

	private static final Logger LOG;
	public static final ExitAction INSTANCE = new ExitAction();

	private ExitAction()
	{
	}

	public void actionPerformed(ActionEvent aIgnore)
	{
		LOG.info("shutting down");
		System.exit(0);
	}

	static Class class$(String x0)
	{
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw (new NoClassDefFoundError()).initCause(x1);
	}

	static 
	{
		LOG = Logger.getLogger(org.apache.log4j.chainsaw.ExitAction.class);
	}
}
