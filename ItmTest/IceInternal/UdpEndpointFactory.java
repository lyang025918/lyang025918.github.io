// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UdpEndpointFactory.java

package IceInternal;


// Referenced classes of package IceInternal:
//			UdpEndpointI, EndpointFactory, Instance, EndpointI, 
//			BasicStream

final class UdpEndpointFactory
	implements EndpointFactory
{

	private Instance _instance;

	UdpEndpointFactory(Instance instance)
	{
		_instance = instance;
	}

	public short type()
	{
		return 3;
	}

	public String protocol()
	{
		return "udp";
	}

	public EndpointI create(String str, boolean oaEndpoint)
	{
		return new UdpEndpointI(_instance, str, oaEndpoint);
	}

	public EndpointI read(BasicStream s)
	{
		return new UdpEndpointI(s);
	}

	public void destroy()
	{
		_instance = null;
	}
}
