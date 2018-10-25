// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UDPEndpointInfo.java

package Ice;


// Referenced classes of package Ice:
//			IPEndpointInfo

public abstract class UDPEndpointInfo extends IPEndpointInfo
{

	public byte protocolMajor;
	public byte protocolMinor;
	public byte encodingMajor;
	public byte encodingMinor;
	public String mcastInterface;
	public int mcastTtl;

	public UDPEndpointInfo()
	{
	}

	public UDPEndpointInfo(int timeout, boolean compress, String host, int port, byte protocolMajor, byte protocolMinor, byte encodingMajor, 
			byte encodingMinor, String mcastInterface, int mcastTtl)
	{
		super(timeout, compress, host, port);
		this.protocolMajor = protocolMajor;
		this.protocolMinor = protocolMinor;
		this.encodingMajor = encodingMajor;
		this.encodingMinor = encodingMinor;
		this.mcastInterface = mcastInterface;
		this.mcastTtl = mcastTtl;
	}
}
