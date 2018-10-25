// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProtocolPluginFacadeI.java

package IceInternal;

import Ice.Communicator;

// Referenced classes of package IceInternal:
//			ProtocolPluginFacade, Util, Instance, DefaultsAndOverrides, 
//			TraceLevels, EndpointFactoryManager, EndpointHostResolver, EndpointFactory

public class ProtocolPluginFacadeI
	implements ProtocolPluginFacade
{

	private Instance _instance;
	private Communicator _communicator;

	public ProtocolPluginFacadeI(Communicator communicator)
	{
		_communicator = communicator;
		_instance = Util.getInstance(communicator);
	}

	public Communicator getCommunicator()
	{
		return _communicator;
	}

	public EndpointHostResolver getEndpointHostResolver()
	{
		return _instance.endpointHostResolver();
	}

	public int getProtocolSupport()
	{
		return _instance.protocolSupport();
	}

	public String getDefaultHost()
	{
		return _instance.defaultsAndOverrides().defaultHost;
	}

	public int getNetworkTraceLevel()
	{
		return _instance.traceLevels().network;
	}

	public String getNetworkTraceCategory()
	{
		return _instance.traceLevels().networkCat;
	}

	public void addEndpointFactory(EndpointFactory factory)
	{
		_instance.endpointFactoryManager().add(factory);
	}

	public EndpointFactory getEndpointFactory(short type)
	{
		return _instance.endpointFactoryManager().get(type);
	}

	public Class findClass(String className)
	{
		return _instance.findClass(className);
	}
}
