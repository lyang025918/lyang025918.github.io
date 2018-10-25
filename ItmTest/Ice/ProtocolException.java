// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProtocolException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class ProtocolException extends LocalException
{

	public String reason;

	public ProtocolException()
	{
	}

	public ProtocolException(Throwable cause)
	{
		super(cause);
	}

	public ProtocolException(String reason)
	{
		this.reason = reason;
	}

	public ProtocolException(String reason, Throwable cause)
	{
		super(cause);
		this.reason = reason;
	}

	public String ice_name()
	{
		return "Ice::ProtocolException";
	}
}
