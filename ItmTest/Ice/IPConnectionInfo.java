// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IPConnectionInfo.java

package Ice;


// Referenced classes of package Ice:
//			ConnectionInfo

public class IPConnectionInfo extends ConnectionInfo
{

	public String localAddress;
	public int localPort;
	public String remoteAddress;
	public int remotePort;

	public IPConnectionInfo()
	{
	}

	public IPConnectionInfo(boolean incoming, String adapterName, String localAddress, int localPort, String remoteAddress, int remotePort)
	{
		super(incoming, adapterName);
		this.localAddress = localAddress;
		this.localPort = localPort;
		this.remoteAddress = remoteAddress;
		this.remotePort = remotePort;
	}
}
