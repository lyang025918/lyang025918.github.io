// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProtocolPluginFacade.java

package IceInternal;

import Ice.Communicator;

// Referenced classes of package IceInternal:
//			EndpointHostResolver, EndpointFactory

public interface ProtocolPluginFacade
{

	public abstract Communicator getCommunicator();

	public abstract EndpointHostResolver getEndpointHostResolver();

	public abstract int getProtocolSupport();

	public abstract String getDefaultHost();

	public abstract int getNetworkTraceLevel();

	public abstract String getNetworkTraceCategory();

	public abstract void addEndpointFactory(EndpointFactory endpointfactory);

	public abstract EndpointFactory getEndpointFactory(short word0);

	public abstract Class findClass(String s);
}
