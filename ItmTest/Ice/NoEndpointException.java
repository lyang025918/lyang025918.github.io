// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoEndpointException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class NoEndpointException extends LocalException
{

	public String proxy;

	public NoEndpointException()
	{
	}

	public NoEndpointException(Throwable cause)
	{
		super(cause);
	}

	public NoEndpointException(String proxy)
	{
		this.proxy = proxy;
	}

	public NoEndpointException(String proxy, Throwable cause)
	{
		super(cause);
		this.proxy = proxy;
	}

	public String ice_name()
	{
		return "Ice::NoEndpointException";
	}
}
