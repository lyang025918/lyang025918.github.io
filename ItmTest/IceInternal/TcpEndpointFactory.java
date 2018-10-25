// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TcpEndpointFactory.java

package IceInternal;


// Referenced classes of package IceInternal:
//			TcpEndpointI, EndpointFactory, Instance, EndpointI, 
//			BasicStream

final class TcpEndpointFactory
	implements EndpointFactory
{

	private Instance _instance;

	TcpEndpointFactory(Instance instance)
	{
		_instance = instance;
	}

	public short type()
	{
		return 1;
	}

	public String protocol()
	{
		return "tcp";
	}

	public EndpointI create(String str, boolean oaEndpoint)
	{
		return new TcpEndpointI(_instance, str, oaEndpoint);
	}

	public EndpointI read(BasicStream s)
	{
		return new TcpEndpointI(s);
	}

	public void destroy()
	{
		_instance = null;
	}
}
