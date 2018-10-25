// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TCPConnectionInfo.java

package Ice;


// Referenced classes of package Ice:
//			IPConnectionInfo

public class TCPConnectionInfo extends IPConnectionInfo
{

	public TCPConnectionInfo()
	{
	}

	public TCPConnectionInfo(boolean incoming, String adapterName, String localAddress, int localPort, String remoteAddress, int remotePort)
	{
		super(incoming, adapterName, localAddress, localPort, remoteAddress, remotePort);
	}
}
