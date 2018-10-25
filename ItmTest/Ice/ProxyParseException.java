// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProxyParseException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class ProxyParseException extends LocalException
{

	public String str;

	public ProxyParseException()
	{
	}

	public ProxyParseException(Throwable cause)
	{
		super(cause);
	}

	public ProxyParseException(String str)
	{
		this.str = str;
	}

	public ProxyParseException(String str, Throwable cause)
	{
		super(cause);
		this.str = str;
	}

	public String ice_name()
	{
		return "Ice::ProxyParseException";
	}
}
