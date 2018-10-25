// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointInfo.java

package IceSSL;

import Ice.IPEndpointInfo;

public abstract class EndpointInfo extends IPEndpointInfo
{

	public EndpointInfo()
	{
	}

	public EndpointInfo(int timeout, boolean compress, String host, int port)
	{
		super(timeout, compress, host, port);
	}
}
