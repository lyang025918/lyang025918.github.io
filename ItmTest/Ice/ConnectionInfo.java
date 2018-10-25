// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionInfo.java

package Ice;


public class ConnectionInfo
{

	public boolean incoming;
	public String adapterName;

	public ConnectionInfo()
	{
	}

	public ConnectionInfo(boolean incoming, String adapterName)
	{
		this.incoming = incoming;
		this.adapterName = adapterName;
	}
}
