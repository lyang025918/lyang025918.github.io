// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IPEndpointInfo.java

package Ice;


// Referenced classes of package Ice:
//			EndpointInfo

public abstract class IPEndpointInfo extends EndpointInfo
{

	public String host;
	public int port;

	public IPEndpointInfo()
	{
	}

	public IPEndpointInfo(int timeout, boolean compress, String host, int port)
	{
		super(timeout, compress);
		this.host = host;
		this.port = port;
	}
}
