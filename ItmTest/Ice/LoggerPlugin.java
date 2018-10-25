// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggerPlugin.java

package Ice;

import IceInternal.Instance;
import IceInternal.Util;

// Referenced classes of package Ice:
//			PluginInitializationException, Plugin, Communicator, Logger

public class LoggerPlugin
	implements Plugin
{

	public LoggerPlugin(Communicator communicator, Logger logger)
	{
		if (communicator == null)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "Communicator cannot be null";
			throw ex;
		}
		if (logger == null)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "Logger cannot be null";
			throw ex;
		} else
		{
			Instance instance = Util.getInstance(communicator);
			instance.setLogger(logger);
			return;
		}
	}

	public void initialize()
	{
	}

	public void destroy()
	{
	}
}
