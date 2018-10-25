// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointParseException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class EndpointParseException extends LocalException
{

	public String str;

	public EndpointParseException()
	{
	}

	public EndpointParseException(Throwable cause)
	{
		super(cause);
	}

	public EndpointParseException(String str)
	{
		this.str = str;
	}

	public EndpointParseException(String str, Throwable cause)
	{
		super(cause);
		this.str = str;
	}

	public String ice_name()
	{
		return "Ice::EndpointParseException";
	}
}
