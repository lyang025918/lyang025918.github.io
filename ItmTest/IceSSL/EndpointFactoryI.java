// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointFactoryI.java

package IceSSL;

import IceInternal.*;

// Referenced classes of package IceSSL:
//			EndpointI, Instance

final class EndpointFactoryI
	implements EndpointFactory
{

	private Instance _instance;

	EndpointFactoryI(Instance instance)
	{
		_instance = instance;
	}

	public short type()
	{
		return 2;
	}

	public String protocol()
	{
		return "ssl";
	}

	public EndpointI create(String str, boolean oaEndpoint)
	{
		return new IceSSL.EndpointI(_instance, str, oaEndpoint);
	}

	public EndpointI read(BasicStream s)
	{
		return new IceSSL.EndpointI(_instance, s);
	}

	public void destroy()
	{
		_instance = null;
	}
}
