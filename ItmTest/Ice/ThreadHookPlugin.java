// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadHookPlugin.java

package Ice;

import IceInternal.Instance;
import IceInternal.Util;

// Referenced classes of package Ice:
//			PluginInitializationException, Plugin, Communicator, ThreadNotification

public class ThreadHookPlugin
	implements Plugin
{

	public ThreadHookPlugin(Communicator communicator, ThreadNotification threadHook)
	{
		if (communicator == null)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "Communicator cannot be null";
			throw ex;
		} else
		{
			Instance instance = Util.getInstance(communicator);
			instance.setThreadHook(threadHook);
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
