// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProxyUnmarshalException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class ProxyUnmarshalException extends MarshalException
{

	public ProxyUnmarshalException()
	{
	}

	public ProxyUnmarshalException(Throwable cause)
	{
		super(cause);
	}

	public ProxyUnmarshalException(String reason)
	{
		super(reason);
	}

	public ProxyUnmarshalException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::ProxyUnmarshalException";
	}
}
