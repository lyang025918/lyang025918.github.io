// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionInfo.java

package IceSSL;

import Ice.IPConnectionInfo;

public class ConnectionInfo extends IPConnectionInfo
{

	public String cipher;
	public String certs[];

	public ConnectionInfo()
	{
	}

	public ConnectionInfo(boolean incoming, String adapterName, String localAddress, int localPort, String remoteAddress, int remotePort, String cipher, 
			String certs[])
	{
		super(incoming, adapterName, localAddress, localPort, remoteAddress, remotePort);
		this.cipher = cipher;
		this.certs = certs;
	}
}
