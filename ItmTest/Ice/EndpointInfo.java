// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointInfo.java

package Ice;


public abstract class EndpointInfo
{

	public int timeout;
	public boolean compress;

	public abstract short type();

	public abstract boolean datagram();

	public abstract boolean secure();

	public EndpointInfo()
	{
	}

	public EndpointInfo(int timeout, boolean compress)
	{
		this.timeout = timeout;
		this.compress = compress;
	}
}
