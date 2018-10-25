// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DNSException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class DNSException extends LocalException
{

	public int error;
	public String host;

	public DNSException()
	{
	}

	public DNSException(Throwable cause)
	{
		super(cause);
	}

	public DNSException(int error, String host)
	{
		this.error = error;
		this.host = host;
	}

	public DNSException(int error, String host, Throwable cause)
	{
		super(cause);
		this.error = error;
		this.host = host;
	}

	public String ice_name()
	{
		return "Ice::DNSException";
	}
}
