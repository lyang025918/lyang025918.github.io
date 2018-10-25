// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UDPConnectionInfo.java

package Ice;


// Referenced classes of package Ice:
//			IPConnectionInfo

public class UDPConnectionInfo extends IPConnectionInfo
{

	public String mcastAddress;
	public int mcastPort;

	public UDPConnectionInfo()
	{
	}

	public UDPConnectionInfo(boolean incoming, String adapterName, String localAddress, int localPort, String remoteAddress, int remotePort, String mcastAddress, 
			int mcastPort)
	{
		super(incoming, adapterName, localAddress, localPort, remoteAddress, remotePort);
		this.mcastAddress = mcastAddress;
		this.mcastPort = mcastPort;
	}
}
