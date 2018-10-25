// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PluginFactory.java

package IceSSL;

import Ice.Communicator;
import Ice.Plugin;

// Referenced classes of package IceSSL:
//			PluginI

public class PluginFactory
	implements Ice.PluginFactory
{

	public PluginFactory()
	{
	}

	public Plugin create(Communicator communicator, String name, String args[])
	{
		return new PluginI(communicator);
	}
}
