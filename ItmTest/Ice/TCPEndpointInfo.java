// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TCPEndpointInfo.java

package Ice;


// Referenced classes of package Ice:
//			IPEndpointInfo

public abstract class TCPEndpointInfo extends IPEndpointInfo
{

	public TCPEndpointInfo()
	{
	}

	public TCPEndpointInfo(int timeout, boolean compress, String host, int port)
	{
		super(timeout, compress, host, port);
	}
}
